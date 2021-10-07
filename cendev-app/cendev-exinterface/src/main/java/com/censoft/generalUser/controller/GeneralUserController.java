package com.censoft.generalUser.controller;


import com.alibaba.fastjson.JSON;
import com.censoft.generalUser.entity.SavePersonUserVO;
import com.censoft.generalUser.service.GeneralUserService;
import com.censoft.generalUser.valid.GeneralUserVaild;
import com.censoft.generalUser.valid.UpdateGeneralUserVaild;
import com.censoft.generalUser.valid.UserLoginVaild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("generalUser")
public class GeneralUserController {
    @Autowired
    private GeneralUserService generalUserInfoService;



    /*目前个人注册  只有中国大陆的手机号
        需要验证短信
     */

    /*个人用户注册*/
    @RequestMapping(value = "registerGeneralUser", method = {RequestMethod.POST})
    @ResponseBody
    public Map registerGeneralUser(@Valid GeneralUserVaild savePersonUserVO, BindingResult bindResult){


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
            return generalUserInfoService.registerGeneralUser(usermap);
        }
        // ...  ...
    }


    /*个人用户登陆*/
    @RequestMapping(value = "/generalUserLogin", method = {RequestMethod.POST})
    @ResponseBody
    public Map generalUserLogin(@Valid UserLoginVaild savePersonUserVO, BindingResult bindResult){
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
            return generalUserInfoService.generalUserLogin(usermap);
        }
    }



    /*修改普通用户信息*/
    @RequestMapping(value = "updateGeneralUser", method = {RequestMethod.POST})
    @ResponseBody
    public Map updateGeneralUser (@Valid UpdateGeneralUserVaild savePersonUserVO, BindingResult bindResult){
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
            return generalUserInfoService.updateGeneralUser(usermap);
        }
    }



    /*用户批量注册*/
    @RequestMapping(value = "registeUserGroup", method = {RequestMethod.POST})
    @ResponseBody
    public Map registeUserGroup(@RequestBody Map map){
        //验证  用户名，手机号，邮箱是否重复。。
        //并返回结果
        //注册个人用户  成功0000
        //记录日志
        return generalUserInfoService.registeUserGroup(map);
    }
    /*用户批量查询*/
    @RequestMapping(value = "showUserStatusGroup", method = {RequestMethod.POST})
    @ResponseBody
    public Map showUserStatusGroup(@RequestBody Map map){
        //验证  用户名，手机号，邮箱是否重复。。
        //并返回结果
        //注册个人用户  成功0000
        //记录日志
        return generalUserInfoService.showUserStatusGroup(map);
    }



    @RequestMapping(value = "savePersonUser", method = {RequestMethod.POST})
    public String savePersonUser( @Valid  SavePersonUserVO savePersonUserVO, BindingResult bindResult, HttpServletResponse resp) {

        if (bindResult.hasErrors()) {
            StringBuilder content = new StringBuilder();
            for (FieldError item : bindResult.getFieldErrors()) {
                content.append(item.getField()).append("=").append(item.getDefaultMessage()).append(",");
            }
            return "";
        }
        // ...  ...
        return "";
    }


    @RequestMapping("ss")
    @ResponseBody
    public Map ss(@Valid GeneralUserVaild savePersonUserVO, BindingResult bindResult, HttpServletResponse resp) {
        Map map = new HashMap();
        if (bindResult.hasErrors()) {
            StringBuilder content = new StringBuilder();
            for (FieldError item : bindResult.getFieldErrors()) {
                content.append(item.getField()).append("=").append(item.getDefaultMessage()).append(",");
            }
            map.put("state","406");
            map.put("status","406");
            map.put("reason",content);
        }else {
           map = JSON.parseObject(JSON.toJSONString(savePersonUserVO), Map.class);
        }
        // ...  ...
        return map;
    }






}
