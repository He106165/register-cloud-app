package com.censoft.insideUserManage.insideUser.mapper;

import com.censoft.insideUserManage.insideUser.domain.InsideUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 内部用户Mapper接口
 *
 * @author cendev
 * @date 2020-10-16
 */
@Mapper
public interface InsideUserInfoMapper {
    /**
     * 查询内部用户
     *
     * @param insideUserId 内部用户ID
     * @return 内部用户
     */
    public InsideUserInfo selectInsideUserInfoById(String insideUserId);

    /**
     * 查询内部用户列表
     *
     * @param insideUserInfo 内部用户
     * @return 内部用户集合
     */
    public List<InsideUserInfo> selectInsideUserInfoList(InsideUserInfo insideUserInfo);

    /**
     * 新增内部用户
     *
     * @param insideUserInfo 内部用户
     * @return 结果
     */
    public int insertInsideUserInfo(InsideUserInfo insideUserInfo);

    /**
     * 修改内部用户
     *
     * @param insideUserInfo 内部用户
     * @return 结果
     */
    public int updateInsideUserInfo(InsideUserInfo insideUserInfo);

    /**
     * 删除内部用户
     *
     * @param insideUserId 内部用户ID
     * @return 结果
     */
    public int deleteInsideUserInfoById(String insideUserId);

    /**
     * 批量删除内部用户
     *
     * @param insideUserIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteInsideUserInfoByIds(String[] insideUserIds);

    /**
     *  根据用户名获取第一条数据 （登陆）
     * */
    public InsideUserInfo getUserByName(@Param("userName") String userName);
}
