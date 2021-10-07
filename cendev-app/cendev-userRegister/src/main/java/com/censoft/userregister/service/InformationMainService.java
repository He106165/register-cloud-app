package com.censoft.userregister.service;

import com.censoft.system.domain.SysDictData;
import com.censoft.userregister.domain.Authentication;
import com.censoft.userregister.domain.PersonalAbroafdstudyInfo;
import com.censoft.userregister.domain.PersonalExtendInfo;
import com.censoft.userregister.domain.PersonalUserInfo;
import com.censoft.userregister.util.ResultUtil;
import java.util.List;

/**
 *@创建人:wqgeng
 *@创建时间:2020-12-1715:35
 *@描述:信息维护
 */

public interface InformationMainService {

    ResultUtil queryInfo(PersonalUserInfo personalUserInfo);

    ResultUtil updateInfo(PersonalUserInfo personalUserInfo);

    ResultUtil saveOrUpdate(PersonalExtendInfo personalExtendInfo);

    ResultUtil queryExtendInfo(PersonalExtendInfo personalExtendInfo);

    ResultUtil queryExtendCount(PersonalExtendInfo personalExtendInfo);

    ResultUtil insertAbroafdstudyInfo(PersonalAbroafdstudyInfo personalAbroafdstudyInfo);

    ResultUtil queryAbroafdstudyInfo(PersonalAbroafdstudyInfo personalAbroafdstudyInfo);

    ResultUtil updateAbroafdstudyInfo(PersonalAbroafdstudyInfo personalAbroafdstudyInfo);

    ResultUtil queryAbroafdstudyInfoById(PersonalAbroafdstudyInfo personalAbroafdstudyInfo);

    ResultUtil deleteAbroafdstudyInfoById(PersonalAbroafdstudyInfo personalAbroafdstudyInfo);

    ResultUtil authentication(Authentication authentication);

    ResultUtil queryRealNameInformation(PersonalUserInfo personalUserInfo);

    List<SysDictData> selectDictDataByType(String dictType);
}
