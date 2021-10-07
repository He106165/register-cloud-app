package com.censoft.smallRoutine.service.impl;

import com.censoft.smallRoutine.domain.BEducations;
import com.censoft.smallRoutine.mapper.SmallRoutineApiServiceMapper;
import com.censoft.smallRoutine.service.SmallRoutineApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SmallRoutineApiServiceImpl implements SmallRoutineApiService {

    @Autowired
    private SmallRoutineApiServiceMapper smallRoutineApiServiceMapper;


    /**
     * 根据国家或者地区、专业信息、院校层次或者三个条件查看所有
     * @return
     */
    @Override
    public List<BEducations> findAllSchools(String address, String info, String leave,String id) {

        return smallRoutineApiServiceMapper.findAllSchools(address,info,leave,id);
   }


    /**
     *根据院校id获取院校详情信息（学校名称，简介，排名信息，招生信息）
     * @param id
     * @return
     */
    @Override
    public Map selectSchoolById(String id) {

        return smallRoutineApiServiceMapper.selectSchoolById(id);
    }

    //获取海外教育资源的查询数据
//    public JSONObject getSelectData(){
//      return  smallRoutineApiServiceMapper.getSelectData();
//    }

}
