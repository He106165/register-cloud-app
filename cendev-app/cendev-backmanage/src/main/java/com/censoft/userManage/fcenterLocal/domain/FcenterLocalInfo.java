package com.censoft.userManage.fcenterLocal.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;

/**
 * 分中心用户地域信息对象 fcenter_local_info
 *
 * @author cendev
 * @date 2020-11-19
 */
public class FcenterLocalInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 地址 */
    @Excel(name = "地址")
    private String unitAddress;

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
    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public String getUnitName()
    {
        return unitName;
    }
    public void setUnitAddress(String unitAddress)
    {
        this.unitAddress = unitAddress;
    }

    public String getUnitAddress()
    {
        return unitAddress;
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
            .append("unitName", getUnitName())
            .append("unitAddress", getUnitAddress())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("version", getVersion())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
