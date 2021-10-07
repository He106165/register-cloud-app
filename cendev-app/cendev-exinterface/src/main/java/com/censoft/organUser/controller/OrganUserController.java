package com.censoft.organUser.controller;


import com.alibaba.fastjson.JSON;
import com.censoft.organUser.service.OrganUserService;
import com.censoft.organUser.valid.OrganUserLoginVaild;
import com.censoft.organUser.valid.OrganUserVaild;
import com.censoft.organUser.valid.UpdateOrganUserVaild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("organUser")
public class OrganUserController {
    @Autowired
    private OrganUserService organUserInfoService;



    /*机构用户注册*/
    @RequestMapping(value = "registerOrganUser", method = {RequestMethod.POST})
    @ResponseBody
    public Map registerOrganUser(@Valid OrganUserVaild savePersonUserVO, BindingResult bindResult){

        Map map = new HashMap();
        if (bindResult.hasErrors()) {
            StringBuilder content = new StringBuilder();
            for (FieldError item : bindResult.getFieldErrors()) {
                content.append(item.getField()).append("=").append(item.getDefaultMessage()).append(",");
            }
            map.put("state","406");
            map.put("status","406");
            map.put("reason",content);
            return map;
        }else {
            Map usermap = JSON.parseObject(JSON.toJSONString(savePersonUserVO), Map.class);
            return organUserInfoService.registerOrganUser(usermap);
        }
    }


    /*机构用户登陆*/
    @RequestMapping(value = "organUserLogin", method = {RequestMethod.POST})
    @ResponseBody
    public Map organUserLogin(@Valid OrganUserLoginVaild savePersonUserVO, BindingResult bindResult){
        Map map = new HashMap();
        if (bindResult.hasErrors()) {
            StringBuilder content = new StringBuilder();
            for (FieldError item : bindResult.getFieldErrors()) {
                content.append(item.getField()).append("=").append(item.getDefaultMessage()).append(",");
            }
            map.put("state","406");
            map.put("status","406");
            map.put("reason",content);
            return map;
        }else {
            Map usermap = JSON.parseObject(JSON.toJSONString(savePersonUserVO), Map.class);
            return organUserInfoService.organUserLogin(usermap);
        }
    }



    /*修改普通用户信息*/
    @RequestMapping(value = "updateOrganUser", method = {RequestMethod.POST})
    @ResponseBody
    public Map updateOrganUser (@Valid UpdateOrganUserVaild savePersonUserVO, BindingResult bindResult){
        //拿到注册的信息




        //验证  用户名，手机号，邮箱格式是否正确
        //并返回结果


        //记录日志
        Map map = new HashMap();
        if (bindResult.hasErrors()) {
            StringBuilder content = new StringBuilder();
            for (FieldError item : bindResult.getFieldErrors()) {
                content.append(item.getField()).append("=").append(item.getDefaultMessage()).append(",");
            }
            map.put("state","406");
            map.put("status","406");
            map.put("reason",content);
            return map;
        }else {
            Map usermap = JSON.parseObject(JSON.toJSONString(savePersonUserVO), Map.class);
            return organUserInfoService.updateOrganUser(usermap);
        }
    }




}
