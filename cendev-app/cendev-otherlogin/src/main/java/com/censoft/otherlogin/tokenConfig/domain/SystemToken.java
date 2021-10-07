package com.censoft.otherlogin.tokenConfig.domain;

import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 令牌管理对象 system_token
 *
 * @author cendev
 * @date 2020-10-20
 */
public class SystemToken extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String systemName;

    /**
     * 有效时间
     */
    @Excel(name = "有效时间")
    private String time;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String descript;

    /**
     * 版本号
     */
    @Excel(name = "版本号")
    private Long version;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getDescript() {
        return descript;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("systemName", getSystemName())
                .append("time", getTime())
                .append("descript", getDescript())
                .append("version", getVersion())
                .toString();
    }
}
