package com.censoft.systemManage.systemDataConfig.mapper;

import com.censoft.systemManage.systemDataConfig.domain.JoinSystemDeploy;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 应用系统配置Mapper接口
 * 
 * @author cendev
 * @date 2020-10-20
 */
@Mapper
public interface JoinSystemDeployMapper 
{
    /**
     * 查询应用系统配置
     * 
     * @param id 应用系统配置ID
     * @return 应用系统配置
     */
    public JoinSystemDeploy selectJoinSystemDeployById(String id);


    /**
     * 根据系统id获取系统配置
     * */
    public List<JoinSystemDeploy> selectJoinSystemDeployBySystem(String id);
    /**
     * 查询应用系统配置列表
     * 
     * @param joinSystemDeploy 应用系统配置
     * @return 应用系统配置集合
     */
    public List<JoinSystemDeploy> selectJoinSystemDeployList(JoinSystemDeploy joinSystemDeploy);

    /**
     * 新增应用系统配置
     * 
     * @param joinSystemDeploy 应用系统配置
     * @return 结果
     */
    public int insertJoinSystemDeploy(JoinSystemDeploy joinSystemDeploy);

    /**
     * 修改应用系统配置
     * 
     * @param joinSystemDeploy 应用系统配置
     * @return 结果
     */
    public int updateJoinSystemDeploy(JoinSystemDeploy joinSystemDeploy);

    /**
     * 删除应用系统配置
     * 
     * @param id 应用系统配置ID
     * @return 结果
     */
    public int deleteJoinSystemDeployById(String id);

    /**
     * 批量删除应用系统配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteJoinSystemDeployByIds(String[] ids);


    /**
     * 根据数据库表获取到用户的数据字段
     * */
    public List<Map> getUserListDateByDB(@Param("dbName") String dbName, @Param("tableName")String tableName);
}
