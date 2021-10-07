package com.censoft.smallRoutine.service;

import com.censoft.smallRoutine.domain.BOverseasEducationBase;
import java.util.List;

/**
 * 海外教育资源--院校基础信息实体Service接口
 *
 * @author cendev
 * @date 2021-01-11
 */
public interface IBOverseasEducationBaseService
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
     * 批量删除海外教育资源--院校基础信息实体
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBOverseasEducationBaseByIds(String ids);

    /**
     * 删除海外教育资源--院校基础信息实体信息
     *
     * @param id 海外教育资源--院校基础信息实体ID
     * @return 结果
     */
    public int deleteBOverseasEducationBaseById(String id);
}
