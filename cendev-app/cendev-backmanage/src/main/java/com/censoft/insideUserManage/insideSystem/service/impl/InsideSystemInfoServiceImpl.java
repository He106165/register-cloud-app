package com.censoft.insideUserManage.insideSystem.service.impl;

import cn.hutool.core.util.IdUtil;
import com.censoft.common.core.text.Convert;
import com.censoft.common.utils.DateUtils;
import com.censoft.insideUserManage.insideSystem.domain.InsideSystemInfo;
import com.censoft.insideUserManage.insideSystem.mapper.InsideSystemInfoMapper;
import com.censoft.insideUserManage.insideSystem.service.IInsideSystemInfoService;
import com.censoft.systemManage.util.SystemManageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内部系统注册Service业务层处理
 *
 * @author cendev
 * @date 2020-10-19
 */
@Service
public class InsideSystemInfoServiceImpl implements IInsideSystemInfoService {
    @Autowired
    private InsideSystemInfoMapper insideSystemInfoMapper;

    /**
     * 查询内部系统注册
     *
     * @param insideSystemId 内部系统注册ID
     * @return 内部系统注册
     */
    @Override
    public InsideSystemInfo selectInsideSystemInfoById(String insideSystemId) {
        return insideSystemInfoMapper.selectInsideSystemInfoById(insideSystemId);
    }

    /**
     * 查询内部系统注册列表
     *
     * @param insideSystemInfo 内部系统注册
     * @return 内部系统注册
     */
    @Override
    public List<InsideSystemInfo> selectInsideSystemInfoList(InsideSystemInfo insideSystemInfo) {
        return insideSystemInfoMapper.selectInsideSystemInfoList(insideSystemInfo);
    }

    /**
     * 新增内部系统注册
     *
     * @param insideSystemInfo 内部系统注册
     * @return 结果
     */
    @Override
    public int insertInsideSystemInfo(InsideSystemInfo insideSystemInfo) {
        String systemIdentify = SystemManageUtil.createSystemIdentify(insideSystemInfo.getName());
        insideSystemInfo.setInsideSystemId(IdUtil.fastSimpleUUID());
        insideSystemInfo.setJoinsysCode(systemIdentify);
        insideSystemInfo.setCreateTime(DateUtils.getNowDate());
        return insideSystemInfoMapper.insertInsideSystemInfo(insideSystemInfo);
    }

    /**
     * 修改内部系统注册
     *
     * @param insideSystemInfo 内部系统注册
     * @return 结果
     */
    @Override
    public int updateInsideSystemInfo(InsideSystemInfo insideSystemInfo) {
        insideSystemInfo.setUpdateTime(DateUtils.getNowDate());
        return insideSystemInfoMapper.updateInsideSystemInfo(insideSystemInfo);
    }

    /**
     * 删除内部系统注册对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteInsideSystemInfoByIds(String ids) {
        return insideSystemInfoMapper.deleteInsideSystemInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除内部系统注册信息
     *
     * @param insideSystemId 内部系统注册ID
     * @return 结果
     */
    public int deleteInsideSystemInfoById(String insideSystemId) {
        return insideSystemInfoMapper.deleteInsideSystemInfoById(insideSystemId);
    }


    @Override
    public List<InsideSystemInfo> getSystemListInfoByIds(String[] ids) {
        return insideSystemInfoMapper.getSystemListInfoByIds(ids);
    }
}
