package com.censoft.personalcenter.personalUser.mapper;

import com.censoft.personalcenter.personalUser.domain.PersonalExtendInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 个人用户扩展信息Mapper接口
 * 
 * @author cendev
 * @date 2020-11-11
 */
@Mapper
public interface PersonalExtendInfoMapper 
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
     * 删除个人用户扩展信息
     * 
     * @param id 个人用户扩展信息ID
     * @return 结果
     */
    public int deletePersonalExtendInfoById(String id);

    /**
     * 批量删除个人用户扩展信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePersonalExtendInfoByIds(String[] ids);
}
