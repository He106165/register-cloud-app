package com.censoft.cendevbackmanage.entity;

import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 应用系统注册对象 join_system_info
 * 
 * @author cendev
 * @date 2020-10-20
 */
public class JoinSystemInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String joinsysId;

    /** 编码 */
    @Excel(name = "编码")
    private String joinsysCode;

    /** 系统名称 */
    @Excel(name = "系统名称")
    private String joinsysName;

    /** 域名 */
    @Excel(name = "域名")
    private String realmPlace;

    /** 系统地址 */
    @Excel(name = "系统地址")
    private String returnUrl;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 版本 */
    @Excel(name = "版本")
    private Long version;

    /** 删除标记 */
    private Long delFlag;

    public void setJoinsysId(String joinsysId) 
    {
        this.joinsysId = joinsysId;
    }

    public String getJoinsysId() 
    {
        return joinsysId;
    }
    public void setJoinsysCode(String joinsysCode) 
    {
        this.joinsysCode = joinsysCode;
    }

    public String getJoinsysCode() 
    {
        return joinsysCode;
    }
    public void setJoinsysName(String joinsysName) 
    {
        this.joinsysName = joinsysName;
    }

    public String getJoinsysName() 
    {
        return joinsysName;
    }
    public void setRealmPlace(String realmPlace) 
    {
        this.realmPlace = realmPlace;
    }

    public String getRealmPlace() 
    {
        return realmPlace;
    }
    public void setReturnUrl(String returnUrl) 
    {
        this.returnUrl = returnUrl;
    }

    public String getReturnUrl() 
    {
        return returnUrl;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("joinsysId", getJoinsysId())
            .append("joinsysCode", getJoinsysCode())
            .append("joinsysName", getJoinsysName())
            .append("realmPlace", getRealmPlace())
            .append("returnUrl", getReturnUrl())
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
