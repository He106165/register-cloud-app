package com.censoft.systemUrl.controller;

import com.censoft.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.censoft.common.core.domain.R;
import com.censoft.common.core.controller.BaseController;
import com.censoft.systemUrl.domain.SystemUrl;
import com.censoft.systemUrl.service.ISystemUrlService;

import java.util.List;

/**
 * 系统地址 提供者
 *
 * @author cendev
 * @date 2021-01-25
 */
@RestController
@RequestMapping("url")
public class SystemUrlController extends BaseController {

    @Autowired
    private ISystemUrlService systemUrlService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{id}")
    public SystemUrl get(@PathVariable("id") String id) {
        return systemUrlService.selectSystemUrlById(id);

    }

    /**
     * 查询系统地址列表
     */
    @GetMapping("list")
    public R list(SystemUrl systemUrl) {
        return result(systemUrlService.selectSystemUrlList(systemUrl));
    }

    /**
     * 查询系统地址列表
     */
    @GetMapping("SystemList")
    public List<SystemUrl> SystemList(SystemUrl systemUrl) {
        return systemUrlService.selectSystemUrlList(systemUrl);
    }


    /**
     * 新增保存系统地址
     */
    @PostMapping("save")
    public R addSave(@RequestBody SystemUrl systemUrl) {
        String loginName = getLoginName();
        //获取当前登录人的名字
        systemUrl.setCreateBy(loginName);
        //如果删除标记为0,创建时间 创建人 还有版本号 保留
        return toAjax(systemUrlService.insertSystemUrl(systemUrl));
    }

    /**
     * 修改保存系统地址
     */
    @PostMapping("update")
    public R editSave(@RequestBody SystemUrl systemUrl) {
        //修改人的信息
        String updateName = getLoginName();
         systemUrl.setUpdateBy(updateName);
        //systemUrl 这里已经获取到version
            int i = systemUrlService.updateSystemUrl(systemUrl);
            if (i == 0) {
                return R.error("修改失败");
            } else {
                return R.ok("修改成功");
            }

    }

    /**
     * 批量删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(String ids) {
//        //获取修改时间(删除的时间)
//        //修改时间
//        systemUrl.setUpdateTime(DateUtils.getNowDate());
//        //修改人
//        String updateName1 = getLoginName();
//        systemUrl.setUpdateBy(updateName1);

        return toAjax(systemUrlService.deleteSystemUrlByIds(ids));
    }

    /**
     * 删除
     */
    @PostMapping("removeById")
    public R removeById(String id,SystemUrl systemUrl) {
        //修改人
        String updateName1 = getLoginName();
        systemUrl.setUpdateBy(updateName1);
        return toAjax(systemUrlService.deleteSystemUrlById(id,systemUrl));
    }

    ;
}
