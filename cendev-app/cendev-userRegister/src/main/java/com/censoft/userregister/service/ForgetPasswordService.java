package com.censoft.userregister.service;

import com.censoft.userregister.domain.FindPersonalInfo;
import com.censoft.userregister.domain.PersonalUserLogin;
import com.censoft.userregister.util.ResultUtil;
/**
 *@创建人:wqgeng
 *@创建时间:2020-12-0213:33
 *@描述:忘记密码接口
 */
public interface ForgetPasswordService {
    ResultUtil updatePassword(PersonalUserLogin personalUserLogin);

    ResultUtil phonePasswordCount(PersonalUserLogin personalUserLogin);

    ResultUtil emailPasswordCount(PersonalUserLogin personalUserLogin);

    ResultUtil saveArtificialInfo(FindPersonalInfo findPersonalInfo);
}
