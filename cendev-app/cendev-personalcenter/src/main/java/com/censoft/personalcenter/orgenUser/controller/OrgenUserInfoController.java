package com.censoft.personalcenter.orgenUser.controller;

import com.censoft.common.core.controller.BaseController;
import com.censoft.common.core.domain.R;
import com.censoft.personalcenter.orgenUser.domain.OrgenUserInfo;
import com.censoft.personalcenter.orgenUser.service.IOrgenUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 机构用户 提供者
 *
 * @author cendev
 * @date 2020-10-20
 */
@RestController
@RequestMapping("orgenUser")
public class OrgenUserInfoController extends BaseController
{

	@Autowired
	private IOrgenUserInfoService orgenUserInfoService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{orgUserId}")
	public OrgenUserInfo get(@PathVariable("orgUserId") String orgUserId)
	{
		return orgenUserInfoService.selectOrgenUserInfoById(orgUserId);

	}

	/**
	 * 查询机构用户列表
	 */
	@GetMapping("list")
	public R list(OrgenUserInfo orgenUserInfo)
	{
		startPage();
        return result(orgenUserInfoService.selectOrgenUserInfoList(orgenUserInfo));
	}


	/**
	 * 新增保存机构用户
	 */
	@PostMapping("save")
	public R addSave(@RequestBody OrgenUserInfo orgenUserInfo)
	{
		return toAjax(orgenUserInfoService.insertOrgenUserInfo(orgenUserInfo));
	}

	/**
	 * 修改保存机构用户
	 */
	@PostMapping("update")
	public R editSave(@RequestBody OrgenUserInfo orgenUserInfo)
	{
		return toAjax(orgenUserInfoService.updateOrgenUserInfo(orgenUserInfo));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{
		return toAjax(orgenUserInfoService.deleteOrgenUserInfoByIds(ids));
	}

}
