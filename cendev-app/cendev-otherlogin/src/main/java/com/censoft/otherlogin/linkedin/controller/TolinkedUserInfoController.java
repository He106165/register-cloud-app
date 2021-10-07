package com.censoft.otherlogin.linkedin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.otherlogin.linkedin.domain.TolinkedUserInfo;
import com.censoft.otherlogin.linkedin.service.ITolinkedUserInfoService;

/**
 * 领英信息 提供者
 * 
 * @author cendev
 * @date 2020-10-21
 */
@RestController
@RequestMapping("LinkedinLogin")
public class TolinkedUserInfoController extends BaseController
{
	
	@Autowired
	private ITolinkedUserInfoService tolinkedUserInfoService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public TolinkedUserInfo get(@PathVariable("id") String id)
	{
		return tolinkedUserInfoService.selectTolinkedUserInfoById(id);
		
	}
	
	/**
	 * 查询领英信息列表
	 */
	@GetMapping("list")
	public R list(TolinkedUserInfo tolinkedUserInfo)
	{
		startPage();
        return result(tolinkedUserInfoService.selectTolinkedUserInfoList(tolinkedUserInfo));
	}
	
	
	/**
	 * 新增保存领英信息
	 */
	@PostMapping("save")
	public R addSave(@RequestBody TolinkedUserInfo tolinkedUserInfo)
	{		
		return toAjax(tolinkedUserInfoService.insertTolinkedUserInfo(tolinkedUserInfo));
	}

	/**
	 * 修改保存领英信息
	 */
	@PostMapping("update")
	public R editSave(@RequestBody TolinkedUserInfo tolinkedUserInfo)
	{		
		return toAjax(tolinkedUserInfoService.updateTolinkedUserInfo(tolinkedUserInfo));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(tolinkedUserInfoService.deleteTolinkedUserInfoByIds(ids));
	}
	
}
