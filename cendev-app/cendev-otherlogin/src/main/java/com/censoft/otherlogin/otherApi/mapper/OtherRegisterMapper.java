package com.censoft.otherlogin.otherApi.mapper;

import com.censoft.otherlogin.util.ResultUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *@创建人:wqgeng
 *@创建时间:2020-11-1620:21
 *@描述:sds
 */
@Mapper
public interface OtherRegisterMapper {

    List<Map> queryNationality();

    Integer countByPhone(Map map);

    Integer countByCardNo(Map map);

    Integer countByEmail(Map map);

    List<Map> queryCardType();
}
