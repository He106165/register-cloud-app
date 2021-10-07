package com.censoft.exInterface.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.censoft.cendevbackmanage.entity.JoinSystemInfo;
import com.censoft.cendevbackmanage.entity.JoinSystemRegisterlog;
import com.censoft.cendevbackmanage.feign.DockedLogServer;
import com.censoft.cendevbackmanage.feign.JoinSystemServer;
import com.censoft.common.core.text.Convert;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.common.utils.DateUtils;
import com.censoft.exInterface.domain.SysMethodInfo;
import com.censoft.exInterface.mapper.SysMethodInfoMapper;
import com.censoft.exInterface.service.ISysMethodInfoService;
import com.censoft.generalUser.mapper.GeneralUserInfoMapper;
import com.censoft.organUser.mapper.OrganUserInfoMapper;
import com.censoft.otherlogin.feign.RemoteTokenServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 对外接口管理Service业务层处理
 *
 * @author cendev
 * @date 2020-10-20
 */
@Service
public class SysMethodInfoServiceImpl implements ISysMethodInfoService {
    @Autowired
    private SysMethodInfoMapper sysMethodInfoMapper;

    @Autowired
    public OrganUserInfoMapper organUserInfoMapper;
    @Autowired
    public GeneralUserInfoMapper generalUserInfoMapper;
    @Autowired
    private RedisUtils redis;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RemoteTokenServer tokenService;
    @Autowired
    private DockedLogServer dockedLogService;


    @Autowired
    private JoinSystemServer systemInfoService;

    /**
     * 查询对外接口管理
     *
     * @param sysmethodId 对外接口管理ID
     * @return 对外接口管理
     */
    @Override
    public SysMethodInfo selectSysMethodInfoById(String sysmethodId) {
        return sysMethodInfoMapper.selectSysMethodInfoById(sysmethodId);
    }

    /**
     * 查询对外接口管理列表
     *
     * @param sysMethodInfo 对外接口管理
     * @return 对外接口管理
     */
    @Override
    public List<SysMethodInfo> selectSysMethodInfoList(SysMethodInfo sysMethodInfo) {
        return sysMethodInfoMapper.selectSysMethodInfoList(sysMethodInfo);
    }

    /**
     * 新增对外接口管理
     *
     * @param sysMethodInfo 对外接口管理
     * @return 结果
     */
    @Override
    public int insertSysMethodInfo(SysMethodInfo sysMethodInfo) {
        sysMethodInfo.setSysmethodId(IdUtil.fastSimpleUUID());
        sysMethodInfo.setCreateTime(DateUtils.getNowDate());
        return sysMethodInfoMapper.insertSysMethodInfo(sysMethodInfo);
    }

    /**
     * 修改对外接口管理
     *
     * @param sysMethodInfo 对外接口管理
     * @return 结果
     */
    @Override
    public int updateSysMethodInfo(SysMethodInfo sysMethodInfo) {
        sysMethodInfo.setUpdateTime(DateUtils.getNowDate());
        return sysMethodInfoMapper.updateSysMethodInfo(sysMethodInfo);
    }

    /**
     * 删除对外接口管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysMethodInfoByIds(String ids) {
        return sysMethodInfoMapper.deleteSysMethodInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除对外接口管理信息
     *
     * @param sysmethodId 对外接口管理ID
     * @return 结果
     */
    public int deleteSysMethodInfoById(String sysmethodId) {
        return sysMethodInfoMapper.deleteSysMethodInfoById(sysmethodId);
    }

    @Override
    public Map getUserInfo(Map map) {

        //返回结果
        Map resMap = new HashMap();
        String joinsysCode = map.get("sysuqid") == null?"":map.get("sysuqid").toString();
        String token = map.get("token") == null?"":map.get("token").toString();


        JoinSystemInfo sysInfo = new JoinSystemInfo();
        sysInfo.setJoinsysCode(joinsysCode);


        List<JoinSystemInfo> sysInfoList = dockedLogService.sysInfolist(sysInfo);
        if(joinsysCode.equals("") || sysInfoList ==null || sysInfoList.size() == 0){
            resMap.put("state","404");
            resMap.put("reason","获取系统标识失败或不正确,请联系管理员！");
            return  resMap;
        }
        JoinSystemRegisterlog joinSystemRegisterlog = new JoinSystemRegisterlog();
        joinSystemRegisterlog.setJoinsysCode(joinsysCode);
        joinSystemRegisterlog.setJoinsysName(sysInfoList.get(0).getJoinsysName());
        //接口id
        joinSystemRegisterlog.setSysmethodId("127");
        //接口名称
        joinSystemRegisterlog.setSysmethodName("其他系统验证token信息");
        joinSystemRegisterlog.setRealm(sysInfoList.get(0).getRealmPlace());
        joinSystemRegisterlog.setOpertionTime(new Date());

        String parameter = "";
        for (Object key : map.keySet()) {
            parameter+=key + ",";
        }
        joinSystemRegisterlog.setParameter(parameter);

        Map params = systemInfoService.getSystemDataConfig(joinsysCode);



        if("".equals(token)){
            resMap.put("status","404");

            resMap.put("state","404");
            resMap.put("reason","token不能为空！");
            //日志结果
            joinSystemRegisterlog.setStatus("404");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("token不能为空！");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
            return resMap;
        }
        String mapName = tokenService.getAccoutName(token);
        mapName = mapName == null?"":mapName;
        if("".equals(mapName)){
            resMap.put("status","406");

            resMap.put("state","406");
            resMap.put("reason","token不正确");

            //日志结果
            joinSystemRegisterlog.setStatus("406");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("token不正确");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
            return resMap;
        }
        /*
            机构用户
        #a_DPCode_SAD123#a_ORGUNAME_wzp8888#a_ORGUCODE_1231231231231123*/
        /*
        *
        * 通过token  得到登录名
        *   redis 里面判断   用户是机构用户？
        *   通过登录名  在到数据库里面再找用户的信息
        *
        * */


        Set<String> unkeys=redisTemplate.keys(mapName);
        List<Object> jsonList=redisTemplate.opsForValue().multiGet(unkeys);
        String userType = "";
        Map userMap = new HashMap();
        //通过token判断用户是不是机构用户等用户类型
        if(jsonList.size()>0){
            Map maps= JSONObject.toJavaObject((JSONObject)jsonList.get(0),Map.class);
            userType =  maps.get("userType")==null?"":maps.get("userType").toString();
            String userId = maps.get("userId")==null?"":maps.get("userId").toString();


            if(userType.equals("personal")){
                if(params.get("proUserData")==null){
                    resMap.put("status","406");

                    resMap.put("state","406");
                    resMap.put("reason","您没有取参数权限，请联系管理员！");

                    //日志结果
                    joinSystemRegisterlog.setStatus("406");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("其他系统验证token"+token+"您没有取参数权限，请联系管理员！");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                }
                map.put("userId",userId);
                String pams = params.get("proUserData")==null?"":params.get("proUserData").toString();
                map.put("params",pams);

                userMap =  generalUserInfoMapper.getUserInfoByUserId(map);
            }
            else if(userType.equals("organ")){
                if(params.get("proUserData")==null){
                    resMap.put("status","406");

                    resMap.put("state","406");
                    resMap.put("reason","您没有取参数权限，请联系管理员！");

                    //日志结果
                    joinSystemRegisterlog.setStatus("406");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("其他系统验证token"+token+"您没有取参数权限，请联系管理员！");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                    return resMap;
                }
                map.put("oegUserId",userId);
                String pams = params.get("orgUserData")==null?"":params.get("orgUserData").toString();
                map.put("params",pams);
                userMap = organUserInfoMapper.getUserInfoByOrgId(map);
            }
        }

        if(userMap == null){
            resMap.put("status","406");

            resMap.put("state","406");
            resMap.put("reason","token不正确");

            //日志结果
            joinSystemRegisterlog.setStatus("406");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("其他系统验证token"+token+"token不正确");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
        }else {
            resMap.put("status","200");

            resMap.put("state","200");
            resMap.put("reason","成功");

            userMap.put("userType",userType);
            resMap.put("userInfo",JSONObject.toJSON(userMap));
            //日志结果
            joinSystemRegisterlog.setStatus("200");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("其他系统验证token"+token+"token验证成功");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
        }
        return resMap;
    }

    @Override
    public Map  getUserInfoByUserId(Map map){
        // 定义返回结果Map集合
        Map resMap = new HashMap();
        try{
            // 判空
            if(map.get("sysuqid") == null || map.get("sysuqid") == "")
                return resultMsg(resMap,404,"系统唯一标识不能为空");
            if(map.get("userId") == null || map.get("userId") == "")
                return resultMsg(resMap,404,"用户Id不能为空");

            // 获取系统配置参数
            Map params = systemInfoService.getSystemDataConfig(map.get("sysuqid").toString());
            // 如果为空，则提示没有权限
            if(params.size() == 0 || params.get("proUserData")==null)
                return resultMsg(resMap,503,"没有数据权限请联系管理员");

            map.put("params",params.get("proUserData").toString());
            // 封装个人用户信息
            Map userMap = generalUserInfoMapper.getUserInfoByUserId(map);
            resMap = resultMsg(resMap,200,"成功");
            resMap.put("userInfo",JSONObject.toJSON(userMap));
        }catch (Exception e){
            e.printStackTrace();
            return resultMsg(resMap,500,"内部错误请联系管理员");
        }
        return resMap;
    }

    public Map resultMsg(Map map,int code,String msg){
        map.put("state",code);
        map.put("reason",msg);
        return map;
    }
}
