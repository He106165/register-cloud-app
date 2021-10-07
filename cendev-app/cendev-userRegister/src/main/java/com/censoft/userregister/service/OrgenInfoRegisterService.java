package com.censoft.userregister.service;

import com.censoft.userregister.domain.OrgenUserInfo;
import com.censoft.userregister.domain.OrgenUserLogin;
import com.censoft.userregister.util.ResultUtil;
import org.springframework.stereotype.Service;

/**
 *@创建人:wqgeng
 *@创建时间:2020-11-2111:08
 *@描述:机构信息接口
 */
public interface OrgenInfoRegisterService {

    ResultUtil addSave(OrgenUserInfo orgenUserInfo);

    ResultUtil queryPerManType();

    ResultUtil queryDepermentCode();

    ResultUtil valiIdType(OrgenUserInfo orgenUserInfo);

    ResultUtil queryConsulateInfo();

    ResultUtil queryperManLine();

    ResultUtil orgenEmailPassCount(OrgenUserLogin orgenUserLogin);

    ResultUtil updateOrgenEmailPassword(OrgenUserLogin orgenUserLogin);

    ResultUtil orgenEmailCount(OrgenUserLogin orgenUserLogin);
}
