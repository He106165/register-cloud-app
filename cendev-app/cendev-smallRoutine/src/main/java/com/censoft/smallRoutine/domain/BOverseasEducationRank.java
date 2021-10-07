package com.censoft.smallRoutine.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 海外教育资源--院校排名事实对象 b_overseas_education_rank
 *
 * @author cendev
 * @date 2021-01-11
 */
public class BOverseasEducationRank extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 关联id */
    @Excel(name = "关联id")
    private String pid;

    /** 排名名称 */
    @Excel(name = "排名名称")
    private String rankName;

    /** 排名年份 */
    @Excel(name = "排名年份", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rankYear;

    /** 排名等级 */
    @Excel(name = "排名等级")
    private String rankGrade;

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
    public void setRankName(String rankName)
    {
        this.rankName = rankName;
    }

    public String getRankName()
    {
        return rankName;
    }
    public void setRankYear(Date rankYear)
    {
        this.rankYear = rankYear;
    }

    public Date getRankYear()
    {
        return rankYear;
    }
    public void setRankGrade(String rankGrade)
    {
        this.rankGrade = rankGrade;
    }

    public String getRankGrade()
    {
        return rankGrade;
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
            .append("rankName", getRankName())
            .append("rankYear", getRankYear())
            .append("rankGrade", getRankGrade())
            .append("insertTime", getInsertTime())
            .toString();
    }
}
