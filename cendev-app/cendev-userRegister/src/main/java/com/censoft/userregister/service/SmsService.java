package com.censoft.userregister.service;

import com.censoft.userregister.domain.PhoneEmailBo;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * @创建人:wqgeng
 * @创建时间:2020-11-1710:26
 * @描述:短信验证信息
 */
public interface SmsService {

    boolean checkValidatePhoneCode(PhoneEmailBo phoneEmailBo);

    boolean checkValidateEmailCode(PhoneEmailBo phoneEmailBo);

    boolean send(Map<String, String> map);

    boolean ogenSend(Map<String, String> map);

    boolean findPasswordMsg(Map<String, String> map);

    boolean checkValidatePhoneEmailCode(PhoneEmailBo phoneEmailBo);

    boolean validatePhonePasswordCode(PhoneEmailBo phoneEmailBo);

    boolean validateEmailPasswordCode(PhoneEmailBo phoneEmailBo);

    boolean checkValidateCodeOrg1(PhoneEmailBo phoneEmailBo);

    boolean checkValidateCodeOrg2(PhoneEmailBo phoneEmailBo);

    boolean checkValidateCodeOrg3(PhoneEmailBo phoneEmailBo);

    boolean ogenForgetSend(Map<String, String> map);

    boolean orgenForgetValidateEmailCode(PhoneEmailBo phoneEmailBo);

    boolean artificialFindPasswordMsg(Map<String, String> map);

    boolean sendMsgCount(Map<String, String> map);
}
