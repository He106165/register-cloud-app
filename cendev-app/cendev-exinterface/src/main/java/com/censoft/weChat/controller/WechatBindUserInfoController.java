package com.censoft.weChat.controller;

import com.censoft.util.ResultUtil;
import com.censoft.weChat.domain.WechatBindParam;
import com.censoft.weChat.service.OnlyVerifyService;
import com.censoft.weChat.service.StrategyService;
import com.censoft.weChat.service.WechatBindUserInfoService;
import com.censoft.weChat.service.impl.VerifyServiceContextImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人:wqgeng
 * @创建时间:2020-12-1410:16
 * @描述:微信绑定用户接口
 */
@RestController
@RequestMapping("wechatBind")
@Api("微信端绑定用户接口")
public class WechatBindUserInfoController {

    @Autowired
    private WechatBindUserInfoService wechatBindUserInfoService;
    @Autowired
    private OnlyVerifyService onlyVerifyService;
    @Autowired
    private VerifyServiceContextImpl verifyServiceContext;


    @ApiOperation(value = "微信端绑定用户", notes = "微信端绑定用户")
    @PostMapping("bindUserInfo")
    public ResultUtil bindUserInfo(@RequestBody WechatBindParam wechatBindParam) {

        return wechatBindUserInfoService.bindUserInfo(wechatBindParam);
    }

    @ApiOperation(value = "微信端绑定用户", notes = "微信端绑定用户")
    @PostMapping("bindUserInfoNew")
    public ResultUtil bindUserInfoNew(@RequestBody WechatBindParam wechatBindParam) {

        return wechatBindUserInfoService.bindUserInfoNew(wechatBindParam);
    }


    @ApiOperation(value = "用户唯一性校验接口", notes = "用户唯一性校验接口")
    @PostMapping("onlyVerify")
    public ResultUtil onlyVerify(@RequestBody WechatBindParam wechatBindParam) {
        // 常用if-else做判断校验唯一性  为解决代码逻辑 不易读 故使用策略模式进行优化
        // return  onlyVerifyService.onlyVerify(wechatBindParam);
        StrategyService resource = verifyServiceContext.getResource(wechatBindParam);
        ResultUtil resultUtil = resource.verifyStrategy(wechatBindParam);
        return resultUtil;

    }
}
