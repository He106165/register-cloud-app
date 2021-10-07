package com.censoft.sendMag.emailManage.service;

import com.censoft.sendMag.emailManage.domain.MailSendLog;
import java.util.List;

/**
 * 邮件发送日志Service接口
 * 
 * @author cendev
 * @date 2020-10-21
 */
public interface IMailSendLogService 
{
    /**
     * 查询邮件发送日志
     * 
     * @param id 邮件发送日志ID
     * @return 邮件发送日志
     */
    public MailSendLog selectMailSendLogById(String id);

    /**
     * 查询邮件发送日志列表
     * 
     * @param mailSendLog 邮件发送日志
     * @return 邮件发送日志集合
     */
    public List<MailSendLog> selectMailSendLogList(MailSendLog mailSendLog);

    /**
     * 新增邮件发送日志
     * 
     * @param mailSendLog 邮件发送日志
     * @return 结果
     */
    public int insertMailSendLog(MailSendLog mailSendLog);

    /**
     * 修改邮件发送日志
     * 
     * @param mailSendLog 邮件发送日志
     * @return 结果
     */
    public int updateMailSendLog(MailSendLog mailSendLog);

    /**
     * 批量删除邮件发送日志
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMailSendLogByIds(String ids);

    /**
     * 删除邮件发送日志信息
     * 
     * @param id 邮件发送日志ID
     * @return 结果
     */
    public int deleteMailSendLogById(String id);
}
