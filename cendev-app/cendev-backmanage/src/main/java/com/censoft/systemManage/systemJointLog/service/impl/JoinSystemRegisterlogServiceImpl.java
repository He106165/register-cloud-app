package com.censoft.systemManage.systemJointLog.service.impl;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import com.censoft.userManage.personalUser.mapper.PersonalUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.censoft.systemManage.systemJointLog.mapper.JoinSystemRegisterlogMapper;
import com.censoft.systemManage.systemJointLog.domain.JoinSystemRegisterlog;
import com.censoft.systemManage.systemJointLog.service.IJoinSystemRegisterlogService;
import com.censoft.common.core.text.Convert;
import org.yaml.snakeyaml.Yaml;

import static oracle.security.pki.resources.OraclePKIMsgID.Y;
import static oracle.security.pki.resources.OraclePKIMsgID.y;

/**
 * 系统对接日志Service业务层处理
 *
 * @author cendev
 * @date 2020-10-20
 */
@Service
public class JoinSystemRegisterlogServiceImpl implements IJoinSystemRegisterlogService {
    @Autowired
    private JoinSystemRegisterlogMapper joinSystemRegisterlogMapper;
    @Autowired
    private PersonalUserInfoMapper personalUserInfoMapper;

    /**
     * 查询系统对接日志
     *
     * @param id 系统对接日志ID
     * @return 系统对接日志
     */
    @Override
    public JoinSystemRegisterlog selectJoinSystemRegisterlogById(String id) {
        return joinSystemRegisterlogMapper.selectJoinSystemRegisterlogById(id);
    }

    /**
     * 查询系统对接日志列表
     *
     * @param joinSystemRegisterlog 系统对接日志
     * @return 系统对接日志
     */
    @Override
    public List<JoinSystemRegisterlog> selectJoinSystemRegisterlogList(JoinSystemRegisterlog joinSystemRegisterlog) {
        return joinSystemRegisterlogMapper.selectJoinSystemRegisterlogList(joinSystemRegisterlog);
    }

    /**
     * 新增系统对接日志
     *
     * @param joinSystemRegisterlog 系统对接日志
     * @return 结果
     */
    @Override
    public int insertJoinSystemRegisterlog(JoinSystemRegisterlog joinSystemRegisterlog) {
        return joinSystemRegisterlogMapper.insertJoinSystemRegisterlog(joinSystemRegisterlog);
    }

    /**
     * 修改系统对接日志
     *
     * @param joinSystemRegisterlog 系统对接日志
     * @return 结果
     */
    @Override
    public int updateJoinSystemRegisterlog(JoinSystemRegisterlog joinSystemRegisterlog) {
        return joinSystemRegisterlogMapper.updateJoinSystemRegisterlog(joinSystemRegisterlog);
    }

    /**
     * 删除系统对接日志对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteJoinSystemRegisterlogByIds(String ids) {
        return joinSystemRegisterlogMapper.deleteJoinSystemRegisterlogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除系统对接日志信息
     *
     * @param id 系统对接日志ID
     * @return 结果
     */
    public int deleteJoinSystemRegisterlogById(String id) {
        return joinSystemRegisterlogMapper.deleteJoinSystemRegisterlogById(id);
    }

    /**
     * 查询最近一年接口的访问数量
     */
    public List<Map> selectJoinSystemInterfaceNum() {

        Map map = new HashMap();
        //当前时间
        Date date = new Date();

        Calendar from = Calendar.getInstance();
        from.setTime(date);
        //结束时间 2021-04
        String endTime = from.get(Calendar.YEAR) + "-" + fillZero(from.get(Calendar.MONTH) + 1);
        from.add(Calendar.MONTH, -11);//11个月前
        //开始时间2020-05
        String startTime = from.get(Calendar.YEAR) + "-" + fillZero(from.get(Calendar.MONTH) + 1);
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        List<Map> list1 = joinSystemRegisterlogMapper.selectJoinSystemInterfaceNum(map);


        return list1;


    }


    /**
     * 格式化月份
     */
    public static String fillZero(int i) {
        String month = "";
        if (i < 10) {
            month = "0" + i;
        } else {
            month = String.valueOf(i);
        }
        return month;
    }

}
