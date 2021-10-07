package com.censoft.userManage.personalUser.domain;

import lombok.Data;

/**
 * @创建人:wqgeng
 * @创建时间:2020-12-2116:57
 * @描述:实名认证实体类
 */
@Data
public class AuthenticationG {
    private String userId;
    private String userName;
    private String cardNo;
}
