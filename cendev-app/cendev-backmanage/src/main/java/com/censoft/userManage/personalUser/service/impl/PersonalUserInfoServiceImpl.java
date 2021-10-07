package com.censoft.userManage.personalUser.service.impl;

import com.censoft.Util.ResultStatusEnum;
import com.censoft.cendevbackmanage.feign.UserInterfaceServer;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.common.utils.StringUtils;
import com.censoft.userManage.personalUser.domain.PersonalUserLogin;
import com.censoft.userManage.personalUser.domain.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.*;

import com.censoft.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.censoft.userManage.personalUser.mapper.PersonalUserInfoMapper;
import com.censoft.userManage.personalUser.domain.PersonalUserInfo;
import com.censoft.userManage.personalUser.service.IPersonalUserInfoService;
import com.censoft.common.core.text.Convert;

/**
 * 个人用户信息Service业务层处理
 *
 * @author cendev
 * @date 2020-10-20
 */
@Service
public class PersonalUserInfoServiceImpl implements IPersonalUserInfoService {
    @Autowired
    private PersonalUserInfoMapper personalUserInfoMapper;
    @Autowired
    private UserInterfaceServer userInterfaceServer;
    @Autowired
    private RedisUtils redisUtils;
    private Object Map;

    /**
     * 查询个人用户信息
     *
     * @param userId 个人用户信息ID
     * @return 个人用户信息
     */
    @Override
    public PersonalUserInfo selectPersonalUserInfoById(String userId) {
        return personalUserInfoMapper.selectPersonalUserInfoById(userId);
    }

    /**
     * 查询个人用户信息列表
     *
     * @param personalUserInfo 个人用户信息
     * @return 个人用户信息
     */
    @Override
    public List<PersonalUserInfo> selectPersonalUserInfoList(PersonalUserInfo personalUserInfo) {
        return personalUserInfoMapper.selectPersonalUserInfoList(personalUserInfo);
    }

    /**
     * 新增个人用户信息
     *
     * @param personalUserInfo 个人用户信息
     * @return 结果
     */
    @Override
    public int insertPersonalUserInfo(PersonalUserInfo personalUserInfo) {
        personalUserInfo.setCreateTime(DateUtils.getNowDate());
        return personalUserInfoMapper.insertPersonalUserInfo(personalUserInfo);
    }

    /**
     * 修改个人用户信息
     *
     * @param personalUserInfo 个人用户信息
     * @return 结果
     */
    @Override
    public int updatePersonalUserInfo(PersonalUserInfo personalUserInfo) {
        personalUserInfo.setUpdateTime(DateUtils.getNowDate());
        return personalUserInfoMapper.updatePersonalUserInfo(personalUserInfo);
    }

    /**
     * 删除个人用户信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePersonalUserInfoByIds(String ids) {
        return personalUserInfoMapper.deletePersonalUserInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除个人用户信息信息
     *
     * @param userId 个人用户信息ID
     * @return 结果
     */
    public int deletePersonalUserInfoById(String userId) {
        return personalUserInfoMapper.deletePersonalUserInfoById(userId);
    }

    @Override
    public ResultUtil authentication(String userName, String cardNo) {
        if (StringUtils.isBlank(userName) && StringUtils.isBlank(cardNo)) {
            return ResultUtil.result(ResultStatusEnum.S_10031.getStatus(), ResultStatusEnum.S_10031.getMassage());
        }
        String s = null;
        try {
            s = personalUserInfoMapper.selectAuth(userName, cardNo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.result(ResultStatusEnum.S_10035.getStatus(), ResultStatusEnum.S_10035.getMassage());
        }
        if (("2").equals(s)) {
            //添加实名认证
            boolean b = userInterfaceServer.certTwoData(userName, cardNo);
            if (b) {
                int row = personalUserInfoMapper.updateAuthLevel(userName, cardNo);
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
    public ResultUtil personalAuthentication(String userId) {
        //将该用户的实名认证信息 改为已实名，是否需要实名 改为0  不需要
        int i = personalUserInfoMapper.personalAuthentication(userId);

        if (i > 0) {
            //查询该用户的个人登录信息，将redis中的信息删除
            PersonalUserLogin p = personalUserInfoMapper.selectInfo(userId);
            if (StringUtils.isNotBlank(p.getPhone())) {
                redisUtils.delete(p.getPhone());
            }
            if (StringUtils.isNotBlank(p.getEmail())) {
                redisUtils.delete(p.getEmail());
            }
            if (StringUtils.isNotBlank(p.getIdnumber())) {
                redisUtils.delete(p.getIdnumber());
            }
            if (StringUtils.isNotBlank(p.getName())) {
                redisUtils.delete(p.getName());
            }
            return ResultUtil.success();
        }
        return ResultUtil.result("实名认证失败");
    }

    /**
     * 查询个人用户今日新增的总数
     *
     * @return
     */
    @Override
    public Integer selectNewAddPersonalSum() {
        Map map = new HashMap();
        //得到当前时间
        Date date = new Date();
        //设置时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取开始的时间
        String endTime = dateFormat.format(date);
        //得到日历
        Calendar calendar = Calendar.getInstance();
        //把当前时间赋给日历
        calendar.setTime(date);
        //设置为前一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        //得到前一天的时间
        String startTime = dateFormat.format(calendar.getTime());
        //获取今日的时间,返回结果
        map.put("startTime", startTime);
        map.put("endTime", endTime);
       /* SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String createTime = dateFormat.format(date);
        map.put("createTime",createTime);*/
        return personalUserInfoMapper.selectNewAddPersonalSum1(map);
    }

    /**
     * 查询个人用户本月新增的总数
     *
     * @return
     */
    @Override
    public Integer selectNewMonthPersonalSum() {
        Map map = new HashMap();
        //得到当前时间
        Date date = new Date();
        //设置时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取开始的时间   当前月份
        String endTime = dateFormat.format(date);
        //得到日历
        Calendar calendar = Calendar.getInstance();
        //把当前时间赋给日历
        calendar.setTime(date);
        //设置为前一个月
        calendar.add(Calendar.MONTH, -1);
        //得到前一个月的时间
        String startTime = dateFormat.format(calendar.getTime());
        //获取今日的时间,返回结果
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return personalUserInfoMapper.selectNewAddPersonalSum1(map);
    }

    /**
     * 查询个人用户本年新增的总数
     *
     * @return
     */
    @Override
    public Integer selectYearPersonalSum() {
        Map map = new HashMap();
        //得到当前时间
        Date date = new Date();
        //设置时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取开始的时间   当前月份
        String endTime = dateFormat.format(date);
        //得到日历
        Calendar calendar = Calendar.getInstance();
        //把当前时间赋给日历
        calendar.setTime(date);
        //设置为当前的前一年
        calendar.add(Calendar.YEAR, -1);
        //得到前一年的时间s
        String startTime = dateFormat.format(calendar.getTime());
        //获取今日的时间,返回结果
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return personalUserInfoMapper.selectNewAddPersonalSum1(map);
    }

    /**
     * 查询个人用户的总数
     *
     * @return
     */
    @Override
    public Integer selectPersonalSum() {
        return personalUserInfoMapper.selectPersonalSum();
    }

    /**
     * 查询最近一周的用户新增数
     */
    public List<String> selectWeekPersonalNum() {

        List<String> list = new ArrayList<String>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 当前日期
        Calendar instance = Calendar.getInstance();
        // 调整到上周
        instance.add(Calendar.WEDNESDAY, -1);
        // 调整到上周1
        instance.set(Calendar.DAY_OF_WEEK, 2);
        //循环打印
        for (int i = 1; i <= 7; i++) {
            String format1 = format.format(instance.getTime());
            instance.add(Calendar.DAY_OF_WEEK, 1);
            list.add(format1);
        }
        List Weeklist = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            //遍历出上周每一天的日期
            String WeekDate = list.get(i);
            //得到上周每一天的新增数量
            int i1 = personalUserInfoMapper.selectWeekPersonalNum(WeekDate);
            Weeklist.add(i1);
        }

        return Weeklist;

    }
}



