package com.censoft.insideUserManage.insideRole.mapper;

import com.censoft.insideUserManage.insideRole.domain.InsideRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 内部用户角色Mapper接口
 * 
 * @author cendev
 * @date 2020-10-20
 */
@Mapper
public interface InsideRoleMapper 
{
    /**
     * 查询内部用户角色
     * 
     * @param roleId 内部用户角色ID
     * @return 内部用户角色
     */
    public InsideRole selectInsideRoleById(String roleId);

    /**
     * 查询内部用户角色列表
     * 
     * @param insideRole 内部用户角色
     * @return 内部用户角色集合
     */
    public List<InsideRole> selectInsideRoleList(InsideRole insideRole);

    /**
     * 查询内部用户角色列表
     *
     * @param insideRole 内部用户角色
     * @return 内部用户角色集合
     */
    public List<InsideRole> selectInsideRoleListByRoles(String[] insideRole);


    /**
     * 新增内部用户角色
     * 
     * @param insideRole 内部用户角色
     * @return 结果
     */
    public int insertInsideRole(InsideRole insideRole);



    /**
     * 修改内部用户角色
     * 
     * @param insideRole 内部用户角色
     * @return 结果
     */
    public int updateInsideRole(InsideRole insideRole);


    /**
     *修改内部用户系统配置项
     */
    public int updateInsideRoleSystemConfide(InsideRole insideRole);

    /**
     * 删除内部用户角色
     * 
     * @param roleId 内部用户角色ID
     * @return 结果
     */
    public int deleteInsideRoleById(String roleId);

    /**
     * 批量删除内部用户角色
     * 
     * @param roleIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteInsideRoleByIds(String[] roleIds);
}
