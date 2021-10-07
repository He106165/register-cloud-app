package com.censoft.smallRoutine.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.censoft.smallRoutine.mapper.ExternalMapper;
import com.censoft.smallRoutine.service.ExternalService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
public class ExternalServiceImpl implements ExternalService {

    @Autowired
    private ExternalMapper externalMapper;


    /**
     * 获取认证业务数据接口
     */
    @Override
    public JSONObject selectAuthenticationByIdnumber(String idnumber) {

        return externalMapper.selectAuthenticationByIdnumber(idnumber);
    }

    /**
     * 获取公派业务数据接口
     */
    @Override
    public JSONObject selectvocationalByIdnumber(String idnumber) {
        return externalMapper.selectvocationalByIdnumber(idnumber);
    }

    /**
     * 获取存档业务数据接口
     *
     * @param idnumber
     * @return
     */
    public JSONObject selectArchivesByIdnumber(String idnumber) {
        return externalMapper.selectArchivesByIdnumber(idnumber);
    }

    /**
     * 获取就业业务数据接口
     */
    public JSONObject selectEmploymentByIdnumber(String idnumber) {
        return externalMapper.selectEmploymentByIdnumber(idnumber);
    }

    /**
     * 查询认证证书接口
     */
    public JSONObject selectCertificate(String idnumber,  String username) {
        return externalMapper.selectCertificate(idnumber, username);
    }
}


