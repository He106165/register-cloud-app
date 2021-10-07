package com.censoft.personalcenter.personalUser.controller;


import com.censoft.common.core.controller.BaseController;
import com.censoft.common.core.domain.R;
import com.censoft.personalcenter.personalUser.domain.PersonalAbroafdstudyInfo;
import com.censoft.personalcenter.personalUser.service.IPersonalAbroafdstudyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * 个人留学信息 提供者
 *
 * @author cendev
 * @date 2020-11-11
 */

@RestController
@RequestMapping("PersonalAbroafdstudyInfo")
public class PersonalAbroafdstudyInfoController extends BaseController
{

	@Autowired
	private IPersonalAbroafdstudyInfoService personalAbroafdstudyInfoService;


/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public PersonalAbroafdstudyInfo get(@PathVariable("id") String id)
	{
		return personalAbroafdstudyInfoService.selectPersonalAbroafdstudyInfoById(id);

	}
/**
	 * 查询个人留学信息列表
	 */

	@GetMapping("list")
	public R list(PersonalAbroafdstudyInfo personalAbroafdstudyInfo)
	{
		startPage();
        return result(personalAbroafdstudyInfoService.selectPersonalAbroafdstudyInfoList(personalAbroafdstudyInfo));
	}



/**
	 * 新增保存个人留学信息
	 */

	@PostMapping("save")
	public R addSave(@RequestBody PersonalAbroafdstudyInfo personalAbroafdstudyInfo)
	{
		return toAjax(personalAbroafdstudyInfoService.insertPersonalAbroafdstudyInfo(personalAbroafdstudyInfo));
	}


/**
	 * 修改保存个人留学信息
	 */

	@PostMapping("update")
	public R editSave(@RequestBody PersonalAbroafdstudyInfo personalAbroafdstudyInfo)
	{
		return toAjax(personalAbroafdstudyInfoService.updatePersonalAbroafdstudyInfo(personalAbroafdstudyInfo));
	}

	/**
	 * 删除${tableComment}
	 */

	@PostMapping("remove")
	public R remove(String ids)
	{
		return toAjax(personalAbroafdstudyInfoService.deletePersonalAbroafdstudyInfoByIds(ids));
	}
}

