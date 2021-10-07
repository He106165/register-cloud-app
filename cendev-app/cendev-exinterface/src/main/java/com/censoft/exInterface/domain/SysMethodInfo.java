package com.censoft.exInterface.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;

/**
 * 对外接口管理对象 sys_method_info
 *
 * @author cendev
 * @date 2020-10-20
 */
public class SysMethodInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String sysmethodId;

    /**
     * 接口名称
     */
    @Excel(name = "接口名称")
    private String sysmethodName;

    /**
     * 接口地址
     */
    @Excel(name = "接口地址")
    private String sysmethodUrl;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String descript;

    /**
     * 参数列表
     */
    @Excel(name = "参数列表")
    private String parameterlist;

    /**
     * 调用示例
     */
    @Excel(name = "调用示例")
    private String example;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long sort;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;

    /**
     * 版本
     */
    @Excel(name = "版本")
    private Long version;

    /**
     * 删除标记
     */
    private Long delFlag;

    public void setSysmethodId(String sysmethodId) {
        this.sysmethodId = sysmethodId;
    }

    public String getSysmethodId() {
        return sysmethodId;
    }

    public void setSysmethodName(String sysmethodName) {
        this.sysmethodName = sysmethodName;
    }

    public String getSysmethodName() {
        return sysmethodName;
    }

    public void setSysmethodUrl(String sysmethodUrl) {
        this.sysmethodUrl = sysmethodUrl;
    }

    public String getSysmethodUrl() {
        return sysmethodUrl;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getDescript() {
        return descript;
    }

    public void setParameterlist(String parameterlist) {
        this.parameterlist = parameterlist;
    }

    public String getParameterlist() {
        return parameterlist;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExample() {
        return example;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getSort() {
        return sort;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getVersion() {
        return version;
    }

    public void setDelFlag(Long delFlag) {
        this.delFlag = delFlag;
    }

    public Long getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("sysmethodId", getSysmethodId())
                .append("sysmethodName", getSysmethodName())
                .append("sysmethodUrl", getSysmethodUrl())
                .append("descript", getDescript())
                .append("parameterlist", getParameterlist())
                .append("example", getExample())
                .append("sort", getSort())
                .append("remark", getRemark())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .append("version", getVersion())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
