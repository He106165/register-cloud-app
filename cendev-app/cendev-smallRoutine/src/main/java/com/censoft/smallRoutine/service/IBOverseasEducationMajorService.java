package com.censoft.smallRoutine.service;

import com.censoft.smallRoutine.domain.BOverseasEducationMajor;
import java.util.List;

/**
 * 海外教育资源--院校专业事实Service接口
 *
 * @author cendev
 * @date 2021-01-11
 */
public interface IBOverseasEducationMajorService
{
    /**
     * 查询海外教育资源--院校专业事实
     *
     * @param id 海外教育资源--院校专业事实ID
     * @return 海外教育资源--院校专业事实
     */
    public BOverseasEducationMajor selectBOverseasEducationMajorById(String id);

    /**
     * 查询海外教育资源--院校专业事实列表
     *
     * @param bOverseasEducationMajor 海外教育资源--院校专业事实
     * @return 海外教育资源--院校专业事实集合
     */
    public List<BOverseasEducationMajor> selectBOverseasEducationMajorList(BOverseasEducationMajor bOverseasEducationMajor);

    /**
     * 新增海外教育资源--院校专业事实
     *
     * @param bOverseasEducationMajor 海外教育资源--院校专业事实
     * @return 结果
     */
    public int insertBOverseasEducationMajor(BOverseasEducationMajor bOverseasEducationMajor);

    /**
     * 修改海外教育资源--院校专业事实
     *
     * @param bOverseasEducationMajor 海外教育资源--院校专业事实
     * @return 结果
     */
    public int updateBOverseasEducationMajor(BOverseasEducationMajor bOverseasEducationMajor);

    /**
     * 批量删除海外教育资源--院校专业事实
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBOverseasEducationMajorByIds(String ids);

    /**
     * 删除海外教育资源--院校专业事实信息
     *
     * @param id 海外教育资源--院校专业事实ID
     * @return 结果
     */
    public int deleteBOverseasEducationMajorById(String id);
}
