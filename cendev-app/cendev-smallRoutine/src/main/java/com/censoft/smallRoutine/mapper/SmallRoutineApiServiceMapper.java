package com.censoft.smallRoutine.mapper;

import com.censoft.smallRoutine.domain.BEducations;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SmallRoutineApiServiceMapper {
    //获取院校列表
    public List<BEducations> findAllSchools(@Param("address") String address, @Param("info") String info, @Param("leave") String leave,@Param("id") String id);


    //获取院校详情信息
    public Map selectSchoolById(@Param("id") String id);
    //获取海外教育资源的查询数据
//    public JSONObject getSelectData();
}
