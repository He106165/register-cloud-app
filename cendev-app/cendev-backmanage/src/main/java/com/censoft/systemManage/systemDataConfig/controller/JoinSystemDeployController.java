package com.censoft.systemManage.systemDataConfig.controller;

import com.censoft.common.log.annotation.OperLogRes;
import com.censoft.common.log.enums.BusinessType;
import com.censoft.common.log.enums.OperatorType;
import com.censoft.systemManage.systemRegister.domain.JoinSystemInfo;
import com.censoft.systemManage.systemRegister.service.IJoinSystemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.systemManage.systemDataConfig.domain.JoinSystemDeploy;
import com.censoft.systemManage.systemDataConfig.service.IJoinSystemDeployService;

import java.util.List;
import java.util.Map;

/**
 * 应用系统配置 提供者
 * 
 * @author cendev
 * @date 2020-10-20
 */
@RestController
@RequestMapping("systemDataConfig")
@PropertySource({"classpath:application.properties"})
public class JoinSystemDeployController extends BaseController
{
	
	@Autowired
	private IJoinSystemDeployService joinSystemDeployService;

	@Autowired
	private IJoinSystemInfoService joinSystemInfoService;

	@Value("${SYSTEMCONFIG.DBNAME}")
	private String DBNAME;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public JoinSystemDeploy get(@PathVariable("id") String id)
	{
		return joinSystemDeployService.selectJoinSystemDeployById(id);
	}

	/**
	 * 获取系统配置，如果存在返回第一个，不存在添加查询ID返回
	 * */
	@GetMapping("getSystemConfig")
	@OperLogRes(opType = BusinessType.QUERY,opContent = "获取应用系统数据项配置",opModel = "应用系统管理",opUserType = OperatorType.ADMIN)
	public JoinSystemDeploy getSystemConfig(@RequestParam("joinsysId") String joinsysId)
	{
		JoinSystemDeploy joinSystemDeploy;
		List<JoinSystemDeploy> joinSystemDeploys=joinSystemDeployService.selectJoinSystemDeployBySystem(joinsysId);
		if(joinSystemDeploys.size()==0){
			joinSystemDeploy=new JoinSystemDeploy();
			joinSystemDeploy.setJoinsysId(joinsysId);
		}else {
			joinSystemDeploy=joinSystemDeploys.get(0);
		}
		return joinSystemDeploy;
	}



	/**
	 * 查询应用系统注册列表(数据想配置页面)
	 */
	@GetMapping("SystemDataConfiglist")
	public R SystemDatalist(JoinSystemInfo joinSystemInfo)
	{
		startPage();
		return result(joinSystemInfoService.selectJoinSystemDataConfigInfoList(joinSystemInfo));
	}



	/**
	 * 查询应用系统配置列表
	 */
	@GetMapping("list")
	public R list(JoinSystemDeploy joinSystemDeploy)
	{
		startPage();
        return result(joinSystemDeployService.selectJoinSystemDeployList(joinSystemDeploy));
	}
	
	
	/**
	 * 新增保存应用系统配置
	 */
	@PostMapping("save")
	public R addSave(@RequestBody JoinSystemDeploy joinSystemDeploy)
	{
		joinSystemDeploy.setEmpowerBy(getLoginName());
		return toAjax(joinSystemDeployService.insertJoinSystemDeploy(joinSystemDeploy));
	}

	/**
	 * 修改保存应用系统配置
	 */
	@PostMapping("update")
	public R editSave(@RequestBody JoinSystemDeploy joinSystemDeploy)
	{		
		return toAjax(joinSystemDeployService.updateJoinSystemDeploy(joinSystemDeploy));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(joinSystemDeployService.deleteJoinSystemDeployByIds(ids));
	}

	/**
	 *
	 * 获取应用系统集合
	 * */
	@GetMapping("getSystemList")
	@OperLogRes(opType = BusinessType.QUERY,opContent = "获取应用系统集合",opModel = "应用系统管理",opUserType = OperatorType.ADMIN)
	public List<JoinSystemInfo> getSystemList(JoinSystemInfo jon){
		return  joinSystemInfoService.selectJoinSystemInfoList(jon);
	}
	/**
	 * 根据数据库表获取到用户的数据字段
	 */
	@PostMapping("getUserListDateByTN")
	@OperLogRes(opType = BusinessType.QUERY,opContent = "根据数据库表获取到用户的数据字段",opModel = "应用系统管理",opUserType = OperatorType.ADMIN)
	public List<Map> getUserListDateByTN(String tableName){

		return  joinSystemDeployService.getUserListDateByDB(DBNAME,tableName);
	}
}
