package com.censoft.generalUser.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 个人用户 --展示对象 -- 注册
 * @author wly
 */
public class SavePersonUserVO {

    private long id;

    @NotBlank
    private String personName;

    @Min(0)
    @Max(1)
    @NotNull
    private int sex;

    @Min(1)
    @Max(4)
    @NotNull
    private int licenseType;

    @NotBlank
    private String licenseNumber;

    @NotBlank
    private String telNumber;

    @NotBlank
    private String address;

    @NotBlank
    private String email;

    //get、set方法

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(int licenseType) {
        this.licenseType = licenseType;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    //toString 方法
}
