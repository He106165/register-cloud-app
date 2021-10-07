package com.censoft.otherlogin.otherApi.controller;

import com.censoft.cendevbackmanage.feign.RemoteMsgSendService;
import com.censoft.otherlogin.otherApi.service.OtherRegisterService;
import com.censoft.otherlogin.util.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("otherApi")
public class OtherApiController {


    @Autowired
    private OtherRegisterService otherRegisterService;
    @Autowired
    private RemoteMsgSendService remoteMsgSendService;


    @ApiOperation(value = "查询国籍信息", notes = "查询国籍信息")
    @PostMapping("queryNationality")
    public ResultUtil queryNationality() {
        return otherRegisterService.queryNationality();
    }


    @ApiOperation(value = "判断手机号是否已经注册", notes = "判断手机号是否已经注册")
    @PostMapping("countByPhone")
    public ResultUtil countByPhone(@RequestBody Map personalUserInfo) {
        return otherRegisterService.countByPhone(personalUserInfo);
    }
    @ApiOperation(value = "判断身份证号是否已经注册", notes = "判断身份证号是否已经注册")
    @PostMapping("countByCardNo")
    public ResultUtil countByCardNo(@RequestBody Map personalUserInfo) {
        return otherRegisterService.countByCardNo(personalUserInfo);
    }
    @ApiOperation(value = "判断邮箱是否已经注册", notes = "判断邮箱是否已经注册")
    @PostMapping("countByEmail")
    public ResultUtil countByEmail(@RequestBody Map personalUserInfo) {
        return otherRegisterService.countByEmail(personalUserInfo);
    }

    @ApiOperation(value = "查询证件类型", notes = "查询证件类型")
    @PostMapping("queryCardType")
    public ResultUtil queryCardType() {
        return otherRegisterService.queryCardType();
    }

    @ApiOperation(value = "查询证件类型", notes = "查询证件类型")
    @PostMapping("sendMsg")
    public ResultUtil sendMsg(@RequestBody Map<String,String> map) {
        boolean b = remoteMsgSendService.SendMsg(map);
        return ResultUtil.success("————————————————————————————————");
    }


}
