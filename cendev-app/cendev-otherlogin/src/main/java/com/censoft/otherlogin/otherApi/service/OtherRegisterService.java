package com.censoft.otherlogin.otherApi.service;

import com.censoft.otherlogin.util.ResultUtil;

import java.util.Map;

/**
 *@创建人:wqgeng
 *@创建时间:2020-11-1620:21
 *@描述:sds
 */
public interface OtherRegisterService {

    ResultUtil queryNationality();

    ResultUtil countByPhone(Map map);

    ResultUtil countByCardNo(Map map);

    ResultUtil countByEmail(Map map);

    ResultUtil queryCardType();
}
