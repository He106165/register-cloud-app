package com.censoft.sendMag.emailManage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 邮件发送日志对象 mail_send_log
 * 
 * @author cendev
 * @date 2020-10-21
 */
public class MailSendLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 地址 */
    @Excel(name = "地址")
    private String reveiceEmail;

    /** 主题 */
    @Excel(name = "主题")
    private String theme;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 发送邮箱 */
    @Excel(name = "发送邮箱")
    private String sendEmail;

    /** 发送时间 */
    @Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sendTime;

    /** 是否成功 */
    @Excel(name = "是否成功")
    private String status;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setReveiceEmail(String reveiceEmail) 
    {
        this.reveiceEmail = reveiceEmail;
    }

    public String getReveiceEmail() 
    {
        return reveiceEmail;
    }
    public void setTheme(String theme) 
    {
        this.theme = theme;
    }

    public String getTheme() 
    {
        return theme;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setSendEmail(String sendEmail) 
    {
        this.sendEmail = sendEmail;
    }

    public String getSendEmail() 
    {
        return sendEmail;
    }
    public void setSendTime(Date sendTime) 
    {
        this.sendTime = sendTime;
    }

    public Date getSendTime() 
    {
        return sendTime;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("reveiceEmail", getReveiceEmail())
            .append("theme", getTheme())
            .append("content", getContent())
            .append("sendEmail", getSendEmail())
            .append("sendTime", getSendTime())
            .append("status", getStatus())
            .toString();
    }
}
