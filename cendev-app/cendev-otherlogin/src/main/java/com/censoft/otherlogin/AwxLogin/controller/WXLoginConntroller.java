package com.censoft.otherlogin.AwxLogin.controller;

import com.censoft.common.core.controller.BaseController;
import com.censoft.common.core.domain.R;
import com.censoft.otherlogin.AwxLogin.domain.Token;
import com.censoft.otherlogin.AwxLogin.service.WXLoginService;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @创建人:wqgeng
 * @创建时间:2021-02-0214:47
 * @描述:微信登录
 */
@Controller
@RequestMapping("wxlogin")
public class WXLoginConntroller extends BaseController {


    @Value("${REDIS.PROINFO}")
    private String proInfoTimeOut;
    @Value("${SYSTEM.COOKIE}")
    private String cookieTimeOut;
    @Value("${SYSTEM.WECHATDOMAIN}")
    public String DOMAIN;
    @Autowired
    private WXLoginService wxLoginService;


    @RequestMapping("pcLoginByWeiXin")
    public String pcLoginByWeiXin(@RequestParam Map map, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {
        return wxLoginService.pcLoginByWeiXin(map, request, response, attr, Integer.valueOf(proInfoTimeOut));
    }

    //完善信息
    @RequestMapping("/completeInfo")
    public String completeInfo(@RequestParam Map map, HttpServletResponse response, RedirectAttributes attr) {
        //执行插入语句
        return wxLoginService.completeInfo(map, response, attr);
    }

    //个人用户绑定微信用户信息
    @RequestMapping("/bangdingWechat")
    public String bangdingWechat(@RequestParam Map map, HttpServletRequest request,HttpServletResponse response) {
       String token = map.get("state").toString();
        return wxLoginService.bangdingWechat(map, response, token);
    }

    /**
     * @return
     * @Description 绑定微信，完善信息发送验证码
     * @Parm
     **/
    @ResponseBody
    @RequestMapping("WechatSendMsg")
    public R WechatSendMsg(String phone) {
        if (phone == null) return R.error("手机号码不能为空");
        if (wxLoginService.WechatSendMsg(phone)) {
            return R.ok("已发送");
        } else {
            return R.error("发送失败");
        }
    }

}
