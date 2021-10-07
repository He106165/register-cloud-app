package com.censoft.otherlogin.linkedin.service;

import com.censoft.otherlogin.linkedin.domain.TolinkedUserInfo;
import java.util.List;

/**
 * 领英信息Service接口
 * 
 * @author cendev
 * @date 2020-10-21
 */
public interface ITolinkedUserInfoService 
{
    /**
     * 查询领英信息
     * 
     * @param id 领英信息ID
     * @return 领英信息
     */
    public TolinkedUserInfo selectTolinkedUserInfoById(String id);

    /**
     * 查询领英信息列表
     * 
     * @param tolinkedUserInfo 领英信息
     * @return 领英信息集合
     */
    public List<TolinkedUserInfo> selectTolinkedUserInfoList(TolinkedUserInfo tolinkedUserInfo);

    /**
     * 新增领英信息
     * 
     * @param tolinkedUserInfo 领英信息
     * @return 结果
     */
    public int insertTolinkedUserInfo(TolinkedUserInfo tolinkedUserInfo);

    /**
     * 修改领英信息
     * 
     * @param tolinkedUserInfo 领英信息
     * @return 结果
     */
    public int updateTolinkedUserInfo(TolinkedUserInfo tolinkedUserInfo);

    /**
     * 批量删除领英信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTolinkedUserInfoByIds(String ids);

    /**
     * 删除领英信息信息
     * 
     * @param id 领英信息ID
     * @return 结果
     */
    public int deleteTolinkedUserInfoById(String id);
}
