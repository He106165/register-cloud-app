package com.censoft.userregister.util;

/**
 * @创建人:wqgeng
 * @创建时间:2020-11-1710:39
 * @描述:自定义注册异常信息
 */
public class RegisterException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    public RegisterException() {
    }

    public RegisterException(String message) {
        super(message);
    }

    public RegisterException(int code ,String message) {
        super(message);
    }
}
