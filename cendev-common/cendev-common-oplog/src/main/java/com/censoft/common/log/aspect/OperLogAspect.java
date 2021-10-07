package com.censoft.common.log.aspect;

import com.censoft.cendevbackmanage.entity.ManagerLog;
import com.censoft.common.constant.Constants;
import com.censoft.common.log.annotation.OperLogRes;
import com.censoft.common.log.enums.BusinessStatus;
import com.censoft.common.log.event.SysOperLogEvent;
import com.censoft.common.utils.AddressUtils;
import com.censoft.common.utils.IpUtils;
import com.censoft.common.utils.ServletUtils;
import com.censoft.common.utils.StringUtils;
import com.censoft.common.utils.spring.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 操作日志记录处理
 *
 */
@Aspect
@Slf4j
@Component
public class OperLogAspect
{
    // 配置织入点
    @Pointcut("@annotation(com.censoft.common.log.annotation.OperLogRes)")
    public void logPointCut()
    {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint)
    {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e)
    {
        handleLog(joinPoint, e);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e)
    {
        try
        {
            // 获得注解
            OperLogRes controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null)
            {
                return;
            }
            // *========数据库日志=========*//
            ManagerLog operLog = new ManagerLog();
            // 请求的地址
            HttpServletRequest request = ServletUtils.getRequest();
            String ip = IpUtils.getIpAddr(request);
            operLog.setOpIp(ip);
            operLog.setOpUrl(request.getRequestURI());
            operLog.setOpLocation(AddressUtils.getRealAddressByIP(ip));
            String username = request.getHeader(Constants.CURRENT_USERNAME);
            operLog.setOpUserName(username);
            //操作人类型
            String userType = request.getHeader("userType");
            operLog.setOpType(userType);
            operLog.setOpUserId(request.getHeader(Constants.CURRENT_ID));
            if (e != null)
            {
                operLog.setStatus(String.valueOf(BusinessStatus.FAIL.ordinal()));
                operLog.setMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }else{
                operLog.setStatus("0");
                operLog.setMsg("success");
            }

            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
         // 设置请求方式
            operLog.setRequestMethod(request.getMethod());
            // 处理设置注解上的参数
            Object[] args = joinPoint.getArgs();
            getControllerMethodDescription(controllerLog, operLog, args);
            // 发布事件
            SpringContextHolder.publishEvent(new SysOperLogEvent(operLog));
        }
        catch (Exception exp)
        {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(OperLogRes log, ManagerLog operLog, Object[] args) throws Exception
    {
        // 设置操作类型
        operLog.setOpType(log.opType().toString());
        // 设置操作人类别
//        operLog.setOpUserType(log.opUserType().toString());
        // 设置操作内容
        operLog.setOpContent(log.opContent());
        // 设置操作模块
        operLog.setOpModel(log.opModel());

    }

    /**
     *  获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(ManagerLog operLog, Object[] args) throws Exception
    {
        List<?> param = new ArrayList<>(Arrays.asList(args)).stream().filter(p -> !(p instanceof ServletResponse))
                .collect(Collectors.toList());
        log.debug("args:{}", param);
//        String params = JSON.toJSONString(param, true);
//        operLog.setOperParam(StringUtils.substring(params, 0, 2000));
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private OperLogRes getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null)
        {
            return method.getAnnotation(OperLogRes.class);
        }
        return null;
    }
}
