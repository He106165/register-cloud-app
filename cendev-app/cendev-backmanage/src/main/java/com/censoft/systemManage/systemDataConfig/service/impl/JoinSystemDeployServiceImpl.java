package com.censoft.systemManage.systemDataConfig.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.IdUtil;
import org.jsoup.helper.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.censoft.systemManage.systemDataConfig.mapper.JoinSystemDeployMapper;
import com.censoft.systemManage.systemDataConfig.domain.JoinSystemDeploy;
import com.censoft.systemManage.systemDataConfig.service.IJoinSystemDeployService;
import com.censoft.common.core.text.Convert;
import org.springframework.util.unit.DataUnit;

/**
 * 应用系统配置Service业务层处理
 * 
 * @author cendev
 * @date 2020-10-20
 */
@Service
public class JoinSystemDeployServiceImpl implements IJoinSystemDeployService 
{
    @Autowired
    private JoinSystemDeployMapper joinSystemDeployMapper;

    /**
     * 查询应用系统配置
     * 
     * @param id 应用系统配置ID
     * @return 应用系统配置
     */
    @Override
    public JoinSystemDeploy selectJoinSystemDeployById(String id)
    {
        return joinSystemDeployMapper.selectJoinSystemDeployById(id);
    }

    /**
     * 查询应用系统配置列表
     * 
     * @param joinSystemDeploy 应用系统配置
     * @return 应用系统配置
     */
    @Override
    public List<JoinSystemDeploy> selectJoinSystemDeployList(JoinSystemDeploy joinSystemDeploy)
    {
        return joinSystemDeployMapper.selectJoinSystemDeployList(joinSystemDeploy);
    }

    /**
     * 新增应用系统配置
     * 
     * @param joinSystemDeploy 应用系统配置
     * @return 结果
     */
    @Override
    public int insertJoinSystemDeploy(JoinSystemDeploy joinSystemDeploy)
    {
        joinSystemDeploy.setId(IdUtil.fastSimpleUUID());
        joinSystemDeploy.setEmpowerTime(new Date());
        return joinSystemDeployMapper.insertJoinSystemDeploy(joinSystemDeploy);
    }

    /**
     * 修改应用系统配置
     * 
     * @param joinSystemDeploy 应用系统配置
     * @return 结果
     */
    @Override
    public int updateJoinSystemDeploy(JoinSystemDeploy joinSystemDeploy)
    {
        return joinSystemDeployMapper.updateJoinSystemDeploy(joinSystemDeploy);
    }

    /**
     * 删除应用系统配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteJoinSystemDeployByIds(String ids)
    {
        return joinSystemDeployMapper.deleteJoinSystemDeployByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除应用系统配置信息
     * 
     * @param id 应用系统配置ID
     * @return 结果
     */
    public int deleteJoinSystemDeployById(String id)
    {
        return joinSystemDeployMapper.deleteJoinSystemDeployById(id);
    }


    /**
     * 根据数据库表获取到用户的数据字段
     * */
    @Override
    public List<Map> getUserListDateByDB(String dbName,String tableName){

        return  joinSystemDeployMapper.getUserListDateByDB(dbName,tableName);
    }


    /**
     * 根据系统id获取系统配置
     * */
    @Override
    public List<JoinSystemDeploy> selectJoinSystemDeployBySystem(String id){
        return joinSystemDeployMapper.selectJoinSystemDeployBySystem(id);
    }
}
