package com.censoft.weChat.controller;

import com.alibaba.fastjson.JSONObject;
import com.censoft.cendevbackmanage.entity.JoinSystemInfo;
import com.censoft.cendevbackmanage.feign.JoinSystemServer;
import com.censoft.cendevbackmanage.feign.RemoteMsgSendService;
import com.censoft.common.core.controller.BaseController;
import com.censoft.generalUser.mapper.GeneralUserInfoMapper;
import com.censoft.generalUser.service.GeneralUserService;
import com.censoft.organUser.mapper.OrganUserInfoMapper;
import com.censoft.weChat.domain.WechatUserInfo;
import com.censoft.weChat.service.IWechatUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信信息 提供者
 *
 * @author cendev
 * @date 2020-10-21
 */
@RestController
@RequestMapping("wechat")
public class WechatUserInfoController extends BaseController
{

	@Autowired
	private IWechatUserInfoService wechatUserInfoService;

	@Autowired
	private GeneralUserService generalUserInfoService;

	@Autowired
	private RemoteMsgSendService remoteMsgSendService;

	@Autowired
	private JoinSystemServer systemInfoService;

	@Autowired
	public GeneralUserInfoMapper generalUserInfoMapper;

	@Autowired
	public OrganUserInfoMapper organUserInfoMapper;



	/**
	* @Description 微信登陆接口,登陆成功返回用户信息
	* @Parm
	* @return
	**/
	@PostMapping("weChatLogin")
	public JSONObject weChatLogin(String unionId,String userType,String sysuqid){
		// 开始校验
		JSONObject res = new JSONObject();
		if(unionId == null) return setJsonInfo(res,-1,"微信唯一标识unionId不能为空");
		if(sysuqid == null) return setJsonInfo(res,-2,"系统唯一标识标识不能为空");

		if(userType == null )
			userType ="00";

		// 获取该微信用户信息
		WechatUserInfo wechatUserInfo = wechatUserInfoService.selectWechatUserInfoByIdAndUserType(unionId,userType);

		if(wechatUserInfo == null ) return setJsonInfo(res,-2,"登陆失败，该微信未绑定用户");
		// 获取系统配置参数
		JoinSystemInfo sysInfo = new JoinSystemInfo();
		sysInfo.setJoinsysCode(sysuqid);
		Map params = systemInfoService.getSystemDataConfig(sysuqid);
		if(params.size() == 0 || params.get("proUserData")==null || params.get("orgUserData")==null){
			return setJsonInfo(res,-3,"应用系统参数配置为空或查询失败,请联系管理员！");
		}

		// 获取参数数据
		Map map=new HashMap();
		map.put("oegUserId",wechatUserInfo.getUserId());
		map.put("userId",wechatUserInfo.getUserId());
		String pams = userType .equals( "00") ? params.get("proUserData").toString() : params.get("orgUserData").toString() ;
		map.put("params",pams);
		res = setJsonInfo(res,1,"登陆成功");
		res.put("userInfo", userType .equals( "00")  ? generalUserInfoMapper.getUserInfoByUserId(map) : organUserInfoMapper.getUserInfoByOrgId(map));
		return res;

	}



	/**
	* @Description 验证码发送接口
	* @Parm
	* @return
	**/
	@PostMapping("sendIdentify")
	@ResponseBody
	public JSONObject sendIdentify(@RequestParam Map map){
		JSONObject jsonObject = new JSONObject();
		if(map.size() == 0 ) return setJsonInfo(jsonObject,-1,"参数不能为空");
		String errorMsg = CheckSendMapData(map);
		if(errorMsg != null)  return setJsonInfo(jsonObject,-2,errorMsg);

		boolean b = remoteMsgSendService.SendMsg(map);
		return b == false ? setJsonInfo(jsonObject,-3,"发送失败，清联系管理员")
				: setJsonInfo(jsonObject,1,"发送成功");
	}

	public JSONObject setJsonInfo(JSONObject jsonObject,int status, String msg){
		jsonObject.put("status",status );
		jsonObject.put("msg",msg);
		return jsonObject;
	}

	/**
	* @Description 校验消息发送传过来的map数据
	* @Parm
	* @return
	**/
	public String  CheckSendMapData(Map map){
		if(map.get("sendType") == null) return "发送类型不能为空";
		if(map.get("subjectTitle") == null ) return "主题不能为空";
		// 邮件
		if(map.get("sendType").equals("1") ||  map.get("sendType").equals("2")){
			if(map.get("emailContent") == null ) return "邮件内容不能为空";
			if(map.get("mailTo") == null ) return "邮件接收方不能为空";
		}
		if(map.get("sendType").equals("1") || map.get("sendType").equals("3")){
			if(map.get("phoneContent") == null ) return "短信内容不能为空";
			if(map.get("phoneTo") == null ) return "短信接收方不能为空";
		}
		return null;
	}
}
