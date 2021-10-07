package com.censoft.personalcenter.personalUser.service.impl;

import com.censoft.common.core.text.Convert;
import com.censoft.common.utils.DateUtils;
import com.censoft.personalcenter.personalUser.domain.PersonalAbroafdstudyInfo;
import com.censoft.personalcenter.personalUser.mapper.PersonalAbroafdstudyInfoMapper;
import com.censoft.personalcenter.personalUser.service.IPersonalAbroafdstudyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 个人留学信息Service业务层处理
 * 
 * @author cendev
 * @date 2020-11-11
 */
@Service
public class PersonalAbroafdstudyInfoServiceImpl implements IPersonalAbroafdstudyInfoService
{
    @Autowired
    private PersonalAbroafdstudyInfoMapper personalAbroafdstudyInfoMapper;

    /**
     * 查询个人留学信息
     * 
     * @param id 个人留学信息ID
     * @return 个人留学信息
     */
    @Override
    public PersonalAbroafdstudyInfo selectPersonalAbroafdstudyInfoById(String id)
    {
        return personalAbroafdstudyInfoMapper.selectPersonalAbroafdstudyInfoById(id);
    }

    /**
     * 查询个人留学信息列表
     * 
     * @param personalAbroafdstudyInfo 个人留学信息
     * @return 个人留学信息
     */
    @Override
    public List<PersonalAbroafdstudyInfo> selectPersonalAbroafdstudyInfoList(PersonalAbroafdstudyInfo personalAbroafdstudyInfo)
    {
        return personalAbroafdstudyInfoMapper.selectPersonalAbroafdstudyInfoList(personalAbroafdstudyInfo);
    }

    /**
     * 新增个人留学信息
     * 
     * @param personalAbroafdstudyInfo 个人留学信息
     * @return 结果
     */
    @Override
    public int insertPersonalAbroafdstudyInfo(PersonalAbroafdstudyInfo personalAbroafdstudyInfo)
    {
        personalAbroafdstudyInfo.setCreateTime(DateUtils.getNowDate());
        return personalAbroafdstudyInfoMapper.insertPersonalAbroafdstudyInfo(personalAbroafdstudyInfo);
    }

    /**
     * 修改个人留学信息
     * 
     * @param personalAbroafdstudyInfo 个人留学信息
     * @return 结果
     */
    @Override
    public int updatePersonalAbroafdstudyInfo(PersonalAbroafdstudyInfo personalAbroafdstudyInfo)
    {
        personalAbroafdstudyInfo.setUpdateTime(DateUtils.getNowDate());
        return personalAbroafdstudyInfoMapper.updatePersonalAbroafdstudyInfo(personalAbroafdstudyInfo);
    }

    /**
     * 删除个人留学信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePersonalAbroafdstudyInfoByIds(String ids)
    {
        return personalAbroafdstudyInfoMapper.deletePersonalAbroafdstudyInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除个人留学信息信息
     * 
     * @param id 个人留学信息ID
     * @return 结果
     */
    public int deletePersonalAbroafdstudyInfoById(String id)
    {
        return personalAbroafdstudyInfoMapper.deletePersonalAbroafdstudyInfoById(id);
    }
}
