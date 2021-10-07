package com.censoft.organUser.valid;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class OrganUserVaild implements Serializable {
    @NotBlank(message = "机构用户类型不能为空")
    @Size(min=2,max = 4,message = "机构用户类型格式不正确")
    private String orgUserType;

    @Pattern(regexp = "[^%&',;=?$<>!@#^*()_\\-+:\\x22]+",message = "机构名称不能有特殊字符")
    @NotBlank(message = "机构名称不能为空")
    @Size(max = 200,message = "机构名称不能超过200位")
    private String enterPriseName;

    @Pattern(regexp = "[^%&',;=?$<>!@#^*()_\\-+:\\x22]+",message = "联系人姓名不能有特殊字符")
    @NotBlank(message = "联系人姓名不能为空")
    @Size(max = 1000,message = "联系人姓名不能超过1000位")
    private String hostPersonName;

    @NotBlank(message = "联系人手机号不能为空")
    @Size(max = 20,message = "联系人手机不能超过20位")
    private String hostPersonTelephone;

    @NotBlank(message = "联系人邮箱不能为空")
    @Size(max = 100,message = "联系人邮箱不能超过100位")
    private String hostPersonEmail;


    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$",message = "密码格式错误")
    private String password;

    @NotBlank(message = "系统标识不能为空")
    @Size(min=12,max = 20,message = "系统标识格式不正确")
    private String sysuqid;


    @Pattern(regexp = "[^%&',;=?$<>!@#^*()_\\-+:\\x22]+",message = "信用代码不正确")
    @Size(max = 100,message = "信用代码不能超过100位")
    private String unifiedSocialCredItCode;

    @Pattern(regexp = "[^%&',;=?$<>!@#^*()_\\-+:\\x22]+",message = "法人姓名不能有特殊字符")
    @Size(max = 1000,message = "法人姓名不能超过1000位")
    private String legalName;

    @Size(min=2,max = 4,message = "法人证件类型格式不正确")
    private String legalCardType;

    @Size(max = 50,message = "法人证件号不能超过50位")
    private String legalIdNumber;


    @Size(min=2,max = 20,message = "部门标识不正确")
    private String departmentCode;

    @Pattern(regexp = "[^%&',;=?$<>!@#^*()_\\-+:\\x22]+",message = "机构英文名不正确")
    @Size(max = 500,message = "机构英文名不能超过500位")
    private String englishName;

    @Size(min=2,max = 30,message = "国家格式不正确")
    private String country;

    @Size(max = 30,message = "省份不能超过30位")
    private String province;
    @Pattern(regexp = "[^%&',;=?$<>!@#^*()_\\-+:\\x22]+",message = "详细地址不正确")
    @Size(max = 500,message = "详细地址不能超过500位")
    private String address;

    @Size(min=2,max = 20,message = "大使馆标识格式不正确")
    private String consulateId;

    @Size(min=2,max = 4,message = "法人性质格式不正确")
    private String perManLine;
    @Size(min=2,max = 4,message = "法人类型格式不正确")
    private String legalType;

    private String unionId;

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSysuqid() {
        return sysuqid;
    }

    public void setSysuqid(String sysuqid) {
        this.sysuqid = sysuqid;
    }

    public String getUnifiedSocialCredItCode() {
        return unifiedSocialCredItCode;
    }

    public void setUnifiedSocialCredItCode(String unifiedSocialCredItCode) {
        this.unifiedSocialCredItCode = unifiedSocialCredItCode;
    }

    public String getLegalType() {
        return legalType;
    }

    public void setLegalType(String legalType) {
        this.legalType = legalType;
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
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
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

    public String getPerManLine() {
        return perManLine;
    }

    public void setPerManLine(String perManLine) {
        this.perManLine = perManLine;
    }


    public String getOrgUserType() {
        return orgUserType;
    }

    public void setOrgUserType(String orgUserType) {
        this.orgUserType = orgUserType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }



}

