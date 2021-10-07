package com.censoft.userManage.personalUser.service;

import com.censoft.userManage.personalUser.domain.PersonalUserInfo;
import com.censoft.userManage.personalUser.domain.ResultUtil;

import java.util.List;

/**
 * 个人用户信息Service接口
 *
 * @author cendev
 * @date 2020-10-20
 */
public interface IPersonalUserInfoService {
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
     * 批量删除个人用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePersonalUserInfoByIds(String ids);

    /**
     * 删除个人用户信息信息
     *
     * @param userId 个人用户信息ID
     * @return 结果
     */

    public int deletePersonalUserInfoById(String userId);

    ResultUtil authentication(String userName, String cardNo);

    ResultUtil personalAuthentication(String userId);

    /**
     * 获取个人用户今日新增数
     *
     * @param
     * @return
     */
    public Integer selectNewAddPersonalSum();

    /**
     * 查询个人用户本月新增的总数
     *
     * @return
     */
    public Integer selectNewMonthPersonalSum();

    /**
     * 查询个人用户本年新增的总数
     *
     * @return
     */
    Integer selectYearPersonalSum();

    /**
     * 查询个人用户的总数
     *
     * @return
     */
    Integer selectPersonalSum();

    /**
     * 查询最近一周的用户总数
     *
     * @return
     */
    List<String> selectWeekPersonalNum();
}
