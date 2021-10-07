package com.censoft.insideUserManage.insideRole.service.impl;

import cn.hutool.core.util.IdUtil;
import com.censoft.common.core.text.Convert;
import com.censoft.common.utils.DateUtils;
import com.censoft.insideUserManage.insideRole.domain.InsideRole;
import com.censoft.insideUserManage.insideRole.mapper.InsideRoleMapper;
import com.censoft.insideUserManage.insideRole.service.IInsideRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内部用户角色Service业务层处理
 * 
 * @author cendev
 * @date 2020-10-20
 */
@Service
public class InsideRoleServiceImpl implements IInsideRoleService 
{
    @Autowired
    private InsideRoleMapper insideRoleMapper;

    /**
     * 查询内部用户角色
     * 
     * @param roleId 内部用户角色ID
     * @return 内部用户角色
     */
    @Override
    public InsideRole selectInsideRoleById(String roleId)
    {
        return insideRoleMapper.selectInsideRoleById(roleId);
    }

    /**
     * 查询内部用户角色列表
     * 
     * @param insideRole 内部用户角色
     * @return 内部用户角色
     */
    @Override
    public List<InsideRole> selectInsideRoleList(InsideRole insideRole)
    {
        return insideRoleMapper.selectInsideRoleList(insideRole);
    }

    /**
     * 新增内部用户角色
     * 
     * @param insideRole 内部用户角色
     * @return 结果
     */
    @Override
    public int insertInsideRole(InsideRole insideRole)
    {
        insideRole.setRoleId(IdUtil.fastSimpleUUID());
        insideRole.setCreateTime(DateUtils.getNowDate());
        insideRole.setVersion(0L);
        insideRole.setDelFlag(0L);
        insideRole.setRoleStatus("1");
        return insideRoleMapper.insertInsideRole(insideRole);
    }

    /**
     * 修改内部用户角色
     * 
     * @param insideRole 内部用户角色
     * @return 结果
     */
    @Override
    public int updateInsideRole(InsideRole insideRole)
    {
        insideRole.setUpdateTime(DateUtils.getNowDate());
        return insideRoleMapper.updateInsideRole(insideRole);
    }

    /**
     * 查询内部用户角色列表
     *
     * @param insideRole 内部用户角色
     * @return 内部用户角色集合
     */
    @Override
    public List<InsideRole> selectInsideRoleListByRoles(String insideRole){
        return insideRoleMapper.selectInsideRoleListByRoles(Convert.toStrArray(insideRole));
    }


    /**
     *修改内部用户系统配置项
     */
    @Override
    public int updateInsideRoleSystemConfide(InsideRole insideRole){
        insideRole.setUpdateTime(DateUtils.getNowDate());
        return insideRoleMapper.updateInsideRoleSystemConfide(insideRole);
    }

    /**
     * 删除内部用户角色对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteInsideRoleByIds(String ids)
    {
        return insideRoleMapper.deleteInsideRoleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除内部用户角色信息
     * 
     * @param roleId 内部用户角色ID
     * @return 结果
     */
    public int deleteInsideRoleById(String roleId)
    {
        return insideRoleMapper.deleteInsideRoleById(roleId);
    }
}
