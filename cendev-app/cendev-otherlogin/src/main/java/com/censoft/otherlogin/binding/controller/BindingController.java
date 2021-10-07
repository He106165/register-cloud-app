package com.censoft.otherlogin.binding.controller;

import com.censoft.common.core.controller.BaseController;
import com.censoft.otherlogin.binding.service.BingdingService;
import com.censoft.otherlogin.feign.RemoteTokenServer;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 领英信息 提供者
 *
 * @author cendev
 * @date 2020-10-21
 */
@RestController
@RequestMapping("bindingsf")
public class BindingController extends BaseController {

    @Autowired
    private BingdingService bingdingService;
    @Autowired
    private RemoteTokenServer tokenService;

    @RequestMapping("getUserInfo")
    public Map getUserInfo() {
        String userType = getUserType() == null ? "" : getUserType();
        if (userType.equals("")) {
            return null;
        }
        String userId = getUserId();
        String token = getToken();
        if (token != null && token.length() > 15) {
            token = token.replace("REGISTER_TOKEN_", "");
        }
        Map map = new HashMap() {
            {
                this.put("userId", userId);
                this.put("userType", userType);
            }
        };
        Map lastMap = bingdingService.getUserInfo(map, userType);
        lastMap = lastMap == null ? new HashMap<>() : lastMap;
        String loginName = tokenService.getAccoutName(token);
        if (loginName.indexOf("#Alipay_") != -1) {
            lastMap.put("loginType", "Alipay");
        }
        if (loginName.indexOf("wechat_") != -1) {
            lastMap.put("loginType", "wechat_");
        } else {
            lastMap.put("loginType", "");
        }
        return lastMap;
    }


    /*绑定支付宝*/
    @RequestMapping("bindAlipayInfo")
    public Integer bindAlipayInfo(Map map) {
        return bingdingService.bindAlipayInfo(map);
    }

    /*解绑支付宝*/
    @RequestMapping("delAlipayInfo")
    public Integer delAlipayInfo(@RequestParam Map map) {
        /*只有个人用户 才能绑定支付宝*/
        Map paramMap = new HashMap() {
            {
                this.put("userId", getUserId());
                this.put("ALIPAY_ID", map.get("ALIPAY_ID"));
                this.put("WECHAT_ID", map.get("WECHAT_ID"));
            }
        };
        return bingdingService.delAlipayInfo(paramMap);
    }

    /*绑定微信*/
    @RequestMapping("bindWeChatInfo")
    public Integer bindWeChatInfo(Map map) {
        return bingdingService.bindWeChatInfo(map);
    }

    /*解绑微信*/
    @RequestMapping("delWeChatInfo")
    public String delWeChatInfo(@RequestParam Map map, HttpServletResponse response) {
        /*只有个人用户 才能绑定支付宝*/
        Map paramMap = new HashMap() {
            {
                this.put("userId", getUserId());
//				this.put("ALIPAY_ID",map.get("ALIPAY_ID"));
                this.put("WECHAT_ID", map.get("WECHAT_ID"));
            }
        };
        return bingdingService.delWeChatInfo(paramMap,response);
    }
}
