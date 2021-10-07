package com.censoft.educationEmbassy.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;

/**
 * 国家教育处/大使馆对象 education_embassy
 * 
 * @author cendev
 * @date 2021-04-27
 */
@Data
public class EducationEmbassy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一标识 */
    private String id;

    /** 教育处code码 */
    @Excel(name = "教育处code码")
    private String edCode;

    /** 教育组/处名称 */
    @Excel(name = "教育组/处名称")
    private String edName;

    /** 对应国家标识 */
    @Excel(name = "对应国家标识")
    private String edNationCode;

    /** 状态 */
    @Excel(name = "状态")
    private String statu;

    /** 版本 */
    @Excel(name = "版本")
    private Long version;

    /** 删除标记 */
    private Long delFlag;

    /** 备用字段1 */
    @Excel(name = "备用字段1")
    private String edBak1;

    /** 备用字段2 */
    @Excel(name = "备用字段2")
    private String edBak2;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setEdCode(String edCode) 
    {
        this.edCode = edCode;
    }

    public String getEdCode() 
    {
        return edCode;
    }
    public void setEdName(String edName) 
    {
        this.edName = edName;
    }

    public String getEdName() 
    {
        return edName;
    }
    public void setEdNationCode(String edNationCode) 
    {
        this.edNationCode = edNationCode;
    }

    public String getEdNationCode() 
    {
        return edNationCode;
    }
    public void setStatu(String statu) 
    {
        this.statu = statu;
    }

    public String getStatu() 
    {
        return statu;
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
    public void setEdBak1(String edBak1) 
    {
        this.edBak1 = edBak1;
    }

    public String getEdBak1() 
    {
        return edBak1;
    }
    public void setEdBak2(String edBak2) 
    {
        this.edBak2 = edBak2;
    }

    public String getEdBak2() 
    {
        return edBak2;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("edCode", getEdCode())
            .append("edName", getEdName())
            .append("edNationCode", getEdNationCode())
            .append("statu", getStatu())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("version", getVersion())
            .append("delFlag", getDelFlag())
            .append("edBak1", getEdBak1())
            .append("edBak2", getEdBak2())
            .toString();
    }
}
