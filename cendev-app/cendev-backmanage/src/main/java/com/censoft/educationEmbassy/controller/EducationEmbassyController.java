package com.censoft.educationEmbassy.controller;

import com.censoft.educationEmbassy.domain.EducationEmbassy;
import com.censoft.educationEmbassy.service.IEducationEmbassyService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;

/**
 * 国家教育处/大使馆 提供者
 * 
 * @author cendev
 * @date 2021-04-27
 */
@RestController
@RequestMapping("embassy")
public class EducationEmbassyController extends BaseController
{
	
	@Autowired
	private IEducationEmbassyService educationEmbassyService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public EducationEmbassy get(@PathVariable("id") String id)
	{
		return educationEmbassyService.selectEducationEmbassyById(id);
		
	}
	
	/**
	 * 查询国家教育处/大使馆列表
	 */
	@GetMapping("list")
	public R list(EducationEmbassy educationEmbassy)
	{
		startPage();
        return result(educationEmbassyService.selectEducationEmbassyList(educationEmbassy));
	}
	/**
	 * 查询国家教育处/大使馆列表
	 */
	@PostMapping("embassyList")
	public List<Map> embassyList()
	{
		return educationEmbassyService.selectEducationEmbassyList1(null);
	}
	
	/**
	 * 新增保存国家教育处/大使馆
	 */
	@PostMapping("save")
	public R addSave(@RequestBody EducationEmbassy educationEmbassy)
	{		
		return toAjax(educationEmbassyService.insertEducationEmbassy(educationEmbassy));
	}

	/**
	 * 修改保存国家教育处/大使馆
	 */
	@PostMapping("update")
	public R editSave(@RequestBody EducationEmbassy educationEmbassy)
	{		
		return toAjax(educationEmbassyService.updateEducationEmbassy(educationEmbassy));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(educationEmbassyService.deleteEducationEmbassyByIds(ids));
	}
	
}
