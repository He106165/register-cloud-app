package com.censoft.userregister.domain;

import lombok.Data;
import org.springframework.stereotype.Service;

/**
 *@创建人:wqgeng
 *@创建时间:2020-11-2421:00
 *@描述:手机邮箱验证码实体类
 */
@Data
public class PhoneEmailBo {
private String phone;
private String validateCode;
private String email;
private String validateCode1;
private String orgenFlag;
}
