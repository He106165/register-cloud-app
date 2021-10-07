package com.censoft.common.utils;

/**
 * @Author: hepengfei
 * @Date: 2020/12/4 13:52
 * @Todo token的状态标识
 */
public enum TokenStatus {
    error("token错误",0),
    normal("token正常",1),
    willbedue("token将要过期",2),
    due("token已经过期",3),
    destroy("token已登出销毁",4);

    private String name;
    private int index;

    TokenStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

