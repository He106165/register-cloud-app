package com.censoft.wechatlogin.wechatUser.service;

import com.censoft.wechatlogin.wechatUser.domain.WechatUserInfo;
import java.util.List;

/**
 * 微信信息Service接口
 * 
 * @author cendev
 * @date 2020-10-21
 */
public interface IWechatUserInfoService 
{
    /**
     * 查询微信信息
     * 
     * @param id 微信信息ID
     * @return 微信信息
     */
    public WechatUserInfo selectWechatUserInfoById(String id);

    /**
     * 查询微信信息列表
     * 
     * @param wechatUserInfo 微信信息
     * @return 微信信息集合
     */
    public List<WechatUserInfo> selectWechatUserInfoList(WechatUserInfo wechatUserInfo);

    /**
     * 新增微信信息
     * 
     * @param wechatUserInfo 微信信息
     * @return 结果
     */
    public int insertWechatUserInfo(WechatUserInfo wechatUserInfo);

    /**
     * 修改微信信息
     * 
     * @param wechatUserInfo 微信信息
     * @return 结果
     */
    public int updateWechatUserInfo(WechatUserInfo wechatUserInfo);

    /**
     * 批量删除微信信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWechatUserInfoByIds(String ids);

    /**
     * 删除微信信息信息
     * 
     * @param id 微信信息ID
     * @return 结果
     */
    public int deleteWechatUserInfoById(String id);
}
