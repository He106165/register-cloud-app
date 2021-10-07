package com.censoft.jouralQuery.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.censoft.jouralQuery.domain.ManagerLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 操作日志Mapper接口
 *
 * @author cendev
 * @date 2020-10-21
 */
@Mapper
public interface ManagerLogMapper {
    /**
     * 查询操作日志
     *
     * @param id 操作日志ID
     * @return 操作日志
     */
    public ManagerLog selectManagerLogById(String id);

    /**
     * 查询操作日志列表
     *
     * @param managerLog 操作日志
     * @return 操作日志集合
     */
    public List<ManagerLog> selectManagerLogList(ManagerLog managerLog);

    /**
     * 新增操作日志
     *
     * @param managerLog 操作日志
     * @return 结果
     */
    public int insertManagerLog(ManagerLog managerLog);

    /**
     * 修改操作日志
     *
     * @param managerLog 操作日志
     * @return 结果
     */
    public int updateManagerLog(ManagerLog managerLog);

    /**
     * 删除操作日志
     *
     * @param id 操作日志ID
     * @return 结果
     */
    public int deleteManagerLogById(String id);

    /**
     * 批量删除操作日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteManagerLogByIds(String[] ids);

    /**
     * 获取个人用户今日活跃数
     *
     * @param map
     * @return
     */
    Integer selectTodayUserSum(Map map);
    /* *//**
     * 获取个人用户本月活跃数
     * @param map
     * @return
     *//*
    public Map selectMonthPersonalSum(Map map);*/

    /**
     * 获取机构用户今日活跃数
     *
     * @return
     */
    public Integer selectTodayOrganSum(Map map);

    /*  *//**
     * 获取机构用户本月活跃数
     * @return
     *//*
    public  Map selectMonthOrganSum(Map map);*/

}
