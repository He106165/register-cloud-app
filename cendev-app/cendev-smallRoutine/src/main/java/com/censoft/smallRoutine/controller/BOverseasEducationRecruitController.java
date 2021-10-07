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
import com.censoft.smallRoutine.domain.BOverseasEducationRecruit;
import com.censoft.smallRoutine.service.IBOverseasEducationRecruitService;

/**
 * 海外教育资源--招生详情 提供者
 *
 * @author cendev
 * @date 2021-01-11
 */
@RestController
@RequestMapping("recruit")
public class BOverseasEducationRecruitController extends BaseController
{

	@Autowired
	private IBOverseasEducationRecruitService bOverseasEducationRecruitService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public BOverseasEducationRecruit get(@PathVariable("id") String id)
	{
		return bOverseasEducationRecruitService.selectBOverseasEducationRecruitById(id);

	}

	/**
	 * 查询海外教育资源--招生详情列表
	 */
	@GetMapping("list")
	public R list(BOverseasEducationRecruit bOverseasEducationRecruit)
	{
		startPage();
        return result(bOverseasEducationRecruitService.selectBOverseasEducationRecruitList(bOverseasEducationRecruit));
	}


	/**
	 * 新增保存海外教育资源--招生详情
	 */
	@PostMapping("save")
	public R addSave(@RequestBody BOverseasEducationRecruit bOverseasEducationRecruit)
	{
		return toAjax(bOverseasEducationRecruitService.insertBOverseasEducationRecruit(bOverseasEducationRecruit));
	}

	/**
	 * 修改保存海外教育资源--招生详情
	 */
	@PostMapping("update")
	public R editSave(@RequestBody BOverseasEducationRecruit bOverseasEducationRecruit)
	{
		return toAjax(bOverseasEducationRecruitService.updateBOverseasEducationRecruit(bOverseasEducationRecruit));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{
		return toAjax(bOverseasEducationRecruitService.deleteBOverseasEducationRecruitByIds(ids));
	}

}
