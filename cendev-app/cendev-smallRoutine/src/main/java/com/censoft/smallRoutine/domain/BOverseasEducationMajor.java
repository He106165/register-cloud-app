package com.censoft.smallRoutine.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 海外教育资源--院校专业事实对象 b_overseas_education_major
 *
 * @author cendev
 * @date 2021-01-11
 */
public class BOverseasEducationMajor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String pid;

    /** 专业 */
    @Excel(name = "专业")
    private String major;

    /** 专业类别 */
    @Excel(name = "专业类别")
    private String majorType;

    /** 历史学历学位认证人数 */
    @Excel(name = "历史学历学位认证人数")
    private String authNumHistory;

    /** 专业层次 */
    @Excel(name = "专业层次")
    private String majorArrangement;

    /** $column.columnComment */
    @Excel(name = "专业层次", width = 30, dateFormat = "yyyy-MM-dd")
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
    public void setMajor(String major)
    {
        this.major = major;
    }

    public String getMajor()
    {
        return major;
    }
    public void setMajorType(String majorType)
    {
        this.majorType = majorType;
    }

    public String getMajorType()
    {
        return majorType;
    }
    public void setAuthNumHistory(String authNumHistory)
    {
        this.authNumHistory = authNumHistory;
    }

    public String getAuthNumHistory()
    {
        return authNumHistory;
    }
    public void setMajorArrangement(String majorArrangement)
    {
        this.majorArrangement = majorArrangement;
    }

    public String getMajorArrangement()
    {
        return majorArrangement;
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
            .append("major", getMajor())
            .append("majorType", getMajorType())
            .append("authNumHistory", getAuthNumHistory())
            .append("majorArrangement", getMajorArrangement())
            .append("insertTime", getInsertTime())
            .toString();
    }
}
