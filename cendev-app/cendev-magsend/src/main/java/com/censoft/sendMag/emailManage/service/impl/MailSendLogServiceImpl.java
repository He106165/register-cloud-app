package com.censoft.sendMag.emailManage.service.impl;

import cn.hutool.core.util.IdUtil;
import com.censoft.common.core.text.Convert;
import com.censoft.sendMag.emailManage.domain.MailSendLog;
import com.censoft.sendMag.emailManage.mapper.MailSendLogMapper;
import com.censoft.sendMag.emailManage.service.IMailSendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 邮件发送日志Service业务层处理
 *
 * @author cendev
 * @date 2020-10-21
 */
@Service
public class MailSendLogServiceImpl implements IMailSendLogService
{
    @Autowired
    private MailSendLogMapper mailSendLogMapper;

    /**
     * 查询邮件发送日志
     *
     * @param id 邮件发送日志ID
     * @return 邮件发送日志
     */
    @Override
    public MailSendLog selectMailSendLogById(String id)
    {
        return mailSendLogMapper.selectMailSendLogById(id);
    }

    /**
     * 查询邮件发送日志列表
     *
     * @param mailSendLog 邮件发送日志
     * @return 邮件发送日志
     */
    @Override
    public List<MailSendLog> selectMailSendLogList(MailSendLog mailSendLog)
    {
        return mailSendLogMapper.selectMailSendLogList(mailSendLog);
    }

    /**
     * 新增邮件发送日志
     *
     * @param mailSendLog 邮件发送日志
     * @return 结果
     */
    @Override
    public int insertMailSendLog(MailSendLog mailSendLog)
    {
        mailSendLog.setId(IdUtil.fastSimpleUUID());
        mailSendLog.setSendTime(new Date());
        return mailSendLogMapper.insertMailSendLog(mailSendLog);
    }

    /**
     * 修改邮件发送日志
     *
     * @param mailSendLog 邮件发送日志
     * @return 结果
     */
    @Override
    public int updateMailSendLog(MailSendLog mailSendLog)
    {
        return mailSendLogMapper.updateMailSendLog(mailSendLog);
    }

    /**
     * 删除邮件发送日志对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMailSendLogByIds(String ids)
    {
        return mailSendLogMapper.deleteMailSendLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除邮件发送日志信息
     *
     * @param id 邮件发送日志ID
     * @return 结果
     */
    public int deleteMailSendLogById(String id)
    {
        return mailSendLogMapper.deleteMailSendLogById(id);
    }
}
