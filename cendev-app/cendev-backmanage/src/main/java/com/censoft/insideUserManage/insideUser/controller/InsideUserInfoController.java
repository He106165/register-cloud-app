package com.censoft.insideUserManage.insideUser.controller;

import com.censoft.insideUserManage.insideUser.domain.InsideUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.insideUserManage.insideUser.service.IInsideUserInfoService;

/**
 * 内部用户 提供者
 *
 * @author cendev
 * @date 2020-10-16
 */
@RestController
@RequestMapping("insideUser")
public class InsideUserInfoController extends BaseController {

    @Autowired
    private IInsideUserInfoService insideUserInfoService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{insideUserId}")
    public InsideUserInfo get(@PathVariable("insideUserId") String insideUserId) {
        return insideUserInfoService.selectInsideUserInfoById(insideUserId);

    }

    /**
     * 查询内部用户列表
     */
    @GetMapping("list")
    public R list(InsideUserInfo insideUserInfo) {
        startPage();
        return result(insideUserInfoService.selectInsideUserInfoList(insideUserInfo));
    }


    /**
     * 新增保存内部用户
     */
    @PostMapping("save")
    public R addSave(@RequestBody InsideUserInfo insideUserInfo) {
        return toAjax(insideUserInfoService.insertInsideUserInfo(insideUserInfo));
    }

    /**
     * 修改保存内部用户
     */
    @PostMapping("update")
    public R editSave(@RequestBody InsideUserInfo insideUserInfo) {
        return toAjax(insideUserInfoService.updateInsideUserInfo(insideUserInfo));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(insideUserInfoService.deleteInsideUserInfoByIds(ids));
    }

}
