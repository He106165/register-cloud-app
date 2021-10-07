package com.censoft.otherlogin.linkedin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.censoft.otherlogin.linkedin.mapper.TolinkedUserInfoMapper;
import com.censoft.otherlogin.linkedin.domain.TolinkedUserInfo;
import com.censoft.otherlogin.linkedin.service.ITolinkedUserInfoService;
import com.censoft.common.core.text.Convert;

/**
 * 领英信息Service业务层处理
 * 
 * @author cendev
 * @date 2020-10-21
 */
@Service
public class TolinkedUserInfoServiceImpl implements ITolinkedUserInfoService 
{
    @Autowired
    private TolinkedUserInfoMapper tolinkedUserInfoMapper;

    /**
     * 查询领英信息
     * 
     * @param id 领英信息ID
     * @return 领英信息
     */
    @Override
    public TolinkedUserInfo selectTolinkedUserInfoById(String id)
    {
        return tolinkedUserInfoMapper.selectTolinkedUserInfoById(id);
    }

    /**
     * 查询领英信息列表
     * 
     * @param tolinkedUserInfo 领英信息
     * @return 领英信息
     */
    @Override
    public List<TolinkedUserInfo> selectTolinkedUserInfoList(TolinkedUserInfo tolinkedUserInfo)
    {
        return tolinkedUserInfoMapper.selectTolinkedUserInfoList(tolinkedUserInfo);
    }

    /**
     * 新增领英信息
     * 
     * @param tolinkedUserInfo 领英信息
     * @return 结果
     */
    @Override
    public int insertTolinkedUserInfo(TolinkedUserInfo tolinkedUserInfo)
    {
        return tolinkedUserInfoMapper.insertTolinkedUserInfo(tolinkedUserInfo);
    }

    /**
     * 修改领英信息
     * 
     * @param tolinkedUserInfo 领英信息
     * @return 结果
     */
    @Override
    public int updateTolinkedUserInfo(TolinkedUserInfo tolinkedUserInfo)
    {
        return tolinkedUserInfoMapper.updateTolinkedUserInfo(tolinkedUserInfo);
    }

    /**
     * 删除领英信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTolinkedUserInfoByIds(String ids)
    {
        return tolinkedUserInfoMapper.deleteTolinkedUserInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除领英信息信息
     * 
     * @param id 领英信息ID
     * @return 结果
     */
    public int deleteTolinkedUserInfoById(String id)
    {
        return tolinkedUserInfoMapper.deleteTolinkedUserInfoById(id);
    }
}
