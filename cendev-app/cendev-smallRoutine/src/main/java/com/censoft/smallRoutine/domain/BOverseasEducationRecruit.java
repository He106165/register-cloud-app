package com.censoft.smallRoutine.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 海外教育资源--招生详情对象 b_overseas_education_recruit
 *
 * @author cendev
 * @date 2021-01-11
 */
public class BOverseasEducationRecruit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 关联id */
    @Excel(name = "关联id")
    private String pid;

    /** 学位证书 */
    @Excel(name = "学位证书")
    private String degreeCertificate;

    /** 所属学科 */
    @Excel(name = "所属学科")
    private String subject;

    /** 学校分类 */
    @Excel(name = "学校分类")
    private String universityType;

    /** 专业分类 */
    @Excel(name = "专业分类")
    private String majorType;

    /** 招生层次 */
    @Excel(name = "招生层次")
    private String recruitArrangement;

    /** 招生专业中文 */
    @Excel(name = "招生专业中文")
    private String recruitMajorCn;

    /** 招生专业英文 */
    @Excel(name = "招生专业英文")
    private String recruitMajorEn;

    /** 年份 */
    @Excel(name = "年份", width = 30, dateFormat = "yyyy-MM-dd")
    private Date year;

    /** 申请时间 */
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /** 招生联系人 */
    @Excel(name = "招生联系人")
    private String recruitContact;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactPhone;

    /** 联系邮箱 */
    @Excel(name = "联系邮箱")
    private String contactEmail;

    /** 国际学生入学要求 */
    @Excel(name = "国际学生入学要求")
    private String recruitRequireInter;

    /** 插入时间 */
    @Excel(name = "插入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date insertTime;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setPid(String pid)
    {
        this.pid = pid;
    }

    public String getPid()
    {
        return pid;
    }
    public void setDegreeCertificate(String degreeCertificate)
    {
        this.degreeCertificate = degreeCertificate;
    }

    public String getDegreeCertificate()
    {
        return degreeCertificate;
    }
    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getSubject()
    {
        return subject;
    }
    public void setUniversityType(String universityType)
    {
        this.universityType = universityType;
    }

    public String getUniversityType()
    {
        return universityType;
    }
    public void setMajorType(String majorType)
    {
        this.majorType = majorType;
    }

    public String getMajorType()
    {
        return majorType;
    }
    public void setRecruitArrangement(String recruitArrangement)
    {
        this.recruitArrangement = recruitArrangement;
    }

    public String getRecruitArrangement()
    {
        return recruitArrangement;
    }
    public void setRecruitMajorCn(String recruitMajorCn)
    {
        this.recruitMajorCn = recruitMajorCn;
    }

    public String getRecruitMajorCn()
    {
        return recruitMajorCn;
    }
    public void setRecruitMajorEn(String recruitMajorEn)
    {
        this.recruitMajorEn = recruitMajorEn;
    }

    public String getRecruitMajorEn()
    {
        return recruitMajorEn;
    }
    public void setYear(Date year)
    {
        this.year = year;
    }

    public Date getYear()
    {
        return year;
    }
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }

    public Date getApplyTime()
    {
        return applyTime;
    }
    public void setRecruitContact(String recruitContact)
    {
        this.recruitContact = recruitContact;
    }

    public String getRecruitContact()
    {
        return recruitContact;
    }
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone()
    {
        return contactPhone;
    }
    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }

    public String getContactEmail()
    {
        return contactEmail;
    }
    public void setRecruitRequireInter(String recruitRequireInter)
    {
        this.recruitRequireInter = recruitRequireInter;
    }

    public String getRecruitRequireInter()
    {
        return recruitRequireInter;
    }
    public void setInsertTime(Date insertTime)
    {
        this.insertTime = insertTime;
    }

    public Date getInsertTime()
    {
        return insertTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("pid", getPid())
            .append("degreeCertificate", getDegreeCertificate())
            .append("subject", getSubject())
            .append("universityType", getUniversityType())
            .append("majorType", getMajorType())
            .append("recruitArrangement", getRecruitArrangement())
            .append("recruitMajorCn", getRecruitMajorCn())
            .append("recruitMajorEn", getRecruitMajorEn())
            .append("year", getYear())
            .append("applyTime", getApplyTime())
            .append("recruitContact", getRecruitContact())
            .append("contactPhone", getContactPhone())
            .append("contactEmail", getContactEmail())
            .append("recruitRequireInter", getRecruitRequireInter())
            .append("insertTime", getInsertTime())
            .toString();
    }
}
