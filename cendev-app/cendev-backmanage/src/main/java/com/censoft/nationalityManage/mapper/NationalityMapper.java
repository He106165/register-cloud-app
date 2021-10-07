package com.censoft.nationalityManage.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface NationalityMapper
{

    public List<Map> selectNationalityListByCode(Map map);



    /*得到所有*/
    public Map selectNationalityAllCode(Map map);
    /**
    * @author hepengfei
    * @Description 获取国家信息
    * @Date 13:31 2020/11/30
    * @Parm
    * @return  Map
    **/
    public List<Map> queryNationality();
}
