package com.censoft.userManage.consulateUser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.userManage.consulateUser.domain.ConsulateInfo;
import com.censoft.userManage.consulateUser.service.IConsulateInfoService;

/**
 * 领使馆用户 提供者
 *
 * @author cendev
 * @date 2020-10-20
 */
@RestController
@RequestMapping("consulateUser")
public class ConsulateInfoController extends BaseController
{

	@Autowired
	private IConsulateInfoService consulateInfoService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public ConsulateInfo get(@PathVariable("id") Long id)
	{
		return consulateInfoService.selectConsulateInfoById(id);

	}

	/**
	 * 查询领使馆用户列表
	 */
	@GetMapping("list")
	public R list(ConsulateInfo consulateInfo)
	{
		startPage();
        return result(consulateInfoService.selectConsulateInfoList(consulateInfo));
	}


	/**
	 * 新增保存领使馆用户
	 */
	@PostMapping("save")
	public R addSave(@RequestBody ConsulateInfo consulateInfo)
	{
		return toAjax(consulateInfoService.insertConsulateInfo(consulateInfo));
	}

	/**
	 * 修改保存领使馆用户
	 */
	@PostMapping("update")
	public R editSave(@RequestBody ConsulateInfo consulateInfo)
	{
		return toAjax(consulateInfoService.updateConsulateInfo(consulateInfo));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{
		return toAjax(consulateInfoService.deleteConsulateInfoByIds(ids));
	}

}
