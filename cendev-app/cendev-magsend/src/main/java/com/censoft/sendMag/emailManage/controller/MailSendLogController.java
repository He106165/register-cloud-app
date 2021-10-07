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
import com.censoft.sendMag.emailManage.domain.MailSendLog;
import com.censoft.sendMag.emailManage.service.IMailSendLogService;

/**
 * 邮件发送日志 提供者
 * 
 * @author cendev
 * @date 2020-10-21
 */
@RestController
@RequestMapping("mailSendLog")
public class MailSendLogController extends BaseController
{
	
	@Autowired
	private IMailSendLogService mailSendLogService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public MailSendLog get(@PathVariable("id") String id)
	{
		return mailSendLogService.selectMailSendLogById(id);
		
	}
	
	/**
	 * 查询邮件发送日志列表
	 */
	@GetMapping("list")
	public R list(MailSendLog mailSendLog)
	{
		startPage();
        return result(mailSendLogService.selectMailSendLogList(mailSendLog));
	}
	
	
	/**
	 * 新增保存邮件发送日志
	 */
	@PostMapping("save")
	public R addSave(@RequestBody MailSendLog mailSendLog)
	{		
		return toAjax(mailSendLogService.insertMailSendLog(mailSendLog));
	}

	/**
	 * 修改保存邮件发送日志
	 */
	@PostMapping("update")
	public R editSave(@RequestBody MailSendLog mailSendLog)
	{		
		return toAjax(mailSendLogService.updateMailSendLog(mailSendLog));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(mailSendLogService.deleteMailSendLogByIds(ids));
	}
	
}
