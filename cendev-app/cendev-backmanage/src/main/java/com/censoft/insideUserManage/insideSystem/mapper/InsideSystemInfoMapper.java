package com.censoft.insideUserManage.insideSystem.mapper;

import com.censoft.insideUserManage.insideSystem.domain.InsideSystemInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 内部系统注册Mapper接口
 *
 * @author cendev
 * @date 2020-10-19
 */
@Mapper
public interface InsideSystemInfoMapper {
    /**
     * 查询内部系统注册
     *
     * @param insideSystemId 内部系统注册ID
     * @return 内部系统注册
     */
    public InsideSystemInfo selectInsideSystemInfoById(String insideSystemId);

    /**
     * 查询内部系统注册列表
     *
     * @param insideSystemInfo 内部系统注册
     * @return 内部系统注册集合
     */
    public List<InsideSystemInfo> selectInsideSystemInfoList(InsideSystemInfo insideSystemInfo);

    /**
     * 新增内部系统注册
     *
     * @param insideSystemInfo 内部系统注册
     * @return 结果
     */
    public int insertInsideSystemInfo(InsideSystemInfo insideSystemInfo);

    /**
     * 修改内部系统注册
     *
     * @param insideSystemInfo 内部系统注册
     * @return 结果
     */
    public int updateInsideSystemInfo(InsideSystemInfo insideSystemInfo);

    /**
     * 删除内部系统注册
     *
     * @param insideSystemId 内部系统注册ID
     * @return 结果
     */
    public int deleteInsideSystemInfoById(String insideSystemId);

    /**
     * 批量删除内部系统注册
     *
     * @param insideSystemIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteInsideSystemInfoByIds(String[] insideSystemIds);


    /**
     * 通过ids获取注册系统集合
     * */
    public List<InsideSystemInfo> getSystemListInfoByIds(String[] ids);
}
