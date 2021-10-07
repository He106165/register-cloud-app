package com.censoft.personalcenter.personalUser.service.impl;

import com.censoft.common.core.text.Convert;
import com.censoft.common.utils.DateUtils;
import com.censoft.personalcenter.personalUser.domain.PersonalUserInfo;
import com.censoft.personalcenter.personalUser.mapper.PersonalUserInfoMapper;
import com.censoft.personalcenter.personalUser.service.IPersonalUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 个人用户信息Service业务层处理
 *
 * @author cendev
 * @date 2020-10-20
 */
@Service
public class PersonalUserInfoServiceImpl implements IPersonalUserInfoService
{
    @Autowired
    private PersonalUserInfoMapper personalUserInfoMapper;

    /**
     * 查询个人用户信息
     *
     * @param userId 个人用户信息ID
     * @return 个人用户信息
     */
    @Override
    public PersonalUserInfo selectPersonalUserInfoById(String userId)
    {
        return personalUserInfoMapper.selectPersonalUserInfoById(userId);
    }

    /**
     * 查询个人用户信息列表
     *
     * @param personalUserInfo 个人用户信息
     * @return 个人用户信息
     */
    @Override
    public List<PersonalUserInfo> selectPersonalUserInfoList(PersonalUserInfo personalUserInfo)
    {
        return personalUserInfoMapper.selectPersonalUserInfoList(personalUserInfo);
    }

    /**
     * 新增个人用户信息
     *
     * @param personalUserInfo 个人用户信息
     * @return 结果
     */
    @Override
    public int insertPersonalUserInfo(PersonalUserInfo personalUserInfo)
    {
        personalUserInfo.setCreateTime(DateUtils.getNowDate());
        return personalUserInfoMapper.insertPersonalUserInfo(personalUserInfo);
    }

    /**
     * 修改个人用户信息
     *
     * @param personalUserInfo 个人用户信息
     * @return 结果
     */
    @Override
    public int updatePersonalUserInfo(PersonalUserInfo personalUserInfo)
    {
        personalUserInfo.setUpdateTime(DateUtils.getNowDate());
        return personalUserInfoMapper.updatePersonalUserInfo(personalUserInfo);
    }

    /**
     * 删除个人用户信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePersonalUserInfoByIds(String ids)
    {
        return personalUserInfoMapper.deletePersonalUserInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除个人用户信息信息
     *
     * @param userId 个人用户信息ID
     * @return 结果
     */
    public int deletePersonalUserInfoById(String userId)
    {
        return personalUserInfoMapper.deletePersonalUserInfoById(userId);
    }
}
