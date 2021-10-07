package com.censoft.weChat.controller;

import com.censoft.common.constant.Constants;
import com.censoft.common.core.domain.R;
import com.censoft.common.redis.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
* 系统层级统一对外接口
 * @Author: hepengfei
 * @Date: 2021/1/15 13:29
 */

@RestController
@RequestMapping("systemApi")
public class SystemApiController {


    @Autowired
    private RedisUtils redisUtils;


    /**
    * @Description 系统登出接口
    * @Parm
    * @return
    **/
    @CrossOrigin
    @PostMapping("loginOut")
    public Map<String, Object> loginOut(String token, HttpServletResponse response, HttpServletRequest request){

        String[]  Cookies ={ "Admin-Token","joinsysCode","lookBackAddr" };
        if(token!=null ){
            // 退出 token加入黑名单中
            redisUtils.set(Constants.BLACK_TOKEN + token.replace(Constants.REGISTER_TOKEN,""),1,8 * 60 * 60);
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length >0) {
            // 遍历浏览器发送到服务器端的所有Cookie，找到自己设置的Cookie
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if (cookieName.equals("Admin-Token") || cookieName.equals("joinsysCode") || cookieName.equals("lookBackAddr") ) {
                    cookie.setMaxAge(0);
                    cookie.setDomain(".censoft.com.cn");
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        return R.ok("登出成功");
    }
}
