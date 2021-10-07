package com.censoft.smallRoutine.service;


import com.censoft.smallRoutine.domain.BEducations;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SmallRoutineApiService {

    /**
     * 根据留学地址信息（国家、地区）、专业信息、学校层次这三个条件或者都为空时获取院校列表
     *
     */
     public List<BEducations> findAllSchools(String address, String info, String leave,String id);

    /**
     * 根据院校id获取院校详情信息
     * @param id
     * @return
     */
    //获取院校详情信息
    public Map selectSchoolById(@Param("id") String id);

    //获取海外教育资源的查询数据
//    public JSONObject getSelectData();
}
