package com.censoft.smallRoutine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.smallRoutine.domain.BOverseasEducationMajor;
import com.censoft.smallRoutine.service.IBOverseasEducationMajorService;

/**
 * 海外教育资源--院校专业事实 提供者
 *
 * @author cendev
 * @date 2021-01-11
 */
@RestController
@RequestMapping("major")
public class BOverseasEducationMajorController extends BaseController
{

	@Autowired
	private IBOverseasEducationMajorService bOverseasEducationMajorService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public BOverseasEducationMajor get(@PathVariable("id") String id)
	{
		return bOverseasEducationMajorService.selectBOverseasEducationMajorById(id);

	}

	/**
	 * 查询海外教育资源--院校专业事实列表
	 */
	@GetMapping("list")
	public R list(BOverseasEducationMajor bOverseasEducationMajor)
	{
		startPage();
        return result(bOverseasEducationMajorService.selectBOverseasEducationMajorList(bOverseasEducationMajor));
	}


	/**
	 * 新增保存海外教育资源--院校专业事实
	 */
	@PostMapping("save")
	public R addSave(@RequestBody BOverseasEducationMajor bOverseasEducationMajor)
	{
		return toAjax(bOverseasEducationMajorService.insertBOverseasEducationMajor(bOverseasEducationMajor));
	}

	/**
	 * 修改保存海外教育资源--院校专业事实
	 */
	@PostMapping("update")
	public R editSave(@RequestBody BOverseasEducationMajor bOverseasEducationMajor)
	{
		return toAjax(bOverseasEducationMajorService.updateBOverseasEducationMajor(bOverseasEducationMajor));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{
		return toAjax(bOverseasEducationMajorService.deleteBOverseasEducationMajorByIds(ids));
	}

}
