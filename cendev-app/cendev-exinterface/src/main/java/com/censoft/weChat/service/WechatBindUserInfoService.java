package com.censoft.weChat.service;

import com.censoft.util.ResultUtil;
import com.censoft.weChat.domain.WechatBindParam;

/**
 * @创建人:wqgeng
 * @创建时间:2020-12-1410:21
 * @描述:微信绑定用户接口
 */
public interface WechatBindUserInfoService {

    ResultUtil bindUserInfo(WechatBindParam wechatBindParam);

    ResultUtil bindUserInfoNew(WechatBindParam wechatBindParam);

}
