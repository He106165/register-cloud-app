package com.censoft.generalUser.entity;

import com.censoft.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 个人用户信息对象 personal_user_info
 *
 * @author cendev
 * @date 2020-10-20
 */
@Alias("PersonalUserInfoToRedis")
public class PersonalUserInfoToRedis extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String userId;

    /** 姓名 */
    private String userName;

    /** 证件类型 */
    private String cardType;

    /** 证件号 */
    private String cardNo;

    /** 国籍 */
    private String nationality;

    /** 出生日期 */
    private Date dateOfBirth;

    /** 性别 */
    private String gender;

    /** 邮箱 */
    private String email;

    /** 手机号 */
    private String phone;

    /** 头像地址 */
    private String avatarUrl;

    /** 状态 */
    private String status;

    /** 实名认证等级 */
    private String authlevel;

    /** 版本 */
    private Long version;

    /** 删除标记 */
    private Long delFlag;

    private String name;

    private String password;

    private String userType;

    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }
    public void setCardType(String cardType)
    {
        this.cardType = cardType;
    }

    public String getCardType()
    {
        return cardType;
    }
    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }

    public String getCardNo()
    {
        return cardNo;
    }
    public void setNationality(String nationality)
    {
        this.nationality = nationality;
    }

    public String getNationality()
    {
        return nationality;
    }
    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }
    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getGender()
    {
        return gender;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setAvatarUrl(String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl()
    {
        return avatarUrl;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setAuthlevel(String authlevel)
    {
        this.authlevel = authlevel;
    }

    public String getAuthlevel()
    {
        return authlevel;
    }
    public void setVersion(Long version)
    {
        this.version = version;
    }

    public Long getVersion()
    {
        return version;
    }
    public void setDelFlag(Long delFlag)
    {
        this.delFlag = delFlag;
    }

    public Long getDelFlag()
    {
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
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
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
