package com.censoft.generalUser.mapper;

import com.censoft.generalUser.entity.PersonalUserInfoToRedis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 个人用户信息Mapper接口
 *
 * @author cendev
 * @date 2020-10-20
 */
@Mapper
public interface GeneralUserInfoMapper
{

    Map<String,Object> checkGeneralUser(Map map);

    Integer checkUserPhone(Map map);

    Integer checkUserIDnumber(Map map);

    Integer checkUserEmail(Map map);

    Integer checkUserName(Map map);
    //验证个人昵称的唯一性
    Integer checkUserNickName(String nickname);


    Integer insertPersionalUserLogin(Map map);

    Integer insertPersionalUserInfo(Map map);

    Integer insertPersionalUserExt(Map map);

    Integer checkUserIDCard(Map map);

    List<Map> generalUserLogin(Map map);

    Integer registeUserGroup(@Param("list")List<Map> list);

    List<Map> showUserStatusGroup(@Param("list")List<Map> list);

    Integer updateGeneralUserLogin(Map map);
    Integer updateGeneralUserInfo(Map map);
    Integer updateGeneralUserExtra(Map map);

    List<Map> getGeneralUserInfoByidCard(Map map);
    Map getGeneralUserInfoByUserId(Map map);

    List<Map> getGeneralUserInfoByPhoneOrEmail(Map map);

    List<Map> getUserinfoNoPassword(Map map);

    Integer registeUserInfoGroup(@Param("list")List<Map> userList);

    Map getUserInfoByUserId(Map map);
    Map getUserInfoByUserIdNoPram(Map map);

    Integer updateGeneralUserInfoByUserId(Map map);

    Integer insertWEchartInfo(Map map);

    Integer registeUserExtGroup(@Param("list")List<Map> userList);


    /**
    * @Description 根据用户ID获取用户信息（用于修改用户信息之后存redis）
    * @Parm
    * @return
    **/
    public PersonalUserInfoToRedis getUserInfoById(@Param("userId") String userId);
}
