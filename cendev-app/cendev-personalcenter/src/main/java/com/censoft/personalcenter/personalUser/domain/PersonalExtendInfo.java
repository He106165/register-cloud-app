package com.censoft.personalcenter.personalUser.domain;

import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 个人用户扩展信息对象 personal_extend_info
 * 
 * @author cendev
 * @date 2020-11-11
 */
public class PersonalExtendInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private String id;

    /** 对应用户表编码 */
    @Excel(name = "对应用户表编码")
    private String userId;

    /** 民族 */
    @Excel(name = "民族")
    private String nation;

    /** 健康状态 */
    @Excel(name = "健康状态")
    private String healthStatus;

    /** 婚姻状态 */
    @Excel(name = "婚姻状态")
    private String marriageNew;

    /** 籍贯 */
    @Excel(name = "籍贯")
    private String nativePlace;

    /** 出生地 */
    @Excel(name = "出生地")
    private String placeOfBirth;

    /** 政治面貌 */
    @Excel(name = "政治面貌")
    private String political;

    /** 最后学历 */
    @Excel(name = "最后学历")
    private String lastScjool;

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
    public void setNation(String nation) 
    {
        this.nation = nation;
    }

    public String getNation() 
    {
        return nation;
    }
    public void setHealthStatus(String healthStatus) 
    {
        this.healthStatus = healthStatus;
    }

    public String getHealthStatus() 
    {
        return healthStatus;
    }
    public void setMarriageNew(String marriageNew) 
    {
        this.marriageNew = marriageNew;
    }

    public String getMarriageNew() 
    {
        return marriageNew;
    }
    public void setNativePlace(String nativePlace) 
    {
        this.nativePlace = nativePlace;
    }

    public String getNativePlace() 
    {
        return nativePlace;
    }
    public void setPlaceOfBirth(String placeOfBirth) 
    {
        this.placeOfBirth = placeOfBirth;
    }

    public String getPlaceOfBirth() 
    {
        return placeOfBirth;
    }
    public void setPolitical(String political) 
    {
        this.political = political;
    }

    public String getPolitical() 
    {
        return political;
    }
    public void setLastScjool(String lastScjool) 
    {
        this.lastScjool = lastScjool;
    }

    public String getLastScjool() 
    {
        return lastScjool;
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
            .append("nation", getNation())
            .append("healthStatus", getHealthStatus())
            .append("marriageNew", getMarriageNew())
            .append("nativePlace", getNativePlace())
            .append("placeOfBirth", getPlaceOfBirth())
            .append("political", getPolitical())
            .append("lastScjool", getLastScjool())
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
