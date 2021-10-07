package com.censoft.otherlogin.orgenUser.controller;

import com.censoft.common.constant.Constants;
import com.censoft.common.core.controller.BaseController;
import com.censoft.common.core.domain.R;
import com.censoft.common.log.enums.BusinessType;
import com.censoft.common.log.enums.OperatorType;
import com.censoft.common.log.publish.PublishFactory;
import com.censoft.common.utils.MD5Format;
import com.censoft.common.utils.RSAUtil;
import com.censoft.common.utils.TokenUtil;
import com.censoft.otherlogin.orgenUser.domain.OrgenUserInfo;
import com.censoft.otherlogin.orgenUser.domain.OrgenUserLogin;
import com.censoft.otherlogin.orgenUser.form.LoginForm;
import com.censoft.otherlogin.orgenUser.service.IOrgenUserLoginService;
import com.censoft.otherlogin.orgenUser.service.ToRedisOrgService;
import com.censoft.otherlogin.tokenConfig.service.ISystemTokenService;
import com.censoft.otherlogin.util.DesNewUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

/**
 * 机构用户登录 提供者
 *
 * @author cendev
 * @date 2020-10-21
 */
@RestController
@RequestMapping("orgenUserlogin")
@PropertySource({"classpath:application.properties"})
public class OrgenUserLoginController extends BaseController
{

	@Autowired
	private IOrgenUserLoginService orgenUserLoginService;

	@Autowired
	private ToRedisOrgService toRedisOrgService;

	@Autowired
	private ISystemTokenService tokenService;

	@Autowired
	private TokenUtil tokenUtil;




	@Value("${TOKEN.TOKEN_SECRET}")
	private  String TOKEN_SECRET;

	@Value("${REDIS.PROINFO}")
	private String proInfoTimeOut;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public OrgenUserLogin get(@PathVariable("id") String id)
	{
		return orgenUserLoginService.selectOrgenUserLoginById(id);

	}

	/**
	 * 查询机构用户登录列表
	 */
	@GetMapping("list")
	public R list(OrgenUserLogin orgenUserLogin)
	{
		startPage();
        return result(orgenUserLoginService.selectOrgenUserLoginList(orgenUserLogin));
	}


	/**
	 * 新增保存机构用户登录
	 */
	@PostMapping("save")
	public R addSave(@RequestBody OrgenUserLogin orgenUserLogin)
	{
		return toAjax(orgenUserLoginService.insertOrgenUserLogin(orgenUserLogin));
	}

	/**
	 * 修改保存机构用户登录
	 */
	@PostMapping("update")
	public R editSave(@RequestBody OrgenUserLogin orgenUserLogin)
	{
		return toAjax(orgenUserLoginService.updateOrgenUserLogin(orgenUserLogin));
	}
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{
		return toAjax(orgenUserLoginService.deleteOrgenUserLoginByIds(ids));
	}


	@PostMapping("orgLogin")
	public R proLogin(LoginForm form){
		if(form == null || form.getUsername() == null || form.getPassword() == null)
			return R.error("用户名或者密码为空，请输入");

		OrgenUserInfo login = orgenUserLoginService.login(form.getUsername(), form.getDeptName());
		// 判断登录人在不在库里，用户类型是不是机构类型
		if(login!=null && login.getUserType() != null  && !login.getUserType().equals("organ")){
			return R.error("用户名不存在或者密码错误");
		}
		if(login==null || isErrorPassword(login.getPassword(),form.getPassword())){
			PublishFactory.recordLogininfor(form.getUsername(), Constants.LOGIN_FAIL,"password.error", BusinessType.LOGIN.toString(),null, OperatorType.ORGAN.toString());
			return R.error("用户名不存在或者密码错误");
		}
		String userName = form.getUsername()+"##"+form.getDeptName();
		Integer time =Integer.valueOf(tokenService.selectSystemTokenList(null).get(0).getTime()) ;
		String token= tokenUtil.getToken(userName,TOKEN_SECRET,Integer.valueOf(time));

		return R.ok(toRedisOrgService.saveToRides(token,userName,form.getJoinsysCode(),login,Integer.valueOf(proInfoTimeOut)));
	}

	// 密码加密处理
	public String dncPassword(String password){
		DesNewUtils desNewUtils=new DesNewUtils();
		String key = "cen)%)(!";
		return desNewUtils.strEnc(password, key);
	}

	// 判断是不是一个错误的密码
	public boolean isErrorPassword(String userPassWord,String inputPassword){
		try{
			// 解密前台传过来的密码
			String outputStr = RSAUtil
					.decryptByPrivateKey(Base64Utils.decode(inputPassword.getBytes()));
			return !userPassWord.isEmpty() && !MD5Format.MD5(outputStr).equals(userPassWord) ? true : false;
		}catch (Exception e){
			e.printStackTrace();
			logger.info("个人登陆解密失败，前端传递的加密后的密码" + inputPassword);
			return true;
		}

	}
}
