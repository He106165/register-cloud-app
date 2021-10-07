package com.censoft.caUserLogin;

import com.censoft.common.core.domain.R;
import com.censoft.common.log.enums.OperatorType;
import com.censoft.otherlogin.feign.RemoteTokenServer;
import com.censoft.system.feign.RemoteUserService;
import com.sca.sign.AuthInfo;
import com.sca.sign.Authentication;
import com.sca.sign.SignException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CALoginVerifyGateWay
 * @Description CA登陆外网访问网关校验接口，传入参数为token；
 * @Author hepengfei
 * @Date 2021/3/29  15:50
 * @Version 1.0
 **/
@Controller
@RequestMapping("CALoginVerifyGW")
public class CALoginVerifyGateWay {

    @Autowired
    private RemoteTokenServer tokenService;

    @Autowired
    private RemoteUserService userService;

    @Value("${VerifyGateWayReturnUrl}")
    private String VerifyGateWayReturnUrl;

    @Value("${VerifyGateWayLoginUrl}")
    private String VerifyGateWayLoginUrl;


    @Autowired
    private CaUserService caUserService;

    /**
    * @Description  获取CA方传递的token，进行网关校验，然后解析获取证书名称，进行统一用户登陆，将登陆结果返回。
    * @Parm
    * @return
    **/
    @PostMapping("VerGateWay")
    public String  VerifyGateWay(HttpServletRequest request){
        // 统一返回的封装个数据
        Map result = new HashMap();
        // 统一用户生成的token
        String Access_Token ;
        // 获取传递过来的token
        String token;
        // Account 账号
        String account = "";
        // 类型
        int authType = -1;
        // 安全认证网关地址
        String IPAddress = "192.168.2.237";
        // 网关端口
        int port = 80;
        token = request.getParameter("token");

        Authentication auth = new Authentication(IPAddress, port);

        try {
            //取认证信息
            AuthInfo authInfo = auth.GetAuthInfo(token);
            if(authInfo != null) {
                authType = authInfo.getAuthType();
                if(authType == 1) {
                    //证书认证
                    String certInfo = authInfo.getUserInfo();

                    String item = "CN=";
                    String value = "";
                    int pos1 = certInfo.indexOf(item);
                    if(pos1 >= 0) {
                        int pos2 = certInfo.indexOf(",", pos1);
                        if(pos2 > 0) {
                            value = certInfo.substring(pos1 + item.length(), pos2);
                        } else {
                            value = certInfo.substring(pos1 + item.length());
                        }
                    }

                    account = value;
                } else {
                    //其他认证类型
                    account = authInfo.getUserInfo();
                }
                Access_Token = CALoginMethod(account);
                if(Access_Token == null){
                    return VerifyGateWayLoginUrl;
                }
                // 封装成功信息
//                result.put("code","200");
//                result.put("account",account);
//                result.put("token",token);
//                result.put("Access_Token",Access_Token);
//                result.put("authType",authType);
//                result.put("msg","认证成功");
            } else {
                //连接安全认证网关失败
                return "www.baidu.com";
            }
        } catch(SignException e) {
            //没有找到令牌所对应的认证信息
            return VerifyGateWayLoginUrl;
        }
        return VerifyGateWayReturnUrl + "?Access_Token="+Access_Token;
    }

    // 搬砖了，CA管理员登陆方法
    public String CALoginMethod( String userName ){
        // 查询内部用户
        JSONObject userinfo = JSONObject.fromObject(userService.selectSysUserByUsername(userName));
        OperatorType operatorType=OperatorType.INSIDE;
        if(userinfo.size() == 0) {
            return null;
        }
        //token 服务导航页面可能会跳转 注册系统，需要token
        String toekn= tokenService.getToken(userName);
        userinfo.put("userType",operatorType.toString());
        caUserService.saveToRides(toekn,userinfo,operatorType);
        return toekn;
    }
}
