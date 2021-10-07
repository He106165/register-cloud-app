package com.censoft.userManage.personalUser.controller;

import com.censoft.userManage.personalUser.domain.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.userManage.personalUser.domain.PersonalUserInfo;
import com.censoft.userManage.personalUser.service.IPersonalUserInfoService;

import java.util.List;

/**
 * 个人用户信息 提供者
 *
 * @author cendev
 * @date 2020-10-20
 */
@RestController
@RequestMapping("personalUser")
public class PersonalUserInfoController extends BaseController {

    @Autowired
    private IPersonalUserInfoService personalUserInfoService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{userId}")
    public PersonalUserInfo get(@PathVariable("userId") String userId) {
        return personalUserInfoService.selectPersonalUserInfoById(userId);

    }

    /**
     * 查询个人用户信息列表
     */
    @GetMapping("list")
    public R list(PersonalUserInfo personalUserInfo) {
        startPage();
        return result(personalUserInfoService.selectPersonalUserInfoList(personalUserInfo));
    }


    /**
     * 新增保存个人用户信息
     */
    @PostMapping("save")
    public R addSave(@RequestBody PersonalUserInfo personalUserInfo) {
        return toAjax(personalUserInfoService.insertPersonalUserInfo(personalUserInfo));
    }

    /**
     * 修改保存个人用户信息
     */
    @PostMapping("update")
    public R editSave(@RequestBody PersonalUserInfo personalUserInfo) {
        return toAjax(personalUserInfoService.updatePersonalUserInfo(personalUserInfo));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(personalUserInfoService.deletePersonalUserInfoByIds(ids));
    }

    //	@ApiOperation(value = "实名认证", notes = "实名认证")
    // 调接口实名认证
    @PostMapping("authentication")
    public ResultUtil authentication(@RequestParam("userName") String userName,
                                     @RequestParam("cardNo") String cardNo) {

        return personalUserInfoService.authentication(userName, cardNo);
    }

    // 通过不调接口的方式实名认证
    @PostMapping("personalAuthentication")
    public ResultUtil personalAuthentication(@RequestParam("userId") String userId) {

        return personalUserInfoService.personalAuthentication(userId);
    }

    /**
     * 查询个人用户今日新增数
     *
     * @param
     * @return结果
     */
    @PostMapping("/selectNewAddPersonalSum")
    // @ResponseBody
    public Integer selectNewPerson() {

        return personalUserInfoService.selectNewAddPersonalSum();
    }

    /**
     * 查询个人用户本月新增的总数
     *
     * @return
     */
    @PostMapping("/selectNewMonthPersonalSum")
    public Integer selectNewMonthPersonalSum() {
        return personalUserInfoService.selectNewMonthPersonalSum();
    }

    /**
     * 查询个人用户本年新增的总数
     *
     * @return
     */
    @PostMapping("/selectYearPersonalSum")
    public Integer selectYearPersonalSum() {
        return personalUserInfoService.selectYearPersonalSum();
    }

    /**
     * 查询个人用户的总数
     *
     * @return
     */
    @PostMapping("/selectPersonalSum")
    public Integer selectPersonalSum() {

        return personalUserInfoService.selectPersonalSum();
    }

    /**
     * 查询最近一周的用户新增数
     *
     * @return
     */
    @PostMapping("/selectWeekPersonalNum")
    public List selectWeekPersonalNum() {

        return personalUserInfoService.selectWeekPersonalNum();

    }

}
