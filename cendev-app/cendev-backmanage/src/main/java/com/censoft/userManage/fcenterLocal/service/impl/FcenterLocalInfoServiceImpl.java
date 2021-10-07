package com.censoft.userManage.fcenterLocal.service.impl;

import java.util.List;
import com.censoft.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.censoft.userManage.fcenterLocal.mapper.FcenterLocalInfoMapper;
import com.censoft.userManage.fcenterLocal.domain.FcenterLocalInfo;
import com.censoft.userManage.fcenterLocal.service.IFcenterLocalInfoService;
import com.censoft.common.core.text.Convert;

/**
 * 分中心用户地域信息Service业务层处理
 *
 * @author cendev
 * @date 2020-11-19
 */
@Service
public class FcenterLocalInfoServiceImpl implements IFcenterLocalInfoService
{
    @Autowired
    private FcenterLocalInfoMapper fcenterLocalInfoMapper;

    /**
     * 查询分中心用户地域信息
     *
     * @param id 分中心用户地域信息ID
     * @return 分中心用户地域信息
     */
    @Override
    public FcenterLocalInfo selectFcenterLocalInfoById(String id)
    {
        return fcenterLocalInfoMapper.selectFcenterLocalInfoById(id);
    }

    /**
     * 查询分中心用户地域信息列表
     *
     * @param fcenterLocalInfo 分中心用户地域信息
     * @return 分中心用户地域信息
     */
    @Override
    public List<FcenterLocalInfo> selectFcenterLocalInfoList(FcenterLocalInfo fcenterLocalInfo)
    {
        return fcenterLocalInfoMapper.selectFcenterLocalInfoList(fcenterLocalInfo);
    }

    /**
     * 新增分中心用户地域信息
     *
     * @param fcenterLocalInfo 分中心用户地域信息
     * @return 结果
     */
    @Override
    public int insertFcenterLocalInfo(FcenterLocalInfo fcenterLocalInfo)
    {
        fcenterLocalInfo.setCreateTime(DateUtils.getNowDate());
        return fcenterLocalInfoMapper.insertFcenterLocalInfo(fcenterLocalInfo);
    }

    /**
     * 修改分中心用户地域信息
     *
     * @param fcenterLocalInfo 分中心用户地域信息
     * @return 结果
     */
    @Override
    public int updateFcenterLocalInfo(FcenterLocalInfo fcenterLocalInfo)
    {
        fcenterLocalInfo.setUpdateTime(DateUtils.getNowDate());
        return fcenterLocalInfoMapper.updateFcenterLocalInfo(fcenterLocalInfo);
    }

    /**
     * 删除分中心用户地域信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFcenterLocalInfoByIds(String ids)
    {
        return fcenterLocalInfoMapper.deleteFcenterLocalInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除分中心用户地域信息信息
     *
     * @param id 分中心用户地域信息ID
     * @return 结果
     */
    public int deleteFcenterLocalInfoById(String id)
    {
        return fcenterLocalInfoMapper.deleteFcenterLocalInfoById(id);
    }
}
