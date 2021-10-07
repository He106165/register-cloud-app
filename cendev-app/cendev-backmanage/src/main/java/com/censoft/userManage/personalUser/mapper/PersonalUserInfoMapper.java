package com.censoft.userManage.personalUser.mapper;

import com.censoft.userManage.personalUser.domain.PersonalUserInfo;

import com.censoft.userManage.personalUser.domain.PersonalUserLogin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 个人用户信息Mapper接口
 *
 * @author cendev
 * @date 2020-10-20
 */
@Mapper
public interface PersonalUserInfoMapper {
    /**
     * 查询个人用户信息
     *
     * @param userId 个人用户信息ID
     * @return 个人用户信息
     */
    public PersonalUserInfo selectPersonalUserInfoById(String userId);

    /**
     * 查询个人用户信息列表
     *
     * @param personalUserInfo 个人用户信息
     * @return 个人用户信息集合
     */
    public List<PersonalUserInfo> selectPersonalUserInfoList(PersonalUserInfo personalUserInfo);

    /**
     * 新增个人用户信息
     *
     * @param personalUserInfo 个人用户信息
     * @return 结果
     */
    public int insertPersonalUserInfo(PersonalUserInfo personalUserInfo);

    /**
     * 修改个人用户信息
     *
     * @param personalUserInfo 个人用户信息
     * @return 结果
     */
    public int updatePersonalUserInfo(PersonalUserInfo personalUserInfo);

    /**
     * 删除个人用户信息
     *
     * @param userId 个人用户信息ID
     * @return 结果
     */
    public int deletePersonalUserInfoById(String userId);

    /**
     * 批量删除个人用户信息
     *
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    public int deletePersonalUserInfoByIds(String[] userIds);

    String selectAuth(@Param("userName") String userName, @Param("cardNo") String cardNo);

    int updateAuthLevel(@Param("userName") String userName, @Param("cardNo") String cardNo);

    int personalAuthentication(@Param("userId") String userId);


    /* public Integer  selectNewAddPersonalSum(Map map);*/

    /**
     * 获取个人用户今日新增数
     *
     * @param
     * @param
     * @return
     */
    Integer selectNewAddPersonalSum1(Map map);

    /**
     * 查询个人用户的总数
     *
     * @return
     */
    Integer selectPersonalSum();

    /**
     * 查询最近一周个人用户的新增数
     *
     * @return
     */

    PersonalUserLogin selectInfo(String userId);

    int selectWeekPersonalNum(String times);
}
