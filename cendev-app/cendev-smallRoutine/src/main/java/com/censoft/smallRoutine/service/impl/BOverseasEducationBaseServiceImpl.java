package com.censoft.smallRoutine.service.impl;

import com.censoft.common.core.text.Convert;
import com.censoft.smallRoutine.domain.BOverseasEducationBase;
import com.censoft.smallRoutine.mapper.BOverseasEducationBaseMapper;
import com.censoft.smallRoutine.service.IBOverseasEducationBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 海外教育资源--院校基础信息实体Service业务层处理
 *
 * @author cendev
 * @date 2021-01-11
 */
@Service
public class BOverseasEducationBaseServiceImpl implements IBOverseasEducationBaseService
{
    @Autowired
    private BOverseasEducationBaseMapper bOverseasEducationBaseMapper;

    /**
     * 查询海外教育资源--院校基础信息实体
     *
     * @param id 海外教育资源--院校基础信息实体ID
     * @return 海外教育资源--院校基础信息实体
     */
    @Override
    public BOverseasEducationBase selectBOverseasEducationBaseById(String id)
    {
        return bOverseasEducationBaseMapper.selectBOverseasEducationBaseById(id);
    }

    /**
     * 查询海外教育资源--院校基础信息实体列表
     *
     * @param bOverseasEducationBase 海外教育资源--院校基础信息实体
     * @return 海外教育资源--院校基础信息实体
     */
    @Override
    public List<BOverseasEducationBase> selectBOverseasEducationBaseList(BOverseasEducationBase bOverseasEducationBase)
    {
        return bOverseasEducationBaseMapper.selectBOverseasEducationBaseList(bOverseasEducationBase);
    }

    /**
     * 新增海外教育资源--院校基础信息实体
     *
     * @param bOverseasEducationBase 海外教育资源--院校基础信息实体
     * @return 结果
     */
    @Override
    public int insertBOverseasEducationBase(BOverseasEducationBase bOverseasEducationBase)
    {
        return bOverseasEducationBaseMapper.insertBOverseasEducationBase(bOverseasEducationBase);
    }

    /**
     * 修改海外教育资源--院校基础信息实体
     *
     * @param bOverseasEducationBase 海外教育资源--院校基础信息实体
     * @return 结果
     */
    @Override
    public int updateBOverseasEducationBase(BOverseasEducationBase bOverseasEducationBase)
    {
        return bOverseasEducationBaseMapper.updateBOverseasEducationBase(bOverseasEducationBase);
    }

    /**
     * 删除海外教育资源--院校基础信息实体对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBOverseasEducationBaseByIds(String ids)
    {
        return bOverseasEducationBaseMapper.deleteBOverseasEducationBaseByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除海外教育资源--院校基础信息实体信息
     *
     * @param id 海外教育资源--院校基础信息实体ID
     * @return 结果
     */
    public int deleteBOverseasEducationBaseById(String id)
    {
        return bOverseasEducationBaseMapper.deleteBOverseasEducationBaseById(id);
    }
}
