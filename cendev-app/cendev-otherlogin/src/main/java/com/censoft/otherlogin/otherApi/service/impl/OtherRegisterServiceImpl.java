package com.censoft.otherlogin.otherApi.service.impl;

import com.censoft.otherlogin.otherApi.mapper.OtherRegisterMapper;
import com.censoft.otherlogin.otherApi.service.OtherRegisterService;
import com.censoft.otherlogin.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @创建人:wqgeng
 * @创建时间:2020-11-1620:24
 * @描述:sdfsdf
 */
@Service
@Transactional
public class OtherRegisterServiceImpl implements OtherRegisterService {

    @Autowired
    private OtherRegisterMapper otherRegisterMapper;


    @Override
    public ResultUtil queryNationality() {
        return ResultUtil.success(otherRegisterMapper.queryNationality());
    }

    @Override
    public ResultUtil countByPhone(Map map) {
        if(otherRegisterMapper.countByPhone(map)>0){
            return ResultUtil.result("您的手机号已经注册,请直接登录，如忘记密码请选择找回密码!");
        }
        return ResultUtil.success();
    }

    @Override
    public ResultUtil countByCardNo(Map map) {
        if(otherRegisterMapper.countByCardNo(map)>0){
            return ResultUtil.result("您的证件号已经注册,请直接登录");
        }
        return ResultUtil.success();
    }

    @Override
    public ResultUtil countByEmail(Map map) {
        if(otherRegisterMapper.countByEmail(map)>0){
            return ResultUtil.result("您的邮箱已经注册,请直接登录");
        }
        return ResultUtil.success();
    }

    @Override
    public ResultUtil queryCardType() {
        return ResultUtil.success(otherRegisterMapper.queryCardType());
    }
}
