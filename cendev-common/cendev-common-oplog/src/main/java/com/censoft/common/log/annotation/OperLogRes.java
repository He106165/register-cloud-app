package com.censoft.common.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.censoft.common.log.enums.BusinessType;
import com.censoft.common.log.enums.OperatorType;

/**
 * 自定义操作日志记录注解
 * 
 * @author hepengfei
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLogRes
{

    /**
     * 操作模块
     */
    public String opModel() default "";

    /**
     * 操作人类别
     */
    public OperatorType opUserType() default OperatorType.MANAGE;

    /**
     * 操作内容
     * */
    public String opContent() default "";

    /**
     * 操作类型
     * */
    public BusinessType opType() default BusinessType.OTHER;
}
