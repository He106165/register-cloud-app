package com.censoft.otherlogin.orgenUser.service;

import com.censoft.otherlogin.orgenUser.domain.OrgenUserInfo;
import com.censoft.otherlogin.orgenUser.domain.OrgenUserLogin;
import java.util.List;

/**
 * 机构用户登录Service接口
 * 
 * @author cendev
 * @date 2020-10-21
 */
public interface IOrgenUserLoginService 
{
    /**
     * 查询机构用户登录
     * 
     * @param id 机构用户登录ID
     * @return 机构用户登录
     */
    public OrgenUserLogin selectOrgenUserLoginById(String id);

    /**
     * 查询机构用户登录列表
     * 
     * @param orgenUserLogin 机构用户登录
     * @return 机构用户登录集合
     */
    public List<OrgenUserLogin> selectOrgenUserLoginList(OrgenUserLogin orgenUserLogin);

    /**
     * 新增机构用户登录
     * 
     * @param orgenUserLogin 机构用户登录
     * @return 结果
     */
    public int insertOrgenUserLogin(OrgenUserLogin orgenUserLogin);

    /**
     * 修改机构用户登录
     * 
     * @param orgenUserLogin 机构用户登录
     * @return 结果
     */
    public int updateOrgenUserLogin(OrgenUserLogin orgenUserLogin);

    /**
     * 批量删除机构用户登录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrgenUserLoginByIds(String ids);

    /**
     * 删除机构用户登录信息
     * 
     * @param id 机构用户登录ID
     * @return 结果
     */
    public int deleteOrgenUserLoginById(String id);

    /**
     * 登陆方法
     * 获取第一条
     * */
    public OrgenUserInfo login (String username,String deptCode);
}
