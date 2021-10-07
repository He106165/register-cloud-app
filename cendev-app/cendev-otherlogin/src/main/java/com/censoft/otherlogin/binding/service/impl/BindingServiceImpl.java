package com.censoft.otherlogin.binding.service.impl;

import ch.qos.logback.classic.net.SimpleSSLSocketServer;
import com.alibaba.fastjson.JSONObject;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.common.utils.StringUtils;
import com.censoft.otherlogin.binding.mapper.BingdingMapper;
import com.censoft.otherlogin.binding.service.BingdingService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 * 领英信息Service业务层处理
 *
 * @author cendev
 * @date 2020-10-21
 */
@Service
public class BindingServiceImpl implements BingdingService {
    @Autowired
    private BingdingMapper bingdingMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisUtils redis;
    @Value("${wechatUrl.wechatLogin}")
    private String wechatLogin;
    @Override
    public Map getUserInfo(Map map, String userType) {
        if ("personal".equals(userType)) {
            return bingdingMapper.getPersonInfo(map);
        } else if ("organ".equals(userType)) {
            return bingdingMapper.getOrganInfo(map);
        }
        return null;
    }

    @Override
    public Integer bindAlipayInfo(Map map) {
        return bingdingMapper.bindAlipayInfo(map);
    }

    @Override
    public Integer delAlipayInfo(Map map) {
        String aliId = map.get("ALIPAY_ID") == null ? "" : map.get("ALIPAY_ID").toString();
        String key = "#Alipay_" + aliId;
        redisTemplate.delete(key);
        return bingdingMapper.delAlipayInfo(map);
    }

    @Override
    public Integer bindWeChatInfo(Map map) {
        return bingdingMapper.bindWeChatInfo(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delWeChatInfo(Map paramMap, HttpServletResponse response) {
        try {
            String wechatId = paramMap.get("WECHAT_ID") == null ? "" : paramMap.get("WECHAT_ID").toString();
            if (StringUtils.isNotBlank(wechatId)) {
                String key = "wechat_" + wechatId;
                redisTemplate.delete(key);
                int ss = bingdingMapper.delWechatUserLogin(paramMap);
                if (ss > 0) {
                    bingdingMapper.delWeChatInfo(paramMap);
                } else {
                    return "2";
                }
            } else {
                return "2";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解除微信失败");
        }
        return "1";
    }
}
