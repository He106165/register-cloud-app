package com.censoft.nationalityManage.service;

import java.util.List;
import java.util.Map;

/**
 * 操作日志Service接口
 *
 * @author cendev
 * @date 2020-10-21
 */
public interface INationalityService {

    /*
    *
    * 得到国家信息
    *
    * */

    public List<Map> selectNationalityListByCode(Map map);



    /*
     *
     * 得到所有国家三字代码
     *
     * */

    public Map selectNationalityAllCode(Map map);


    /**
     * @author hepengfei
     * @Description 获取国家信息
     * @Date 13:31 2020/11/30
     * @Parm
     * @return  List<Map>
     **/
    public List<Map> queryNationality();
}
