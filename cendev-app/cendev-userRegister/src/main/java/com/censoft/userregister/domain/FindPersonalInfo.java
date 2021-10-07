package com.censoft.userregister.domain;

import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

/**
 * 找回个人用户信息对象 find_personal_info
 *
 * @author cendev
 * @date 2020-10-20
 */
public class FindPersonalInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Length(min = 1, max = 32, message = "主键长度在1到50个字符！")
    private String id;

    /**
     * 国籍
     */
    @Excel(name = "国籍")
    @Length(min = 1, max = 50, message = "国家/地区长度在1到50个字符！")
    private String nationality;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    @Length(min = 1, max = 2000, message = "姓名长度在1到2000个字符！")
    private String userName;

    /**
     * 证件类型
     */
    @Excel(name = "证件类型")
    @Length(min = 1, max = 50, message = "证件类型长度在1到50个字符！")
    private String cardType;

    /**
     * 证件号
     */
    @Excel(name = "证件号")
    @Length(min = 1, max = 200, message = "证件号长度在1到200个字符！")
    private String cardNo;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    @Length(min = 1, max = 50, message = "手机号长度在1到50个字符！")
    private String mobile;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    @Length(min = 1, max = 200, message = "邮箱长度在1到200个字符！")
    private String email;

    /**
     * 密码
     */
    @Excel(name = "密码")
    private String password;

    /**
     * 附件地址
     */
    @Excel(name = "附件地址")
    @Length(min = 1, max = 1000, message = "附件地址长度在1到1000个字符！")
    private String fileUrl;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;

    /**
     * 版本
     */
    @Excel(name = "版本")
    private Long version;

    /**
     * 删除标记
     */
    private Long delFlag;
    /**
     * 驳回理由
     */
    private String reasonRejection;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getVersion() {
        return version;
    }

    public void setDelFlag(Long delFlag) {
        this.delFlag = delFlag;
    }

    public Long getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("nationality", getNationality())
                .append("userName", getUserName())
                .append("cardType", getCardType())
                .append("cardNo", getCardNo())
                .append("mobile", getMobile())
                .append("email", getEmail())
                .append("password", getPassword())
                .append("fileUrl", getFileUrl())
                .append("status", getStatus())
                .append("remark", getRemark())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .append("version", getVersion())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
