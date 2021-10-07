package com.censoft.generalUser.valid;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class GeneralUserVaild implements Serializable {



    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$",message = "用户名不能有特殊字符")
    @Size(min = 0,max = 50,message = "外派学号不能超过50位")
    //name姓名
    private String ovestuId;



    @NotBlank(message = "证件类型不能为空")
    @Size(min = 2,max = 4,message = "证件类型不正确")
    //        cardType证件类型
    private String cardType;



    @NotBlank(message = "证件号不能为空")
    @Size(max = 50,message = "证件号不正确")
    //    idNumber证件号
    private String idNumber;

    @Pattern(regexp = "[^%&',;=?$<>!@#^*()_\\-+:\\x22]+",message = "真实姓名不能有特殊字符")
    @NotBlank(message = "真实姓名不能为空")
    @Size(min = 0,max = 1000,message = "真实姓名不正确")
    //        cardType证件类型
    private String realName;




    @NotBlank(message = "手机号不能为空")
    @Size(min = 0,max = 20,message = "手机号不正确")
    //            phone手机
    private String phone;


    @Pattern(regexp = "^(?:0|[1-1]?|1)$",message = "手机号通过标识错误")
    //    phoneIsCheck 手机是否实名通过
    private String phoneIsCheck;

    @NotBlank(message = "邮箱不能为空")
    @Size(min = 0,max = 100,message = "邮箱不能超过100位")
    //    email邮箱
    private String email;

    @Pattern(regexp = "^(?:0|[1-1]?|1)$",message = "邮箱通过标识错误")
    //            emailIsCheck邮箱是否验证
    private String emailIsCheck;

    @Pattern(regexp = "^[A-Za-z]+$",message = "国籍格式不正确")
    @NotBlank(message = "国籍不能为空")
    @Size(min = 2,max = 10,message = "国籍不正确")
    //    nationalIty国籍
    private String nationality;

    //    countryCode 国家编码（默认中国大陆）
    private String countryCode;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$",message = "密码格式错误")
    //    PASSWORD密码
    private String password;

    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$",message = "系统标识有特殊字符")
    @NotBlank(message = "系统唯一标识不能为空")
    @Size(min = 12,max = 20,message = "系统唯一标识不正确")
    //    SYSTEMCODE:系统唯一标识
    private String sysuqid;
    @Pattern(regexp = "[^%&',;=?$<>!@#^*()_\\-+:\\x22]+",message = "外派单位格式不正确")
    @Size(max = 120)
    /*外派单位*/
    private String EP1;
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$",message = "个人昵称格式不正确")
    @NotBlank(message = "个人昵称不能为空")
    @Size(min = 0,max = 20,message = "个人昵称长度不能超过20位")
    //    nickname 个人昵称
    private String nickName;
    /*微信unionid*/
    private String unionId;
    // 留学身份
    private String mc;
    // 留学单位
    private String dwzwmc;
    // 学号
    private String xh;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getOvestuId() {
        return ovestuId;
    }

    public void setOvestuId(String ovestuId) {
        this.ovestuId = ovestuId;
    }

    public String getCardType() {
        return cardType;
    }

    public String getSysuqid() {
        return sysuqid;
    }

    public void setSysuqid(String sysuqid) {
        this.sysuqid = sysuqid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneIsCheck() {
        return phoneIsCheck;
    }

    public void setPhoneIsCheck(String phoneIsCheck) {
        this.phoneIsCheck = phoneIsCheck;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailIsCheck() {
        return emailIsCheck;
    }

    public void setEmailIsCheck(String emailIsCheck) {
        this.emailIsCheck = emailIsCheck;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getDwzwmc() {
        return dwzwmc;
    }

    public void setDwzwmc(String dwzwmc) {
        this.dwzwmc = dwzwmc;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }
}

