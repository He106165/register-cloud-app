package com.censoft.wechatlogin.wechatUser.service.impl;

import java.util.List;
import com.censoft.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.censoft.wechatlogin.wechatUser.mapper.WechatUserInfoMapper;
import com.censoft.wechatlogin.wechatUser.domain.WechatUserInfo;
import com.censoft.wechatlogin.wechatUser.service.IWechatUserInfoService;
import com.censoft.common.core.text.Convert;

/**
 * 微信信息Service业务层处理
 * 
 * @author cendev
 * @date 2020-10-21
 */
@Service
public class WechatUserInfoServiceImpl implements IWechatUserInfoService 
{
    @Autowired
    private WechatUserInfoMapper wechatUserInfoMapper;

    /**
     * 查询微信信息
     * 
     * @param id 微信信息ID
     * @return 微信信息
     */
    @Override
    public WechatUserInfo selectWechatUserInfoById(String id)
    {
        return wechatUserInfoMapper.selectWechatUserInfoById(id);
    }

    /**
     * 查询微信信息列表
     * 
     * @param wechatUserInfo 微信信息
     * @return 微信信息
     */
    @Override
    public List<WechatUserInfo> selectWechatUserInfoList(WechatUserInfo wechatUserInfo)
    {
        return wechatUserInfoMapper.selectWechatUserInfoList(wechatUserInfo);
    }

    /**
     * 新增微信信息
     * 
     * @param wechatUserInfo 微信信息
     * @return 结果
     */
    @Override
    public int insertWechatUserInfo(WechatUserInfo wechatUserInfo)
    {
        wechatUserInfo.setCreateTime(DateUtils.getNowDate());
        return wechatUserInfoMapper.insertWechatUserInfo(wechatUserInfo);
    }

    /**
     * 修改微信信息
     * 
     * @param wechatUserInfo 微信信息
     * @return 结果
     */
    @Override
    public int updateWechatUserInfo(WechatUserInfo wechatUserInfo)
    {
        wechatUserInfo.setUpdateTime(DateUtils.getNowDate());
        return wechatUserInfoMapper.updateWechatUserInfo(wechatUserInfo);
    }

    /**
     * 删除微信信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWechatUserInfoByIds(String ids)
    {
        return wechatUserInfoMapper.deleteWechatUserInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除微信信息信息
     * 
     * @param id 微信信息ID
     * @return 结果
     */
    public int deleteWechatUserInfoById(String id)
    {
        return wechatUserInfoMapper.deleteWechatUserInfoById(id);
    }
}
