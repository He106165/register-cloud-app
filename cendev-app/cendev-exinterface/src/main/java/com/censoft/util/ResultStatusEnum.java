package com.censoft.util;

import lombok.Getter;

/**
 * @author 郑军
 * @创建人: 杨文培
 * @创建时间: 2019/5/17 21:48
 * @描述: 返回状态码
 */
@Getter
public enum ResultStatusEnum {
    S_10001(10001,"微信唯一标识unionId不能为空"),
    S_10002(10002,"用户登录账号为空"),
    S_10003(10003,"密码错误，验证失败"),
    S_10004(10004,"绑定成功"),
    S_10005(10005,"绑定失败"),
    S_10006(10006,"用户类型未选择或出现未知错误"),
    S_10007(10007,"该用户不存在"),
    S_10008(10008,"手机号已经验证"),
    S_10009(10009,"邮箱已经验证"),
    S_10010(10010,"该证件号已经被绑定"),
    S_10011(10011,"社会信用码已经被绑定"),
    S_10012(10012,"邮箱已经绑定"),
    S_10013(10013,"用户类型未选择"),
    S_10014(10014,"参数不符"),
    S_10015(10015,"不包含登录的账号信息"),
    S_10016(10016,"用户类型错误"),
    S_10017(10017,"该微信已经绑定，请直接登录"),
    ;

    private Integer status;
    private String massage;

    public Integer getStatus() {
        return status;
    }
    public String getMassage() {
        return massage;
    }

    ResultStatusEnum(Integer status, String massage) {
        this.status = status;
        this.massage = massage;
    }}
