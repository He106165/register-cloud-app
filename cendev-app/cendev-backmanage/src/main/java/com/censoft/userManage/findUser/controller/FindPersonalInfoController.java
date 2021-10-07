package com.censoft.userManage.findUser.controller;

import com.censoft.Util.ResultStatusEnum;
import com.censoft.userManage.findUser.service.FindSendMsgService;
import com.censoft.userManage.personalUser.domain.ResultUtil;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.userManage.findUser.domain.FindPersonalInfo;
import com.censoft.userManage.findUser.service.IFindPersonalInfoService;

/**
 * 找回个人用户信息 提供者
 * 
 * @author cendev
 * @date 2020-10-20
 */
@RestController
@RequestMapping("findUser")
public class FindPersonalInfoController extends BaseController
{
	
	@Autowired
	private IFindPersonalInfoService findPersonalInfoService;

	@Autowired
	private FindSendMsgService findSendMsgService;
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public FindPersonalInfo get(@PathVariable("id") String id)
	{
		return findPersonalInfoService.selectFindPersonalInfoById(id);
		
	}
	
	/**
	 * 查询找回个人用户信息列表
	 */
	@GetMapping("list")
	public R list(FindPersonalInfo findPersonalInfo)
	{
		startPage();
        return result(findPersonalInfoService.selectFindPersonalInfoList(findPersonalInfo));
	}
	
	
	/**
	 * 新增保存找回个人用户信息
	 */
	@PostMapping("save")
	public R addSave(@RequestBody FindPersonalInfo findPersonalInfo)
	{		
		return toAjax(findPersonalInfoService.insertFindPersonalInfo(findPersonalInfo));
	}

	/**
	 * 修改保存找回个人用户信息
	 */
	@PostMapping("update")
	public R editSave(@RequestBody FindPersonalInfo findPersonalInfo)
	{		
		return toAjax(findPersonalInfoService.updateFindPersonalInfo(findPersonalInfo));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(findPersonalInfoService.deleteFindPersonalInfoByIds(ids));
	}

	/**
	 * 个人用户找回
	 */
	@PostMapping("findUserInfo")
	public ResultUtil findUser(@RequestBody FindPersonalInfo findPersonalInfo)
	{
		return findPersonalInfoService.findUser(findPersonalInfo);
	}
	//向表中添加驳回内容
	@PostMapping("inserBohui")
	public ResultUtil inserBohui(@RequestBody FindPersonalInfo findPersonalInfo)
	{
		return findPersonalInfoService.inserBohui(findPersonalInfo);
	}
	//审批通过发送短信模板
	@ApiOperation(value = "个人用户人工找回密码成功发送短信", notes = "个人用户人工找回密码成功发送短信")
	@PostMapping("successMsg")
	public ResultUtil successMsg(@RequestBody Map<String, String> map) {
		boolean ok = findSendMsgService.successMsg(map);
		if (ok) {
			return ResultUtil.result(ResultStatusEnum.S_10009.getStatus(), ResultStatusEnum.S_10009.getMassage());
		} else {
			return ResultUtil.result(ResultStatusEnum.S_10010.getStatus(), ResultStatusEnum.S_10010.getMassage());
		}
	}

	//审批不通过发送短信模板
	@ApiOperation(value = "个人用户人工找回密码失败发送短信", notes = "个人用户人工找回密码失败发送短信")
	@PostMapping("failMsg")
	public ResultUtil failMsg(@RequestBody Map<String, String> map) {
		boolean ok = findSendMsgService.failMsg(map);
		if (ok) {
			return ResultUtil.result(ResultStatusEnum.S_10009.getStatus(), ResultStatusEnum.S_10009.getMassage());
		} else {
			return ResultUtil.result(ResultStatusEnum.S_10010.getStatus(), ResultStatusEnum.S_10010.getMassage());
		}
	}
}
