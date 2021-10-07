package com.censoft.userManage.orgenUser.controller;

import com.censoft.userManage.personalUser.domain.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.userManage.orgenUser.domain.OrgenUserInfo;
import com.censoft.userManage.orgenUser.service.IOrgenUserInfoService;

/**
 * 机构用户 提供者
 *
 * @author cendev
 * @date 2020-10-20
 */
@RestController
@RequestMapping("orgenUser")
public class OrgenUserInfoController extends BaseController {

    @Autowired
    private IOrgenUserInfoService orgenUserInfoService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{orgUserId}")
    public OrgenUserInfo get(@PathVariable("orgUserId") String orgUserId) {
        return orgenUserInfoService.selectOrgenUserInfoById(orgUserId);

    }

    /**
     * 查询机构用户列表
     */
    @GetMapping("list")
    public R list(OrgenUserInfo orgenUserInfo) {
        startPage();
        return result(orgenUserInfoService.selectOrgenUserInfoList(orgenUserInfo));
    }


    /**
     * 新增保存机构用户
     */
    @PostMapping("save")
    public R addSave(@RequestBody OrgenUserInfo orgenUserInfo) {
        return toAjax(orgenUserInfoService.insertOrgenUserInfo(orgenUserInfo));
    }

    /**
     * 修改保存机构用户
     */
    @PostMapping("update")
    public R editSave(@RequestBody OrgenUserInfo orgenUserInfo) {
        return toAjax(orgenUserInfoService.updateOrgenUserInfo(orgenUserInfo));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(orgenUserInfoService.deleteOrgenUserInfoByIds(ids));
    }

    //通过调接口的实名认证
    @PostMapping("authentication")
    public ResultUtil authentication(@RequestParam("legalName") String legalName,
                                     @RequestParam("legalIdnumber") String legalIdnumber) {

        return orgenUserInfoService.authentication(legalName, legalIdnumber);
    }

    //不通过调接口的方式实现实名认证
    @PostMapping("orgenAuthentication")
    public ResultUtil orgenAuthentication(@RequestParam("orgUserId") String orgUserId) {

        return orgenUserInfoService.orgenAuthentication(orgUserId);
    }

    //不通过调接口的方式实现实名认证
    @PostMapping("federationApprove")
    public ResultUtil federationApprove(@RequestParam("orgUserId") String orgUserId) {

        return orgenUserInfoService.federationApprove(orgUserId);
    }

    /**
     * 查询机构用户今日新增数
     *
     * @param
     * @return结果
     */
    @PostMapping("/selectNewDayOrgenSum")
    // @ResponseBody
    public int selectNewPerson() {

        return orgenUserInfoService.selectNewAddOrgenSum();
    }

    /**
     * 查询机构用户本月新增的总数
     *
     * @return
     */
    @PostMapping("/selectNewMonthPersonalSum")
    public int selectNewMonthOrgenSum() {

        return orgenUserInfoService.selectNewMonthOrgenSum();
    }

    /**
     * 查询机构用户本年新增的总数
     *
     * @return
     */
    @PostMapping("/selectYearOrgenSum")
    public Integer selectYearOrgenSum() {
        return orgenUserInfoService.selectYearOrgenSum();
    }

    /**
     * 查询机构用户的总数
     *
     * @return
     */
    @PostMapping("/selectOrgenPersonSum")
    public Integer selectOrgenPersonSum() {
        return orgenUserInfoService.selectOrgenPersonSum();
    }

}
