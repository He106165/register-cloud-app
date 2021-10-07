package com.censoft.userManage.fcenterUser.service.impl;

import com.censoft.common.core.text.Convert;
import com.censoft.common.utils.DateUtils;
import com.censoft.userManage.fcenterUser.domain.FcenterUserInfo;
import com.censoft.userManage.fcenterUser.mapper.FcenterUserInfoMapper;
import com.censoft.userManage.fcenterUser.service.IFcenterUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 分中心用户Service业务层处理
 *
 * @author cendev
 * @date 2020-11-23
 */
@Service
public class FcenterUserInfoServiceImpl implements IFcenterUserInfoService
{
    @Autowired
    private FcenterUserInfoMapper fcenterUserInfoMapper;

    /**
     * 查询分中心用户
     *
     * @param id 分中心用户ID
     * @return 分中心用户
     */
    @Override
    public FcenterUserInfo selectFcenterUserInfoById(Long id)
    {
        return fcenterUserInfoMapper.selectFcenterUserInfoById(id);
    }

    /**
     * 查询分中心用户列表
     *
     * @param fcenterUserInfo 分中心用户
     * @return 分中心用户
     */
    @Override
    public List<FcenterUserInfo> selectFcenterUserInfoList(FcenterUserInfo fcenterUserInfo)
    {
        return fcenterUserInfoMapper.selectFcenterUserInfoList(fcenterUserInfo);
    }

    /**
     * 新增分中心用户
     *
     * @param fcenterUserInfo 分中心用户
     * @return 结果
     */
    @Override
    public int insertFcenterUserInfo(FcenterUserInfo fcenterUserInfo)
    {
        fcenterUserInfo.setCreateTime(DateUtils.getNowDate());
        return fcenterUserInfoMapper.insertFcenterUserInfo(fcenterUserInfo);
    }

    /**
     * 修改分中心用户
     *
     * @param fcenterUserInfo 分中心用户
     * @return 结果
     */
    @Override
    public int updateFcenterUserInfo(FcenterUserInfo fcenterUserInfo)
    {
        fcenterUserInfo.setUpdateTime(DateUtils.getNowDate());
        return fcenterUserInfoMapper.updateFcenterUserInfo(fcenterUserInfo);
    }

    /**
     * 删除分中心用户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFcenterUserInfoByIds(String ids)
    {
        return fcenterUserInfoMapper.deleteFcenterUserInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除分中心用户信息
     *
     * @param id 分中心用户ID
     * @return 结果
     */
    public int deleteFcenterUserInfoById(Long id)
    {
        return fcenterUserInfoMapper.deleteFcenterUserInfoById(id);
    }
    /**
     *  业务系统之间传递分中心用户方法
     * */
    @Override
    public Map getFcenterUserById(Long id){
        return fcenterUserInfoMapper.getFcenterUserById(id);
    }

    @Override
    public FcenterUserInfo getUserInfoByName(String name) {
        return fcenterUserInfoMapper.getUserInfoByName(name);
    }
}
