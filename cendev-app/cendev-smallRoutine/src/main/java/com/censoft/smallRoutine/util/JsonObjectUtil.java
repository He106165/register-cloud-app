package com.censoft.smallRoutine.util;

import java.io.Serializable;

public class JsonObjectUtil implements Serializable {
    private boolean success;
    private String message;

    public JsonObjectUtil(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public JsonObjectUtil() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "JsonObjectUtil{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}

