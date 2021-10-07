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
import com.censoft.smallRoutine.domain.BOverseasEducationBase;
import com.censoft.smallRoutine.service.IBOverseasEducationBaseService;

/**
 * 海外教育资源--院校基础信息实体 提供者
 *
 * @author cendev
 * @date 2021-01-11
 */
@RestController
@RequestMapping("base")
public class BOverseasEducationBaseController extends BaseController
{

	@Autowired
	private IBOverseasEducationBaseService bOverseasEducationBaseService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public BOverseasEducationBase get(@PathVariable("id") String id)
	{
		return bOverseasEducationBaseService.selectBOverseasEducationBaseById(id);

	}

	/**
	 * 查询海外教育资源--院校基础信息实体列表
	 */
	@GetMapping("list")
	public R list(BOverseasEducationBase bOverseasEducationBase)
	{
		startPage();
        return result(bOverseasEducationBaseService.selectBOverseasEducationBaseList(bOverseasEducationBase));
	}


	/**
	 * 新增保存海外教育资源--院校基础信息实体
	 */
	@PostMapping("save")
	public R addSave(@RequestBody BOverseasEducationBase bOverseasEducationBase)
	{
		return toAjax(bOverseasEducationBaseService.insertBOverseasEducationBase(bOverseasEducationBase));
	}

	/**
	 * 修改保存海外教育资源--院校基础信息实体
	 */
	@PostMapping("update")
	public R editSave(@RequestBody BOverseasEducationBase bOverseasEducationBase)
	{
		return toAjax(bOverseasEducationBaseService.updateBOverseasEducationBase(bOverseasEducationBase));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{
		return toAjax(bOverseasEducationBaseService.deleteBOverseasEducationBaseByIds(ids));
	}

}
