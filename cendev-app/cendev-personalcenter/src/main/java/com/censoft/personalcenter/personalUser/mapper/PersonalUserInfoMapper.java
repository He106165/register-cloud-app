package com.censoft.personalcenter.personalUser.mapper;

import com.censoft.personalcenter.personalUser.domain.PersonalUserInfo;
import com.censoft.personalcenter.personalUser.domain.PersonalUserLogin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 个人用户信息Mapper接口
 *
 * @author cendev
 * @date 2020-10-20
 */
@Mapper
public interface PersonalUserInfoMapper
{
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

    PersonalUserLogin selectLoginInfo(String userId);
}
