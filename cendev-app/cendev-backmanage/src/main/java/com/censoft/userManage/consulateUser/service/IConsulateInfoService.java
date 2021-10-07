package com.censoft.userManage.consulateUser.service;

import com.censoft.userManage.consulateUser.domain.ConsulateInfo;
import java.util.List;
import java.util.Map;

/**
 * 领使馆用户Service接口
 *
 * @author cendev
 * @date 2020-10-20
 */
public interface IConsulateInfoService
{
    /**
     * 查询领使馆用户
     *
     * @param id 领使馆用户ID
     * @return 领使馆用户
     */
    public ConsulateInfo selectConsulateInfoById(Long id);

    /**
     *  业务系统之间传递教育处用户方法
     * */
    public Map getConUserById(Long id);
    /**
     * 查询领使馆用户列表
     *
     * @param consulateInfo 领使馆用户
     * @return 领使馆用户集合
     */
    public List<ConsulateInfo> selectConsulateInfoList(ConsulateInfo consulateInfo);

    /**
     * 新增领使馆用户
     *
     * @param consulateInfo 领使馆用户
     * @return 结果
     */
    public int insertConsulateInfo(ConsulateInfo consulateInfo);

    /**
     * 修改领使馆用户
     *
     * @param consulateInfo 领使馆用户
     * @return 结果
     */
    public int updateConsulateInfo(ConsulateInfo consulateInfo);

    /**
     * 批量删除领使馆用户
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteConsulateInfoByIds(String ids);

    /**
     * 删除领使馆用户信息
     *
     * @param id 领使馆用户ID
     * @return 结果
     */
    public int deleteConsulateInfoById(String id);


    /**
     *  根据用户名获取第一条数据 （登陆）
     * */
    public ConsulateInfo getUserByName(String userName);
}
