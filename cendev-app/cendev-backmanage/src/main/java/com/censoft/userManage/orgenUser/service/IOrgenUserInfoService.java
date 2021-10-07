package com.censoft.userManage.orgenUser.service;

import com.censoft.userManage.orgenUser.domain.OrgenUserInfo;
import com.censoft.userManage.personalUser.domain.ResultUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 机构用户Service接口
 *
 * @author cendev
 * @date 2020-10-20
 */
public interface IOrgenUserInfoService {
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
     * 批量删除机构用户
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrgenUserInfoByIds(String ids);

    /**
     * 删除机构用户信息
     *
     * @param orgUserId 机构用户ID
     * @return 结果
     */
    public int deleteOrgenUserInfoById(String orgUserId);

    ResultUtil authentication(String legalName, String legalIdnumber);

    ResultUtil orgenAuthentication(String orgUserId);

    ResultUtil federationApprove(String orgUserId);

    /**
     * 查询机构用户今日新增数
     *
     * @param
     * @return结果
     */
    int selectNewAddOrgenSum();

    /**
     * 查询机构用户本月新增的总数
     *
     * @return
     */
    int selectNewMonthOrgenSum();

    /**
     * 查询机构用户本年新增的总数
     *
     * @return
     */
    int selectYearOrgenSum();

    /**
     * 查询机构用户的总数
     *
     * @return
     */
    int selectOrgenPersonSum();


    /* *//**
     * 时间范围查询人员数量
     * @return
     *//*
    public int selectOrganPersonNum();*/

}
