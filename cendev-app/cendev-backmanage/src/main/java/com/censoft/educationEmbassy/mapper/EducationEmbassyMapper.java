package com.censoft.educationEmbassy.mapper;


import com.censoft.educationEmbassy.domain.EducationEmbassy;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

/**
 * 国家教育处/大使馆Mapper接口
 * 
 * @author cendev
 * @date 2021-04-27
 */
@Mapper
public interface EducationEmbassyMapper 
{
    /**
     * 查询国家教育处/大使馆
     * 
     * @param id 国家教育处/大使馆ID
     * @return 国家教育处/大使馆
     */
    public EducationEmbassy selectEducationEmbassyById(String id);

    /**
     * 查询国家教育处/大使馆列表
     * 
     * @param educationEmbassy 国家教育处/大使馆
     * @return 国家教育处/大使馆集合
     */
    public List<EducationEmbassy> selectEducationEmbassyList(EducationEmbassy educationEmbassy);

    /**
     * 新增国家教育处/大使馆
     * 
     * @param educationEmbassy 国家教育处/大使馆
     * @return 结果
     */
    public int insertEducationEmbassy(EducationEmbassy educationEmbassy);

    /**
     * 修改国家教育处/大使馆
     * 
     * @param educationEmbassy 国家教育处/大使馆
     * @return 结果
     */
    public int updateEducationEmbassy(EducationEmbassy educationEmbassy);

    /**
     * 删除国家教育处/大使馆
     * 
     * @param id 国家教育处/大使馆ID
     * @return 结果
     */
    public int deleteEducationEmbassyById(String id);

    /**
     * 批量删除国家教育处/大使馆
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteEducationEmbassyByIds(String[] ids);

    List<Map> selectEducationEmbassyList1(EducationEmbassy educationEmbassy);
}
