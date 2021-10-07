package com.censoft.systemUrl.mapper;

import com.censoft.systemUrl.domain.SystemUrl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统地址Mapper接口
 *
 * @author cendev
 * @date 2021-01-25
 */
@Mapper
public interface SystemUrlMapper {
    /**
     * 查询系统地址
     *
     * @param id 系统地址ID
     * @return 系统地址
     */
    public SystemUrl selectSystemUrlById(String id);

    /**
     * 查询系统地址列表
     *
     * @param systemUrl 系统地址
     * @return 系统地址集合
     */
    public List<SystemUrl> selectSystemUrlList(SystemUrl systemUrl);

    /**
     * 新增系统地址
     *
     * @param systemUrl 系统地址
     * @return 结果
     */
    public int insertSystemUrl(SystemUrl systemUrl);

    /**
     * 修改系统地址
     *
     * @param systemUrl 系统地址
     * @return 结果
     */
    public int updateSystemUrl(SystemUrl systemUrl);

    /**
     * 删除系统地址
     *
     * @param id 系统地址ID
     * @return 结果
     */
    public int deleteSystemUrlById(String id,SystemUrl systemUrl);

    /**
     * 批量删除系统地址
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSystemUrlByIds(String[] ids);
}
