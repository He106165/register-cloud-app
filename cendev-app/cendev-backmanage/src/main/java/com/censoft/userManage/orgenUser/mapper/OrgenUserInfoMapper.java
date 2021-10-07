package com.censoft.userManage.orgenUser.mapper;

import com.censoft.userManage.orgenUser.domain.OrgenUserInfo;
import com.censoft.userManage.orgenUser.domain.OrgenUserLogin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 机构用户Mapper接口
 *
 * @author cendev
 * @date 2020-10-20
 */
@Mapper
public interface OrgenUserInfoMapper {
    /**
     * 查询机构用户
     *
     * @param orgUserId 机构用户ID
     * @return 机构用户
     */
    public OrgenUserInfo selectOrgenUserInfoById(String orgUserId);

    /**
     * 查询机构用户列表
     *
     * @param orgenUserInfo 机构用户
     * @return 机构用户集合
     */
    public List<OrgenUserInfo> selectOrgenUserInfoList(OrgenUserInfo orgenUserInfo);

    /**
     * 新增机构用户
     *
     * @param orgenUserInfo 机构用户
     * @return 结果
     */
    public int insertOrgenUserInfo(OrgenUserInfo orgenUserInfo);

    /**
     * 修改机构用户
     *
     * @param orgenUserInfo 机构用户
     * @return 结果
     */
    public int updateOrgenUserInfo(OrgenUserInfo orgenUserInfo);

    /**
     * 删除机构用户
     *
     * @param orgUserId 机构用户ID
     * @return 结果
     */
    public int deleteOrgenUserInfoById(String orgUserId);

    /**
     * 批量删除机构用户
     *
     * @param orgUserIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrgenUserInfoByIds(String[] orgUserIds);

    String selectAuth(@Param("legalName") String legalName, @Param("legalIdnumber") String legalIdnumber);

    int updateAuthLevel(@Param("legalName") String legalName, @Param("legalIdnumber") String legalIdnumber);

    int updateStatus(OrgenUserLogin orgenUserLogin);

    int orgenAuthentication(@RequestParam("orgUserId") String orgUserId);

    int federationApprove(@RequestParam("orgUserId") String orgUserId);

    int updateSta(@RequestParam("orgUserId") String orgUserId);

    /**
     * 查询机构用户今日新增数
     *
     * @param
     * @return结果
     */
    int selectNewAddOrgenSum(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /* *//**
     * 时间范围查询人员数量
     * @param startTime
     * @param endTime
     * @return
     *//*
    public int selectOrganPersonNum(@Param("startTime")String startTime,@Param("endTime")String endTime);*/

    /**
     * 查询机构用户总数
     *
     * @param
     * @return结果
     */
    public int selectOrgenPersonSum();
}
