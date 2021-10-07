package com.censoft.userManage.findUser.mapper;

import com.censoft.userManage.findUser.domain.FindPersonalInfo;
import com.censoft.userManage.personalUser.domain.ResultUtil;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 找回个人用户信息Mapper接口
 * 
 * @author cendev
 * @date 2020-10-20
 */
@Mapper
public interface FindPersonalInfoMapper 
{
    /**
     * 查询找回个人用户信息
     * 
     * @param id 找回个人用户信息ID
     * @return 找回个人用户信息
     */
    public FindPersonalInfo selectFindPersonalInfoById(String id);

    /**
     * 查询找回个人用户信息列表
     * 
     * @param findPersonalInfo 找回个人用户信息
     * @return 找回个人用户信息集合
     */
    public List<FindPersonalInfo> selectFindPersonalInfoList(FindPersonalInfo findPersonalInfo);

    /**
     * 新增找回个人用户信息
     * 
     * @param findPersonalInfo 找回个人用户信息
     * @return 结果
     */
    public int insertFindPersonalInfo(FindPersonalInfo findPersonalInfo);

    /**
     * 修改找回个人用户信息
     * 
     * @param findPersonalInfo 找回个人用户信息
     * @return 结果
     */
    public int updateFindPersonalInfo(FindPersonalInfo findPersonalInfo);

    /**
     * 删除找回个人用户信息
     * 
     * @param id 找回个人用户信息ID
     * @return 结果
     */
    public int deleteFindPersonalInfoById(String id);

    /**
     * 批量删除找回个人用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFindPersonalInfoByIds(String[] ids);

    boolean selectUserInfo(FindPersonalInfo findPersonalInfo);

    int updatePersonalUserInfo(FindPersonalInfo findPersonalInfo);

    String selectFindPassword(FindPersonalInfo findPersonalInfo);

    int updatePersonalUserLogin(FindPersonalInfo findPersonalInfo);

    int updateFindStatus(FindPersonalInfo findPersonalInfo);


    int inserBohui(FindPersonalInfo findPersonalInfo);
}
