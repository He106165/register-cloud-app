package com.censoft.insideUserManage.insideSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.insideUserManage.insideSystem.domain.InsideSystemInfo;
import com.censoft.insideUserManage.insideSystem.service.IInsideSystemInfoService;

import java.util.List;

/**
 * 内部系统注册 提供者
 *
 * @author cendev
 * @date 2020-10-19
 */
@RestController
@RequestMapping("InsideSystem")
public class InsideSystemInfoController extends BaseController {

    @Autowired
    private IInsideSystemInfoService insideSystemInfoService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{insideSystemId}")
    public InsideSystemInfo get(@PathVariable("insideSystemId") String insideSystemId) {
        return insideSystemInfoService.selectInsideSystemInfoById(insideSystemId);

    }

    /**
     * 查询内部系统注册列表
     */
    @GetMapping("list")
    public R list(InsideSystemInfo insideSystemInfo) {
        startPage();
        return result(insideSystemInfoService.selectInsideSystemInfoList(insideSystemInfo));
    }

    /**
     * 查询内部系统注册列表
     */
    @GetMapping("getSystemList")
    public List<InsideSystemInfo> getSystemList(InsideSystemInfo insideSystemInfo) {
        return insideSystemInfoService.selectInsideSystemInfoList(insideSystemInfo);
    }


    /**
     * 新增保存内部系统注册
     */
    @PostMapping("save")
    public R addSave(@RequestBody InsideSystemInfo insideSystemInfo) {
        return toAjax(insideSystemInfoService.insertInsideSystemInfo(insideSystemInfo));
    }

    /**
     * 修改保存内部系统注册
     */
    @PostMapping("update")
    public R editSave(@RequestBody InsideSystemInfo insideSystemInfo) {
        return toAjax(insideSystemInfoService.updateInsideSystemInfo(insideSystemInfo));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(insideSystemInfoService.deleteInsideSystemInfoByIds(ids));
    }

}
