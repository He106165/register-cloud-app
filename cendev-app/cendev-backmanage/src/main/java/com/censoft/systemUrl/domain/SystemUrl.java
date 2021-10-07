package com.censoft.systemUrl.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 系统地址对象 system_url
 *
 * @author cendev
 * @date 2021-01-25
 */
public class SystemUrl extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 系统地址的ID
     */
    private String id;

    /**
     * 系统地址
     */
    @Excel(name = "系统地址")
    private String address;

    /**
     * 版本
     */
    @Excel(name = "版本")
    private Long version;

    /**
     * 删除标记
     */
    private Long delFlag;

    /**
     * 用户类型
     */
    @Excel(name = "用户类型")
    private String userType;

    /**
     * 系统名称
     */
    @Excel(name = "系统名称")
    private String systemName;

    /**
     * 图片地址
     */
    @Excel(name = "图片地址")
    private String imageUrl;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
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

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("address", getAddress())
                .append("remark", getRemark())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .append("version", getVersion())
                .append("delFlag", getDelFlag())
                .append("userType", getUserType())
                .append("systemName", getSystemName())
                .append("imageUrl", getImageUrl())
                .toString();
    }
}
