package com.censoft.userManage.fcenterUser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.userManage.fcenterUser.domain.FcenterUserInfo;
import com.censoft.userManage.fcenterUser.service.IFcenterUserInfoService;

/**
 * 分中心用户 提供者
 *
 * @author cendev
 * @date 2020-11-23
 */
@RestController
@RequestMapping("centerUser")
public class FcenterUserInfoController extends BaseController
{

	@Autowired
	private IFcenterUserInfoService fcenterUserInfoService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public FcenterUserInfo get(@PathVariable("id") Long id)
	{
		return fcenterUserInfoService.selectFcenterUserInfoById(id);

	}

	/**
	 * 查询分中心用户列表
	 */
	@GetMapping("list")
	public R list(FcenterUserInfo fcenterUserInfo)
	{
		startPage();
        return result(fcenterUserInfoService.selectFcenterUserInfoList(fcenterUserInfo));
	}


	/**
	 * 新增保存分中心用户
	 */
	@PostMapping("save")
	public R addSave(@RequestBody FcenterUserInfo fcenterUserInfo)
	{
		return toAjax(fcenterUserInfoService.insertFcenterUserInfo(fcenterUserInfo));
	}

	/**
	 * 修改保存分中心用户
	 */
	@PostMapping("update")
	public R editSave(@RequestBody FcenterUserInfo fcenterUserInfo)
	{
		return toAjax(fcenterUserInfoService.updateFcenterUserInfo(fcenterUserInfo));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{
		return toAjax(fcenterUserInfoService.deleteFcenterUserInfoByIds(ids));
	}

}
