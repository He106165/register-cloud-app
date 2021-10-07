package com.censoft.sendMag.emailManage.service;

import com.censoft.sendMag.emailManage.domain.NoteSendLog;
import java.util.List;

/**
 * 短信发送日志Service接口
 * 
 * @author cendev
 * @date 2020-10-21
 */
public interface INoteSendLogService 
{
    /**
     * 查询短信发送日志
     * 
     * @param id 短信发送日志ID
     * @return 短信发送日志
     */
    public NoteSendLog selectNoteSendLogById(String id);

    /**
     * 查询短信发送日志列表
     * 
     * @param noteSendLog 短信发送日志
     * @return 短信发送日志集合
     */
    public List<NoteSendLog> selectNoteSendLogList(NoteSendLog noteSendLog);

    /**
     * 新增短信发送日志
     * 
     * @param noteSendLog 短信发送日志
     * @return 结果
     */
    public int insertNoteSendLog(NoteSendLog noteSendLog);

    /**
     * 修改短信发送日志
     * 
     * @param noteSendLog 短信发送日志
     * @return 结果
     */
    public int updateNoteSendLog(NoteSendLog noteSendLog);

    /**
     * 批量删除短信发送日志
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoteSendLogByIds(String ids);

    /**
     * 删除短信发送日志信息
     * 
     * @param id 短信发送日志ID
     * @return 结果
     */
    public int deleteNoteSendLogById(String id);
}
