package com.censoft.userregister.controller;

import com.censoft.userregister.domain.FindPersonalInfo;
import com.censoft.userregister.domain.PersonalUserLogin;
import com.censoft.userregister.domain.PhoneEmailBo;
import com.censoft.userregister.service.ForgetPasswordService;
import com.censoft.userregister.service.SmsService;
import com.censoft.userregister.util.ResultStatusEnum;
import com.censoft.userregister.util.ResultUtil;
import com.censoft.userregister.util.ValidUtil;
import com.sun.rowset.internal.WebRowSetXmlReader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@创建人:wqgeng
 *@创建时间:2020-12-0209:39
 *@描述:忘记密码功能页
 */
@RestController
@RequestMapping("forgetPassword")
@Api("忘记密码界面")
public class ForgetPasswordController {
@Autowired
private ForgetPasswordService forgetPasswordService;
@Autowired
private SmsService smsService;

    @ApiOperation(value = "修改手机号、邮箱密码", notes = "修改手机号、邮箱密码")
    @PostMapping("updatePassword")
    public ResultUtil updatePassword(@RequestBody PersonalUserLogin personalUserLogin) {
        return  forgetPasswordService.updatePassword(personalUserLogin);
    }

    @ApiOperation(value = "个人用户用来找回密码发送短信", notes = "个人用户用来找回密码发送短信")
    @PostMapping("findPasswordMsg")
    public ResultUtil findPasswordMsg(@RequestBody Map<String, String> map) {

        boolean ok = smsService.findPasswordMsg(map);

        if (ok) {
            return ResultUtil.result(ResultStatusEnum.S_10009.getStatus(), ResultStatusEnum.S_10009.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10010.getStatus(), ResultStatusEnum.S_10010.getMassage());
        }
    }

    @ApiOperation(value = "通过人工方式用来找回密码发送短信", notes = "通过人工方式用来找回密码发送短信")
    @PostMapping("artificialFindPasswordMsg")
    public ResultUtil artificialFindPasswordMsg(@RequestBody Map<String, String> map) {

        boolean ok = smsService.artificialFindPasswordMsg(map);

        if (ok) {
            return ResultUtil.result(ResultStatusEnum.S_10009.getStatus(), ResultStatusEnum.S_10009.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10010.getStatus(), ResultStatusEnum.S_10010.getMassage());
        }
    }

    @ApiOperation("验证手机验证码,用于找回密码")
    @PostMapping("validatePhonePasswordCode")
    @Transactional
    public ResultUtil validatePhonePasswordCode(@RequestBody PhoneEmailBo phoneEmailBo){
        boolean check = smsService.validatePhonePasswordCode(phoneEmailBo);
        if (check) {
            return  ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10006.getStatus(), ResultStatusEnum.S_10006.getMassage());
        }
    }
    @ApiOperation("验证邮箱验证码,用于找回密码")
    @PostMapping("validateEmailPasswordCode")
    public ResultUtil validateEmailPasswordCode(@RequestBody PhoneEmailBo phoneEmailBo){
        boolean check = smsService.validateEmailPasswordCode(phoneEmailBo);
        if (check) {
            return  ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10006.getStatus(), ResultStatusEnum.S_10006.getMassage());
        }
    }

    @ApiOperation(value = "判断手机号是否已经注册", notes = "判断手机号是否已经注册")
    @PostMapping("phonePasswordCount")
    public ResultUtil phonePasswordCount(@RequestBody PersonalUserLogin personalUserLogin) {
        return  forgetPasswordService.phonePasswordCount(personalUserLogin);
    }

    @ApiOperation(value = "判断邮箱是否已经注册", notes = "判断邮箱是否已经注册")
    @PostMapping("emailPasswordCount")
    public ResultUtil emailPasswordCount(@RequestBody PersonalUserLogin personalUserLogin) {
        return  forgetPasswordService.emailPasswordCount(personalUserLogin);
    }
    /**
     * 新增保存找回个人用户信息
     */
    @PostMapping("saveArtificialInfo")
    public ResultUtil saveArtificialInfo(@RequestBody @Valid FindPersonalInfo findPersonalInfo, BindingResult result)
    {
        //获取后台校验信息（注解的方式）
        if (result.hasErrors()) {
            return ValidUtil.getDefaultMsg(result);
        }
        return forgetPasswordService.saveArtificialInfo(findPersonalInfo);
    }

}
