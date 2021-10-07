package com.censoft.wechatlogin.wechatUser.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;

/**
 * 微信信息对象 wechat_user_info
 * 
 * @author cendev
 * @date 2020-10-21
 */
public class WechatUserInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** 用户id */
    @Excel(name = "用户id")
    private String userId;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 省份 */
    @Excel(name = "省份")
    private String provice;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 健康 */
    @Excel(name = "健康")
    private String health;

    /** 版本 */
    @Excel(name = "版本")
    private Long version;

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
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setProvice(String provice) 
    {
        this.provice = provice;
    }

    public String getProvice() 
    {
        return provice;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setHealth(String health) 
    {
        this.health = health;
    }

    public String getHealth() 
    {
        return health;
    }
    public void setVersion(Long version) 
    {
        this.version = version;
    }

    public Long getVersion() 
    {
        return version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("nickName", getNickName())
            .append("sex", getSex())
            .append("provice", getProvice())
            .append("status", getStatus())
            .append("health", getHealth())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("version", getVersion())
            .toString();
    }
}
