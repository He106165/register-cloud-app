package com.censoft.userManage.orgenUser.service.impl;

import com.censoft.Util.ResultStatusEnum;
import com.censoft.cendevbackmanage.feign.UserInterfaceServer;
import com.censoft.common.utils.StringUtils;
import com.censoft.insideUserManage.insideRole.domain.InsideRole;
import com.censoft.userManage.orgenUser.domain.OrgenUserLogin;
import com.censoft.userManage.personalUser.domain.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.*;

import com.censoft.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.censoft.userManage.orgenUser.mapper.OrgenUserInfoMapper;
import com.censoft.userManage.orgenUser.domain.OrgenUserInfo;
import com.censoft.userManage.orgenUser.service.IOrgenUserInfoService;
import com.censoft.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 机构用户Service业务层处理
 *
 * @author cendev
 * @date 2020-10-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrgenUserInfoServiceImpl implements IOrgenUserInfoService {
    @Autowired
    private OrgenUserInfoMapper orgenUserInfoMapper;
    @Autowired
    private UserInterfaceServer userInterfaceServer;

    /**
     * 查询机构用户
     *
     * @param orgUserId 机构用户ID
     * @return 机构用户
     */
    @Override
    public OrgenUserInfo selectOrgenUserInfoById(String orgUserId) {
        return orgenUserInfoMapper.selectOrgenUserInfoById(orgUserId);
    }

    /**
     * 查询机构用户列表
     *
     * @param orgenUserInfo 机构用户
     * @return 机构用户
     */
    @Override
    public List<OrgenUserInfo> selectOrgenUserInfoList(OrgenUserInfo orgenUserInfo) {
        return orgenUserInfoMapper.selectOrgenUserInfoList(orgenUserInfo);
    }

    /**
     * 新增机构用户
     *
     * @param orgenUserInfo 机构用户
     * @return 结果
     */
    @Override
    public int insertOrgenUserInfo(OrgenUserInfo orgenUserInfo) {
        orgenUserInfo.setCreateTime(DateUtils.getNowDate());
        return orgenUserInfoMapper.insertOrgenUserInfo(orgenUserInfo);
    }

    /**
     * 修改机构用户
     *
     * @param orgenUserInfo 机构用户
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOrgenUserInfo(OrgenUserInfo orgenUserInfo) {
        orgenUserInfo.setUpdateTime(DateUtils.getNowDate());
        orgenUserInfo.setIspass(1L);
        return orgenUserInfoMapper.updateOrgenUserInfo(orgenUserInfo);
//        OrgenUserLogin orgenUserLogin = orgenUserInfo.getOrgenUserLogin();
//        orgenUserLogin.setOegUserId(orgenUserInfo.getOrgUserId());
//        orgenUserLogin.setStatus("01");
//        return orgenUserInfoMapper.updateStatus(orgenUserLogin);
    }

    /**
     * 删除机构用户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOrgenUserInfoByIds(String ids) {
        return orgenUserInfoMapper.deleteOrgenUserInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除机构用户信息
     *
     * @param orgUserId 机构用户ID
     * @return 结果
     */
    public int deleteOrgenUserInfoById(String orgUserId) {
        return orgenUserInfoMapper.deleteOrgenUserInfoById(orgUserId);
    }

    @Override
    public ResultUtil authentication(String legalName, String legalIdnumber) {
        if (StringUtils.isBlank(legalName) && StringUtils.isBlank(legalIdnumber)) {
            return ResultUtil.result(ResultStatusEnum.S_10031.getStatus(), ResultStatusEnum.S_10031.getMassage());
        }
        String s = null;
        try {
            s = orgenUserInfoMapper.selectAuth(legalName, legalIdnumber);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.result(ResultStatusEnum.S_10035.getStatus(), ResultStatusEnum.S_10035.getMassage());
        }
        if (("2").equals(s)) {
            //添加实名认证
            boolean b = userInterfaceServer.certTwoData(legalName, legalIdnumber);
            if (b) {
                int row = orgenUserInfoMapper.updateAuthLevel(legalName, legalIdnumber);
                if (row == 0) {
                    return ResultUtil.result(ResultStatusEnum.S_10031.getStatus(), ResultStatusEnum.S_10031.getMassage());
                }
            } else {
                return ResultUtil.result(ResultStatusEnum.S_10033.getStatus(), ResultStatusEnum.S_10033.getMassage());
            }
        } else if (("3").equals(s)) {
            return ResultUtil.result(ResultStatusEnum.S_10034.getStatus(), ResultStatusEnum.S_10034.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10032.getStatus(), ResultStatusEnum.S_10032.getMassage());
        }

        return ResultUtil.success();
    }

    @Override
    public ResultUtil orgenAuthentication(String orgUserId) {
        int i = orgenUserInfoMapper.orgenAuthentication(orgUserId);
        if (i > 0) {
            return ResultUtil.success();
        }
        return ResultUtil.result("实名认证失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil federationApprove(String orgUserId) {
        //修改info表内ispass状态为 通过
        int row = orgenUserInfoMapper.federationApprove(orgUserId);
        if (row > 0) {
            //修改登录表状态为 通过
            int r = orgenUserInfoMapper.updateSta(orgUserId);
            if (r > 0) {
                return ResultUtil.success();
            } else {
                return ResultUtil.result(ResultStatusEnum.S_10036.getStatus(), ResultStatusEnum.S_10036.getMassage());
            }
        }
        return ResultUtil.result("审批不通过");
    }

    /**
     * 查询机构用户今日新增数
     *
     * @param
     * @return结果
     */
    @Override
    public int selectNewAddOrgenSum() {
        /*  Map map  = new HashMap();*/
        //得到当前时间
        Date date = new Date();
        //设置时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取开始的时间
        String endTime = dateFormat.format(date);
        //得到日历
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        String startTime = dateFormat.format(calendar.getTime());   //得到前一天的时间
       /* //获取今日的时间,返回结果
        map.put("startTime", startTime);
        map.put("endTime", endTime);*/
        return orgenUserInfoMapper.selectNewAddOrgenSum(startTime, endTime);
    }

    /**
     * 查询机构用户本月新增数
     *
     * @param
     * @return结果
     */
    @Override
    public int selectNewMonthOrgenSum() {
        /* Map  map  = new HashMap();*/
        //得到当前时间
        Date date = new Date();
        //设置时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取开始的时间   当前月份
        String endTime = dateFormat.format(date);
        //得到日历
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, -1);  //设置为前一个月
        String startTime = dateFormat.format(calendar.getTime());   //得到前一个月的时间
       /* //获取今日的时间,返回结果
        map.put("startTime", startTime);
        map.put("endTime", endTime);*/
        return orgenUserInfoMapper.selectNewAddOrgenSum(startTime, endTime);
    }

    /**
     * 查询机构用户本年新增数
     *
     * @param
     * @return结果
     */
    @Override
    public int selectYearOrgenSum() {
        /* Map  map  = new HashMap();*/
        //得到当前时间
        Date date = new Date();
        //设置时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取开始的时间   当前月份
        String endTime = dateFormat.format(date);
        //得到日历
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);//把当前时间赋给日历
        calendar.add(Calendar.YEAR, -1);  //设置为当前的前一年
        String startTime = dateFormat.format(calendar.getTime());   //得到前一年的时间
       /* //获取今日的时间,返回结果
        map.put("startTime", startTime);
        map.put("endTime", endTime);*/
        return orgenUserInfoMapper.selectNewAddOrgenSum(startTime, endTime);
    }

    /**
     * 查询机构用户总数
     *
     * @param
     * @return结果
     */
    @Override
    public int selectOrgenPersonSum() {

        return orgenUserInfoMapper.selectOrgenPersonSum();
    }


  /*  @Override
    public int selectOrganPersonNum() {
        //得到当前时间
        Date  date = new  Date();
        //设置时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取开始的时间   当前月份
        String endTime = dateFormat.format(date);
        //得到日历
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);//把当前时间赋给日历
        calendar.add(Calendar.YEAR, -1);  //设置为当前的前一年
        String  startTime = dateFormat.format(calendar.getTime());   //得到前一年的时间
        return orgenUserInfoMapper.selectOrganPersonNum(startTime,endTime);
    }*/
}

