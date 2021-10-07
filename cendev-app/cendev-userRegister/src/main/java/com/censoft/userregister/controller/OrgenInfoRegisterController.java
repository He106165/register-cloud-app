package com.censoft.userregister.controller;

import com.censoft.userregister.domain.*;
import com.censoft.userregister.service.OrgenInfoRegisterService;
import com.censoft.userregister.service.SmsService;
import com.censoft.userregister.util.ResultStatusEnum;
import com.censoft.userregister.util.ResultUtil;
import com.censoft.userregister.util.ValidUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人:wqgeng
 * @创建时间:2020-11-2111:01
 * @描述:机构信息注册
 */
@RestController
@RequestMapping("orgenRegister")
@Api("用户注册界面")
public class OrgenInfoRegisterController {

    @Autowired
    private OrgenInfoRegisterService orgenInfoRegisterService;
    @Autowired
    private SmsService smsService;

    @ApiOperation(value = "机构注册", notes = "机构注册")
    @PostMapping("orgenInfo")
    public ResultUtil addSave(@RequestBody @Valid OrgenUserInfo orgenUserInfo, BindingResult result) {
        //获取后台校验信息（注解的方式）
        if (result.hasErrors()) {
            return ValidUtil.getDefaultMsg(result);
        }
        //机构注册
        return orgenInfoRegisterService.addSave(orgenUserInfo);
    }

//    @ApiOperation(value = "机构注册(海外学联)", notes = "机构注册(海外学联)")
//    @PostMapping("orgenInfo")
//    public ResultUtil addSaveHwxl(@RequestBody @Valid OrgenUserInfo orgenUserInfo, BindingResult result) {
//        //获取后台校验信息（注解的方式）
//        if (result.hasErrors()) {
//            return ValidUtil.getDefaultMsg(result);
//        }
//        //机构注册
//        return orgenInfoRegisterService.addSaveHwxl(orgenUserInfo);
//    }

    @ApiOperation(value = "个人注册发送短信/邮件", notes = "个人注册发送短信/邮件")
    @PostMapping("ogenSend")
    public ResultUtil ogenSend(@RequestBody Map<String, String> map) {

        boolean ok = smsService.ogenSend(map);

        if (ok) {
            return ResultUtil.result(ResultStatusEnum.S_10009.getStatus(), ResultStatusEnum.S_10009.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10010.getStatus(), ResultStatusEnum.S_10010.getMassage());
        }

    }

    @ApiOperation(value = "查询法人类型", notes = "查询法人类型")
    @PostMapping("queryPerManType")
    public ResultUtil queryPerManType() {
        return orgenInfoRegisterService.queryPerManType();
    }

    @ApiOperation(value = "查询法人性质", notes = "查询法人性质")
    @PostMapping("queryperManLine")
    public ResultUtil queryperManLine() {
        return orgenInfoRegisterService.queryperManLine();
    }

    @ApiOperation(value = "查询部门类型", notes = "查询部门类型")
    @PostMapping("queryDepermentCode")
    public ResultUtil queryDepermentCode() {
        return orgenInfoRegisterService.queryDepermentCode();
    }

    @ApiOperation(value = "查询使领馆教育处", notes = "查询使领馆教育处")
    @PostMapping("queryConsulateInfo")
    public ResultUtil queryConsulateInfo() {
        return orgenInfoRegisterService.queryConsulateInfo();
    }

    @ApiOperation(value = "国内企业，证件号码验证唯一性", notes = "国内企业证件号码验证唯一性")
    @PostMapping("valiIdType")
    public ResultUtil valiIdType(@RequestBody OrgenUserInfo orgenUserInfo) {
        return orgenInfoRegisterService.valiIdType(orgenUserInfo);
    }

    @ApiOperation("国内企业验证邮箱验证码(国内企业)")
    @PostMapping("checkValidateCodeOrg1")
    public ResultUtil checkValidateCodeOrg1(@RequestBody PhoneEmailBo phoneEmailBo) {
        boolean check = smsService.checkValidateCodeOrg1(phoneEmailBo);
        if (check) {
            return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10006.getStatus(), ResultStatusEnum.S_10006.getMassage());
        }
    }

    @ApiOperation("国内企业验证手机验证码(海外机构)")
    @PostMapping("checkValidateCodeOrg2")
    public ResultUtil checkValidateCodeOrg2(@RequestBody PhoneEmailBo phoneEmailBo) {
        boolean check = smsService.checkValidateCodeOrg2(phoneEmailBo);
        if (check) {
            return ResultUtil.result(ResultStatusEnum.S_10023.getStatus(), ResultStatusEnum.S_10023.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10022.getStatus(), ResultStatusEnum.S_10022.getMassage());
        }
    }

    @ApiOperation("国内企业验证手机验证码(海外学联)")
    @PostMapping("checkValidateCodeOrg3")
    public ResultUtil checkValidateCodeOrg3(@RequestBody PhoneEmailBo phoneEmailBo) {
        boolean check = smsService.checkValidateCodeOrg3(phoneEmailBo);
        if (check) {
            return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10006.getStatus(), ResultStatusEnum.S_10006.getMassage());
        }
    }
    @ApiOperation(value = "判断邮箱是否已经注册", notes = "判断邮箱是否已经注册")
    @PostMapping("orgenEmailCount")
    public ResultUtil orgenEmailCount(@RequestBody OrgenUserLogin orgenUserLogin) {
        return orgenInfoRegisterService.orgenEmailCount(orgenUserLogin);
    }

    @ApiOperation(value = "判断邮箱是否已经注册(机构用户找回密码)", notes = "判断邮箱是否已经注册(机构用户找回密码)")
    @PostMapping("orgenEmailPassCount")
    public ResultUtil orgenEmailPassCount(@RequestBody OrgenUserLogin orgenUserLogin) {
        return orgenInfoRegisterService.orgenEmailPassCount(orgenUserLogin);
    }

    @ApiOperation(value = "发送邮件(机构用户找回密码)", notes = "发送邮件(机构用户找回密码)")
    @PostMapping("ogenForgetSend")
    public ResultUtil ogenForgetSend(@RequestBody Map<String, String> map) {
        boolean ok = smsService.ogenForgetSend(map);
        if (ok) {
            return ResultUtil.result(ResultStatusEnum.S_10009.getStatus(), ResultStatusEnum.S_10009.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10010.getStatus(), ResultStatusEnum.S_10010.getMassage());
        }
    }

    @ApiOperation("验证邮箱验证码(机构用户找回密码)")
    @PostMapping("orgenForgetValidateEmailCode")
    public ResultUtil orgenForgetValidateEmailCode(@RequestBody PhoneEmailBo phoneEmailBo) {
        boolean check = smsService.orgenForgetValidateEmailCode(phoneEmailBo);
        if (check) {
            return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10006.getStatus(), ResultStatusEnum.S_10006.getMassage());
        }
    }

    @ApiOperation(value = "修改邮箱密码(机构用户找回密码)", notes = "修改邮箱密码(机构用户找回密码)")
    @PostMapping("updateOrgenEmailPassword")
    public ResultUtil updateOrgenEmailPassword(@RequestBody OrgenUserLogin orgenUserLogin) {
        return orgenInfoRegisterService.updateOrgenEmailPassword(orgenUserLogin);
    }
}
