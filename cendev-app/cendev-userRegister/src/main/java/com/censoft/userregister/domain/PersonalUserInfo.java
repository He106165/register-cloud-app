package com.censoft.userregister.domain;

import com.censoft.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

/**
 * 个人用户信息对象 personal_user_info
 *
 * @author cendev
 * @date 2020-10-20
 */
@Data
public class PersonalUserInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private PersonalUserLogin personalUserLogin;
    /**
     * ID
     */
    private String userId;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Length(min = 1, max = 2000, message = "姓名长度在1到2000个字符！")
    @Pattern(regexp = "^[^`~!@#$^\"&*()+|{}''\\[\\].<>~！@#￥……&*（）——+|{}【】‘”“’]+$", message = "姓名输入内容包含非法字符")
    private String userName;

    /**
     * 证件类型
     */
    private String cardType;

    /**
     * 证件号
     */
    @NotBlank(message = "证件号不能为空")
    private String cardNo;

    /**
     * 国家/地区
     */
    @NotNull(message = "国家/地区不能为空")
    private String nationality;

    /**
     * 出生日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;

    /**
     * 性别
     */

    private String gender;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$", message = "手机号不符合规则")
    private String phone;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 状态
     */
    private String status;

    /**
     * 实名认证等级
     */
    private String authlevel;

    /**
     * 版本
     */
    private Long version;

    /**
     * 删除标记
     */
    private Long delFlag;

    private String name;

    private String password;
    /**
     * 临时字段区分密码是否加解密
     */
    private String isEncode;
    //选择的是手机号注册还是邮箱注册
    private String peFlag;
    // 个人昵称
    private String nickName;
    //是否需要实名
    private String isLabourReal;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIsEncode() {
        return isEncode;
    }

    public void setIsEncode(String isEncode) {
        this.isEncode = isEncode;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
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

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setAuthlevel(String authlevel) {
        this.authlevel = authlevel;
    }

    public String getAuthlevel() {
        return authlevel;
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("userName", getUserName())
                .append("cardType", getCardType())
                .append("cardNo", getCardNo())
                .append("nationality", getNationality())
                .append("dateOfBirth", getDateOfBirth())
                .append("gender", getGender())
                .append("email", getEmail())
                .append("phone", getPhone())
                .append("avatarUrl", getAvatarUrl())
                .append("status", getStatus())
                .append("authlevel", getAuthlevel())
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
