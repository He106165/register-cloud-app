package com.censoft.otherlogin.Alipay.controller;

import com.censoft.common.core.controller.BaseController;
import com.censoft.common.core.domain.R;
import com.censoft.common.log.annotation.OperLogRes;
import com.censoft.common.log.enums.BusinessType;
import com.censoft.common.log.enums.OperatorType;
import com.censoft.otherlogin.Alipay.domain.AlipayUserInfo;
import com.censoft.otherlogin.Alipay.service.AlipayLoginService;
import com.censoft.otherlogin.Alipay.service.IAlipayUserInfoService;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 支付宝信息 提供者
 *
 * @author cendev
 * @date 2020-10-21
 */
@Controller
@RequestMapping("info")
@PropertySource({"classpath:application.properties"})
public class AlipayUserInfoController extends BaseController
{

	@Autowired
	private IAlipayUserInfoService alipayUserInfoService;

	@Autowired
	private AlipayLoginService alipayLoginService;


	@Value("${SYSTEM.DOMAIN}")
	public  String DOMAIN;

	@Value("${SYSTEM.COOKIE}")
	private String cookieTimeOut;

	@Value("${REDIS.PROINFO}")
	private String proInfoTimeOut;
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public AlipayUserInfo get(@PathVariable("id") String id)
	{
		return alipayUserInfoService.selectAlipayUserInfoById(id);

	}

	/**
	 * 查询支付宝信息列表
	 */
	@GetMapping("list")
	public R list(AlipayUserInfo alipayUserInfo)
	{
		startPage();
        return result(alipayUserInfoService.selectAlipayUserInfoList(alipayUserInfo));
	}


	/**
	 * 新增保存支付宝信息
	 */
	@PostMapping("save")
	public R addSave(@RequestBody AlipayUserInfo alipayUserInfo)
	{
		return toAjax(alipayUserInfoService.insertAlipayUserInfo(alipayUserInfo));
	}

	/**
	 * 修改保存支付宝信息
	 */
	@PostMapping("update")
	public R editSave(@RequestBody AlipayUserInfo alipayUserInfo)
	{
		return toAjax(alipayUserInfoService.updateAlipayUserInfo(alipayUserInfo));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{
		return toAjax(alipayUserInfoService.deleteAlipayUserInfoByIds(ids));
	}





	@RequestMapping("AlipayLogin")
//	@OperLogRes(opUserType = OperatorType.PERSONAL,opModel ="支付宝登陆",opContent = "支付宝登陆",opType = BusinessType.QUERY)
	public String AlipayLogin(@RequestParam Map map, HttpServletResponse response, RedirectAttributes attr
	){
		return  alipayUserInfoService.AlipayLogin(map,response,attr,Integer.valueOf(proInfoTimeOut));
	}
	@RequestMapping("bindAlipayInfo")
//	@OperLogRes(opUserType = OperatorType.PERSONAL,opModel ="绑定支付宝",opContent = "支付宝登陆",opType = BusinessType.QUERY)
	public String bindAlipayInfo(@RequestParam Map map, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr
	){
//		String token = "";
//		map.put("token","");
//		Cookie[] cookies = request.getCookies();
//		if(cookies != null && cookies.length > 0){
//			for (Cookie cookie : cookies){
//				if(cookie.getName().equals("Admin-Token")){
//					token = cookie.getValue();
//					map.put("token",token);
//					break;
//				}
//				//return cookie.getName() + " " + cookie.getValue();
//			}
//		}

		return  alipayUserInfoService.bindAlipayInfo(map,response,attr);
	}

	@RequestMapping("insertAlipayUser")
	@OperLogRes(opUserType = OperatorType.PERSONAL,opModel ="补充支付宝信息",opContent = "补充支付宝信息",opType = BusinessType.QUERY)
	@ResponseBody
	public R insertAlipayUser(@RequestParam Map map, HttpServletResponse response
	){
		return  alipayUserInfoService.insertAlipayUser(map,response);
	}
	@RequestMapping("chenck")
	public String chenck(@RequestParam Map map, HttpServletResponse response
	){
		String url = map.get("url")==null?"":map.get("url").toString();
		if(map.get("token")!=null){
			Cookie testCookie1= new Cookie("Admin-Token", map.get("token").toString());
			/*testCookie1.setDomain("localhost");*/
			testCookie1.setDomain(DOMAIN);
			//testCookie1.setDomain("localhost");
			testCookie1.setPath("/");
			testCookie1.setMaxAge(Integer.valueOf(cookieTimeOut));
			response.addCookie(testCookie1);
		}

		return url;

	}
	/**
	* @Description 绑定支付宝，完善信息发送验证码
	* @Parm
	* @return
	**/
	@ResponseBody
	@RequestMapping("AliPayBindUserSendMsg")
	public R AliPayBindUserSendMsg(String phone){
		if(phone == null)  return R.error("手机号码不能为空");
		if(alipayUserInfoService.sendMsg(phone)){
			return R.ok("已发送");
		}else{
			return R.error("发送失败");
		}
	}



	public static void delCookies(String url) throws IOException {
		// 全局请求设置
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
		// 创建cookie store的本地实例
		CookieStore cookieStore = new BasicCookieStore();
		// 创建HttpClient上下文
		HttpClientContext context = HttpClientContext.create();
		context.setCookieStore(cookieStore);
		// 创建一个HttpClient
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
				.setDefaultCookieStore(cookieStore).build();
		// 创建一个get请求用来获取必要的Cookie，如_xsrf信息
		HttpGet get = new HttpGet(url);
		httpClient.execute(get, context);
		cookieStore.clear();
	}

}
