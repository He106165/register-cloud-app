package com.censoft.sendMag.emailManage.service.impl;

import cn.hutool.core.util.IdUtil;
import com.censoft.common.core.text.Convert;
import com.censoft.sendMag.emailManage.domain.NoteSendLog;
import com.censoft.sendMag.emailManage.mapper.NoteSendLogMapper;
import com.censoft.sendMag.emailManage.service.INoteSendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 短信发送日志Service业务层处理
 *
 * @author cendev
 * @date 2020-10-21
 */
@Service
public class NoteSendLogServiceImpl implements INoteSendLogService
{
    @Autowired
    private NoteSendLogMapper noteSendLogMapper;

    /**
     * 查询短信发送日志
     *
     * @param id 短信发送日志ID
     * @return 短信发送日志
     */
    @Override
    public NoteSendLog selectNoteSendLogById(String id)
    {
        return noteSendLogMapper.selectNoteSendLogById(id);
    }

    /**
     * 查询短信发送日志列表
     *
     * @param noteSendLog 短信发送日志
     * @return 短信发送日志
     */
    @Override
    public List<NoteSendLog> selectNoteSendLogList(NoteSendLog noteSendLog)
    {
        return noteSendLogMapper.selectNoteSendLogList(noteSendLog);
    }

    /**
     * 新增短信发送日志
     *
     * @param noteSendLog 短信发送日志
     * @return 结果
     */
    @Override
    public int insertNoteSendLog(NoteSendLog noteSendLog)
    {
        noteSendLog.setId(IdUtil.fastSimpleUUID());
        noteSendLog.setMesPlantime(new Date());
        return noteSendLogMapper.insertNoteSendLog(noteSendLog);
    }

    /**
     * 修改短信发送日志
     *
     * @param noteSendLog 短信发送日志
     * @return 结果
     */
    @Override
    public int updateNoteSendLog(NoteSendLog noteSendLog)
    {
        return noteSendLogMapper.updateNoteSendLog(noteSendLog);
    }

    /**
     * 删除短信发送日志对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNoteSendLogByIds(String ids)
    {
        return noteSendLogMapper.deleteNoteSendLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除短信发送日志信息
     *
     * @param id 短信发送日志ID
     * @return 结果
     */
    public int deleteNoteSendLogById(String id)
    {
        return noteSendLogMapper.deleteNoteSendLogById(id);
    }
}
