package com.censoft.personalcenter.personalUser.service.impl;

import com.censoft.common.core.text.Convert;
import com.censoft.common.utils.DateUtils;
import com.censoft.personalcenter.personalUser.domain.PersonalExtendInfo;
import com.censoft.personalcenter.personalUser.mapper.PersonalExtendInfoMapper;
import com.censoft.personalcenter.personalUser.service.IPersonalExtendInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 个人用户扩展信息Service业务层处理
 * 
 * @author cendev
 * @date 2020-11-11
 */
@Service
public class PersonalExtendInfoServiceImpl implements IPersonalExtendInfoService
{
    @Autowired
    private PersonalExtendInfoMapper personalExtendInfoMapper;

    /**
     * 查询个人用户扩展信息
     * 
     * @param id 个人用户扩展信息ID
     * @return 个人用户扩展信息
     */
    @Override
    public PersonalExtendInfo selectPersonalExtendInfoById(String id)
    {
        return personalExtendInfoMapper.selectPersonalExtendInfoById(id);
    }

    /**
     * 查询个人用户扩展信息列表
     * 
     * @param personalExtendInfo 个人用户扩展信息
     * @return 个人用户扩展信息
     */
    @Override
    public List<PersonalExtendInfo> selectPersonalExtendInfoList(PersonalExtendInfo personalExtendInfo)
    {
        return personalExtendInfoMapper.selectPersonalExtendInfoList(personalExtendInfo);
    }

    /**
     * 新增个人用户扩展信息
     * 
     * @param personalExtendInfo 个人用户扩展信息
     * @return 结果
     */
    @Override
    public int insertPersonalExtendInfo(PersonalExtendInfo personalExtendInfo)
    {
        personalExtendInfo.setCreateTime(DateUtils.getNowDate());
        return personalExtendInfoMapper.insertPersonalExtendInfo(personalExtendInfo);
    }

    /**
     * 修改个人用户扩展信息
     * 
     * @param personalExtendInfo 个人用户扩展信息
     * @return 结果
     */
    @Override
    public int updatePersonalExtendInfo(PersonalExtendInfo personalExtendInfo)
    {
        personalExtendInfo.setUpdateTime(DateUtils.getNowDate());
        return personalExtendInfoMapper.updatePersonalExtendInfo(personalExtendInfo);
    }

    /**
     * 删除个人用户扩展信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePersonalExtendInfoByIds(String ids)
    {
        return personalExtendInfoMapper.deletePersonalExtendInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除个人用户扩展信息信息
     * 
     * @param id 个人用户扩展信息ID
     * @return 结果
     */
    public int deletePersonalExtendInfoById(String id)
    {
        return personalExtendInfoMapper.deletePersonalExtendInfoById(id);
    }
}
