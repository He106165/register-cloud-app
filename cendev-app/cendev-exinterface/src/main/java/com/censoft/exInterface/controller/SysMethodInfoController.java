package com.censoft.exInterface.controller;

import com.censoft.exInterface.domain.SysMethodInfo;
import com.censoft.exInterface.service.ISysMethodInfoService;
import com.censoft.otherlogin.feign.RemoteTokenServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;

import java.util.Map;

/**
 * 对外接口管理 提供者
 *
 * @author cendev
 * @date 2020-10-20
 */
@RestController
@RequestMapping("exInterface")
public class SysMethodInfoController extends BaseController {

    @Autowired
    private ISysMethodInfoService sysMethodInfoService;


    @Autowired
    private RemoteTokenServer tokenService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{sysmethodId}")
    public SysMethodInfo get(@PathVariable("sysmethodId") String sysmethodId) {
        return sysMethodInfoService.selectSysMethodInfoById(sysmethodId);

    }

    /**
     * 查询对外接口管理列表
     */
    @GetMapping("list")
    public R list(SysMethodInfo sysMethodInfo) {
        startPage();
        return result(sysMethodInfoService.selectSysMethodInfoList(sysMethodInfo));
    }


    /**
     * 新增保存对外接口管理
     */
    @PostMapping("save")
    public R addSave(@RequestBody SysMethodInfo sysMethodInfo) {
        return toAjax(sysMethodInfoService.insertSysMethodInfo(sysMethodInfo));
    }

    /**
     * 修改保存对外接口管理
     */
    @PostMapping("update")
    public R editSave(@RequestBody SysMethodInfo sysMethodInfo) {
        return toAjax(sysMethodInfoService.updateSysMethodInfo(sysMethodInfo));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(sysMethodInfoService.deleteSysMethodInfoByIds(ids));
    }

    @GetMapping("getToken")
    public String getToken(String accoutName) {
        return tokenService.getToken(accoutName);
    }

    @GetMapping("getAccoutName")
    public String getAccoutName(String token) {
        return "null";
    }

    @GetMapping("checkToken")
    public String checkToken(String token) {
        return "null";
    }

    @RequestMapping(value = "getTokenInfo", method = {RequestMethod.POST})
    public Map getTokenInfo(@RequestParam Map map) {

        return sysMethodInfoService.getUserInfo(map);
    }


    @RequestMapping(value = "getUserInfoByUserId", method = {RequestMethod.POST})
    public Map getUserInfoByUserId(@RequestParam Map map) {

        return sysMethodInfoService.getUserInfoByUserId(map);
    }


}
