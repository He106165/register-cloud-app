package com.censoft.otherlogin.tokenConfig.controller;

import com.alibaba.fastjson.JSONObject;
import com.censoft.common.constant.Constants;
import com.censoft.common.core.controller.BaseController;
import com.censoft.common.core.domain.R;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.common.utils.TokenStatus;
import com.censoft.common.utils.TokenUtil;
import com.censoft.otherlogin.tokenConfig.domain.SystemToken;
import com.censoft.otherlogin.tokenConfig.service.ISystemTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 令牌管理 提供者
 *
 * @author cendev
 * @date 2020-10-20
 */
@RestController
@RequestMapping("/token")
@PropertySource({"classpath:application.properties"})
public class SystemTokenController extends BaseController {

    @Autowired
    private ISystemTokenService systemTokenService;

    @Autowired
    private TokenUtil tokenUtil;
    // 生成token秘钥
    @Value("${TOKEN.TOKEN_SECRET}")
    private  String TOKEN_SECRET;
    // 系统主域
    @Value("${SYSTEM.DOMAIN}")
    private  String DOMAIN;
    // 黑名单过期时间
    @Value("${BLACKLIST.TIME_OUT}")
    private  String blacklistTimeOut;
    // cookie 过期时间
    @Value("${SYSTEM.COOKIE}")
    private  String systemCookieTimeOut;

    @Autowired
    private RedisUtils redis;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{id}")
    public SystemToken get(@PathVariable("id") String id) {
        return systemTokenService.selectSystemTokenById(id);
    }

    /**
     * 查询令牌管理列表
     */
    @GetMapping("list")
    public SystemToken list(SystemToken systemToken) {
        List<SystemToken> systemTokens = systemTokenService.selectSystemTokenList(systemToken);
        return systemTokens.get(0);
    }


    /**
     * 新增保存令牌管理
     */
    @PostMapping("save")
    public R addSave(@RequestBody SystemToken systemToken) {
        return toAjax(systemTokenService.insertSystemToken(systemToken));
    }

    /**
     * 修改保存令牌管理
     */
    @PostMapping("update")
    public R editSave(@RequestBody SystemToken systemToken) {
        return toAjax(systemTokenService.updateSystemToken(systemToken));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(systemTokenService.deleteSystemTokenByIds(ids));
    }

    /**
     * 生成token方法
     * accoutName：用户名【邮箱、身份证、手机号】
     * */
    @GetMapping("getToken")
    public String  getToken(String accoutName){
 //       System.out.println(loginService.selectSysUserByUsername("dev"));
        String time = systemTokenService.selectSystemTokenList(null).get(0).getTime();
//        String time = "1000";
        return tokenUtil.getToken(accoutName,TOKEN_SECRET,Integer.valueOf(time));
    }

    /**
     * 校验token
     * token
     * */
    @GetMapping("checkToken")
    public boolean  checkToken(String token){
        if(token==null || token==""){
            return false;
        }
        return tokenUtil.verify(token,TOKEN_SECRET);
    }

    /**
     * 获取token里的用户名
     * token
     * */
    @GetMapping("getAccoutName")
    public String getAccoutName(String token){
        if(token==null || token==""){
            return null;
        }
        return tokenUtil.getInfo(token,TOKEN_SECRET);
    }

    @GetMapping("getTokenConfigTime")
    public String getTokenConfigTime(){
        String time = systemTokenService.selectSystemTokenList(null).get(0).getTime();
        return time;
    }
    /**
    * @Description 校验token 获取
    * @Parm
    * @return
     * count=1  未过期且小于加载因子
     *      2  大于加载因子 小于 1
     *      3  大于1    token已经过期
    **/
    @RequestMapping(path = "countToken",method={RequestMethod.POST,RequestMethod.GET})
    public JSONObject countToken(String token,String loginName, HttpServletResponse response){
        //校验为空
        if(token == null ) return setJsonInfo(0,"token不能为空");
        //if(loginName == null ) return setJsonInfo(-1,"loginName 登陆名不能为空");

        //校验是否在黑名单中
        if(redis.get(Constants.BLACK_TOKEN + token) != null) return setJsonInfo(TokenStatus.destroy.getIndex(),TokenStatus.destroy.getName());

        //正式校验token 嗷呜~
        int count = tokenUtil.countToken(token,loginName);
        if (count == 5) return setJsonInfo(count,TokenStatus.error.getName());
        //token 正常  此时如果主域为空，则在主域里面加入token
        if (count == 1){
            if(getUgcCookie()){
                Cookie c = new Cookie("Admin-Token", token);
                c.setDomain(DOMAIN);
                response.addCookie(c);
            }
            return setJsonInfo(count,TokenStatus.normal.getName());
        }
        // token 将要过期，刷新token, 并且将新的token放到主域里面，将老的token，放到黑名单里面，防止恶意刷新token
        if(count == 2) {
            String time = systemTokenService.selectSystemTokenList(null).get(0).getTime();

            String newToken = tokenUtil.flushToken(token, TOKEN_SECRET, Integer.valueOf(time));
            // 旧的token加入黑名单中 时长为8小时
            redis.set(Constants.BLACK_TOKEN + token,1, 8 * 60 * 60);

            //设置主域cookie
            Cookie c = new Cookie("Admin-Token", newToken);
            c.setPath("/");
            c.setDomain(DOMAIN);
            c.setMaxAge(Integer.valueOf(systemCookieTimeOut));
            response.addCookie(c);
            JSONObject jsonObject=setJsonInfo(count,TokenStatus.willbedue.getName());
            jsonObject.put("newToken",newToken);
            return jsonObject;
        }
        // 已经过期了
        if(count == 3) return setJsonInfo(count,TokenStatus.due.getName());

        return setJsonInfo(-2 ,"内部错误，请联系管理员");
    }

    public JSONObject setJsonInfo(int status,String msg){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("status",status );
        jsonObject.put("msg",msg);
        return jsonObject;
    }

    // 判断cookie里面是否存在token,  true 需要重新放一份， false不需要
    private boolean getUgcCookie() {
        Cookie[] cookies1 = getRequest().getCookies();

        if(cookies1 == null) return true;

        for (Cookie cookie : cookies1) {
            if(cookie.getName().equals("Admin-Token") && cookie.getValue() != null ){
                return false;
            }
        }
        return true;
    }
}
