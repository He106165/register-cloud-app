package com.censoft.weChat.service.impl;

import com.censoft.common.core.text.Convert;
import com.censoft.common.utils.DateUtils;
import com.censoft.weChat.domain.PersonalUserInfo;
import com.censoft.weChat.domain.WechatUserInfo;
import com.censoft.weChat.mapper.WechatUserInfoMapper;
import com.censoft.weChat.service.IWechatUserInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * @Description 个人用户登陆方法（前提是获取到用户Id）
     * @Parm
     * @return
     **/
    @Override
    public PersonalUserInfo personallogin(@Param("userId") String userId){
        return wechatUserInfoMapper.personallogin(userId);
    }
    @Override
    public WechatUserInfo selectWechatUserInfoByIdAndUserType(@Param("id")String id,@Param("userType")String userType){
        return wechatUserInfoMapper.selectWechatUserInfoByIdAndUserType(id,userType);
    }


}
