package com.censoft.generalUser.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 个人用户信息Mapper接口
 * 
 * @author cendev
 * @date 2020-10-20
 */
@Mapper
public interface DictDataMapper
{

    List<Map> selectDictDataByDictType(List<String> list);


    Map selectDataByDataId(Map consuMap);
}
