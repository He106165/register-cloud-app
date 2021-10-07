package com.censoft.userregister.domain;

import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 个人留学信息对象 personal_abroafdstudy_info
 * 
 * @author cendev
 * @date 2020-11-11
 */
@Data
public class PersonalAbroafdstudyInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private String id;

    /** 对应用户表编码 */
    @Excel(name = "对应用户表编码")
    private String userId;

    /** 留学国家 */
    @Excel(name = "留学国家")
    private String abroadCountry;

    /** 留学类别 */
    @Excel(name = "留学类别")
    private String abroadType;

    /** 留学院校 */
    @Excel(name = "留学院校")
    private String abroadUniversity;

    /** 所在院校 */
    @Excel(name = "所在院校")
    private String nativeClass;

    /** 留学专业 */
    @Excel(name = "留学专业")
    private String major;

    /** 取得学位 */
    @Excel(name = "取得学位")
    private String grade;

    /** 开始时间 */
    @Excel(name = "开始时间", width = 30)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @Excel(name = "结束时间", width = 30)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /** 院校网址 */
    @Excel(name = "院校网址")
    private String webUrl;

    /** 版本 */
    @Excel(name = "版本")
    private Long version;

    /** 删除标记 */
    private Long delFlag;

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
    public void setAbroadCountry(String abroadCountry) 
    {
        this.abroadCountry = abroadCountry;
    }

    public String getAbroadCountry() 
    {
        return abroadCountry;
    }
    public void setAbroadType(String abroadType) 
    {
        this.abroadType = abroadType;
    }

    public String getAbroadType() 
    {
        return abroadType;
    }
    public void setAbroadUniversity(String abroadUniversity) 
    {
        this.abroadUniversity = abroadUniversity;
    }

    public String getAbroadUniversity() 
    {
        return abroadUniversity;
    }
    public void setNativeClass(String nativeClass) 
    {
        this.nativeClass = nativeClass;
    }

    public String getNativeClass() 
    {
        return nativeClass;
    }
    public void setMajor(String major) 
    {
        this.major = major;
    }

    public String getMajor() 
    {
        return major;
    }
    public void setGrade(String grade) 
    {
        this.grade = grade;
    }

    public String getGrade() 
    {
        return grade;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }


    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public void setWebUrl(String webUrl)
    {
        this.webUrl = webUrl;
    }

    public String getWebUrl() 
    {
        return webUrl;
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
            .append("id", getId())
            .append("userId", getUserId())
            .append("abroadCountry", getAbroadCountry())
            .append("abroadType", getAbroadType())
            .append("abroadUniversity", getAbroadUniversity())
            .append("nativeClass", getNativeClass())
            .append("major", getMajor())
            .append("grade", getGrade())
            .append("startTime", getStartTime())
            .append("finishTime", getFinishTime())
            .append("webUrl", getWebUrl())
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
