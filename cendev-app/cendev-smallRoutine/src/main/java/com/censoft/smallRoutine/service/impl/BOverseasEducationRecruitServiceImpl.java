package com.censoft.smallRoutine.service.impl;

import com.censoft.common.core.text.Convert;
import com.censoft.smallRoutine.domain.BOverseasEducationRecruit;
import com.censoft.smallRoutine.mapper.BOverseasEducationRecruitMapper;
import com.censoft.smallRoutine.service.IBOverseasEducationRecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 海外教育资源--招生详情Service业务层处理
 *
 * @author cendev
 * @date 2021-01-11
 */
@Service
public class BOverseasEducationRecruitServiceImpl implements IBOverseasEducationRecruitService
{
    @Autowired
    private BOverseasEducationRecruitMapper bOverseasEducationRecruitMapper;

    /**
     * 查询海外教育资源--招生详情
     *
     * @param id 海外教育资源--招生详情ID
     * @return 海外教育资源--招生详情
     */
    @Override
    public BOverseasEducationRecruit selectBOverseasEducationRecruitById(String id)
    {
        return bOverseasEducationRecruitMapper.selectBOverseasEducationRecruitById(id);
    }

    /**
     * 查询海外教育资源--招生详情列表
     *
     * @param bOverseasEducationRecruit 海外教育资源--招生详情
     * @return 海外教育资源--招生详情
     */
    @Override
    public List<BOverseasEducationRecruit> selectBOverseasEducationRecruitList(BOverseasEducationRecruit bOverseasEducationRecruit)
    {
        return bOverseasEducationRecruitMapper.selectBOverseasEducationRecruitList(bOverseasEducationRecruit);
    }

    /**
     * 新增海外教育资源--招生详情
     *
     * @param bOverseasEducationRecruit 海外教育资源--招生详情
     * @return 结果
     */
    @Override
    public int insertBOverseasEducationRecruit(BOverseasEducationRecruit bOverseasEducationRecruit)
    {
        return bOverseasEducationRecruitMapper.insertBOverseasEducationRecruit(bOverseasEducationRecruit);
    }

    /**
     * 修改海外教育资源--招生详情
     *
     * @param bOverseasEducationRecruit 海外教育资源--招生详情
     * @return 结果
     */
    @Override
    public int updateBOverseasEducationRecruit(BOverseasEducationRecruit bOverseasEducationRecruit)
    {
        return bOverseasEducationRecruitMapper.updateBOverseasEducationRecruit(bOverseasEducationRecruit);
    }

    /**
     * 删除海外教育资源--招生详情对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBOverseasEducationRecruitByIds(String ids)
    {
        return bOverseasEducationRecruitMapper.deleteBOverseasEducationRecruitByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除海外教育资源--招生详情信息
     *
     * @param id 海外教育资源--招生详情ID
     * @return 结果
     */
    public int deleteBOverseasEducationRecruitById(String id)
    {
        return bOverseasEducationRecruitMapper.deleteBOverseasEducationRecruitById(id);
    }
}
