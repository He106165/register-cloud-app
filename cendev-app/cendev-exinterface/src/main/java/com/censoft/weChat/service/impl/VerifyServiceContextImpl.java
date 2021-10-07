package com.censoft.weChat.service.impl;

import com.censoft.weChat.domain.WechatBindParam;
import com.censoft.weChat.domain.WechatUserInfo;
import com.censoft.weChat.service.StrategyService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @创建人:wqgeng
 * @创建时间:2020-12-1510:15
 * @描述:策略接口实现类的调度使用类 利用Spring的发现机制, 将实现了StrategyService的类都put到strategyServiceMap里面。
 * 后面只需要根据VerifyTypeId对应好 各个实现类的注解 如： @Component("phone") 就可以取出不同的业务实现类
 */
@Service
public class VerifyServiceContextImpl {
    @Autowired
    private final Map<String, StrategyService> strategyServiceMap = new ConcurrentHashMap<>();

    public VerifyServiceContextImpl(Map<String, StrategyService> map) {
        this.strategyServiceMap.clear();
        map.forEach((k, v) -> this.strategyServiceMap.put(k, v));
    }

    public StrategyService getResource(WechatBindParam wechatBindParam) {
        return strategyServiceMap.get(wechatBindParam.getVerifyType());
    }
}
