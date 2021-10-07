package com.censoft.userregister.domain;

import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 机构用户登录对象 orgen_user_login
 * 
 * @author cendev
 * @date 2020-10-21
 */
@Data
public class OrgenUserLogin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一ID */
    private String id;

    /** 机构用户ID */
    @Excel(name = "机构用户ID")
    private String oegUserId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String name;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    private String unifiedsocialcreditcode;

    /** 是否可用 */
    @Excel(name = "是否可用")
    private String status;

    /** 版本 */
    @Excel(name = "版本")
    private Long version;

    /** 删除标记 */
    private Long delFlag;

    /** 机构部门 */
    @Excel(name = "机构部门")
    private String depermentCode;
    /** 联系人邮箱 */
    @Excel(name = "联系人邮箱")
    private String hostpersionEmail;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setOegUserId(String oegUserId) 
    {
        this.oegUserId = oegUserId;
    }

    public String getOegUserId() 
    {
        return oegUserId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setUnifiedsocialcreditcode(String unifiedsocialcreditcode) 
    {
        this.unifiedsocialcreditcode = unifiedsocialcreditcode;
    }

    public String getUnifiedsocialcreditcode() 
    {
        return unifiedsocialcreditcode;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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
    public void setDepermentCode(String depermentCode) 
    {
        this.depermentCode = depermentCode;
    }

    public String getDepermentCode() 
    {
        return depermentCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("oegUserId", getOegUserId())
            .append("name", getName())
            .append("password", getPassword())
            .append("unifiedsocialcreditcode", getUnifiedsocialcreditcode())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("version", getVersion())
            .append("delFlag", getDelFlag())
            .append("depermentCode", getDepermentCode())
            .toString();
    }
}
