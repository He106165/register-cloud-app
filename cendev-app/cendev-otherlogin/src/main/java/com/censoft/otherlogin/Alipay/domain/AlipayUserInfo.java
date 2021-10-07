package com.censoft.otherlogin.Alipay.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.censoft.common.annotation.Excel;
import com.censoft.common.core.domain.BaseEntity;

/**
 * 支付宝信息对象 alipay_user_info
 * 
 * @author cendev
 * @date 2020-10-21
 */
public class AlipayUserInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** 用户id */
    @Excel(name = "用户id")
    private String userid;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickname;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 省份 */
    @Excel(name = "省份")
    private String provice;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 健康 */
    @Excel(name = "健康")
    private String health;
    /*返回文本*/
    private String content;

    /** 版本 */
    @Excel(name = "版本")
    private Long version;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setUserid(String userid) 
    {
        this.userid = userid;
    }

    public String getUserid() 
    {
        return userid;
    }
    public void setNickname(String nickname) 
    {
        this.nickname = nickname;
    }

    public String getNickname() 
    {
        return nickname;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setProvice(String provice) 
    {
        this.provice = provice;
    }

    public String getProvice() 
    {
        return provice;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setHealth(String health) 
    {
        this.health = health;
    }

    public String getHealth() 
    {
        return health;
    }
    public void setVersion(Long version) 
    {
        this.version = version;
    }

    public Long getVersion() 
    {
        return version;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userid", getUserid())
            .append("nickname", getNickname())
            .append("sex", getSex())
            .append("provice", getProvice())
            .append("status", getStatus())
            .append("health", getHealth())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("version", getVersion())
            .toString();
    }
}
