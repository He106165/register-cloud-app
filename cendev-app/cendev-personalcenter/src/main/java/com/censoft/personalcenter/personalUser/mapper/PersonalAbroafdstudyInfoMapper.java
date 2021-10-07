package com.censoft.personalcenter.personalUser.mapper;

import com.censoft.personalcenter.personalUser.domain.PersonalAbroafdstudyInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 个人留学信息Mapper接口
 * 
 * @author cendev
 * @date 2020-11-11
 */
@Mapper
public interface PersonalAbroafdstudyInfoMapper 
{
    /**
     * 查询个人留学信息
     * 
     * @param id 个人留学信息ID
     * @return 个人留学信息
     */
    public PersonalAbroafdstudyInfo selectPersonalAbroafdstudyInfoById(String id);

    /**
     * 查询个人留学信息列表
     * 
     * @param personalAbroafdstudyInfo 个人留学信息
     * @return 个人留学信息集合
     */
    public List<PersonalAbroafdstudyInfo> selectPersonalAbroafdstudyInfoList(PersonalAbroafdstudyInfo personalAbroafdstudyInfo);

    /**
     * 新增个人留学信息
     * 
     * @param personalAbroafdstudyInfo 个人留学信息
     * @return 结果
     */
    public int insertPersonalAbroafdstudyInfo(PersonalAbroafdstudyInfo personalAbroafdstudyInfo);

    /**
     * 修改个人留学信息
     * 
     * @param personalAbroafdstudyInfo 个人留学信息
     * @return 结果
     */
    public int updatePersonalAbroafdstudyInfo(PersonalAbroafdstudyInfo personalAbroafdstudyInfo);

    /**
     * 删除个人留学信息
     * 
     * @param id 个人留学信息ID
     * @return 结果
     */
    public int deletePersonalAbroafdstudyInfoById(String id);

    /**
     * 批量删除个人留学信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePersonalAbroafdstudyInfoByIds(String[] ids);
}
