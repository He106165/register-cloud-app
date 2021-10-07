package com.censoft.wechatlogin.wechatUser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.wechatlogin.wechatUser.domain.WechatUserInfo;
import com.censoft.wechatlogin.wechatUser.service.IWechatUserInfoService;

/**
 * 微信信息 提供者
 * 
 * @author cendev
 * @date 2020-10-21
 */
@RestController
@RequestMapping("info")
public class WechatUserInfoController extends BaseController
{
	
	@Autowired
	private IWechatUserInfoService wechatUserInfoService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public WechatUserInfo get(@PathVariable("id") String id)
	{
		return wechatUserInfoService.selectWechatUserInfoById(id);
		
	}
	
	/**
	 * 查询微信信息列表
	 */
	@GetMapping("list")
	public R list(WechatUserInfo wechatUserInfo)
	{
		startPage();
        return result(wechatUserInfoService.selectWechatUserInfoList(wechatUserInfo));
	}
	
	
	/**
	 * 新增保存微信信息
	 */
	@PostMapping("save")
	public R addSave(@RequestBody WechatUserInfo wechatUserInfo)
	{		
		return toAjax(wechatUserInfoService.insertWechatUserInfo(wechatUserInfo));
	}

	/**
	 * 修改保存微信信息
	 */
	@PostMapping("update")
	public R editSave(@RequestBody WechatUserInfo wechatUserInfo)
	{		
		return toAjax(wechatUserInfoService.updateWechatUserInfo(wechatUserInfo));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(wechatUserInfoService.deleteWechatUserInfoByIds(ids));
	}
	
}
