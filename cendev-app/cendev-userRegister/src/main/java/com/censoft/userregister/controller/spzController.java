package com.censoft.userregister.controller;

import com.censoft.userregister.domain.SpzDomain;
import com.censoft.userregister.service.SpzService;
import com.censoft.userregister.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @创建人:wqgeng
 * @创建时间:2021-04-0715:01
 * @描述:生僻字路径查询
 */
@RestController
@RequestMapping("spz")
@Api("生僻字路径查询")
public class spzController {
    @Autowired
    private SpzService spzService;


    @ApiOperation(value = "生僻字路径查询", notes = "生僻字路径查询")
    @PostMapping("querySpz")
    public ResultUtil querySpz(@RequestBody SpzDomain spzDomain) {
        return spzService.querySpz(spzDomain);
    }
}
