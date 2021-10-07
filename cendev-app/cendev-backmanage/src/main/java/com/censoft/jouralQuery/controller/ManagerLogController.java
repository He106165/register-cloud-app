package com.censoft.jouralQuery.controller;

import com.censoft.jouralQuery.domain.ManagerLog;
import com.censoft.jouralQuery.service.IManagerLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作日志 提供者
 *
 * @author cendev
 * @date 2020-10-21
 */
@RestController
@RequestMapping("managerLog")
public class ManagerLogController extends BaseController {

    @Autowired
    private IManagerLogService managerLogService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{id}")
    public ManagerLog get(@PathVariable("id") String id) {
        return managerLogService.selectManagerLogById(id);

    }

    /**
     * 查询操作日志列表
     */
    @GetMapping("list")
    public R list(ManagerLog managerLog) {
        startPage();
        return result(managerLogService.selectManagerLogList(managerLog));
    }


    /**
     * 新增保存操作日志
     */
    @PostMapping("save")
    public R addSave(@RequestBody ManagerLog managerLog) {
        return toAjax(managerLogService.insertManagerLog(managerLog));
    }

    /**
     * 修改保存操作日志
     */
    @PostMapping("update")
    public R editSave(@RequestBody ManagerLog managerLog) {
        return toAjax(managerLogService.updateManagerLog(managerLog));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(managerLogService.deleteManagerLogByIds(ids));
    }

    /**
     * 查询今日个人用户活跃数
     *
     * @param
     * @return结果
     */
    @PostMapping("/selectTodayUserSum")
    @ResponseBody
    public Integer selectTodayUserSum() {
        //1.获取今天的日期
        //2.取今天的开始结束时间
        //3.调用查询方法
        return managerLogService.selectTodayUserSum();
    }

    /**
     * 查询本月个人用户活跃数
     *
     * @param
     * @return结果
     */
    @PostMapping("/selectMonthPersonalSum")
    @ResponseBody
    public Integer selectMonthPersonalSum() {
        //1.获取今天的日期
        //2.取今天的开始结束时间
        //3.调用查询方法
        return managerLogService.selectMonthPersonalSum();
    }

    /**
     * 查询本季个人用户活跃数
     *
     * @param
     * @return结果
     */
    @PostMapping("/selectQuarterPersonalSum")
    @ResponseBody
    public Integer selectQuarterPersonalSum() {
        //1.获取今天的日期
        //2.取今天的开始结束时间
        //3.调用查询方法
        return managerLogService.selectQuarterPersonalSum();
    }

    /**
     * 查询本年个人用户活跃数
     *
     * @param
     * @return结果
     */
    @PostMapping("/selectYearPersonalSum")
    @ResponseBody
    public Integer selectYearPersonalSum() {
        //1.获取今天的日期
        //2.取今天的开始结束时间
        //3.调用查询方法
        return managerLogService.selectYearPersonalSum();
    }

    /**
     * 查询今日机构用户活跃数
     *
     * @param
     * @return结果
     */
    @PostMapping("/selectTodayOrganSum")
    @ResponseBody
    public Integer selectTodayOrganSum() {
        //1.获取今天的日期
        //2.取今天的开始结束时间
        //3.调用查询方法
        return managerLogService.selectTodayOrganSum();
    }

    /**
     * 查询本月机构用户活跃数
     *
     * @param
     * @return结果
     */
    @PostMapping("/selectMonthOrganSum")
    @ResponseBody
    public Integer selectMonthOrganSum() {
        //1.获取今天的日期
        //2.取今天的开始结束时间
        //3.调用查询方法
        return managerLogService.selectMonthOrganSum();
    }

    /**
     * 查询本季机构用户活跃数
     *
     * @param
     * @return结果
     */
    @PostMapping("/selectQuarterOrganSum")
    @ResponseBody
    public Integer selectQuarterOrganSum() {
        //1.获取今天的日期
        //2.取今天的开始结束时间
        //3.调用查询方法
        return managerLogService.selectQuarterOrganSum();
    }

    /**
     * 查询本年个人用户活跃数
     *
     * @param
     * @return结果
     */
    @PostMapping("/selectYearOrganSum")
    @ResponseBody
    public Integer selectYearOrganSum() {
        //1.获取今天的日期
        //2.取今天的开始结束时间
        //3.调用查询方法
        return managerLogService.selectYearOrganSum();
    }

}

