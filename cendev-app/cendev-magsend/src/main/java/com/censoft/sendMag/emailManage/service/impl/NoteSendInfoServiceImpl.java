package com.censoft.sendMag.emailManage.service.impl;

import cn.hutool.core.util.IdUtil;
import com.censoft.common.core.text.Convert;
import com.censoft.sendMag.emailManage.domain.NoteSendInfo;
import com.censoft.sendMag.emailManage.mapper.NoteSendInfoMapper;
import com.censoft.sendMag.emailManage.service.INoteSendInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 短信发送Service业务层处理
 *
 * @author cendev
 * @date 2020-10-21
 */
@Service
public class NoteSendInfoServiceImpl implements INoteSendInfoService
{
    @Autowired
    private NoteSendInfoMapper noteSendInfoMapper;

    /**
     * 查询短信发送
     *
     * @param id 短信发送ID
     * @return 短信发送
     */
    @Override
    public NoteSendInfo selectNoteSendInfoById(String id)
    {
        return noteSendInfoMapper.selectNoteSendInfoById(id);
    }

    /**
     * 查询短信发送列表
     *
     * @param noteSendInfo 短信发送
     * @return 短信发送
     */
    @Override
    public List<NoteSendInfo> selectNoteSendInfoList(NoteSendInfo noteSendInfo)
    {
        return noteSendInfoMapper.selectNoteSendInfoList(noteSendInfo);
    }

    /**
     * 新增短信发送
     *
     * @param noteSendInfo 短信发送
     * @return 结果
     */
    @Override
    public int insertNoteSendInfo(NoteSendInfo noteSendInfo)
    {
        noteSendInfo.setId(IdUtil.fastSimpleUUID());
        noteSendInfo.setMesPlantime(new Date());

        return noteSendInfoMapper.insertNoteSendInfo(noteSendInfo);
    }

    /**
     * 修改短信发送
     *
     * @param noteSendInfo 短信发送
     * @return 结果
     */
    @Override
    public int updateNoteSendInfo(NoteSendInfo noteSendInfo)
    {
        return noteSendInfoMapper.updateNoteSendInfo(noteSendInfo);
    }

    /**
     * 删除短信发送对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNoteSendInfoByIds(String ids)
    {
        return noteSendInfoMapper.deleteNoteSendInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除短信发送信息
     *
     * @param id 短信发送ID
     * @return 结果
     */
    public int deleteNoteSendInfoById(String id)
    {
        return noteSendInfoMapper.deleteNoteSendInfoById(id);
    }
}
