package com.censoft.systemManage.systemRegister.mapper;

import com.censoft.systemManage.systemRegister.domain.JoinSystemInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 应用系统注册Mapper接口
 * 
 * @author cendev
 * @date 2020-10-20
 */
@Mapper
public interface JoinSystemInfoMapper 
{
    /**
     * 查询应用系统注册
     * 
     * @param joinsysId 应用系统注册ID
     * @return 应用系统注册
     */
    public JoinSystemInfo selectJoinSystemInfoById(String joinsysId);

    /**
     * 查询应用系统注册列表
     * 
     * @param joinSystemInfo 应用系统注册
     * @return 应用系统注册集合
     */
    public List<JoinSystemInfo> selectJoinSystemInfoList(JoinSystemInfo joinSystemInfo);


    /**
     * 查询应用系统注册列表(数据想配置页面)
     *
     * @param joinSystemInfo 应用系统注册
     * @return 应用系统注册集合
     */
    public List<JoinSystemInfo> selectJoinSystemDataConfigInfoList(JoinSystemInfo joinSystemInfo);


    /**
     * 新增应用系统注册
     * 
     * @param joinSystemInfo 应用系统注册
     * @return 结果
     */
    public int insertJoinSystemInfo(JoinSystemInfo joinSystemInfo);

    /**
     * 修改应用系统注册
     * 
     * @param joinSystemInfo 应用系统注册
     * @return 结果
     */
    public int updateJoinSystemInfo(JoinSystemInfo joinSystemInfo);

    /**
     * 删除应用系统注册
     * 
     * @param joinsysId 应用系统注册ID
     * @return 结果
     */
    public int deleteJoinSystemInfoById(String joinsysId);

    /**
     * 批量删除应用系统注册
     * 
     * @param joinsysIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteJoinSystemInfoByIds(String[] joinsysIds);

    /**
     * 检验系统系统名称是否存在
     * */
    public int checkSystemName(String systemName);

    /**
     * 检验系统code 是否存在
     * */
    public int checkSystemCode(String systemCode);
    /**
     * 获取系统数据项配置，
     * */
    public JoinSystemInfo getSystemDataConfig(String systemCode);

    /**
     * 获取系统返回地址
     * **/
    public String 	getSystemRetUrl(String systemCode);
}
