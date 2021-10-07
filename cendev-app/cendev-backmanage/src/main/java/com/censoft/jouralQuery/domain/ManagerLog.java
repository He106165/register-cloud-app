package com.censoft.jouralQuery.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 操作日志对象 manager_log
 *
 * @author cendev
 * @date 2020-11-10
 */
public class ManagerLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** 操作人名称 */
    @Excel(name = "操作人名称")
    private String opUserName;

    /** 操作人 */
    @Excel(name = "操作人")
    private String opUserId;

    /** 操作时间 */
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date opTime;

    /** 操作人类型 */
    @Excel(name = "操作人类型")
    private String opUserType;

    /** 操作类型 */
    @Excel(name = "操作类型")
    private String opType;

    /** 操作模块 */
    @Excel(name = "操作模块")
    private String opModel;

    /** 操作内容 */
    @Excel(name = "操作内容")
    private String opContent;

    /** 操作IP */
    @Excel(name = "操作IP")
    private String opIp;

    /** 操作路劲 */
    @Excel(name = "操作路劲")
    private String opUrl;

    /** 操作地址 */
    @Excel(name = "操作地址")
    private String opLocation;

    /** 方法 */
    @Excel(name = "方法")
    private String method;

    /** 请求方式 */
    @Excel(name = "请求方式")
    private String requestMethod;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 消息 */
    @Excel(name = "消息")
    private String msg;


    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setOpUserName(String opUserName)
    {
        this.opUserName = opUserName;
    }

    public String getOpUserName()
    {
        return opUserName;
    }
    public void setOpUserId(String opUserId)
    {
        this.opUserId = opUserId;
    }

    public String getOpUserId()
    {
        return opUserId;
    }
    public void setOpTime(Date opTime)
    {
        this.opTime = opTime;
    }

    public Date getOpTime()
    {
        return opTime;
    }
    public void setOpUserType(String opUserType)
    {
        this.opUserType = opUserType;
    }

    public String getOpUserType()
    {
        return opUserType;
    }
    public void setOpType(String opType)
    {
        this.opType = opType;
    }

    public String getOpType()
    {
        return opType;
    }
    public void setOpModel(String opModel)
    {
        this.opModel = opModel;
    }

    public String getOpModel()
    {
        return opModel;
    }
    public void setOpContent(String opContent)
    {
        this.opContent = opContent;
    }

    public String getOpContent()
    {
        return opContent;
    }
    public void setOpIp(String opIp)
    {
        this.opIp = opIp;
    }

    public String getOpIp()
    {
        return opIp;
    }
    public void setOpUrl(String opUrl)
    {
        this.opUrl = opUrl;
    }

    public String getOpUrl()
    {
        return opUrl;
    }
    public void setOpLocation(String opLocation)
    {
        this.opLocation = opLocation;
    }

    public String getOpLocation()
    {
        return opLocation;
    }
    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getMethod()
    {
        return method;
    }
    public void setRequestMethod(String requestMethod)
    {
        this.requestMethod = requestMethod;
    }

    public String getRequestMethod()
    {
        return requestMethod;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public String getMsg()
    {
        return msg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("opUserName", getOpUserName())
            .append("opUserId", getOpUserId())
            .append("opTime", getOpTime())
            .append("opUserType", getOpUserType())
            .append("opType", getOpType())
            .append("opModel", getOpModel())
            .append("opContent", getOpContent())
            .append("opIp", getOpIp())
            .append("opUrl", getOpUrl())
            .append("opLocation", getOpLocation())
            .append("method", getMethod())
            .append("requestMethod", getRequestMethod())
            .append("status", getStatus())
            .append("msg", getMsg())
            .toString();
    }
}
