package com.censoft.generalUser.valid;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UpdateGeneralUserVaild implements Serializable {

    @Size(max = 50,message = "证件号不能超过50位")
    private String idNumber;
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$",message = "系统标识有特殊字符")
    @NotBlank(message = "系统唯一标识不能为空")
    @Size(min=12,max = 20,message = "系统唯一标识不正确")
    private String sysuqid;
    @Size(max = 20,message = "验证前手机号不能超过20位")
    private String oldPhone;

    @Size(max = 100,message = "验证前邮箱不能超过100位")
    private String oldEmail;

    @Size(min=2,max = 4,message = "婚姻状态格式不正确")
    private String maritalStatus;

    @Size(min=2,max = 10,message = "政治面貌格式不正确")
    private String politicsStatus;

    @Size(min=2,max = 20,message = "最后学历格式不正确")
    private String lastDegree;

    @Size(max = 200,message = "出生地不能超过200位")
    private String birthplace;

    @Size(max = 20,message = "修改后手机号不能超过20位")
    private String phone;
    @Size(max = 100,message = "修改后邮箱不能超过100位")
    private String email;
    @Pattern(regexp = "^(?:0|[1-1]?|1)$",message = "手机号通过标识错误")
    private String phoneIsCheck;
    @Pattern(regexp = "^(?:0|[1-1]?|1)$",message = "邮箱通过标识错误")
    private String emailIsCheck;
    @Pattern(regexp = "[^%&',;=?$<>!@#^*()_\\-+:\\x22]+",message = "外派单位格式不正确")
    @Size(max = 120)
    /*外派单位*/
    private String EP1;
    private String token;

    @Size(min = 2,max = 4,message = "证件类型不正确")
    //        cardType证件类型
    private String cardType;

    // 留学身份
    private String mc ;
    // 外派学号
    private String ovestuId ;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /*用户标识*/
    @NotBlank(message = "用户标识不能为空")
    private String userId;

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSysuqid() {
        return sysuqid;
    }

    public void setSysuqid(String sysuqid) {
        this.sysuqid = sysuqid;
    }

    public String getOldPhone() {
        return oldPhone;
    }

    public void setOldPhone(String oldPhone) {
        this.oldPhone = oldPhone;
    }

    public String getOldEmail() {
        return oldEmail;
    }

    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPoliticsStatus() {
        return politicsStatus;
    }

    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
    }

    public String getLastDegree() {
        return lastDegree;
    }

    public void setLastDegree(String lastDegree) {
        this.lastDegree = lastDegree;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneIsCheck() {
        return phoneIsCheck;
    }

    public void setPhoneIsCheck(String phoneIsCheck) {
        this.phoneIsCheck = phoneIsCheck;
    }

    public String getEmailIsCheck() {
        return emailIsCheck;
    }

    public void setEmailIsCheck(String emailIsCheck) {
        this.emailIsCheck = emailIsCheck;
    }

    public String getEP1() {
        return EP1;
    }

    public void setEP1(String EP1) {
        this.EP1 = EP1;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getOvestuId() {
        return ovestuId;
    }

    public void setOvestuId(String ovestuId) {
        this.ovestuId = ovestuId;
    }
}

