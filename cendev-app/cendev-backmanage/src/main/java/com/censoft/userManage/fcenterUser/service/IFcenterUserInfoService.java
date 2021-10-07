package com.censoft.userManage.fcenterUser.service;

import com.censoft.userManage.fcenterUser.domain.FcenterUserInfo;
import java.util.List;
import java.util.Map;

/**
 * 分中心用户Service接口
 *
 * @author cendev
 * @date 2020-11-23
 */
public interface IFcenterUserInfoService
{
    /**
     * 查询分中心用户
     *
     * @param id 分中心用户ID
     * @return 分中心用户
     */
    public FcenterUserInfo selectFcenterUserInfoById(Long id);

    /**
     * 查询分中心用户列表
     *
     * @param fcenterUserInfo 分中心用户
     * @return 分中心用户集合
     */
    public List<FcenterUserInfo> selectFcenterUserInfoList(FcenterUserInfo fcenterUserInfo);

    /**
     * 新增分中心用户
     *
     * @param fcenterUserInfo 分中心用户
     * @return 结果
     */
    public int insertFcenterUserInfo(FcenterUserInfo fcenterUserInfo);

    /**
     * 修改分中心用户
     *
     * @param fcenterUserInfo 分中心用户
     * @return 结果
     */
    public int updateFcenterUserInfo(FcenterUserInfo fcenterUserInfo);

    /**
     * 批量删除分中心用户
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFcenterUserInfoByIds(String ids);

    /**
     * 删除分中心用户信息
     *
     * @param id 分中心用户ID
     * @return 结果
     */
    public int deleteFcenterUserInfoById(Long id);
    /**
     *  业务系统之间传递分中心用户方法
     * */
    public Map getFcenterUserById(Long id);

    /**
     * 分中心用户登陆（获取第一条信息）
     * */
    public FcenterUserInfo getUserInfoByName(String name);

}
