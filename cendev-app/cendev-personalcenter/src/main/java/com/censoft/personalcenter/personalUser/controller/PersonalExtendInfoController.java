package com.censoft.personalcenter.personalUser.controller;
import com.censoft.common.core.controller.BaseController;
import com.censoft.common.core.domain.R;
import com.censoft.personalcenter.personalUser.domain.PersonalExtendInfo;
import com.censoft.personalcenter.personalUser.service.IPersonalExtendInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 个人用户扩展信息 提供者
 *
 * @author cendev
 * @date 2020-11-11
 */

@RestController
@RequestMapping("PersonalExtendInfo")
public class PersonalExtendInfoController extends BaseController
{

	@Autowired
	private IPersonalExtendInfoService personalExtendInfoService;


	/**
	 * 查询${tableComment}
	 */

	@GetMapping("get/{id}")
	public PersonalExtendInfo get(@PathVariable("id") String id)
	{
		return personalExtendInfoService.selectPersonalExtendInfoById(id);

	}


	/**
	 * 查询个人用户扩展信息列表
	 */

	@GetMapping("list")
	public R list(PersonalExtendInfo personalExtendInfo)
	{
		startPage();
        return result(personalExtendInfoService.selectPersonalExtendInfoList(personalExtendInfo));
	}

	/**
	 * 新增保存个人用户扩展信息
	 */

	@PostMapping("save")
	public R addSave(@RequestBody PersonalExtendInfo personalExtendInfo)
	{
		return toAjax(personalExtendInfoService.insertPersonalExtendInfo(personalExtendInfo));
	}


	/**
	 * 修改保存个人用户扩展信息
	 */

	@PostMapping("update")
	public R editSave(@RequestBody PersonalExtendInfo personalExtendInfo)
	{
		return toAjax(personalExtendInfoService.updatePersonalExtendInfo(personalExtendInfo));
	}


	/**
	 * 删除${tableComment}
	 */

	@PostMapping("remove")
	public R remove(String ids)
	{
		return toAjax(personalExtendInfoService.deletePersonalExtendInfoByIds(ids));
	}

}

