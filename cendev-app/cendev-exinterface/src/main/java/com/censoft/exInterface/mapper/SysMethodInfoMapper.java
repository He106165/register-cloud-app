package com.censoft.exInterface.mapper;

import com.censoft.exInterface.domain.SysMethodInfo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 对外接口管理Mapper接口
 *
 * @author cendev
 * @date 2020-10-20
 */
@Mapper
public interface SysMethodInfoMapper {
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
     * 删除对外接口管理
     *
     * @param sysmethodId 对外接口管理ID
     * @return 结果
     */
    public int deleteSysMethodInfoById(String sysmethodId);

    /**
     * 批量删除对外接口管理
     *
     * @param sysmethodIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysMethodInfoByIds(String[] sysmethodIds);
}
