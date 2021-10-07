package com.censoft.userregister.dao;

import com.censoft.system.domain.SysDictData;
import com.censoft.userregister.domain.*;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *@创建人:wqgeng
 *@创建时间:2020-12-1715:37
 *@描述:
 */
@Mapper
public interface InformationMainMapper {

    PersonalUserInfo queryInfo(PersonalUserInfo personalUserInfo);

    int updateInfo(PersonalUserInfo personalUserInfo);

    int save(PersonalExtendInfo personalExtendInfo);

    int update(PersonalExtendInfo personalExtendInfo);

    int selectUserInfo(PersonalExtendInfo personalExtendInfo);

    PersonalExtendInfo queryExtendInfo(PersonalExtendInfo personalExtendInfo);

    int insertAbroafdstudyInfo(PersonalAbroafdstudyInfo personalAbroafdstudyInfo);

    List<PersonalAbroafdstudyInfo> queryAbroafdstudyInfo(PersonalAbroafdstudyInfo personalAbroafdstudyInfo);

    int updateAbroafdstudyInfo(PersonalAbroafdstudyInfo personalAbroafdstudyInfo);

    PersonalAbroafdstudyInfo queryAbroafdstudyInfoById(@Param("id") String id);

    int deleteAbroafdstudyInfoById(String id);

    String selectAuth(Authentication authentication);

    int updateAuthLevel(Authentication authentication);

    List<PersonalUserInfo> queryRealNameInformation(PersonalUserInfo personalUserInfo);

    int updateIsLabourReal(Authentication authentication);

    PersonalUserLogin selectCardInfo(Authentication authentication);

    List<SysDictData> selectDictDataByType(String dictType);
}
