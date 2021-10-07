package com.censoft.otherlogin.AwxLogin.service.impl;

import cn.hutool.db.nosql.redis.RedisDS;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.censoft.cendevbackmanage.feign.RemoteMsgSendService;
import com.censoft.common.core.domain.R;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.common.utils.MD5Format;
import com.censoft.otherlogin.Alipay.domain.PersonalUserInfo;
import com.censoft.otherlogin.AwxLogin.mapper.WXLoginMapper;
import com.censoft.otherlogin.AwxLogin.service.WXLoginService;
import com.censoft.otherlogin.feign.RemoteTokenServer;
import com.censoft.otherlogin.util.DesNewUtils;
import com.censoft.otherlogin.util.HttpClientUtils;
import com.google.gson.Gson;
import java.nio.channels.SelectableChannel;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.misc.Request;

/**
 * @创建人:wqgeng
 * @创建时间:2021-02-0411:39
 * @描述:个人用户微信信息
 */
@Service
@Transactional
public class WXLoginServiceImpl implements WXLoginService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${weixin.pc.fw.accessTokenUrl}")
    private String pcAccessTokenUrl;
    @Value("${weixin.pc.fw.userInfoUrl}")
    private String pcUserInfoUrl;
    @Value("${weixin.pc.fw.pcAppID}")
    private String pcAppID;
    @Value("${weixin.pc.fw.pcAppsecret}")
    private String pcAppsecret;
    //进入个人中心页
    @Value("${wechatUrl.wechatCurrentUrl}")
    private String wechatCurrentUrl;
    //localhost
    @Value("${wechatUrl.wechatBangding}")
    private String wechatBangding;
    //localhost
    @Value("${wechatUrl.bindingWecahtUserErrorUrl}")
    private String bindingWecahtUserErrorUrl;
    //完善界面URL地址
    @Value("${wechatUrl.wechatUserUrl}")
    private String wechatUserUrl;
    //微信登录 回到登录页
    @Value("${wechatUrl.wechatLogin}")
    private String wechatLogin;
    //设置redis前缀
    private static final String redis_key = "wechat_";
    @Autowired
    private WXLoginMapper wxLoginMapper;
    @Autowired
    private RemoteTokenServer tokenService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisUtils redis;
    @Value("${REDIS.PROINFO}")
    private String proInfoTimeOut;
    @Value("${SYSTEM.COOKIE}")
    private String cookieTimeOut;
    @Value("${SYSTEM.WECHATDOMAIN}")
    public String DOMAIN;
    @Autowired
    private RemoteMsgSendService remoteMsgSendService;

    @Override
    public String pcLoginByWeiXin(Map map, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr, Integer integer) {
        if (map.get("code") != null && !map.get("code").toString().equals("")) {
            // 第一次进入界面，code不空，openid为空，根据code获取openid，然后查询是否存在用户信息。
            Map<String, String> accessTokenMap = getPcWXAccessToken(map.get("code").toString()); // 获取getWXAccessToken（微信网站PC扫码登录）
            if (StringUtils.isBlank(accessTokenMap.get("openId")) && StringUtils.isBlank(accessTokenMap.get("access_token"))) {
                throw new RuntimeException("扫码登录失败,获取个人信息异常...");
            }
            String openId = accessTokenMap.get("openid");
            String accessToken = accessTokenMap.get("access_token");
            String unionid = accessTokenMap.get("unionid");
            // 查询出微信信息
            Map<String, String> wxUserMap = this.getPcWeiXinUserInfo(openId, accessToken); // 获得微信用户信息
            //根据unionid查询微信表是否存在该微信 信息 如果存在 查询是否存在 个人用户信息
            if (wxLoginMapper.selectWxInfo(wxUserMap.get("unionid"))) {
                //根据unionid微信信息  个人信息不存在  跳转到完善个人信息界面  如果个人信息存在的话  直接跳到个人中心
                if (wxLoginMapper.selectUserId(wxUserMap.get("unionid"))) {
                    //查询redis是否存在该用户的登录信息
                    String key = new StringBuilder(redis_key).append(wxUserMap.get("unionid")).toString();
                    //当用用户使用过微信扫码登录，并且登录信息未过期，redis中存在用户 key 的情况下，直接获取redis中的信息 进行登录操作
                    if (StringUtils.isNotBlank(redis.get(key))) {
                        String token = tokenService.getToken(key);
                        Cookie testCookie = new Cookie("Admin-Token", token == null ? "" : token);
                        testCookie.setDomain("localhost");
                        testCookie.setDomain(DOMAIN);
                        testCookie.setPath("/");
                        testCookie.setMaxAge(Integer.valueOf(cookieTimeOut));
                        response.addCookie(testCookie);
                        return wechatCurrentUrl;
                    } else {
                        //流程：key 为空  查询数据信息  进行登录  然后将登录信息放入redis
                        Integer tokenConfigTime = Integer.valueOf(tokenService.getTokenConfigTime());
                        tokenConfigTime = tokenConfigTime * 60 * 1000;
                        //根据微信表中得userId 查询用户 信息
                        PersonalUserInfo info = wxLoginMapper.selectWechatUserInfo(wxLoginMapper.queryUserId(unionid));
                        info.setUserType("personal");
                        redis.set(key, info, tokenConfigTime);
                        if (StringUtils.isNotBlank(redis.get(key))) {
                            String token = tokenService.getToken(key);
                            Cookie testCookie = new Cookie("Admin-Token", token == null ? "" : token);
                            testCookie.setDomain("localhost");
                            testCookie.setDomain(DOMAIN);
                            testCookie.setPath("/");
                            testCookie.setMaxAge(Integer.valueOf(cookieTimeOut));
                            response.addCookie(testCookie);
                        }
                        return wechatCurrentUrl;
                    }
                } else {
                    // 用户未完善信息，重定向到完善信息界面 进行信息的完善
                    return wechatUserUrl + "?unionid=" + wxUserMap.get("unionid");
                }
            } else {
                // 微信信息不存在直接跳转到个人中心填写详细信息（相当于注册账号）
                int row = wxLoginMapper.insertWxinfo(wxUserMap.get("unionid"), wxUserMap.get("nickname"), wxUserMap.get("sex"), wxUserMap.get("province"));
                // 用户未完善信息，重定向到完善信息界面 进行信息的完善       + "&nickName=" + wxUserMap.get("nickname")
                return wechatUserUrl + "?unionid=" + wxUserMap.get("unionid");
            }
        }
        return wechatUserUrl;
    }

    /**
     * 将微信用户扫码完善信息入库
     *
     * @param map
     * @param response
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String completeInfo(Map map, HttpServletResponse response, RedirectAttributes attr) {
        if (map.get("checkCode") == null) throw new RuntimeException("验证码不能为空");
        String code = redis.get("wechat_" + map.get("phone")).replace("\"", "");
        if (code == null) throw new RuntimeException("验证码已过期");
        if (!code.equals(map.get("checkCode"))) throw new RuntimeException("验证码输入错误");
        Integer tokenConfigTime = Integer.valueOf(tokenService.getTokenConfigTime());
        tokenConfigTime = tokenConfigTime * 60 * 1000;
        //向info表里插入输入
        String userId = UUID.randomUUID().toString().replace("-", "");
        String id = UUID.randomUUID().toString().replace("-", "");
        String password1 = map.get("password") == null ? "" : map.get("password").toString();
        //将密码做加密处理
        //对密码进行解密(首先对用户传过来的密码进行加密，然后与数据库的加密后的密码进行比对)
        String password = MD5Format.MD5(password1);
        //通过微信的unionid查询 微信表中的 nicename 存入info 表中 和redis 中
        String nickName = wxLoginMapper.selectNicename(map.get("weChatUnionid").toString());
        map.put("nickName",nickName);
        map.put("password", password);
        map.put("userId", userId);
        map.put("id", id);
        map.put("phoneIscheck", 1);
        map.put("wechar_id", map.get("weChatUnionid").toString());


        //向微信表中添加扫码的信息
//        int wxinfo = 0;
//        try {
//            wxinfo = wxLoginMapper.insertWxsinfo(map.get("weChatUnionid").toString(), map.get("nickName").toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("添加个人信息失败");
//        }
//        if(wxLoginMapper.selectWxInfo(map.get("weChatUnionid").toString())&&!wxLoginMapper.selectUserId(map.get("weChatUnionid").toString())){

        //根据unionid查询微信信息 将nicename存入info表中

        int row = wxLoginMapper.insertPersonInfo(map);
        if (row > 0) {
            int rowLogin = wxLoginMapper.insertPersonLogin(map);
            //向微信表里边插入用户信息的userId
            int succRow = wxLoginMapper.updateUserId(userId, map.get("weChatUnionid").toString());
            if (succRow < 0) {
                throw new RuntimeException("添加个人信息失败");
            }
        } else {
            throw new RuntimeException("个人信息未录入");
        }
//        }else {
//            throw new RuntimeException("微信已经绑定其他账号");
//        }

        PersonalUserInfo personUser = JSON.parseObject(JSON.toJSONString(map), PersonalUserInfo.class);
        String rkey = new StringBuilder(redis_key).append(map.get("weChatUnionid")).toString();
        String token = tokenService.getToken(rkey);
        personUser.setName(personUser.getUserName());
        personUser.setUserType("personal");
        redis.set(rkey, personUser, tokenConfigTime);
        Cookie cookie = new Cookie("Admin-Token", token);
        cookie.setDomain(DOMAIN);
        cookie.setPath("/");
        cookie.setMaxAge(Integer.valueOf(cookieTimeOut));
        response.addCookie(cookie);

        return wechatCurrentUrl;
    }

    //个人用户绑定微信
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String bangdingWechat(Map map, HttpServletResponse response, String token) {
        try {
            if (map.get("code") != null && !map.get("code").toString().equals("")) {
                // 第一次进入界面，code不空，openid为空，根据code获取openid，然后查询是否存在用户信息。
                Map<String, String> accessTokenMap = getPcWXAccessToken(map.get("code").toString()); // 获取getWXAccessToken（微信网站PC扫码登录）
                if (StringUtils.isBlank(accessTokenMap.get("openId")) && StringUtils.isBlank(accessTokenMap.get("access_token"))) {
                    throw new RuntimeException("扫码登录失败,获取个人信息异常...");
                }
                String openId = accessTokenMap.get("openid");
                String accessToken = accessTokenMap.get("access_token");
                String unionid = accessTokenMap.get("unionid");
                // 查询出微信信息
                Map<String, String> wxUserMap = this.getPcWeiXinUserInfo(openId, accessToken); // 获得微信用户信息
                //查询微信表是否存在unionid
                if (wxLoginMapper.queryUnionid(unionid) && wxLoginMapper.selectUserId(unionid)) {
                    //微信已经绑定微信信息  返回个人中心
                    return bindingWecahtUserErrorUrl;
                } else if (wxLoginMapper.queryUnionid(unionid) && !wxLoginMapper.selectUserId(unionid)) {
                    //微信未绑定微信信息 向用户表添加unionid 以及微信的个人信息  创建时间
                    //查询个人用户登录的 userId 存入微信表
                    // int row = wxLoginMapper.insertWxinfo(wxUserMap.get("unionid"), wxUserMap.get("nickname"), wxUserMap.get("sex"), wxUserMap.get("province"));

                    //获取当前登录用户ID
                    /*token取当前登录人id*/
                    String userName = tokenService.getAccoutName(map.get("state").toString()) == null ? "" : tokenService.getAccoutName(map.get("state").toString());
                    String s = redis.get(userName);
                    //将redis里的信息变为 json格式
                    Map userMap = JSONObject.parseObject(s, Map.class);
                    //向微信表中添加个人信息 userId
                    int updateWe = wxLoginMapper.updateWechatInfo(userMap.get("userId").toString(), unionid);
                    if (updateWe > 0) {
                        //向登录表中插入 微信标识ID
                        int a = wxLoginMapper.updateLoginWechat(userMap.get("userId").toString(), unionid);
                        if (a > 0) {
                            return wechatBangding;
                        } else {
                            throw new RuntimeException("绑定微信失败");
                        }
                    } else {
                        throw new RuntimeException("绑定微信失败");
                    }

                }else {
                    //微信未绑定微信信息 向用户表添加unionid 以及微信的个人信息  创建时间
                    //查询个人用户登录的 userId 存入微信表
                     int row = wxLoginMapper.insertWxinfo(wxUserMap.get("unionid"), wxUserMap.get("nickname"), wxUserMap.get("sex"), wxUserMap.get("province"));
                    //获取当前登录用户ID
                    /*token取当前登录人id*/
                    String userName = tokenService.getAccoutName(map.get("state").toString()) == null ? "" : tokenService.getAccoutName(map.get("state").toString());
                    String s = redis.get(userName);
                    //将redis里的信息变为 json格式
                    Map userMap = JSONObject.parseObject(s, Map.class);
                    int updateWe = wxLoginMapper.updateWechatInfo(userMap.get("userId").toString(), unionid);
                    if (updateWe > 0) {
                        //向登录表中插入 微信标识ID
                        int a = wxLoginMapper.updateLoginWechat(userMap.get("userId").toString(), unionid);
                        if (a > 0) {
                            return wechatBangding;
                        } else {
                            throw new RuntimeException("绑定微信失败");
                        }
                    } else {
                        throw new RuntimeException("绑定微信失败");
                    }
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return wechatCurrentUrl;
    }

    //微信绑定完善信息发送验证码
    @Override
    public boolean WechatSendMsg(String phone) {
        Map<String, String> map = new HashMap<>();
        //生成6位的随机数
        String validateCode = this.generateCaptcha();
        map.put("subjectTitle", "统一用户注册系统信息发送");
        map.put("sendType", "3");
        map.put("phoneTo", phone);
        map.put("phoneContent", "验证码为: " + validateCode + ",您完善微信登陆完善信息,感谢您的支持!【教留服】");
        ValueOperations forValue = redisTemplate.opsForValue();
        String key = "wechat_" + phone;
        forValue.set(key, validateCode);
        redisTemplate.expire(key, 5, TimeUnit.MINUTES);
        return remoteMsgSendService.SendMsg(map);
    }

    /**
     * 获取getPcWXAccessToken（微信网站PC扫码）
     */
    private Map<String, String> getPcWXAccessToken(String code) {
        logger.info("WeiXinLoginController ==> getPcWXAccessToken(){}");
        Map<String, String> resMap = new HashMap<String, String>();
        StringBuffer target = new StringBuffer();
        target.append(pcAccessTokenUrl).append("appid=").append(pcAppID).append("&secret=").append(pcAppsecret)
                .append("&code=").append(code).append("&grant_type=authorization_code");
        logger.info("WeiXinLoginController ==> getPcWXAccessToken(){} target: " + target);
        //通过HttpClients 工具 提交get请求
        JSONObject jSONObject = HttpClientUtils.httpGet(target.toString());
        String accessToken = String.valueOf(jSONObject.get("access_token"));
        String refreshToken = String.valueOf(jSONObject.get("refresh_token"));
        String openid = String.valueOf(jSONObject.get("openid"));
        String expiresIn = String.valueOf(jSONObject.get("expires_in"));
        String unionid = String.valueOf(jSONObject.get("unionid"));

        resMap.put("access_token", accessToken);
        resMap.put("refresh_token", refreshToken);
        resMap.put("openid", openid);
        resMap.put("expires_in", expiresIn);
        resMap.put("unionid", unionid);
        return resMap;
    }

    /**
     * 获得微信用户信息（微信网站PC扫码）
     *
     * @param openId
     * @param accessToken
     * @return
     */
    private Map<String, String> getPcWeiXinUserInfo(String openId, String accessToken) {
        Map<String, String> resMap = new HashMap<String, String>();
        StringBuffer url = new StringBuffer(pcUserInfoUrl);
        url.append("access_token=").append(accessToken).append("&").append("openid=").append(openId).append("&")
                .append("lang=zh_CN");
        logger.info("WeiXinLoginController ==> getPcWeiXinUserInfo(){} url: " + url);
        // ClientResponseEntity responceEntity = HttpClientUtil.httpGet(url.toString());
        JSONObject jSONObject = HttpClientUtils.httpGet(url.toString());
        String nickname = String.valueOf(jSONObject.get("nickname"));
        String openid = String.valueOf(jSONObject.get("openid"));
        String sex = String.valueOf(jSONObject.get("sex"));
        String province = String.valueOf(jSONObject.get("province"));
        String city = String.valueOf(jSONObject.get("city"));
        String country = String.valueOf(jSONObject.get("country"));
        String headimgurl = String.valueOf(jSONObject.get("headimgurl"));
        String unionid = String.valueOf(jSONObject.get("unionid"));
        // 向map中添加微信获取的个人信息
        resMap.put("nickname", nickname);
        resMap.put("openid", openid);
        resMap.put("sex", sex);
        resMap.put("province", province);
        resMap.put("city", city);
        resMap.put("country", country);
        resMap.put("headimgurl", headimgurl);
        resMap.put("unionid", unionid);
        return resMap;
    }

    //生成六位的随机数
    public static String generateCaptcha() {
        /** 生成6位随机数 */
        String captcha = UUID.randomUUID().toString()
                .replaceAll("-", "")
                .replaceAll("[a-z|A-Z]", "")
                .substring(0, 6);
        return captcha;
    }
}
