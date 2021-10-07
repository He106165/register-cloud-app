package com.censoft.smallRoutine.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 海外教育资源--院校基础信息实体对象 b_overseas_education_base
 *
 * @author cendev
 * @date 2021-01-11
 */
public class BOverseasEducationBase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 院校中文名称 */
    @Excel(name = "院校中文名称")
    private String universityCn;

    /** 院校英文名称 */
    @Excel(name = "院校英文名称")
    private String universityEn;

    /** 院校校徽 */
    @Excel(name = "院校校徽")
    private String badge;

    /** 所在国 */
    @Excel(name = "所在国")
    private String country;

    /** 所在州/省 */
    @Excel(name = "所在州/省")
    private String province;

    /** 建校时间 */
    @Excel(name = "建校时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date buildTime;

    /** 院校性质 */
    @Excel(name = "院校性质")
    private String universityNature;

    /** 学校地址 */
    @Excel(name = "学校地址")
    private String schoolAddress;

    /** 学校网址 */
    @Excel(name = "学校网址")
    private String website;

    /** 所属教育处 */
    @Excel(name = "所属教育处")
    private String educationSection;

    /** 外部质量保障机构 */
    @Excel(name = "外部质量保障机构")
    private String qualityOrg;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contact;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contactInfo;

    /** 联系邮箱 */
    @Excel(name = "联系邮箱")
    private String contactEmail;

    /** 学校分类 */
    @Excel(name = "学校分类")
    private String universityClass;

    /** 是否为签约院校 */
    @Excel(name = "是否为签约院校")
    private String isContracted;

    /** 学校简介 */
    @Excel(name = "学校简介")
    private String schoolProfile;

    /** 优势专业 */
    @Excel(name = "优势专业")
    private String advantageMajor;

    /** 知名校友 */
    @Excel(name = "知名校友")
    private String famousFirend;

    /** 招生信息 */
    @Excel(name = "招生信息")
    private String recruitInfo;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setUniversityCn(String universityCn)
    {
        this.universityCn = universityCn;
    }

    public String getUniversityCn()
    {
        return universityCn;
    }
    public void setUniversityEn(String universityEn)
    {
        this.universityEn = universityEn;
    }

    public String getUniversityEn()
    {
        return universityEn;
    }
    public void setBadge(String badge)
    {
        this.badge = badge;
    }

    public String getBadge()
    {
        return badge;
    }
    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getCountry()
    {
        return country;
    }
    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getProvince()
    {
        return province;
    }
    public void setBuildTime(Date buildTime)
    {
        this.buildTime = buildTime;
    }

    public Date getBuildTime()
    {
        return buildTime;
    }
    public void setUniversityNature(String universityNature)
    {
        this.universityNature = universityNature;
    }

    public String getUniversityNature()
    {
        return universityNature;
    }
    public void setSchoolAddress(String schoolAddress)
    {
        this.schoolAddress = schoolAddress;
    }

    public String getSchoolAddress()
    {
        return schoolAddress;
    }
    public void setWebsite(String website)
    {
        this.website = website;
    }

    public String getWebsite()
    {
        return website;
    }
    public void setEducationSection(String educationSection)
    {
        this.educationSection = educationSection;
    }

    public String getEducationSection()
    {
        return educationSection;
    }
    public void setQualityOrg(String qualityOrg)
    {
        this.qualityOrg = qualityOrg;
    }

    public String getQualityOrg()
    {
        return qualityOrg;
    }
    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String getContact()
    {
        return contact;
    }
    public void setContactInfo(String contactInfo)
    {
        this.contactInfo = contactInfo;
    }

    public String getContactInfo()
    {
        return contactInfo;
    }
    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }

    public String getContactEmail()
    {
        return contactEmail;
    }
    public void setUniversityClass(String universityClass)
    {
        this.universityClass = universityClass;
    }

    public String getUniversityClass()
    {
        return universityClass;
    }
    public void setIsContracted(String isContracted)
    {
        this.isContracted = isContracted;
    }

    public String getIsContracted()
    {
        return isContracted;
    }
    public void setSchoolProfile(String schoolProfile)
    {
        this.schoolProfile = schoolProfile;
    }

    public String getSchoolProfile()
    {
        return schoolProfile;
    }
    public void setAdvantageMajor(String advantageMajor)
    {
        this.advantageMajor = advantageMajor;
    }

    public String getAdvantageMajor()
    {
        return advantageMajor;
    }
    public void setFamousFirend(String famousFirend)
    {
        this.famousFirend = famousFirend;
    }

    public String getFamousFirend()
    {
        return famousFirend;
    }
    public void setRecruitInfo(String recruitInfo)
    {
        this.recruitInfo = recruitInfo;
    }

    public String getRecruitInfo()
    {
        return recruitInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("universityCn", getUniversityCn())
            .append("universityEn", getUniversityEn())
            .append("badge", getBadge())
            .append("country", getCountry())
            .append("province", getProvince())
            .append("buildTime", getBuildTime())
            .append("universityNature", getUniversityNature())
            .append("schoolAddress", getSchoolAddress())
            .append("website", getWebsite())
            .append("educationSection", getEducationSection())
            .append("qualityOrg", getQualityOrg())
            .append("contact", getContact())
            .append("contactInfo", getContactInfo())
            .append("contactEmail", getContactEmail())
            .append("universityClass", getUniversityClass())
            .append("isContracted", getIsContracted())
            .append("schoolProfile", getSchoolProfile())
            .append("advantageMajor", getAdvantageMajor())
            .append("famousFirend", getFamousFirend())
            .append("recruitInfo", getRecruitInfo())
            .toString();
    }
}
