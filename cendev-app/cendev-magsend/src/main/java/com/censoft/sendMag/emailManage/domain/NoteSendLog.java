package com.censoft.sendMag.emailManage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 短信发送日志对象 note_send_log
 * 
 * @author cendev
 * @date 2020-10-21
 */
public class NoteSendLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 手机号 */
    @Excel(name = "手机号")
    private String reveicePhone;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 是否成功 */
    @Excel(name = "是否成功")
    private String status;

    /** 发送时间 */
    @Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date mesPlantime;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setReveicePhone(String reveicePhone) 
    {
        this.reveicePhone = reveicePhone;
    }

    public String getReveicePhone() 
    {
        return reveicePhone;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setMesPlantime(Date mesPlantime) 
    {
        this.mesPlantime = mesPlantime;
    }

    public Date getMesPlantime() 
    {
        return mesPlantime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("reveicePhone", getReveicePhone())
            .append("content", getContent())
            .append("status", getStatus())
            .append("mesPlantime", getMesPlantime())
            .toString();
    }
}
