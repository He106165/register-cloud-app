package com.censoft.userregister.controller;


import com.censoft.userregister.domain.PersonalUserInfo;
import com.censoft.userregister.domain.PersonalUserLogin;
import com.censoft.userregister.domain.PhoneEmailBo;
import com.censoft.userregister.service.PersonalUserInfoRegisterService;
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
import org.springframework.web.bind.annotation.*;

/**
 * @创建人:wqgeng
 * @创建时间:2020-11-1619:25
 * @描述:用户注册
 */
@RestController
@RequestMapping("userRegister")
@Api("用户注册界面")
public class PersonalUserInfoRegisterController {

    @Autowired
    private PersonalUserInfoRegisterService personalUserInfoRegisterService;
    @Autowired
    private SmsService smsService;

    @ApiOperation(value = "注册为用户", notes = "注册为用户")
    @PostMapping("userInfo")
    public ResultUtil addSave(@RequestBody @Valid PersonalUserInfo personalUserInfo, BindingResult result) {
        //获取后台校验信息（注解的方式）
        if (result.hasErrors()) {
            return ValidUtil.getDefaultMsg(result);
        }
        return personalUserInfoRegisterService.insert(personalUserInfo);
    }

    @ApiOperation(value = "判断手机号是否已经注册", notes = "判断手机号是否已经注册")
    @PostMapping("countByPhone")
    public ResultUtil countByPhone(@RequestBody PersonalUserLogin personalUserLogin) {
        return personalUserInfoRegisterService.countByPhone(personalUserLogin);
    }

    @ApiOperation(value = "判断邮箱是否已经注册", notes = "判断邮箱是否已经注册")
    @PostMapping("countByEmail")
    public ResultUtil countByEmail(@RequestBody PersonalUserLogin personalUserLogin) {
        return personalUserInfoRegisterService.countByEmail(personalUserLogin);
    }

    @ApiOperation(value = "判断身份证号是否已经注册", notes = "判断身份证号是否已经注册")
    @PostMapping("countByCardNo")
    public ResultUtil countByCardNo(@RequestBody PersonalUserLogin personalUserLogin) {
        return personalUserInfoRegisterService.countByCardNo(personalUserLogin);
    }

    @ApiOperation(value = "校验注册的手机号是否绑定了该邮箱", notes = "校验注册的手机号是否绑定了邮箱")
    @PostMapping("countByPhoneEmail")
    public ResultUtil countByPhoneEmail(@RequestBody PersonalUserLogin personalUserLogin) {
        return personalUserInfoRegisterService.countByPhoneEmail(personalUserLogin);
    }

    @ApiOperation(value = "校验注册的邮箱是否绑定了手机号", notes = "校验注册的邮箱是否绑定了手机号")
    @PostMapping("countByEmailPhone")
    public ResultUtil countByEmailPhone(@RequestBody PersonalUserLogin personalUserLogin) {
        return personalUserInfoRegisterService.countByEmailPhone(personalUserLogin);
    }

    @ApiOperation(value = "个人昵称唯一校验", notes = "个人昵称唯一校验")
    @PostMapping("countBySomeThing")
    public ResultUtil countBySomeThing(@RequestBody PersonalUserInfo personalUserInfo) {
        if (personalUserInfoRegisterService.countBySomeThing(personalUserInfo) == 0) return ResultUtil.success();
        return ResultUtil.result(10035, "该信息已存在");
    }


    @ApiOperation(value = "查询国籍信息", notes = "查询国籍信息")
    @PostMapping("queryNationality")
    public ResultUtil queryNationality() {
        return personalUserInfoRegisterService.queryNationality();
    }

    @ApiOperation(value = "查询证件类型", notes = "查询证件类型")
    @PostMapping("queryCardType")
    public ResultUtil queryCardType() {
        return personalUserInfoRegisterService.queryCardType();
    }

    @ApiOperation(value = "个人注册发送短信/邮件", notes = "个人注册发送短信/邮件")
    @PostMapping("sendMsg")
    public ResultUtil sendMsg(@RequestBody Map<String, String> map) {

        boolean ok = smsService.send(map);

        if (ok) {
            return ResultUtil.result(ResultStatusEnum.S_10009.getStatus(), ResultStatusEnum.S_10009.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10010.getStatus(), ResultStatusEnum.S_10010.getMassage());
        }

    }

    @PostMapping("sendMsgCount")
    public ResultUtil sendMsgCount(@RequestBody Map<String, String> map) {
        boolean yz = smsService.sendMsgCount(map);
        if (yz){
            return ResultUtil.success();
        }
        return ResultUtil.result(00000,"发送失败,发送短信次数过多!");
    }

    @ApiOperation("验证手机验证码")
    @PostMapping("checkValidatePhoneCode")
    public ResultUtil checkValidatePhoneCode(@RequestBody PhoneEmailBo phoneEmailBo) {
        boolean check = smsService.checkValidatePhoneCode(phoneEmailBo);
        if (check) {
            return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10006.getStatus(), ResultStatusEnum.S_10006.getMassage());
        }
    }

    @ApiOperation("验证邮箱验证码")
    @PostMapping("checkValidateEmailCode")
    public ResultUtil checkValidateEmailCode(@RequestBody PhoneEmailBo phoneEmailBo) {
        boolean check = smsService.checkValidateEmailCode(phoneEmailBo);
        if (check) {
            return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10006.getStatus(), ResultStatusEnum.S_10006.getMassage());
        }
    }

    @ApiOperation("对手机号邮箱验证码同时验证")
    @PostMapping("checkValidatePhoneEmailCode")
    public ResultUtil checkValidatePhoneEmailCode(@RequestBody PhoneEmailBo phoneEmailBo) {
        boolean check = smsService.checkValidatePhoneEmailCode(phoneEmailBo);
        if (check) {
            return ResultUtil.result(ResultStatusEnum.S_10007.getStatus(), ResultStatusEnum.S_10007.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10006.getStatus(), ResultStatusEnum.S_10006.getMassage());
        }
    }
}
