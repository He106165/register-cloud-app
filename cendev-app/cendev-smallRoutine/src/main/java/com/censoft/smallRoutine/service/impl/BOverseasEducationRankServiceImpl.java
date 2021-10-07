package com.censoft.smallRoutine.service.impl;

import com.censoft.common.core.text.Convert;
import com.censoft.smallRoutine.domain.BOverseasEducationRank;
import com.censoft.smallRoutine.mapper.BOverseasEducationRankMapper;
import com.censoft.smallRoutine.service.IBOverseasEducationRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 海外教育资源--院校排名事实Service业务层处理
 *
 * @author cendev
 * @date 2021-01-11
 */
@Service
public class BOverseasEducationRankServiceImpl implements IBOverseasEducationRankService
{
    @Autowired
    private BOverseasEducationRankMapper bOverseasEducationRankMapper;

    /**
     * 查询海外教育资源--院校排名事实
     *
     * @param id 海外教育资源--院校排名事实ID
     * @return 海外教育资源--院校排名事实
     */
    @Override
    public BOverseasEducationRank selectBOverseasEducationRankById(String id)
    {
        return bOverseasEducationRankMapper.selectBOverseasEducationRankById(id);
    }

    /**
     * 查询海外教育资源--院校排名事实列表
     *
     * @param bOverseasEducationRank 海外教育资源--院校排名事实
     * @return 海外教育资源--院校排名事实
     */
    @Override
    public List<BOverseasEducationRank> selectBOverseasEducationRankList(BOverseasEducationRank bOverseasEducationRank)
    {
        return bOverseasEducationRankMapper.selectBOverseasEducationRankList(bOverseasEducationRank);
    }

    /**
     * 新增海外教育资源--院校排名事实
     *
     * @param bOverseasEducationRank 海外教育资源--院校排名事实
     * @return 结果
     */
    @Override
    public int insertBOverseasEducationRank(BOverseasEducationRank bOverseasEducationRank)
    {
        return bOverseasEducationRankMapper.insertBOverseasEducationRank(bOverseasEducationRank);
    }

    /**
     * 修改海外教育资源--院校排名事实
     *
     * @param bOverseasEducationRank 海外教育资源--院校排名事实
     * @return 结果
     */
    @Override
    public int updateBOverseasEducationRank(BOverseasEducationRank bOverseasEducationRank)
    {
        return bOverseasEducationRankMapper.updateBOverseasEducationRank(bOverseasEducationRank);
    }

    /**
     * 删除海外教育资源--院校排名事实对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBOverseasEducationRankByIds(String ids)
    {
        return bOverseasEducationRankMapper.deleteBOverseasEducationRankByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除海外教育资源--院校排名事实信息
     *
     * @param id 海外教育资源--院校排名事实ID
     * @return 结果
     */
    public int deleteBOverseasEducationRankById(String id)
    {
        return bOverseasEducationRankMapper.deleteBOverseasEducationRankById(id);
    }
}
