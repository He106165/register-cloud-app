package com.censoft.smallRoutine.service;

import com.censoft.smallRoutine.domain.BOverseasEducationRecruit;
import java.util.List;

/**
 * 海外教育资源--招生详情Service接口
 *
 * @author cendev
 * @date 2021-01-11
 */
public interface IBOverseasEducationRecruitService
{
    /**
     * 查询海外教育资源--招生详情
     *
     * @param id 海外教育资源--招生详情ID
     * @return 海外教育资源--招生详情
     */
    public BOverseasEducationRecruit selectBOverseasEducationRecruitById(String id);

    /**
     * 查询海外教育资源--招生详情列表
     *
     * @param bOverseasEducationRecruit 海外教育资源--招生详情
     * @return 海外教育资源--招生详情集合
     */
    public List<BOverseasEducationRecruit> selectBOverseasEducationRecruitList(BOverseasEducationRecruit bOverseasEducationRecruit);

    /**
     * 新增海外教育资源--招生详情
     *
     * @param bOverseasEducationRecruit 海外教育资源--招生详情
     * @return 结果
     */
    public int insertBOverseasEducationRecruit(BOverseasEducationRecruit bOverseasEducationRecruit);

    /**
     * 修改海外教育资源--招生详情
     *
     * @param bOverseasEducationRecruit 海外教育资源--招生详情
     * @return 结果
     */
    public int updateBOverseasEducationRecruit(BOverseasEducationRecruit bOverseasEducationRecruit);

    /**
     * 批量删除海外教育资源--招生详情
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBOverseasEducationRecruitByIds(String ids);

    /**
     * 删除海外教育资源--招生详情信息
     *
     * @param id 海外教育资源--招生详情ID
     * @return 结果
     */
    public int deleteBOverseasEducationRecruitById(String id);
}
