package com.censoft.sendMag.emailManage.service.impl;

import cn.hutool.core.util.IdUtil;
import com.censoft.common.core.text.Convert;
import com.censoft.sendMag.emailManage.domain.MailSendInfo;
import com.censoft.sendMag.emailManage.mapper.MailSendInfoMapper;
import com.censoft.sendMag.emailManage.service.IMailSendInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 邮件发送Service业务层处理
 *
 * @author cendev
 * @date 2020-10-21
 */
@Service
public class MailSendInfoServiceImpl implements IMailSendInfoService
{
    @Autowired
    private MailSendInfoMapper mailSendInfoMapper;

    /**
     * 查询邮件发送
     *
     * @param id 邮件发送ID
     * @return 邮件发送
     */
    @Override
    public MailSendInfo selectMailSendInfoById(String id)
    {
        return mailSendInfoMapper.selectMailSendInfoById(id);
    }

    /**
     * 查询邮件发送列表
     *
     * @param mailSendInfo 邮件发送
     * @return 邮件发送
     */
    @Override
    public List<MailSendInfo> selectMailSendInfoList(MailSendInfo mailSendInfo)
    {
        return mailSendInfoMapper.selectMailSendInfoList(mailSendInfo);
    }

    /**
     * 新增邮件发送
     *
     * @param mailSendInfo 邮件发送
     * @return 结果
     */
    @Override
    public int insertMailSendInfo(MailSendInfo mailSendInfo)
    {
        mailSendInfo.setId(IdUtil.fastSimpleUUID());
        mailSendInfo.setSendTime(new Date());
        return mailSendInfoMapper.insertMailSendInfo(mailSendInfo);
    }

    /**
     * 修改邮件发送
     *
     * @param mailSendInfo 邮件发送
     * @return 结果
     */
    @Override
    public int updateMailSendInfo(MailSendInfo mailSendInfo)
    {
        return mailSendInfoMapper.updateMailSendInfo(mailSendInfo);
    }

    /**
     * 删除邮件发送对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMailSendInfoByIds(String ids)
    {
        return mailSendInfoMapper.deleteMailSendInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除邮件发送信息
     *
     * @param id 邮件发送ID
     * @return 结果
     */
    public int deleteMailSendInfoById(String id)
    {
        return mailSendInfoMapper.deleteMailSendInfoById(id);
    }
}
