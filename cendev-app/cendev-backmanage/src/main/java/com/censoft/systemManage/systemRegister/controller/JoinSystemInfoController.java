package com.censoft.systemManage.systemRegister.controller;

import com.censoft.common.json.JSONObject;
import com.censoft.common.log.annotation.OperLogRes;
import com.censoft.common.log.enums.BusinessType;
import com.censoft.common.log.enums.OperatorType;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.systemManage.systemRegister.domain.JoinSystemInfo;
import com.censoft.systemManage.systemRegister.service.IJoinSystemInfoService;

import java.util.List;

/**
 * 应用系统注册 提供者
 * 
 * @author cendev
 * @date 2020-10-20
 */
@RestController
@RequestMapping("joinSystemInfo")
public class JoinSystemInfoController extends BaseController
{
	
	@Autowired
	private IJoinSystemInfoService joinSystemInfoService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{joinsysId}")
	@OperLogRes(opType = BusinessType.QUERY,opContent = "获取应用系统信息",opModel = "应用系统管理",opUserType = OperatorType.ADMIN)
	public JoinSystemInfo get(@PathVariable("joinsysId") String joinsysId)
	{
		return joinSystemInfoService.selectJoinSystemInfoById(joinsysId);
		
	}
	
	/**
	 * 查询应用系统注册列表
	 */
	@GetMapping("list")
	@OperLogRes(opType = BusinessType.QUERY,opContent = "获取应用系统列表",opModel = "应用系统管理",opUserType = OperatorType.ADMIN)
	public R list(JoinSystemInfo joinSystemInfo)
	{
		startPage();
        return result(joinSystemInfoService.selectJoinSystemInfoList(joinSystemInfo));
	}
	/**
	 * 查询应用系统注册列表
	 */
	@GetMapping("sysInfolist")
	@OperLogRes(opType = BusinessType.QUERY,opContent = "获取应用系统列表",opModel = "应用系统管理",opUserType = OperatorType.ADMIN)
	public List<JoinSystemInfo> sysInfolist(JoinSystemInfo joinSystemInfo)
	{
        return joinSystemInfoService.selectJoinSystemInfoList(joinSystemInfo);
	}

	
	/**
	 * 新增保存应用系统注册
	 */
	@PostMapping("save")
	@OperLogRes(opType = BusinessType.INSERT,opContent = "添加应用系统",opModel = "应用系统管理",opUserType = OperatorType.ADMIN)
	public R addSave(@RequestBody JoinSystemInfo joinSystemInfo)
	{
		int i = joinSystemInfoService.checkSystemName(joinSystemInfo.getJoinsysName().trim());
		if( i > 0 ){
			return R.error("新增应用系统+'"+joinSystemInfo.getJoinsysName()+"',已存在，添加失败");
		}
		return toAjax(joinSystemInfoService.insertJoinSystemInfo(joinSystemInfo));
	}

	/**
	 * 修改保存应用系统注册
	 */
	@PostMapping("update")
	@OperLogRes(opType = BusinessType.UPDATE,opContent = "修改应用系统",opModel = "应用系统管理",opUserType = OperatorType.ADMIN)
	public R editSave(@RequestBody JoinSystemInfo joinSystemInfo)
	{		
		return toAjax(joinSystemInfoService.updateJoinSystemInfo(joinSystemInfo));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@OperLogRes(opType = BusinessType.DELETE,opContent = "删除应用系统",opModel = "应用系统管理",opUserType = OperatorType.ADMIN)
	public R remove(String ids)
	{		
		return toAjax(joinSystemInfoService.deleteJoinSystemInfoByIds(ids));
	}

	/**
	 * 校验应用系统唯一标识规则如下
	 * 系统不存在或者已经被禁用 返回 false   其他情况为true
	 * */
	@GetMapping("checkSystemCode")
	@OperLogRes(opType = BusinessType.QUERY,opContent = "校验应用系统唯一标识",opModel = "应用系统管理",opUserType = OperatorType.ADMIN)
	public Boolean checkSystemCode(String code){
		if (code==null || joinSystemInfoService.checkSystemCode(code)==0) return false;
		return true;
	}


	/**
	 * 查询应用系统数据项接口
	 * 1.系统存在返回状态标志 200 返回各数据集合
	 * 2.系统不存在或者已经被禁用 返回 404
	 * 3. 未配置返回500
	 * **/
	@GetMapping("getSystemDataConfig")
	@OperLogRes(opType = BusinessType.QUERY,opContent = "获取应用系统数据项配置",opModel = "应用系统管理",opUserType = OperatorType.ADMIN)
	public JSONObject  getSystemDataConfig(String code){
		JSONObject jsonObject=new JSONObject();
		// 判断有效
		if (code==null || joinSystemInfoService.checkSystemCode(code)==0){
			jsonObject.put("code","404");
			jsonObject.put("mag","应用系统不存在或被禁用，请联系管理员");
			return jsonObject;
		}
		// 判断配置了木有
		JoinSystemInfo systemDataConfig = joinSystemInfoService.getSystemDataConfig(code);
		if(systemDataConfig.getRemark()==null && systemDataConfig.getStatus()==null){
			jsonObject.put("code","500");
			jsonObject.put("mag","应用系统数据项未配置，请联系管理员");
			return jsonObject;
		}else{
			jsonObject.put("code","200");
			jsonObject.put("orgUserData",systemDataConfig.getStatus());
			jsonObject.put("proUserData",systemDataConfig.getRemark());
			return jsonObject;
		}
	}

	/**
	 * 根据
	 */
	@GetMapping("getSystemRetUrl")
	@OperLogRes(opType = BusinessType.QUERY,opContent = "获取应用系统免登陆地址",opModel = "应用系统管理",opUserType = OperatorType.ADMIN)
	public String getSystemRetUrl(@RequestParam("joinsysCode") String joinsysCode)
	{
		if(joinsysCode==null || joinsysCode =="") return null ;
		return joinSystemInfoService.getSystemRetUrl(joinsysCode);
	}
}
