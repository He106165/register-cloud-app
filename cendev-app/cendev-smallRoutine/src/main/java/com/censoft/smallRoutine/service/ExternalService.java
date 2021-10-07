package com.censoft.smallRoutine.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 小程序对外的接口
 */
@Service
public interface ExternalService {

    /**
     * 获取认证业务数据接口
     */
    public  JSONObject selectAuthenticationByIdnumber(String idnumber);

    /**
     * 获取公派业务数据接口
     */
    public JSONObject selectvocationalByIdnumber(String idnumber) ;

    /**
     *获取存档业务数据接口
     * @param idnumber
     * @return
     */
    public JSONObject selectArchivesByIdnumber( String idnumber);

    /**
     * 获取就业业务数据接口
     */
    public  JSONObject selectEmploymentByIdnumber(String idnumber);
    /**
     * 查询认证证书接口
     */
    public JSONObject selectCertificate( String idnumber, String username);
}
