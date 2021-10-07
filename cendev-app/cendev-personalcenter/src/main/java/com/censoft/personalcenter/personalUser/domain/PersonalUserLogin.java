package com.censoft.personalcenter.personalUser.domain;

import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 个人用户登录对象 personal_user_login
 *
 * @author cendev
 * @date 2020-10-21
 */
public class PersonalUserLogin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** 自增主键 */
    @Excel(name = "自增主键")
    private String userId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String name;

    /** 手机号是否验证 */
    @Excel(name = "手机号是否验证")
    private Long phoneIscheck;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 邮箱是否验证 */
    @Excel(name = "邮箱是否验证")
    private Long emailIscheck;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 证件号 */
    @Excel(name = "证件号")
    private String idnumber;

    /** 支付宝标识 */
    @Excel(name = "支付宝标识")
    private String alipayId;

    /** 微信标识 */
    @Excel(name = "微信标识")
    private String wecharId;

    /** 领英标识 */
    @Excel(name = "领英标识")
    private String tolinkedId;

    /** 是否可用 */
    @Excel(name = "是否可用")
    private String status;

    /** 版本 */
    @Excel(name = "版本")
    private Long version;

    /** 删除标记 */
    private Long delFlag;

    /**
     * 登陆方式 （登陆时会用到）
     * */
    private String loginType;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setPhoneIscheck(Long phoneIscheck)
    {
        this.phoneIscheck = phoneIscheck;
    }

    public Long getPhoneIscheck()
    {
        return phoneIscheck;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setEmailIscheck(Long emailIscheck)
    {
        this.emailIscheck = emailIscheck;
    }

    public Long getEmailIscheck()
    {
        return emailIscheck;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }
    public void setIdnumber(String idnumber)
    {
        this.idnumber = idnumber;
    }

    public String getIdnumber()
    {
        return idnumber;
    }
    public void setAlipayId(String alipayId)
    {
        this.alipayId = alipayId;
    }

    public String getAlipayId()
    {
        return alipayId;
    }
    public void setWecharId(String wecharId)
    {
        this.wecharId = wecharId;
    }

    public String getWecharId()
    {
        return wecharId;
    }
    public void setTolinkedId(String tolinkedId)
    {
        this.tolinkedId = tolinkedId;
    }

    public String getTolinkedId()
    {
        return tolinkedId;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
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

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("name", getName())
            .append("phoneIscheck", getPhoneIscheck())
            .append("phone", getPhone())
            .append("emailIscheck", getEmailIscheck())
            .append("email", getEmail())
            .append("password", getPassword())
            .append("idnumber", getIdnumber())
            .append("alipayId", getAlipayId())
            .append("wecharId", getWecharId())
            .append("tolinkedId", getTolinkedId())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("version", getVersion())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
