package com.censoft.sendMag.emailManage.mapper;

import com.censoft.sendMag.emailManage.domain.NoteSendInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信发送Mapper接口
 * 
 * @author cendev
 * @date 2020-10-21
 */
@Mapper
public interface NoteSendInfoMapper 
{
    /**
     * 查询短信发送
     * 
     * @param id 短信发送ID
     * @return 短信发送
     */
    public NoteSendInfo selectNoteSendInfoById(String id);

    /**
     * 查询短信发送列表
     * 
     * @param noteSendInfo 短信发送
     * @return 短信发送集合
     */
    public List<NoteSendInfo> selectNoteSendInfoList(NoteSendInfo noteSendInfo);

    /**
     * 新增短信发送
     * 
     * @param noteSendInfo 短信发送
     * @return 结果
     */
    public int insertNoteSendInfo(NoteSendInfo noteSendInfo);

    /**
     * 修改短信发送
     * 
     * @param noteSendInfo 短信发送
     * @return 结果
     */
    public int updateNoteSendInfo(NoteSendInfo noteSendInfo);

    /**
     * 删除短信发送
     * 
     * @param id 短信发送ID
     * @return 结果
     */
    public int deleteNoteSendInfoById(String id);

    /**
     * 批量删除短信发送
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoteSendInfoByIds(String[] ids);
}
