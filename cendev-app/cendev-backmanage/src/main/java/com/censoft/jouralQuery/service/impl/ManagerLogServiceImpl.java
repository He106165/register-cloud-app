package com.censoft.jouralQuery.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import cn.hutool.core.util.IdUtil;
import com.censoft.jouralQuery.domain.ManagerLog;
import com.censoft.jouralQuery.mapper.ManagerLogMapper;
import com.censoft.jouralQuery.service.IManagerLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.censoft.common.core.text.Convert;

/**
 * 操作日志Service业务层处理
 *
 * @author cendev
 * @date 2020-10-21
 */
@Service
public class ManagerLogServiceImpl implements IManagerLogService {
    @Autowired
    private ManagerLogMapper managerLogMapper;

    /**
     * 查询操作日志
     *
     * @param id 操作日志ID
     * @return 操作日志
     */
    @Override
    public ManagerLog selectManagerLogById(String id) {
        return managerLogMapper.selectManagerLogById(id);
    }

    /**
     * 查询操作日志列表
     *
     * @param managerLog 操作日志
     * @return 操作日志
     */
    @Override
    public List<ManagerLog> selectManagerLogList(ManagerLog managerLog) {
        return managerLogMapper.selectManagerLogList(managerLog);
    }

    /**
     * 新增操作日志
     *
     * @param managerLog 操作日志
     * @return 结果
     */
    @Override
    public int insertManagerLog(ManagerLog managerLog) {
        managerLog.setId(IdUtil.fastSimpleUUID());
        managerLog.setOpTime(new Date());
        return managerLogMapper.insertManagerLog(managerLog);
    }

    /**
     * 修改操作日志
     *
     * @param managerLog 操作日志
     * @return 结果
     */
    @Override
    public int updateManagerLog(ManagerLog managerLog) {
        return managerLogMapper.updateManagerLog(managerLog);
    }

    /**
     * 删除操作日志对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteManagerLogByIds(String ids) {
        return managerLogMapper.deleteManagerLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除操作日志信息
     *
     * @param id 操作日志ID
     * @return 结果
     */
    public int deleteManagerLogById(String id) {
        return managerLogMapper.deleteManagerLogById(id);
    }

    /**
     * 获取个人用户今日活跃数
     *
     * @return
     */
    public Integer selectTodayUserSum() {
        Map map = new HashMap();
        //1.取当前时间
        Date date = new Date();
        //2.取今天开始的时间
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = dateFormat1.format(date);
        //3.取今天结束的时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = dateFormat.format(date);
        //4取个人用户今日活跃数,返回结果
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return managerLogMapper.selectTodayUserSum(map);
    }

    /**
     * 获取个人用户本月活跃数
     *
     * @return
     */
    @Override
    public Integer selectMonthPersonalSum() {
        Map map = new HashMap();
 /*       //1.取当前时间
        Date date = new Date();*/
        //2.获取本月的第一天
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String startTime = dateFormat1.format(cal.getTime());
        //3.获取本月最后一天
        /* cal.set(Calendar.DATE,1);*/
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
//        cal.roll(Calendar.DATE,1);
        String endTime = dateFormat1.format(cal.getTime());
        //4取个人用户本月活跃数,返回结果
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        return managerLogMapper.selectTodayUserSum(map);
    }

    /**
     * 获取个人用户本季活跃数
     *
     * @return
     */
    @Override
    public Integer selectQuarterPersonalSum() {
        Map map = new HashMap();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        //获取本季的第一天
        cal.set(Calendar.MONTH, getFirstMonthOfSeason(cal) - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String startTime = dateFormat1.format(cal.getTime());
        //获取当前时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String endTime = dateFormat.format(date);
        //4取个人用户本季活跃数,返回结果
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return managerLogMapper.selectTodayUserSum(map);
    }

    /**
     * 获取个人用户本年活跃数
     *
     * @return
     */
    @Override
    public Integer selectYearPersonalSum() {
        Map map = new HashMap();
        Calendar calendar = Calendar.getInstance();
        //获取本年的第一天
        String startTime = calendar.get(Calendar.YEAR) + "-01-01";
        //获取当前时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String endTime = dateFormat.format(date);
        //取个人用户本年活跃数,返回结果
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return managerLogMapper.selectTodayUserSum(map);
    }


    /**
     * 获取机构用户今日活跃数
     *
     * @return
     */
    public Integer selectTodayOrganSum() {

        Map map = new HashMap();
        //1.取当前时间
        Date date = new Date();
        //2.取今天开始的时间
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = dateFormat1.format(date);
        //3.取今天结束的时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = dateFormat.format(date);
        //4取个人用户活跃数,返回结果
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return managerLogMapper.selectTodayOrganSum(map);
    }

    /**
     * 获取机构用户本月活跃数
     *
     * @return
     */
    @Override
    public Integer selectMonthOrganSum() {
        Map map = new HashMap();
        //1.获取本月的第一天
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String startTime = dateFormat1.format(cal.getTime());
        //2.获取本月最后一天
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endTime = dateFormat1.format(cal.getTime());
        //3取个人用户本月活跃数,返回结果
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        return managerLogMapper.selectTodayOrganSum(map);
    }

    /**
     * 获取机构用户本季活跃数
     *
     * @return
     */
    @Override
    public Integer selectQuarterOrganSum() {
        Map map = new HashMap();
        //设置时间格式
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        //得到日历
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, getFirstMonthOfSeason(cal) - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //获取本季的第一天
        String startTime = dateFormat1.format(cal.getTime());
        //获取当前时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String endTime = dateFormat.format(date);
        //4取个人用户本月活跃数,返回结果
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return managerLogMapper.selectTodayOrganSum(map);
    }

    /**
     * 获取本年机构用户活跃数
     *
     * @return
     */
    public Integer selectYearOrganSum() {
        Map map = new HashMap();
        Calendar calendar = Calendar.getInstance();
        //获取本年的第一天
        String startTime = calendar.get(Calendar.YEAR) + "-01-01";
        //获取当前时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String endTime = dateFormat.format(date);
        //取个人用户本年活跃数,返回结果
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return managerLogMapper.selectTodayOrganSum(map);
    }

    public Integer getFirstMonthOfSeason(Calendar cal) {
        //获取本季的第一个月
        Integer currentMonth = cal.get(Calendar.MONTH) + 1;
        if (1 <= currentMonth && currentMonth <= 3) {
            return 1;
        } else if (4 <= currentMonth && currentMonth <= 6) {
            return 4;
        } else if (7 <= currentMonth && currentMonth <= 9) {
            return 7;
        } else {
            return 10;
        }
    }

}

