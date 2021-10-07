package com.censoft.userManage.fcenterLocal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.userManage.fcenterLocal.domain.FcenterLocalInfo;
import com.censoft.userManage.fcenterLocal.service.IFcenterLocalInfoService;

/**
 * 分中心用户地域信息 提供者
 *
 * @author cendev
 * @date 2020-11-19
 */
@RestController
@RequestMapping("fcenterLocal")
public class FcenterLocalInfoController extends BaseController
{

	@Autowired
	private IFcenterLocalInfoService fcenterLocalInfoService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public FcenterLocalInfo get(@PathVariable("id") String id)
	{
		return fcenterLocalInfoService.selectFcenterLocalInfoById(id);

	}

	/**
	 * 查询分中心用户地域信息列表
	 */
	@GetMapping("list")
	public R list(FcenterLocalInfo fcenterLocalInfo)
	{
		startPage();
        return result(fcenterLocalInfoService.selectFcenterLocalInfoList(fcenterLocalInfo));
	}


	/**
	 * 新增保存分中心用户地域信息
	 */
	@PostMapping("save")
	public R addSave(@RequestBody FcenterLocalInfo fcenterLocalInfo)
	{
		return toAjax(fcenterLocalInfoService.insertFcenterLocalInfo(fcenterLocalInfo));
	}

	/**
	 * 修改保存分中心用户地域信息
	 */
	@PostMapping("update")
	public R editSave(@RequestBody FcenterLocalInfo fcenterLocalInfo)
	{
		return toAjax(fcenterLocalInfoService.updateFcenterLocalInfo(fcenterLocalInfo));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{
		return toAjax(fcenterLocalInfoService.deleteFcenterLocalInfoByIds(ids));
	}

}
