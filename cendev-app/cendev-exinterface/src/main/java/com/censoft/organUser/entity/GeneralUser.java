package com.censoft.organUser.entity;

import java.io.Serializable;

public class GeneralUser implements Serializable {

        //id
        private String id;

        //name姓名
        private String name;

        //        cardType证件类型
        private String cardType证件类型;

        //    idNumber证件号
        private String idNumber证件号;

        //            phone手机
        private String phone;

        //    phoneIsCheck 手机是否实名通过
        private String phoneIsCheck;

        //    email邮箱
        private String email;

        //            emailIsCheck邮箱是否验证
        private String emailIsCheck;

        //    nationalIty国籍
        private String nationalIty;

        //    countryCode 国家编码（默认中国大陆）
        private String countryCode;

        //    PASSWORD密码
        private String PASSWORD;

        //    SYSTEMCODE:系统唯一标识
        private String SYSTEMCODE;





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardType证件类型() {
        return cardType证件类型;
    }

    public void setCardType证件类型(String cardType证件类型) {
        this.cardType证件类型 = cardType证件类型;
    }

    public String getIdNumber证件号() {
        return idNumber证件号;
    }

    public void setIdNumber证件号(String idNumber证件号) {
        this.idNumber证件号 = idNumber证件号;
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

    public String getNationalIty() {
        return nationalIty;
    }

    public void setNationalIty(String nationalIty) {
        this.nationalIty = nationalIty;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getSYSTEMCODE() {
        return SYSTEMCODE;
    }

    public void setSYSTEMCODE(String SYSTEMCODE) {
        this.SYSTEMCODE = SYSTEMCODE;
    }
}
