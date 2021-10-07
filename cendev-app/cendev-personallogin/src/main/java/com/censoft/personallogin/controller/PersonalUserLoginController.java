package com.censoft.personallogin.controller;

import com.censoft.common.constant.Constants;
import com.censoft.common.core.controller.BaseController;
import com.censoft.common.core.domain.R;
import com.censoft.common.json.JSONObject;
import com.censoft.common.log.annotation.OperLogRes;
import com.censoft.common.log.enums.BusinessType;
import com.censoft.common.log.enums.OperatorType;
import com.censoft.common.log.publish.PublishFactory;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.common.utils.MD5Format;
import com.censoft.common.utils.RSAUtil;
import com.censoft.common.utils.StringUtils;
import com.censoft.common.utils.TokenUtil;
import com.censoft.otherlogin.feign.RemoteTokenServer;
import com.censoft.personallogin.domain.PersonalUserInfo;
import com.censoft.personallogin.domain.PersonalUserLogin;
import com.censoft.personallogin.form.LoginForm;
import com.censoft.personallogin.mapper.PersonalUserLoginMapper;
import com.censoft.personallogin.service.IPersonalUserLoginService;
import com.censoft.personallogin.service.ToRedisService;
import com.censoft.personallogin.util.DesNewUtils;
import com.censoft.personallogin.util.loginUtil;
import com.censoft.tokenConfig.service.ISystemTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 个人用户登录 提供者
 *
 * @author cendev
 * @date 2020-10-21
 */
@RestController
@RequestMapping("login")
@PropertySource({"classpath:application.properties"})
public class PersonalUserLoginController extends BaseController {

    @Autowired
    private IPersonalUserLoginService personalUserLoginService;

    @Autowired
    private PersonalUserLoginMapper personalUserLoginMapper;

    @Autowired
    private RemoteTokenServer tokenService;

    @Autowired
    private ToRedisService toRedisService;

    @Autowired
    private ISystemTokenService systemTokenService;
    @Autowired
    private RedisUtils redis;

    @Value("${PA.SALT}")
    private String SALT;

    @Value("${REDIS.PROINFO}")
    private String loginTime;

    // 生成token秘钥
    @Value("${TOKEN.TOKEN_SECRET}")
    private String TOKEN_SECRET;
    //设置redis前缀
    private static final String wechat_redis_key = "wechat_";
    //设置redis前缀
    private static final String Alipay_redis_key = "#Alipay_";

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{id}")
    public PersonalUserLogin get(@PathVariable("id") String id) {
        return personalUserLoginService.selectPersonalUserLoginById(id);

    }

    /**
     * 查询个人用户登录列表
     */
    @GetMapping("list")
    public R list(PersonalUserLogin personalUserLogin) {
        startPage();
        return result(personalUserLoginService.selectPersonalUserLoginList(personalUserLogin));
    }


    /**
     * 新增保存个人用户登录
     */
    @PostMapping("save")
    public R addSave(@RequestBody PersonalUserLogin personalUserLogin) {
        return toAjax(personalUserLoginService.insertPersonalUserLogin(personalUserLogin));
    }

    /**
     * 修改保存个人用户登录
     */
    @PostMapping("update")
    public R editSave(@RequestBody PersonalUserLogin personalUserLogin) {
        return toAjax(personalUserLoginService.updatePersonalUserLogin(personalUserLogin));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(personalUserLoginService.deletePersonalUserLoginByIds(ids));
    }


    @PostMapping("proLogin")
    public R proLogin(LoginForm form) {
//		if(!loginUtil.checkUserInfo(form.getUsername(),form.getPassword())){
//			return R.error("用户名校验失败");
//		}
        if (form == null || form.getUsername() == null || form.getPassword() == null)
            return R.error("用户名或者密码为空，请输入");

        PersonalUserLogin personalUserLogin = loginUtil.getInfo(form.getUsername());

        PersonalUserInfo info = personalUserLoginService.login(personalUserLogin, form.getUsername());

        if (isErrorUser(info, form)) return R.error("用户名或者密码错误，请重新输入");

        String token = personGetToken(form.getUsername());
        if (token == null) return R.error("登陆失败，请联系管理员");
        return R.ok(toRedisService.saveToRides(token, form.getUsername(), form.getJoinsysCode() == null ? null : form.getJoinsysCode(), info, Integer.valueOf(loginTime)));
    }

    /**
     * @return
     * @Description 登出方法，获取当前token,加入黑名单中，有效时长为token有效时长的2倍【为了使得token失效】
     * @Parm
     **/
    @PostMapping("loginOut")
    public R loginOut(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token != null) {
            //退出 token 加入黑名单中
            toRedisService.setToReids(Constants.BLACK_TOKEN + token.replace(Constants.REGISTER_TOKEN, ""), 1, 8 * 60 * 60);
        }
        return R.ok("登出成功");
    }

    //获取用户信息
    @PostMapping("getInfo")
    @OperLogRes(opModel = "用户登陆", opContent = "查询个人信息", opType = BusinessType.QUERY)
    public JSONObject getInfo(String token) {
        if (token == null) {
            System.out.println("token 不能为空");
            return null;
        }
        String token1 = tokenService.getAccoutName(token);
        JSONObject userInfo = toRedisService.getUserInfoJson(token1);
        //todo
        //判断为空的情况下需要去数据库里查询
        return userInfo;
    }

    /**
     * @return
     * @Description 修改用户密码
     * @Parm
     **/
    @PostMapping("updateUserPwd")
    @OperLogRes(opModel = "用户登陆", opContent = "修改个人密码", opType = BusinessType.QUERY)
    public R updateUserPwd(String oldPassword, String newPassword) {
        //获取当前登录人
        String loginName = getLoginName();
        if (oldPassword == null || newPassword == null)
            return R.error("密码不能为空");
        if (getUserId() == null || getUserType() == null || getLoginName() == null)
            return R.error(00001, "登陆超时");
        JSONObject userInfoJson = toRedisService.getUserInfoJson(getLoginName());
        if (userInfoJson.size() == 0)
            return R.error(00001, "登陆超时");

        String password = userInfoJson.get("password").toString();

        if (!password.equals(MD5Format.MD5(oldPassword))) {
            return R.error("旧密码输入错误，请重新输入");
        } else {
            String userType = getUserType().equals("personal") ? "personal" : "organ";
            //修改密码，刷新redis的对象值
            int i = personalUserLoginService.updateUserPwd(getUserId(),
                    userType,
                    MD5Format.MD5(newPassword),
                    getLoginName()
            );
            if (i > 0) {
                userInfoJson.put("password", MD5Format.MD5(newPassword));
                toRedisService.flashUserInfo(getLoginName(), userInfoJson);
                //根据userId查询登录表内容
                PersonalUserLogin pp = personalUserLoginMapper.selectLoginInfo(getUserId());
                //redis中微信扫码登录的key
                String wechatKey = new StringBuilder(wechat_redis_key).append(pp.getWecharId()).toString();
                //redis中支付宝扫码登录的key
                String AlipaytKey = new StringBuilder(Alipay_redis_key).append(pp.getAlipayId()).toString();
                //删除redis信息
                if (StringUtils.isNotBlank(pp.getPhone()) && !pp.getPhone().equals(loginName)) {
                    redis.delete(pp.getPhone());
                }
                if (StringUtils.isNotBlank(pp.getEmail()) && !pp.getEmail().equals(loginName)) {
                    redis.delete(pp.getEmail());
                }
                if (StringUtils.isNotBlank(pp.getIdnumber()) && !pp.getIdnumber().equals(loginName)) {
                    redis.delete(pp.getIdnumber());
                }
                if (StringUtils.isNotBlank(pp.getName()) && !pp.getName().equals(loginName)) {
                    redis.delete(pp.getName());
                }
                if (StringUtils.isNotBlank(pp.getWecharId()) && !wechatKey.equals(loginName)) {
                    redis.delete(wechatKey);
                }
                if (StringUtils.isNotBlank(pp.getAlipayId()) && !AlipaytKey.equals(loginName)) {
                    redis.delete(AlipaytKey);
                }
                return R.ok("修改成功");
            } else {
                return R.error("内部错误，请联系管理员");
            }

        }
//
//		//如果是个人用户
//		if(getUserType().equals(OperatorType.PERSONAL.toString())){
//			//从reids获取个人用户信息，如果找不到 就去库里查询
//			PersonalUserInfo personUserInfo = toRedisService.getPersonUserInfo(getLoginName());
//			personUserInfo = personUserInfo != null ? personUserInfo
//					: personalUserLoginService.getUserInfoById(getUserId());
//			if(!personUserInfo.getPassword().equals(dncPassword(oldPassword))){
//				return R.error("旧密码输入错误，清重新输入");
//			}
//			else{
//				//修改密码，刷新redis的对象值
//				personalUserLoginService.updateUserPwd(getUserId(),OperatorType.PERSONAL.toString(),dncPassword(newPassword));
//				personUserInfo.setPassword(dncPassword(newPassword));
//				toRedisService.flashUserInfo(getLoginName(),personUserInfo);
//			}
//
//		}else if(getUserType().equals(OperatorType.ORGAN.toString())){
//
//		}else
//			return R.error("内部错误，清联系管理员");
//
    }

    //
//	// 密码加密处理
//	public String dncPassword(String password){
//		DesNewUtils desNewUtils=new DesNewUtils();
//		String key = "cen)%)(!";
//		return desNewUtils.strEnc(password, key);
//	}
    // 判断是不是一个错误的密码
    public boolean isErrorPassword(String userPassWord, String inputPassword) {
        try {
            // 解密前台传过来的密码
            String outputStr = RSAUtil
                    .decryptByPrivateKey(Base64Utils.decode(inputPassword.getBytes()));
            return !userPassWord.isEmpty() && !MD5Format.MD5(outputStr).equals(userPassWord) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("个人登陆解密失败，前端传递的加密后的密码" + inputPassword);
            return true;
        }
    }

    // 个人用户登陆生成token
    public String personGetToken(String loginName) {
        String token_effTime = toRedisService.getForRedis("Token_effTime");
        if (StringUtils.isEmpty(token_effTime)) {
            token_effTime = systemTokenService.selectSystemTokenList(null).get(0).getTime();
            toRedisService.setToReids("Token_effTime", token_effTime);
        }
        return TokenUtil.getToken(loginName, TOKEN_SECRET, Integer.valueOf(token_effTime));
    }

    // 用户错误
    public boolean isErrorUser(PersonalUserInfo info, LoginForm form) {
        //用户不存在
        if (info == null || (info.getUserType() != null && !info.getUserType().equals("personal")) || isErrorPassword(info.getPassword(), form.getPassword())) {
            PublishFactory.recordLogininfor(form.getUsername(), Constants.LOGIN_FAIL, "username.not.", BusinessType.LOGIN.toString(), null, OperatorType.PERSONAL.toString());
            return true;
        }
        return false;
    }

}
