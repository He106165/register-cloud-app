package com.censoft.smallRoutine.mapper;

import com.censoft.smallRoutine.domain.BOverseasEducationRank;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 海外教育资源--院校排名事实Mapper接口
 *
 * @author cendev
 * @date 2021-01-11
 */
@Mapper
public interface BOverseasEducationRankMapper
{
    /**
     * 查询海外教育资源--院校排名事实
     *
     * @param id 海外教育资源--院校排名事实ID
     * @return 海外教育资源--院校排名事实
     */
    public BOverseasEducationRank selectBOverseasEducationRankById(String id);

    /**
     * 查询海外教育资源--院校排名事实列表
     *
     * @param bOverseasEducationRank 海外教育资源--院校排名事实
     * @return 海外教育资源--院校排名事实集合
     */
    public List<BOverseasEducationRank> selectBOverseasEducationRankList(BOverseasEducationRank bOverseasEducationRank);

    /**
     * 新增海外教育资源--院校排名事实
     *
     * @param bOverseasEducationRank 海外教育资源--院校排名事实
     * @return 结果
     */
    public int insertBOverseasEducationRank(BOverseasEducationRank bOverseasEducationRank);

    /**
     * 修改海外教育资源--院校排名事实
     *
     * @param bOverseasEducationRank 海外教育资源--院校排名事实
     * @return 结果
     */
    public int updateBOverseasEducationRank(BOverseasEducationRank bOverseasEducationRank);

    /**
     * 删除海外教育资源--院校排名事实
     *
     * @param id 海外教育资源--院校排名事实ID
     * @return 结果
     */
    public int deleteBOverseasEducationRankById(String id);

    /**
     * 批量删除海外教育资源--院校排名事实
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBOverseasEducationRankByIds(String[] ids);
}
