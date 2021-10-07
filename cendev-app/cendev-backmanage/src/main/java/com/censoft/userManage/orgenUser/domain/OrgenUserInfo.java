package com.censoft.userManage.orgenUser.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;

/**
 * 机构用户对象 orgen_user_info
 * 
 * @author cendev
 * @date 2020-10-20
 */
@Data
public class OrgenUserInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private OrgenUserLogin orgenUserLogin;

    /** 编号 */
    private String orgUserId;

    /** 机构用户类型 */
    @Excel(name = "机构用户类型")
    private String oegUserTpye;

    /** 法人类型 */
    @Excel(name = "法人类型")
    private String permanType;

    /** 企业名称/院校名称/机构名称/学联名称 */
    @Excel(name = "企业名称/院校名称/机构名称/学联名称")
    private String enterpriseName;

    /** 院校\机构英文名称（海外院校） */
    @Excel(name = "英文名称", readConverterExp = "英文名称")
    private String englishName;

    /** 法人姓名 */
    @Excel(name = "法人姓名")
    private String legalName;

    /** 法人证件类型 */
    @Excel(name = "法人证件类型")
    private String legalCardType;

    /** 法人证件号 */
    @Excel(name = "法人证件号")
    private String legalIdnumber;

    /** 法人手机号 */
    @Excel(name = "法人手机号")
    private String permanMobile;

    /** 联系人姓名 */
    @Excel(name = "联系人姓名")
    private String hostpersionName;

    /** 联系人邮箱 */
    @Excel(name = "联系人邮箱")
    private String hostpersionEmail;

    /** 联系人手机号/注册手机号 */
    @Excel(name = "联系人手机号/注册手机号")
    private String hostpersionTelephone;

    /** 国家 */
    @Excel(name = "国家")
    private String country;

    /** 省份 */
    @Excel(name = "省份")
    private String province;

    /** 院校\机构详细地址 */
    @Excel(name = "详细地址")
    private String address;

    /** 领事馆名称 */
    @Excel(name = "领事馆名称")
    private String consulateName;

    /** 领事馆ID */
    @Excel(name = "领事馆ID")
    private String consulateId;

    /** 实名等级 */
    @Excel(name = "实名等级")
    private String authlevel;

    /** 是否通过 */
    @Excel(name = "是否通过")
    private Long ispass;

    /** 状态 */
    @Excel(name = "状态")
    private String statu;

    /** 头像地址 */
    @Excel(name = "头像地址")
    private String avatarUrl;

    /** 版本 */
    @Excel(name = "版本")
    private Long version;

    /** 删除标记 */
    private Long delFlag;

    public void setOrgUserId(String orgUserId) 
    {
        this.orgUserId = orgUserId;
    }

    public String getOrgUserId() 
    {
        return orgUserId;
    }
    public void setOegUserTpye(String oegUserTpye) 
    {
        this.oegUserTpye = oegUserTpye;
    }

    public String getOegUserTpye() 
    {
        return oegUserTpye;
    }
    public void setPermanType(String permanType) 
    {
        this.permanType = permanType;
    }

    public String getPermanType() 
    {
        return permanType;
    }
    public void setEnterpriseName(String enterpriseName) 
    {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseName() 
    {
        return enterpriseName;
    }
    public void setEnglishName(String englishName) 
    {
        this.englishName = englishName;
    }

    public String getEnglishName() 
    {
        return englishName;
    }
    public void setLegalName(String legalName) 
    {
        this.legalName = legalName;
    }

    public String getLegalName() 
    {
        return legalName;
    }
    public void setLegalCardType(String legalCardType) 
    {
        this.legalCardType = legalCardType;
    }

    public String getLegalCardType() 
    {
        return legalCardType;
    }
    public void setLegalIdnumber(String legalIdnumber) 
    {
        this.legalIdnumber = legalIdnumber;
    }

    public String getLegalIdnumber() 
    {
        return legalIdnumber;
    }
    public void setPermanMobile(String permanMobile) 
    {
        this.permanMobile = permanMobile;
    }

    public String getPermanMobile() 
    {
        return permanMobile;
    }
    public void setHostpersionName(String hostpersionName) 
    {
        this.hostpersionName = hostpersionName;
    }

    public String getHostpersionName() 
    {
        return hostpersionName;
    }
    public void setHostpersionEmail(String hostpersionEmail) 
    {
        this.hostpersionEmail = hostpersionEmail;
    }

    public String getHostpersionEmail() 
    {
        return hostpersionEmail;
    }
    public void setHostpersionTelephone(String hostpersionTelephone) 
    {
        this.hostpersionTelephone = hostpersionTelephone;
    }

    public String getHostpersionTelephone() 
    {
        return hostpersionTelephone;
    }
    public void setCountry(String country) 
    {
        this.country = country;
    }

    public String getCountry() 
    {
        return country;
    }
    public void setProvince(String province) 
    {
        this.province = province;
    }

    public String getProvince() 
    {
        return province;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setConsulateName(String consulateName) 
    {
        this.consulateName = consulateName;
    }

    public String getConsulateName() 
    {
        return consulateName;
    }
    public void setConsulateId(String consulateId) 
    {
        this.consulateId = consulateId;
    }

    public String getConsulateId() 
    {
        return consulateId;
    }
    public void setAuthlevel(String authlevel) 
    {
        this.authlevel = authlevel;
    }

    public String getAuthlevel() 
    {
        return authlevel;
    }
    public void setIspass(Long ispass) 
    {
        this.ispass = ispass;
    }

    public Long getIspass() 
    {
        return ispass;
    }
    public void setStatu(String statu) 
    {
        this.statu = statu;
    }

    public String getStatu() 
    {
        return statu;
    }
    public void setAvatarUrl(String avatarUrl) 
    {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() 
    {
        return avatarUrl;
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
            .append("orgUserId", getOrgUserId())
            .append("oegUserTpye", getOegUserTpye())
            .append("permanType", getPermanType())
            .append("enterpriseName", getEnterpriseName())
            .append("englishName", getEnglishName())
            .append("legalName", getLegalName())
            .append("legalCardType", getLegalCardType())
            .append("legalIdnumber", getLegalIdnumber())
            .append("permanMobile", getPermanMobile())
            .append("hostpersionName", getHostpersionName())
            .append("hostpersionEmail", getHostpersionEmail())
            .append("hostpersionTelephone", getHostpersionTelephone())
            .append("country", getCountry())
            .append("province", getProvince())
            .append("address", getAddress())
            .append("consulateName", getConsulateName())
            .append("consulateId", getConsulateId())
            .append("authlevel", getAuthlevel())
            .append("ispass", getIspass())
            .append("statu", getStatu())
            .append("remark", getRemark())
            .append("avatarUrl", getAvatarUrl())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("version", getVersion())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
