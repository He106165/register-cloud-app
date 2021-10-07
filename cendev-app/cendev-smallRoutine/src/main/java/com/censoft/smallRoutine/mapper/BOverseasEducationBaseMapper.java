package com.censoft.smallRoutine.mapper;

import com.censoft.smallRoutine.domain.BOverseasEducationBase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 海外教育资源--院校基础信息实体Mapper接口
 *
 * @author cendev
 * @date 2021-01-11
 */
@Mapper
public interface BOverseasEducationBaseMapper
{
    /**
     * 查询海外教育资源--院校基础信息实体
     *
     * @param id 海外教育资源--院校基础信息实体ID
     * @return 海外教育资源--院校基础信息实体
     */
    public BOverseasEducationBase selectBOverseasEducationBaseById(String id);

    /**
     * 查询海外教育资源--院校基础信息实体列表
     *
     * @param bOverseasEducationBase 海外教育资源--院校基础信息实体
     * @return 海外教育资源--院校基础信息实体集合
     */
    public List<BOverseasEducationBase> selectBOverseasEducationBaseList(BOverseasEducationBase bOverseasEducationBase);

    /**
     * 新增海外教育资源--院校基础信息实体
     *
     * @param bOverseasEducationBase 海外教育资源--院校基础信息实体
     * @return 结果
     */
    public int insertBOverseasEducationBase(BOverseasEducationBase bOverseasEducationBase);

    /**
     * 修改海外教育资源--院校基础信息实体
     *
     * @param bOverseasEducationBase 海外教育资源--院校基础信息实体
     * @return 结果
     */
    public int updateBOverseasEducationBase(BOverseasEducationBase bOverseasEducationBase);

    /**
     * 删除海外教育资源--院校基础信息实体
     *
     * @param id 海外教育资源--院校基础信息实体ID
     * @return 结果
     */
    public int deleteBOverseasEducationBaseById(String id);

    /**
     * 批量删除海外教育资源--院校基础信息实体
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBOverseasEducationBaseByIds(String[] ids);
}
