package com.censoft.systemManage.systemDataConfig.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 应用系统配置对象 join_system_deploy
 * 
 * @author cendev
 * @date 2020-10-20
 */
public class JoinSystemDeploy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 系统ID */
    @Excel(name = "系统ID")
    private String joinsysId;

    /** 数据类型 */
    @Excel(name = "个人用户数据想")
    private String proParamArray;

    /** 参数集合 */
    @Excel(name = "机构用户数据想")
    private String orgParamArray;

    /** 配置人 */
    @Excel(name = "配置人")
    private String empowerBy;

    /** 配置时间 */
    @Excel(name = "配置时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date empowerTime;

    /** 主键ID */
    private String id;

    public void setJoinsysId(String joinsysId) 
    {
        this.joinsysId = joinsysId;
    }

    public String getJoinsysId() 
    {
        return joinsysId;
    }


    public String getProParamArray() {
        return proParamArray;
    }

    public void setProParamArray(String proParamArray) {
        this.proParamArray = proParamArray;
    }

    public String getOrgParamArray() {
        return orgParamArray;
    }

    public void setOrgParamArray(String orgParamArray) {
        this.orgParamArray = orgParamArray;
    }

    public void setEmpowerBy(String empowerBy)
    {
        this.empowerBy = empowerBy;
    }

    public String getEmpowerBy() 
    {
        return empowerBy;
    }
    public void setEmpowerTime(Date empowerTime) 
    {
        this.empowerTime = empowerTime;
    }

    public Date getEmpowerTime() 
    {
        return empowerTime;
    }
    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("joinsysId", getJoinsysId())
            .append("proParamArray", getProParamArray())
            .append("orgParamArray", getOrgParamArray())
            .append("empowerBy", getEmpowerBy())
            .append("empowerTime", getEmpowerTime())
            .append("id", getId())
            .toString();
    }
}
