package com.censoft.weChat.service;

import com.censoft.util.ResultUtil;
import com.censoft.weChat.domain.WechatBindParam;
/**
 * @创建人:wqgeng
 * @创建时间:2020-12-1509:48
 * @描述:策略模式接口（微信端唯一性校验接口）
 */
public interface StrategyService {
    
    ResultUtil verifyStrategy(WechatBindParam wechatBindParam);
}
