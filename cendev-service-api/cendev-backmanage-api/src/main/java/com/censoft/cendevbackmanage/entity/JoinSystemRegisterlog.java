package com.censoft.cendevbackmanage.entity;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 系统对接日志对象 join_system_registerlog
 *
 * @author cendev
 * @date 2020-10-20
 */
public class JoinSystemRegisterlog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** 应用系统唯一标识 */
    @Excel(name = "应用系统唯一标识")
    private String joinsysCode;

    /** 应用系统名称 */
    @Excel(name = "应用系统名称")
    private String joinsysName;

    /** 日志类型 */
    @Excel(name = "日志类型")
    private String sysmethodId;

    /** 接口名称 */
    @Excel(name = "接口名称")
    private String sysmethodName;

    /** 域名 */
    @Excel(name = "域名")
    private String realm;

    /** 参数 */
    @Excel(name = "参数")
    private String parameter;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 原因 */
    @Excel(name = "原因")
    private String opertionmes;

    /** 操作时间 */
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date opertionTime;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
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
    public void setSysmethodId(String sysmethodId)
    {
        this.sysmethodId = sysmethodId;
    }

    public String getSysmethodId()
    {
        return sysmethodId;
    }
    public void setSysmethodName(String sysmethodName)
    {
        this.sysmethodName = sysmethodName;
    }

    public String getSysmethodName()
    {
        return sysmethodName;
    }
    public void setRealm(String realm)
    {
        this.realm = realm;
    }

    public String getRealm()
    {
        return realm;
    }
    public void setParameter(String parameter)
    {
        this.parameter = parameter;
    }

    public String getParameter()
    {
        return parameter;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setOpertionmes(String opertionmes)
    {
        this.opertionmes = opertionmes;
    }

    public String getOpertionmes()
    {
        return opertionmes;
    }
    public void setOpertionTime(Date opertionTime)
    {
        this.opertionTime = opertionTime;
    }

    public Date getOpertionTime()
    {
        return opertionTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("joinsysCode", getJoinsysCode())
                .append("joinsysName", getJoinsysName())
                .append("sysmethodId", getSysmethodId())
                .append("sysmethodName", getSysmethodName())
                .append("realm", getRealm())
                .append("parameter", getParameter())
                .append("status", getStatus())
                .append("opertionmes", getOpertionmes())
                .append("opertionTime", getOpertionTime())
                .append("remark", getRemark())
                .toString();
    }
}
