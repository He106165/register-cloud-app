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
import com.censoft.smallRoutine.domain.BOverseasEducationRank;
import com.censoft.smallRoutine.service.IBOverseasEducationRankService;

/**
 * 海外教育资源--院校排名事实 提供者
 *
 * @author cendev
 * @date 2021-01-11
 */
@RestController
@RequestMapping("rank")
public class BOverseasEducationRankController extends BaseController
{

	@Autowired
	private IBOverseasEducationRankService bOverseasEducationRankService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public BOverseasEducationRank get(@PathVariable("id") String id)
	{
		return bOverseasEducationRankService.selectBOverseasEducationRankById(id);

	}

	/**
	 * 查询海外教育资源--院校排名事实列表
	 */
	@GetMapping("list")
	public R list(BOverseasEducationRank bOverseasEducationRank)
	{
		startPage();
        return result(bOverseasEducationRankService.selectBOverseasEducationRankList(bOverseasEducationRank));
	}


	/**
	 * 新增保存海外教育资源--院校排名事实
	 */
	@PostMapping("save")
	public R addSave(@RequestBody BOverseasEducationRank bOverseasEducationRank)
	{
		return toAjax(bOverseasEducationRankService.insertBOverseasEducationRank(bOverseasEducationRank));
	}

	/**
	 * 修改保存海外教育资源--院校排名事实
	 */
	@PostMapping("update")
	public R editSave(@RequestBody BOverseasEducationRank bOverseasEducationRank)
	{
		return toAjax(bOverseasEducationRankService.updateBOverseasEducationRank(bOverseasEducationRank));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{
		return toAjax(bOverseasEducationRankService.deleteBOverseasEducationRankByIds(ids));
	}

}
