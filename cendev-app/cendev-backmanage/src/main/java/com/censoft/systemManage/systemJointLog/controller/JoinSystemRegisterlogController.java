package com.censoft.systemManage.systemJointLog.controller;

import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.systemManage.systemJointLog.domain.JoinSystemRegisterlog;
import com.censoft.systemManage.systemJointLog.service.IJoinSystemRegisterlogService;

import java.util.*;

/**
 * 系统对接日志 提供者
 *
 * @author cendev
 * @date 2020-10-20
 */
@RestController
@RequestMapping("Jointlog")
public class JoinSystemRegisterlogController extends BaseController {

    @Autowired
    private IJoinSystemRegisterlogService joinSystemRegisterlogService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{id}")
    public JoinSystemRegisterlog get(@PathVariable("id") String id) {
        return joinSystemRegisterlogService.selectJoinSystemRegisterlogById(id);

    }

    /**
     * 查询系统对接日志列表
     */
    @GetMapping("list")
    public R list(JoinSystemRegisterlog joinSystemRegisterlog) {
        startPage();
        return result(joinSystemRegisterlogService.selectJoinSystemRegisterlogList(joinSystemRegisterlog));
    }


    /**
     * 新增保存系统对接日志
     */
    @PostMapping("save")
    public R addSave(@RequestBody JoinSystemRegisterlog joinSystemRegisterlog) {
        return toAjax(joinSystemRegisterlogService.insertJoinSystemRegisterlog(joinSystemRegisterlog));
    }

    @RequestMapping(value = "sysLogaddSave")
    public String sysLogaddSave(@RequestBody JoinSystemRegisterlog joinSystemRegisterlog) {
        return joinSystemRegisterlogService.insertJoinSystemRegisterlog(joinSystemRegisterlog) + "";
    }

    /**
     * 修改保存系统对接日志
     */
    @PostMapping("update")
    public R editSave(@RequestBody JoinSystemRegisterlog joinSystemRegisterlog) {
        return toAjax(joinSystemRegisterlogService.updateJoinSystemRegisterlog(joinSystemRegisterlog));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(String ids) {

        return toAjax(joinSystemRegisterlogService.deleteJoinSystemRegisterlogByIds(ids));
    }


    /**
     * 查询最近一年接口的访问数量
     */
    @PostMapping("selectJoinSystemInterfaceNum")
    public Map selectJoinSystemInterfaceNum() {
		/*List<Map> l1  =  joinSystemRegisterlogService.selectJoinSystemInterfaceNum();
		List list = new ArrayList();
		for (Map m:l1) {
			Integer a  = Integer.parseInt(m.get("VALUE").toString());
			list.add(a);
		}*/

        //返回值是map

        List<Map> list = joinSystemRegisterlogService.selectJoinSystemInterfaceNum();

        List monthList = new ArrayList();
        List sumList = new ArrayList();

        for (int i = 0; i < list.size(); i++) {
            Map a = list.get(i);
            monthList.add(a.get("GG"));
            sumList.add(a.get("VALUE"));
        }

        //返回map
        Map map = new HashMap();
        map.put("monthList", monthList.toArray());
        map.put("sumList", sumList.toArray());


        return map;
    }
}
