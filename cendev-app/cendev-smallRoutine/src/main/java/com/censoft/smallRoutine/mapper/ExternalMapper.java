package com.censoft.smallRoutine.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface ExternalMapper {

    /**
     * 获取认证业务数据接口
     */
    public JSONObject selectAuthenticationByIdnumber(@Param("idnumber") String idnumber);

    /**
     * 获取公派业务数据接口
     */
  public JSONObject selectvocationalByIdnumber(@Param("idnumber") String idnumber);

    /**
     *获取存档业务数据接口
     * @param idnumber
     * @return
     */
    public JSONObject selectArchivesByIdnumber(@Param("idnumber") String idnumber);

    /**
     * 获取就业业务数据接口
     */
    public  JSONObject selectEmploymentByIdnumber(@Param("idnumber") String idnumber);
    /**
     * 查询认证证书接口
     */
    public JSONObject selectCertificate(@Param("idnumber") String idnumber,@Param("username") String username);
}
