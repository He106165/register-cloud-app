package com.censoft.smallRoutine.service.impl;

import com.censoft.common.core.text.Convert;
import com.censoft.smallRoutine.domain.BOverseasEducationMajor;
import com.censoft.smallRoutine.mapper.BOverseasEducationMajorMapper;
import com.censoft.smallRoutine.service.IBOverseasEducationMajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 海外教育资源--院校专业事实Service业务层处理
 *
 * @author cendev
 * @date 2021-01-11
 */
@Service
public class BOverseasEducationMajorServiceImpl implements IBOverseasEducationMajorService
{
    @Autowired
    private BOverseasEducationMajorMapper bOverseasEducationMajorMapper;

    /**
     * 查询海外教育资源--院校专业事实
     *
     * @param id 海外教育资源--院校专业事实ID
     * @return 海外教育资源--院校专业事实
     */
    @Override
    public BOverseasEducationMajor selectBOverseasEducationMajorById(String id)
    {
        return bOverseasEducationMajorMapper.selectBOverseasEducationMajorById(id);
    }

    /**
     * 查询海外教育资源--院校专业事实列表
     *
     * @param bOverseasEducationMajor 海外教育资源--院校专业事实
     * @return 海外教育资源--院校专业事实
     */
    @Override
    public List<BOverseasEducationMajor> selectBOverseasEducationMajorList(BOverseasEducationMajor bOverseasEducationMajor)
    {
        return bOverseasEducationMajorMapper.selectBOverseasEducationMajorList(bOverseasEducationMajor);
    }

    /**
     * 新增海外教育资源--院校专业事实
     *
     * @param bOverseasEducationMajor 海外教育资源--院校专业事实
     * @return 结果
     */
    @Override
    public int insertBOverseasEducationMajor(BOverseasEducationMajor bOverseasEducationMajor)
    {
        return bOverseasEducationMajorMapper.insertBOverseasEducationMajor(bOverseasEducationMajor);
    }

    /**
     * 修改海外教育资源--院校专业事实
     *
     * @param bOverseasEducationMajor 海外教育资源--院校专业事实
     * @return 结果
     */
    @Override
    public int updateBOverseasEducationMajor(BOverseasEducationMajor bOverseasEducationMajor)
    {
        return bOverseasEducationMajorMapper.updateBOverseasEducationMajor(bOverseasEducationMajor);
    }

    /**
     * 删除海外教育资源--院校专业事实对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBOverseasEducationMajorByIds(String ids)
    {
        return bOverseasEducationMajorMapper.deleteBOverseasEducationMajorByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除海外教育资源--院校专业事实信息
     *
     * @param id 海外教育资源--院校专业事实ID
     * @return 结果
     */
    public int deleteBOverseasEducationMajorById(String id)
    {
        return bOverseasEducationMajorMapper.deleteBOverseasEducationMajorById(id);
    }
}
