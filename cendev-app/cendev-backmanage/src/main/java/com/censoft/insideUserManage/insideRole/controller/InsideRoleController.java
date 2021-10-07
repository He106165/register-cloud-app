package com.censoft.insideUserManage.insideRole.controller;

import com.censoft.common.core.controller.BaseController;
import com.censoft.common.core.domain.R;
import com.censoft.insideUserManage.insideRole.domain.InsideRole;
import com.censoft.insideUserManage.insideRole.service.IInsideRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 内部用户角色 提供者
 * 
 * @author cendev
 * @date 2020-10-20
 */
@RestController
@RequestMapping("insideRole")
public class InsideRoleController extends BaseController
{
	
	@Autowired
	private IInsideRoleService insideRoleService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{roleId}")
	public InsideRole get(@PathVariable("roleId") String roleId)
	{
		return insideRoleService.selectInsideRoleById(roleId);
		
	}
	
	/**
	 * 查询内部用户角色列表
	 */
	@GetMapping("list")
	public R list(InsideRole insideRole)
	{
		startPage();
        return result(insideRoleService.selectInsideRoleList(insideRole));
	}

	/**
	 * 查询内部用户角色列表
	 */
	@GetMapping("getRoleList")
	public List<InsideRole> getRoleList(InsideRole insideRole)
	{
		return insideRoleService.selectInsideRoleList(insideRole);
	}
	
	
	/**
	 * 新增保存内部用户角色
	 */
	@PostMapping("save")
	public R addSave(@RequestBody InsideRole insideRole)
	{		
		return toAjax(insideRoleService.insertInsideRole(insideRole));
	}

	/**
	 * 修改保存内部用户角色
	 */
	@PostMapping("update")
	public R editSave(@RequestBody InsideRole insideRole)
	{		
		return toAjax(insideRoleService.updateInsideRole(insideRole));
	}

	@PostMapping("updateInsideRoleSystemConfide")
	public R updateInsideRoleSystemConfide(@RequestBody  InsideRole insideRole){
		return toAjax(insideRoleService.updateInsideRoleSystemConfide(insideRole));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(insideRoleService.deleteInsideRoleByIds(ids));
	}
	
}
