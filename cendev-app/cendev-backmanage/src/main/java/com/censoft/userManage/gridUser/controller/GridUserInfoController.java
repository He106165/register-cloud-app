package com.censoft.userManage.gridUser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.userManage.gridUser.domain.GridUserInfo;
import com.censoft.userManage.gridUser.service.IGridUserInfoService;

/**
 * 网格用户信息 提供者
 *
 * @author cendev
 * @date 2020-12-29
 */
@RestController
@RequestMapping("gridUser")
public class GridUserInfoController extends BaseController
{

	@Autowired
	private IGridUserInfoService gridUserInfoService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public GridUserInfo get(@PathVariable("id") Long id)
	{
		return gridUserInfoService.selectGridUserInfoById(id);

	}

	/**
	 * 查询网格用户信息列表
	 */
	@GetMapping("list")
	public R list(GridUserInfo gridUserInfo)
	{
		startPage();
        return result(gridUserInfoService.selectGridUserInfoList(gridUserInfo));
	}


	/**
	 * 新增保存网格用户信息
	 */
	@PostMapping("save")
	public R addSave(@RequestBody GridUserInfo gridUserInfo)
	{
		return toAjax(gridUserInfoService.insertGridUserInfo(gridUserInfo));
	}

	/**
	 * 修改保存网格用户信息
	 */
	@PostMapping("update")
	public R editSave(@RequestBody GridUserInfo gridUserInfo)
	{
		return toAjax(gridUserInfoService.updateGridUserInfo(gridUserInfo));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{
		return toAjax(gridUserInfoService.deleteGridUserInfoByIds(ids));
	}

}
