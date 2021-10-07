package com.censoft.exInterface.service;

import com.censoft.exInterface.domain.SysMethodInfo;

import java.util.List;
import java.util.Map;

/**
 * 对外接口管理Service接口
 *
 * @author cendev
 * @date 2020-10-20
 */
public interface ISysMethodInfoService {
    /**
     * 查询对外接口管理
     *
     * @param sysmethodId 对外接口管理ID
     * @return 对外接口管理
     */
    public SysMethodInfo selectSysMethodInfoById(String sysmethodId);

    /**
     * 查询对外接口管理列表
     *
     * @param sysMethodInfo 对外接口管理
     * @return 对外接口管理集合
     */
    public List<SysMethodInfo> selectSysMethodInfoList(SysMethodInfo sysMethodInfo);

    /**
     * 新增对外接口管理
     *
     * @param sysMethodInfo 对外接口管理
     * @return 结果
     */
    public int insertSysMethodInfo(SysMethodInfo sysMethodInfo);

    /**
     * 修改对外接口管理
     *
     * @param sysMethodInfo 对外接口管理
     * @return 结果
     */
    public int updateSysMethodInfo(SysMethodInfo sysMethodInfo);

    /**
     * 批量删除对外接口管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysMethodInfoByIds(String ids);

    /**
     * 删除对外接口管理信息
     *
     * @param sysmethodId 对外接口管理ID
     * @return 结果
     */
    public int deleteSysMethodInfoById(String sysmethodId);


    /*通过token获取用户对象*/
    public Map getUserInfo(Map map);

    /**
    * @Description 通过userId获取用户信息
    * @Parm
    * @return
    **/
    public Map getUserInfoByUserId(Map map);
}
