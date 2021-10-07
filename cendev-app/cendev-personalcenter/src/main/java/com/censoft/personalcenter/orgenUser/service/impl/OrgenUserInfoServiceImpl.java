package com.censoft.personalcenter.orgenUser.service.impl;

import com.censoft.common.core.text.Convert;
import com.censoft.common.utils.DateUtils;
import com.censoft.personalcenter.orgenUser.domain.OrgenUserInfo;
import com.censoft.personalcenter.orgenUser.mapper.OrgenUserInfoMapper;
import com.censoft.personalcenter.orgenUser.service.IOrgenUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构用户Service业务层处理
 *
 * @author cendev
 * @date 2020-10-20
 */
@Service
public class OrgenUserInfoServiceImpl implements IOrgenUserInfoService
{
    @Autowired
    private OrgenUserInfoMapper orgenUserInfoMapper;

    /**
     * 查询机构用户
     *
     * @param orgUserId 机构用户ID
     * @return 机构用户
     */
    @Override
    public OrgenUserInfo selectOrgenUserInfoById(String orgUserId)
    {
        return orgenUserInfoMapper.selectOrgenUserInfoById(orgUserId);
    }

    /**
     * 查询机构用户列表
     *
     * @param orgenUserInfo 机构用户
     * @return 机构用户
     */
    @Override
    public List<OrgenUserInfo> selectOrgenUserInfoList(OrgenUserInfo orgenUserInfo)
    {
        return orgenUserInfoMapper.selectOrgenUserInfoList(orgenUserInfo);
    }

    /**
     * 新增机构用户
     *
     * @param orgenUserInfo 机构用户
     * @return 结果
     */
    @Override
    public int insertOrgenUserInfo(OrgenUserInfo orgenUserInfo)
    {
        orgenUserInfo.setCreateTime(DateUtils.getNowDate());
        return orgenUserInfoMapper.insertOrgenUserInfo(orgenUserInfo);
    }

    /**
     * 修改机构用户
     *
     * @param orgenUserInfo 机构用户
     * @return 结果
     */
    @Override
    public int updateOrgenUserInfo(OrgenUserInfo orgenUserInfo)
    {
        orgenUserInfo.setUpdateTime(DateUtils.getNowDate());
        return orgenUserInfoMapper.updateOrgenUserInfo(orgenUserInfo);
    }

    /**
     * 删除机构用户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOrgenUserInfoByIds(String ids)
    {
        return orgenUserInfoMapper.deleteOrgenUserInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除机构用户信息
     *
     * @param orgUserId 机构用户ID
     * @return 结果
     */
    public int deleteOrgenUserInfoById(String orgUserId)
    {
        return orgenUserInfoMapper.deleteOrgenUserInfoById(orgUserId);
    }
}
