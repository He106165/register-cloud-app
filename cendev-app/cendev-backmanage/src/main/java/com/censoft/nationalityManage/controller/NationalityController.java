package com.censoft.nationalityManage.controller;

import com.censoft.common.core.controller.BaseController;
import com.censoft.nationalityManage.service.INationalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 操作日志 提供者
 *
 * @author cendev
 * @date 2020-10-21
 */
@RestController
@RequestMapping("nationality")
public class NationalityController extends BaseController
{

	@Autowired
	private INationalityService iNationalityService;


	@RequestMapping("list")
	public List<Map> selectNationalityListByCode(@RequestParam Map map){
		return iNationalityService.selectNationalityListByCode(map);
	}
	@RequestMapping("allThreeCode")
	public Map selectNationalityAllCode(@RequestParam Map map){
		return iNationalityService.selectNationalityAllCode(map);
	}
	/**
	* @Description 获取国家代码，以字典形式
	* @Parm
	* @return  Map
	**/
	@RequestMapping("queryNationality")
	public List<Map> queryNationality(){
		return iNationalityService.queryNationality();
	}
}
