package com.censoft.weChat.service;

import com.censoft.util.ResultUtil;
import com.censoft.weChat.domain.WechatBindParam;

/**
 *@创建人:wqgeng
 *@创建时间:2020-12-1415:58
 *@描述:用户唯一性校验
 */
public interface OnlyVerifyService {

    ResultUtil onlyVerify(WechatBindParam wechatBindParam);
}
