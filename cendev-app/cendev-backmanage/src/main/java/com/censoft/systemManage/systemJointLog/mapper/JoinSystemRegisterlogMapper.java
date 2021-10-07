package com.censoft.systemManage.systemJointLog.mapper;

import com.censoft.systemManage.systemJointLog.domain.JoinSystemRegisterlog;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 系统对接日志Mapper接口
 *
 * @author cendev
 * @date 2020-10-20
 */
@Mapper
public interface JoinSystemRegisterlogMapper {
    /**
     * 查询系统对接日志
     *
     * @param id 系统对接日志ID
     * @return 系统对接日志
     */
    public JoinSystemRegisterlog selectJoinSystemRegisterlogById(String id);

    /**
     * 查询系统对接日志列表
     *
     * @param joinSystemRegisterlog 系统对接日志
     * @return 系统对接日志集合
     */
    public List<JoinSystemRegisterlog> selectJoinSystemRegisterlogList(JoinSystemRegisterlog joinSystemRegisterlog);

    /**
     * 新增系统对接日志
     *
     * @param joinSystemRegisterlog 系统对接日志
     * @return 结果
     */
    public int insertJoinSystemRegisterlog(JoinSystemRegisterlog joinSystemRegisterlog);

    /**
     * 修改系统对接日志
     *
     * @param joinSystemRegisterlog 系统对接日志
     * @return 结果
     */
    public int updateJoinSystemRegisterlog(JoinSystemRegisterlog joinSystemRegisterlog);

    /**
     * 删除系统对接日志
     *
     * @param id 系统对接日志ID
     * @return 结果
     */
    public int deleteJoinSystemRegisterlogById(String id);

    /**
     * 批量删除系统对接日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteJoinSystemRegisterlogByIds(String[] ids);

    List<Map> selectJoinSystemInterfaceNum(Map map);

}
