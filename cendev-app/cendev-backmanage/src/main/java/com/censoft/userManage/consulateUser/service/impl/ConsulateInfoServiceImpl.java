package com.censoft.userManage.consulateUser.service.impl;

import com.censoft.common.core.text.Convert;
import com.censoft.common.utils.DateUtils;
import com.censoft.userManage.consulateUser.domain.ConsulateInfo;
import com.censoft.userManage.consulateUser.mapper.ConsulateInfoMapper;
import com.censoft.userManage.consulateUser.service.IConsulateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 领使馆用户Service业务层处理
 *
 * @author cendev
 * @date 2020-10-20
 */
@Service
public class ConsulateInfoServiceImpl implements IConsulateInfoService
{
    @Autowired
    private ConsulateInfoMapper consulateInfoMapper;

    /**
     * 查询领使馆用户
     *
     * @param id 领使馆用户ID
     * @return 领使馆用户
     */
    @Override
    public ConsulateInfo selectConsulateInfoById(Long id)
    {
        return consulateInfoMapper.selectConsulateInfoById(id);
    }

    /**
     * 查询领使馆用户列表
     *
     * @param consulateInfo 领使馆用户
     * @return 领使馆用户
     */
    @Override
    public List<ConsulateInfo> selectConsulateInfoList(ConsulateInfo consulateInfo)
    {
        return consulateInfoMapper.selectConsulateInfoList(consulateInfo);
    }

    /**
     * 新增领使馆用户
     *
     * @param consulateInfo 领使馆用户
     * @return 结果
     */
    @Override
    public int insertConsulateInfo(ConsulateInfo consulateInfo)
    {
        consulateInfo.setCreateTime(DateUtils.getNowDate());
        return consulateInfoMapper.insertConsulateInfo(consulateInfo);
    }

    /**
     * 修改领使馆用户
     *
     * @param consulateInfo 领使馆用户
     * @return 结果
     */
    @Override
    public int updateConsulateInfo(ConsulateInfo consulateInfo)
    {
        consulateInfo.setUpdateTime(DateUtils.getNowDate());
        return consulateInfoMapper.updateConsulateInfo(consulateInfo);
    }

    /**
     * 删除领使馆用户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteConsulateInfoByIds(String ids)
    {
        return consulateInfoMapper.deleteConsulateInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除领使馆用户信息
     *
     * @param id 领使馆用户ID
     * @return 结果
     */
    public int deleteConsulateInfoById(String id)
    {
        return consulateInfoMapper.deleteConsulateInfoById(id);
    }

    /**
     *  根据用户名获取第一条数据 （登陆）
     * */
    @Override
    public ConsulateInfo getUserByName(String userName){
        return consulateInfoMapper.getUserByName(userName);
    }


    @Override
    public Map getConUserById(Long id) {
        return consulateInfoMapper.getConUserById(id);
    }
}
