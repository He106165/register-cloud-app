package com.censoft.systemManage.systemRegister.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.censoft.common.utils.DateUtils;
import com.censoft.systemManage.util.SystemManageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.censoft.systemManage.systemRegister.mapper.JoinSystemInfoMapper;
import com.censoft.systemManage.systemRegister.domain.JoinSystemInfo;
import com.censoft.systemManage.systemRegister.service.IJoinSystemInfoService;
import com.censoft.common.core.text.Convert;

/**
 * 应用系统注册Service业务层处理
 * 
 * @author cendev
 * @date 2020-10-20
 */
@Service
public class JoinSystemInfoServiceImpl implements IJoinSystemInfoService 
{
    @Autowired
    private JoinSystemInfoMapper joinSystemInfoMapper;

    /**
     * 查询应用系统注册
     * 
     * @param joinsysId 应用系统注册ID
     * @return 应用系统注册
     */
    @Override
    public JoinSystemInfo selectJoinSystemInfoById(String joinsysId)
    {
        return joinSystemInfoMapper.selectJoinSystemInfoById(joinsysId);
    }

    /**
     * 查询应用系统注册列表
     * 
     * @param joinSystemInfo 应用系统注册
     * @return 应用系统注册
     */
    @Override
    public List<JoinSystemInfo> selectJoinSystemInfoList(JoinSystemInfo joinSystemInfo)
    {
        return joinSystemInfoMapper.selectJoinSystemInfoList(joinSystemInfo);
    }

    /**
     * 新增应用系统注册
     * 
     * @param joinSystemInfo 应用系统注册
     * @return 结果
     */
    @Override
    public int insertJoinSystemInfo(JoinSystemInfo joinSystemInfo)
    {
        joinSystemInfo.setJoinsysId(IdUtil.fastSimpleUUID());
        //设置系统唯一标识
        joinSystemInfo.setJoinsysCode(IdUtil.fastSimpleUUID().substring(0,16));

        joinSystemInfo.setStatus("1");

        joinSystemInfo.setCreateTime(DateUtils.getNowDate());
        return joinSystemInfoMapper.insertJoinSystemInfo(joinSystemInfo);
    }

    /**
     * 修改应用系统注册
     * 
     * @param joinSystemInfo 应用系统注册
     * @return 结果
     */
    @Override
    public int updateJoinSystemInfo(JoinSystemInfo joinSystemInfo)
    {
        joinSystemInfo.setUpdateTime(DateUtils.getNowDate());
        return joinSystemInfoMapper.updateJoinSystemInfo(joinSystemInfo);
    }

    /**
     * 删除应用系统注册对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteJoinSystemInfoByIds(String ids)
    {
        return joinSystemInfoMapper.deleteJoinSystemInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除应用系统注册信息
     * 
     * @param joinsysId 应用系统注册ID
     * @return 结果
     */
    public int deleteJoinSystemInfoById(String joinsysId)
    {
        return joinSystemInfoMapper.deleteJoinSystemInfoById(joinsysId);
    }

    /**
     * 检验系统系统名称是否存在
     * */
    @Override
    public int checkSystemName(String systemName){
        return joinSystemInfoMapper.checkSystemName(systemName);
    }


    @Override
    public List<JoinSystemInfo> selectJoinSystemDataConfigInfoList(JoinSystemInfo joinSystemInfo) {
        return joinSystemInfoMapper.selectJoinSystemDataConfigInfoList(joinSystemInfo);
    }

    /**
     * 检验系统code 是否存在
     * */
    @Override
    public int checkSystemCode(String systemCode){
        return  joinSystemInfoMapper.checkSystemCode(systemCode);
    }

    @Override
    public JoinSystemInfo getSystemDataConfig(String systemCode) {
        return joinSystemInfoMapper.getSystemDataConfig(systemCode);
    }

    @Override
    public String getSystemRetUrl(String systemCode) {
        return joinSystemInfoMapper.getSystemRetUrl(systemCode);
    }
}
