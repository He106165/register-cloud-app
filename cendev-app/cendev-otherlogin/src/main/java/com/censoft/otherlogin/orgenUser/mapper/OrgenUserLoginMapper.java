package com.censoft.otherlogin.orgenUser.mapper;

import com.censoft.otherlogin.orgenUser.domain.OrgenUserLogin;
import java.util.List;

import com.censoft.otherlogin.orgenUser.domain.OrgenUserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 机构用户登录Mapper接口
 * 
 * @author cendev
 * @date 2020-10-21
 */
@Mapper
public interface OrgenUserLoginMapper 
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
     * 删除机构用户登录
     * 
     * @param id 机构用户登录ID
     * @return 结果
     */
    public int deleteOrgenUserLoginById(String id);

    /**
     * 批量删除机构用户登录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrgenUserLoginByIds(String[] ids);

    /**
     * 登陆方法
     * 获取第一条
     * */
    public OrgenUserInfo login (OrgenUserLogin orgenUserLogin);
}
