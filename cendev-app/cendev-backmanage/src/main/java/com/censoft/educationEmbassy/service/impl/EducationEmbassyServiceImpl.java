package com.censoft.educationEmbassy.service.impl;

import com.censoft.educationEmbassy.domain.EducationEmbassy;
import com.censoft.educationEmbassy.mapper.EducationEmbassyMapper;
import com.censoft.educationEmbassy.service.IEducationEmbassyService;
import java.util.List;
import com.censoft.common.utils.DateUtils;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.censoft.common.core.text.Convert;

/**
 * 国家教育处/大使馆Service业务层处理
 *
 * @author cendev
 * @date 2021-04-27
 */
@Service
public class EducationEmbassyServiceImpl implements IEducationEmbassyService {
    @Autowired
    private EducationEmbassyMapper educationEmbassyMapper;

    /**
     * 查询国家教育处/大使馆
     *
     * @param id 国家教育处/大使馆ID
     * @return 国家教育处/大使馆
     */
    @Override
    public EducationEmbassy selectEducationEmbassyById(String id) {
        return educationEmbassyMapper.selectEducationEmbassyById(id);
    }

    /**
     * 查询国家教育处/大使馆列表
     *
     * @param educationEmbassy 国家教育处/大使馆
     * @return 国家教育处/大使馆
     */
    @Override
    public List<EducationEmbassy> selectEducationEmbassyList(EducationEmbassy educationEmbassy) {
        return educationEmbassyMapper.selectEducationEmbassyList(educationEmbassy);
    }

    @Override
    public List<Map> selectEducationEmbassyList1(EducationEmbassy educationEmbassy) {
        return educationEmbassyMapper.selectEducationEmbassyList1(educationEmbassy);
    }

    /**
     * 新增国家教育处/大使馆
     *
     * @param educationEmbassy 国家教育处/大使馆
     * @return 结果
     */
    @Override
    public int insertEducationEmbassy(EducationEmbassy educationEmbassy) {
        educationEmbassy.setCreateTime(DateUtils.getNowDate());
        return educationEmbassyMapper.insertEducationEmbassy(educationEmbassy);
    }

    /**
     * 修改国家教育处/大使馆
     *
     * @param educationEmbassy 国家教育处/大使馆
     * @return 结果
     */
    @Override
    public int updateEducationEmbassy(EducationEmbassy educationEmbassy) {
        educationEmbassy.setUpdateTime(DateUtils.getNowDate());
        return educationEmbassyMapper.updateEducationEmbassy(educationEmbassy);
    }

    /**
     * 删除国家教育处/大使馆对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteEducationEmbassyByIds(String ids) {
        return educationEmbassyMapper.deleteEducationEmbassyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除国家教育处/大使馆信息
     *
     * @param id 国家教育处/大使馆ID
     * @return 结果
     */
    public int deleteEducationEmbassyById(String id) {
        return educationEmbassyMapper.deleteEducationEmbassyById(id);
    }
}
