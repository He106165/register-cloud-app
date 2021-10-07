package com.censoft.userregister.domain;

import com.censoft.common.annotation.Excel;
import lombok.Data;

/**
 * @创建人:wqgeng
 * @创建时间:2020-11-1916:32
 * @描述:国家信息实体类
 */
@Data
public class NationalityInfo {
    private static final long serialVersionUID = 1L;
    @Excel(name = "国家名称")
    private String nationalityName;
    @Excel(name = "二字节代码")
    private String twoCode;
    @Excel(name = "三字节代码")
    private String threeCode;
    @Excel(name = "说明")
    private String remark;
    private String ep1;
    private String ep2;
}
