package com.censoft.organUser.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 个人用户信息Mapper接口
 * 
 * @author cendev
 * @date 2020-10-20
 */
@Mapper
public interface OrganUserInfoMapper
{


    Integer checkUserSocialCodeAndDeptCode(Map map);

    Map checkUserNameAndDeptcode(Map map);

    Integer insertOrganUserLogin(Map map);

    Integer insertOrganUserInfo(Map map);

    List<Map> organUserLogin(Map map);


    Integer updateOrganUser();

    Integer updateOrganUserLogin(Map map);
    Integer updateOrganUserInfo(Map map);
    Integer updateOrganUserSupply(Map map);


    List<Map> checkOrganUser(Map map);

    Map checkUserOldNameAndDeptcode(Map map);

    List<Map> selectOrganAllInfo(Map map);

    List<Map> getUserInfoByName(Map map);

    List<Map> getUserInfoByUncode(Map map);
    Map getUserInfoByOrgId(Map map);

    Integer updateOrganUserInfoByUserID(Map map);

    Integer insertWEchartInfo(Map map);
}
