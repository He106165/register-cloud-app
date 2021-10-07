package com.censoft.sendMag.emailManage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.sendMag.emailManage.domain.NoteSendLog;
import com.censoft.sendMag.emailManage.service.INoteSendLogService;

/**
 * 短信发送日志 提供者
 * 
 * @author cendev
 * @date 2020-10-21
 */
@RestController
@RequestMapping("noteSendLog")
public class NoteSendLogController extends BaseController
{
	
	@Autowired
	private INoteSendLogService noteSendLogService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public NoteSendLog get(@PathVariable("id") String id)
	{
		return noteSendLogService.selectNoteSendLogById(id);
		
	}
	
	/**
	 * 查询短信发送日志列表
	 */
	@GetMapping("list")
	public R list(NoteSendLog noteSendLog)
	{
		startPage();
        return result(noteSendLogService.selectNoteSendLogList(noteSendLog));
	}
	
	
	/**
	 * 新增保存短信发送日志
	 */
	@PostMapping("save")
	public R addSave(@RequestBody NoteSendLog noteSendLog)
	{		
		return toAjax(noteSendLogService.insertNoteSendLog(noteSendLog));
	}

	/**
	 * 修改保存短信发送日志
	 */
	@PostMapping("update")
	public R editSave(@RequestBody NoteSendLog noteSendLog)
	{		
		return toAjax(noteSendLogService.updateNoteSendLog(noteSendLog));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(noteSendLogService.deleteNoteSendLogByIds(ids));
	}
	
}
