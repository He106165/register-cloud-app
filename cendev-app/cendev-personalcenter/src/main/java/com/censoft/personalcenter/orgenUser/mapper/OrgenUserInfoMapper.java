package com.censoft.personalcenter.orgenUser.mapper;

import com.censoft.personalcenter.orgenUser.domain.OrgenUserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 机构用户Mapper接口
 *
 * @author cendev
 * @date 2020-10-20
 */
@Mapper
public interface OrgenUserInfoMapper
{
    /**
     * 查询机构用户
     *
     * @param orgUserId 机构用户ID
     * @return 机构用户
     */
    public OrgenUserInfo selectOrgenUserInfoById(String orgUserId);

    /**
     * 查询机构用户列表
     *
     * @param orgenUserInfo 机构用户
     * @return 机构用户集合
     */
    public List<OrgenUserInfo> selectOrgenUserInfoList(OrgenUserInfo orgenUserInfo);

    /**
     * 新增机构用户
     *
     * @param orgenUserInfo 机构用户
     * @return 结果
     */
    public int insertOrgenUserInfo(OrgenUserInfo orgenUserInfo);

    /**
     * 修改机构用户
     *
     * @param orgenUserInfo 机构用户
     * @return 结果
     */
    public int updateOrgenUserInfo(OrgenUserInfo orgenUserInfo);

    /**
     * 删除机构用户
     *
     * @param orgUserId 机构用户ID
     * @return 结果
     */
    public int deleteOrgenUserInfoById(String orgUserId);

    /**
     * 批量删除机构用户
     *
     * @param orgUserIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrgenUserInfoByIds(String[] orgUserIds);
}
