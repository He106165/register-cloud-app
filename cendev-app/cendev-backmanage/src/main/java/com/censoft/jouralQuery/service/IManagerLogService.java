package com.censoft.jouralQuery.service;

import com.censoft.jouralQuery.domain.ManagerLog;

import java.util.List;
import java.util.Map;

/**
 * 操作日志Service接口
 *
 * @author cendev
 * @date 2020-10-21
 */
public interface IManagerLogService {
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
     * 批量删除操作日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteManagerLogByIds(String ids);

    /**
     * 删除操作日志信息
     *
     * @param id 操作日志ID
     * @return 结果
     */
    public int deleteManagerLogById(String id);

    /**
     * 获取今日个人用户活跃数
     *
     * @return
     */
    public Integer selectTodayUserSum();

    /**
     * 获取本月个人用户活跃数
     *
     * @return
     */
    public Integer selectMonthPersonalSum();

    /**
     * 获取本季个人用户活跃数
     *
     * @return
     */
    Integer selectQuarterPersonalSum();

    /**
     * 获取本年个人用户活跃数
     *
     * @return
     */
    Integer selectYearPersonalSum();

    /**
     * 获取今日机构用户活跃数
     *
     * @return
     */
    public Integer selectTodayOrganSum();

    /**
     * 获取本月机构用户活跃数
     *
     * @return
     */
    Integer selectMonthOrganSum();

    /**
     * 获取本季机构用户活跃数
     *
     * @return
     */
    Integer selectQuarterOrganSum();

    /**
     * 获取本年机构用户活跃数
     *
     * @return
     */
    Integer selectYearOrganSum();
}
