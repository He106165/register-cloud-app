package com.censoft.personalcenter.personalUser.service;


import com.censoft.personalcenter.personalUser.domain.PersonalExtendInfo;

import java.util.List;

/**
 * 个人用户扩展信息Service接口
 * 
 * @author cendev
 * @date 2020-11-11
 */
public interface IPersonalExtendInfoService 
{
    /**
     * 查询个人用户扩展信息
     * 
     * @param id 个人用户扩展信息ID
     * @return 个人用户扩展信息
     */
    public PersonalExtendInfo selectPersonalExtendInfoById(String id);

    /**
     * 查询个人用户扩展信息列表
     * 
     * @param personalExtendInfo 个人用户扩展信息
     * @return 个人用户扩展信息集合
     */
    public List<PersonalExtendInfo> selectPersonalExtendInfoList(PersonalExtendInfo personalExtendInfo);

    /**
     * 新增个人用户扩展信息
     * 
     * @param personalExtendInfo 个人用户扩展信息
     * @return 结果
     */
    public int insertPersonalExtendInfo(PersonalExtendInfo personalExtendInfo);

    /**
     * 修改个人用户扩展信息
     * 
     * @param personalExtendInfo 个人用户扩展信息
     * @return 结果
     */
    public int updatePersonalExtendInfo(PersonalExtendInfo personalExtendInfo);

    /**
     * 批量删除个人用户扩展信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePersonalExtendInfoByIds(String ids);

    /**
     * 删除个人用户扩展信息信息
     * 
     * @param id 个人用户扩展信息ID
     * @return 结果
     */
    public int deletePersonalExtendInfoById(String id);
}
