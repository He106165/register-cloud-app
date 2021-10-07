package com.censoft.nationalityManage.service.impl;

import com.censoft.nationalityManage.mapper.NationalityMapper;
import com.censoft.nationalityManage.service.INationalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 操作日志Service业务层处理
 *
 * @author cendev
 * @date 2020-10-21
 */
@Service
public class INationalityServiceImpl implements INationalityService
{

    @Autowired
    private NationalityMapper nationalityMapper;


    @Override
    public List<Map> selectNationalityListByCode(Map map) {
        return nationalityMapper.selectNationalityListByCode(map);
    }
    @Override
    public Map selectNationalityAllCode(Map map) {
        return nationalityMapper.selectNationalityAllCode(map);
    }


    /**
     * @author hepengfei
     * @Description 获取国家信息
     * @Date 13:31 2020/11/30
     * @Parm
     * @return  Map
     **/
    @Override
    public List<Map> queryNationality(){
        return nationalityMapper.queryNationality();
    }
}
