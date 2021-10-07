package com.censoft.sendMag.emailManage.service;

import com.censoft.sendMag.emailManage.domain.MailSendInfo;
import java.util.List;

/**
 * 邮件发送Service接口
 * 
 * @author cendev
 * @date 2020-10-21
 */
public interface IMailSendInfoService 
{
    /**
     * 查询邮件发送
     * 
     * @param id 邮件发送ID
     * @return 邮件发送
     */
    public MailSendInfo selectMailSendInfoById(String id);

    /**
     * 查询邮件发送列表
     * 
     * @param mailSendInfo 邮件发送
     * @return 邮件发送集合
     */
    public List<MailSendInfo> selectMailSendInfoList(MailSendInfo mailSendInfo);

    /**
     * 新增邮件发送
     * 
     * @param mailSendInfo 邮件发送
     * @return 结果
     */
    public int insertMailSendInfo(MailSendInfo mailSendInfo);

    /**
     * 修改邮件发送
     * 
     * @param mailSendInfo 邮件发送
     * @return 结果
     */
    public int updateMailSendInfo(MailSendInfo mailSendInfo);

    /**
     * 批量删除邮件发送
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMailSendInfoByIds(String ids);

    /**
     * 删除邮件发送信息
     * 
     * @param id 邮件发送ID
     * @return 结果
     */
    public int deleteMailSendInfoById(String id);
}
