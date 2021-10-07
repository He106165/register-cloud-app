package com.censoft.organUser.valid;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UpdateOrganUserVaild implements Serializable {

    @NotBlank(message = "系统标识不能为空")
    @Size(min=12,max = 20,message = "系统标识格式不正确")
    private String sysuqid;
    @Size(min=2,max = 20,message = "部门标识格式不正确")
    private String oldDepartmentCode;
    @Size(max = 100,message = "信用代码不能超过100位")
    private String oldunifiedSocialCredItCode;
    @Pattern(regexp = "^[^%&',;=?$<>!#^*()_\\-+:\\x22]+$",message = "用户名不能有特殊字符")
    @Size(max = 100,message = "用户名不能超过100位")
    private String oldhostEmail;


    @Pattern(regexp = "[^%&',;=?$<>!@#^*()_\\-+:\\x22]+",message = "机构名称不能有特殊字符")
    @Size(max = 200,message = "机构名称格式不正确")
    private String enterPriseName;

    @Pattern(regexp = "[^%&',;=?$<>!@#^*()_\\-+:\\x22]+",message = "联系人姓名不能有特殊字符")
    @Size(max = 1000,message = "联系人姓名格式不正确")
    private String hostPersonName;

    @Size(max = 20,message = "联系人手机格式不正确")
    private String hostPersonTelephone;

    @Size(max = 100,message = "联系人邮箱格式不正确")
    private String hostPersonEmail;


    @Size(min=2,max = 4,message = "法人类型不正确")
    private String legalType;
    @Size(min=2,max = 4,message = "法人性质格式不正确")
    private String perManLine;

    @Pattern(regexp = "[^%&',;=?$<>!@#^*()_\\-+:\\x22]+",message = "法人姓名不能有特殊字符")
    @Size(max = 1000,message = "法人姓名不能超过1000位")
    private String legalName;

    @Size(min=2,max = 4,message = "法人证件类型不正确")
    private String legalCardType;

    @Size(max = 50,message = "法人证件号不正确")
    private String legalIdNumber;


    @Size(min=2,max = 20,message = "部门标识不正确")
    private String DepartmentCode;


    @Pattern(regexp = "[^%&',;=?$<>!@#^*()_\\-+:\\x22]+",message = "机构英文名不正确")
    @Size(max = 500,message = "机构英文名不能超过500位")
    private String englishName;

    @Size(min=2,max = 30,message = "国家不正确")
    private String country;
    @Pattern(regexp = "[^%&',;=?$<>!@#^*()_\\-+:\\x22]+",message = "省份不正确")
    @Size(max = 30,message = "省份不能超过30位")
    private String province;
    @Pattern(regexp = "[^%&',;=?$<>!@#^*()_\\-+:\\x22]+",message = "详细地址不正确")
    @Size(max = 500,message = "详细地址不能超过500位")
    private String address;

    @Size(min=2,max = 20,message = "大使馆标识不正确")
    private String consulateId;


    public String getSysuqid() {
        return sysuqid;
    }

    public void setSysuqid(String sysuqid) {
        this.sysuqid = sysuqid;
    }

    public String getOldDepartmentCode() {
        return oldDepartmentCode;
    }

    public void setOldDepartmentCode(String oldDepartmentCode) {
        this.oldDepartmentCode = oldDepartmentCode;
    }

    public String getOldunifiedSocialCredItCode() {
        return oldunifiedSocialCredItCode;
    }

    public void setOldunifiedSocialCredItCode(String oldunifiedSocialCredItCode) {
        this.oldunifiedSocialCredItCode = oldunifiedSocialCredItCode;
    }

    public String getOldhostEmail() {
        return oldhostEmail;
    }

    public void setOldhostEmail(String oldhostEmail) {
        this.oldhostEmail = oldhostEmail;
    }

    public String getEnterPriseName() {
        return enterPriseName;
    }

    public void setEnterPriseName(String enterPriseName) {
        this.enterPriseName = enterPriseName;
    }

    public String getHostPersonName() {
        return hostPersonName;
    }

    public void setHostPersonName(String hostPersonName) {
        this.hostPersonName = hostPersonName;
    }

    public String getHostPersonTelephone() {
        return hostPersonTelephone;
    }

    public void setHostPersonTelephone(String hostPersonTelephone) {
        this.hostPersonTelephone = hostPersonTelephone;
    }

    public String getHostPersonEmail() {
        return hostPersonEmail;
    }

    public void setHostPersonEmail(String hostPersonEmail) {
        this.hostPersonEmail = hostPersonEmail;
    }


    public String getPerManLine() {
        return perManLine;
    }

    public void setPerManLine(String perManLine) {
        this.perManLine = perManLine;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalCardType() {
        return legalCardType;
    }

    public void setLegalCardType(String legalCardType) {
        this.legalCardType = legalCardType;
    }

    public String getLegalIdNumber() {
        return legalIdNumber;
    }

    public void setLegalIdNumber(String legalIdNumber) {
        this.legalIdNumber = legalIdNumber;
    }

    public String getDepartmentCode() {
        return DepartmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        DepartmentCode = departmentCode;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConsulateId() {
        return consulateId;
    }

    public void setConsulateId(String consulateId) {
        this.consulateId = consulateId;
    }

    public String getLegalType() {
        return legalType;
    }

    public void setLegalType(String legalType) {
        this.legalType = legalType;
    }
}

