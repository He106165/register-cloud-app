package com.censoft.userregister.util;

import java.io.Serializable;

/**
 *@创建人:wqgeng
 *@创建时间:2020-11-1813:52
 *@描述:返回值
 */
public class ResultUtil<T> implements Serializable {
    private static final long serialVersionUID = 2120267584344923858L;
    private Integer status = 0;
    private String message = "Success";
    private T data = null;

    public ResultUtil() {
    }

    public ResultUtil(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ResultUtil<T> success() {
        return new ResultUtil();
    }

    public static <T> ResultUtil<T> success(T data) {
        ResultUtil<T> resultUtil = new ResultUtil();
        resultUtil.setData(data);
        return resultUtil;
    }

    public static <T> ResultUtil<T> result(String message) {
        ResultUtil<T> resultUtil = new ResultUtil();
        resultUtil.setMessage(message);
        return resultUtil;
    }

    public static <T> ResultUtil<T> result(Integer status, String message) {
        ResultUtil<T> resultUtil = new ResultUtil();
        resultUtil.setStatus(status);
        resultUtil.setMessage(message);
        return resultUtil;
    }

    public static <T> ResultUtil<T> result(Integer status, String message, T data) {
        ResultUtil<T> resultUtil = new ResultUtil();
        resultUtil.setStatus(status);
        resultUtil.setMessage(message);
        resultUtil.setData(data);
        return resultUtil;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        return "ResultUtil{status=" + this.status + ", message='" + this.message + '\'' + ", data=" + this.data + '}';
    }
}