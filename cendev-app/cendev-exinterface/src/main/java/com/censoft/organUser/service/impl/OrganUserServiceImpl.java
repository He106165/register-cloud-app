/**
 * Copyright (C), 2018-2019, 中关村科技软件股份有限公司
 * FileName: SysOnlineUserRedisServiceImpl
 * Author:   XG-X1
 * Date:     2019/11/8 14:51
 * Description: 查询系统在线用户Redis
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.censoft.organUser.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.censoft.cendevbackmanage.entity.JoinSystemInfo;
import com.censoft.cendevbackmanage.entity.JoinSystemRegisterlog;
import com.censoft.cendevbackmanage.feign.CheckNationalityServer;
import com.censoft.cendevbackmanage.feign.DockedLogServer;
import com.censoft.cendevbackmanage.feign.JoinSystemServer;
import com.censoft.cendevbackmanage.feign.UserInterfaceServer;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.common.utils.MD5Format;
import com.censoft.generalUser.mapper.DictDataMapper;
import com.censoft.organUser.mapper.OrganUserInfoMapper;
import com.censoft.organUser.service.OrganUserService;
import com.censoft.otherlogin.feign.RemoteTokenServer;
import com.censoft.util.*;
import com.censoft.weChat.mapper.WechatUserInfoMapper;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈查询系统在线用户Redis〉
 *
 * @author XG-X1
 * @create 2019/11/8
 * @since 1.0.0
 */
@Service
public class OrganUserServiceImpl  implements OrganUserService {

    @Autowired
    public OrganUserInfoMapper organUserInfoMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisUtils redis;


    @Autowired
    private RemoteTokenServer tokenService;
    @Autowired
    private DockedLogServer dockedLogService;
    @Autowired
    private JoinSystemServer systemInfoService;
    @Autowired
    private CheckNationalityServer checkNationalityServer;
    @Autowired
    private WechatUserInfoMapper wechatUserInfoMapper;
    @Autowired
    private UserInterfaceServer userInterfaceServer;

    @Autowired
    private DictDataMapper dictDataMapper;
    DesNewUtils desObj = new DesNewUtils();
    String key = "cen)%)(!";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map registerOrganUser(Map map) {


        //返回结果
        Map resMap = new HashMap();


        String joinsysCode = map.get("sysuqid") == null?"":map.get("sysuqid").toString();


        JoinSystemInfo sysInfo = new JoinSystemInfo();
        sysInfo.setJoinsysCode(joinsysCode);

        if(systemInfoService.checkSystemCode(joinsysCode) ==null || !systemInfoService.checkSystemCode(joinsysCode)){
            resMap.put("state","404");
            resMap.put("reason","获取系统标识失败或不正确,请联系管理员！");
            return  resMap;
        }


        List<JoinSystemInfo> sysInfoList = dockedLogService.sysInfolist(sysInfo);
        if(sysInfoList.size() == 0){
            resMap.put("state","404");
            resMap.put("reason","获取系统标识失败或不正确！");
            return  resMap;
        }



        JoinSystemRegisterlog joinSystemRegisterlog = new JoinSystemRegisterlog();
        joinSystemRegisterlog.setJoinsysCode(joinsysCode);
        joinSystemRegisterlog.setJoinsysName(sysInfoList.get(0).getJoinsysName());
        //接口id
        joinSystemRegisterlog.setSysmethodId("127");
        //接口名称
        joinSystemRegisterlog.setSysmethodName("对接系统机构用户注册接口");
        joinSystemRegisterlog.setRealm(sysInfoList.get(0).getRealmPlace());
        joinSystemRegisterlog.setOpertionTime(new Date());

        String parameter = "";
        for (Object key : map.keySet()) {
            parameter+=key + ",";
        }
        joinSystemRegisterlog.setParameter(parameter);



        //验证  机构用户类型是否存在
        String orgUserType = map.get("orgUserType")==null?"":map.get("orgUserType").toString();
        if(!"01".equals(orgUserType) && !"02".equals(orgUserType) && !"03".equals(orgUserType) ){
            resMap.put("state","404");
            resMap.put("reason","机构用户类型不正确！");

            //日志结果
            joinSystemRegisterlog.setStatus("404");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("机构用户类型不正确！");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
            return  resMap;
        }else if(orgUserType.equals("03")){

            //海外学联 登陆表 ispass不通过  info状态状态  不通过
            map.put("ispass","0");

            map.put("status","1");
            map.put("state","1");
        }


        String unifiedSocialCredItCode = map.get("unifiedSocialCredItCode")==null?"":map.get("unifiedSocialCredItCode").toString();
        String perManType = map.get("legalType")==null?"":map.get("legalType").toString();
        map.put("perManType",perManType);

        String perManLine = map.get("perManLine")==null?"":map.get("perManLine").toString();
        String legalName = map.get("legalName")==null?"":map.get("legalName").toString();
        String legalCardType = map.get("legalCardType")==null?"":map.get("legalCardType").toString();
        String legalIdNumber = map.get("legalIdNumber")==null?"":map.get("legalIdNumber").toString();
        String hostPersonEmail = map.get("hostPersonEmail")==null?"":map.get("hostPersonEmail").toString();
        String departmentCode = map.get("departmentCode")==null?"":map.get("departmentCode").toString();
        String password = map.get("password")==null?"":map.get("password").toString();

        String englishName = map.get("englishName")==null?"":map.get("englishName").toString();
        String country = map.get("country")==null?"":map.get("country").toString();
        String province = map.get("province")==null?"":map.get("province").toString();
        String address = map.get("address")==null?"":map.get("address").toString();
        String unionId = map.get("unionId")==null?"":map.get("unionId").toString();
        String userType = "01";

        //20210204修改  普通用户注册状态 00   海外学联  状态  01
        map.put("status","00");
        map.put("state","00");

        switch(orgUserType){
            case "01":
                //国内学校
                //检测  用户名/社会信用代码  和部门编码 是否存在
                //国内机构
                //检测  用户名/社会信用代码  和部门编码 是否存在
                if(unifiedSocialCredItCode.equals("") || !UnifiedCreditCodeUtil.checkUnifiedCreditCode(unifiedSocialCredItCode)){
                    resMap.put("state","406");
                    resMap.put("reason","统一社会信用代码格式不正确！");
                    //日志结果
                    joinSystemRegisterlog.setStatus("406");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("注册国内机构用户信用代码"+unifiedSocialCredItCode+"统一社会信用代码格式不正确！");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                    return  resMap;
                }
                else if(legalName.equals("")){
                    resMap.put("state","404");
                    resMap.put("reason","法人姓名不正确！");
                    //日志结果
                    joinSystemRegisterlog.setStatus("406");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("注册国内机构用户信用代码"+unifiedSocialCredItCode+"法人姓名不正确！");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                    return  resMap;
                }
                //先查字典类型的数据（法人类型，法人证件类型，部门编号）
                List<String> paramList2 = new ArrayList<String>() {
                    {
                        this.add("perMan_type");
                        this.add("perMan_line");
                        this.add("card_type");
                        this.add("deperment_code");
                    }
                };
                List<Map> disctDataList2 = dictDataMapper.selectDictDataByDictType(paramList2);
                if(disctDataList2.size() == 0){
                    resMap.put("status","500");
                    resMap.put("state","500");
                    resMap.put("reason","程序错误！");
                    return resMap;
                }
                //法人类型是否正确
                boolean perManTypeTy2 = false;
                //法人性质是否正确
                boolean perManLineTy2 = false;
                //法人证件类型是否正确
                boolean legalCardTypeTy2 = false;
                //部门编号是否正确
                boolean depermentCodeTy2 = false;
                for (Map a:disctDataList2) {
                    if(perManTypeTy2 == false && a.get("DICT_TYPE").equals("perMan_type")){
                        if(perManType.equals("") || a.get("DICT_DATA").toString().indexOf(perManType)==-1){
                            resMap.put("state","406");
                            resMap.put("reason","法人类型不正确！");
                            return  resMap;
                        }else
                            perManTypeTy2 = true;
                        continue;
                    }
                    if(perManLineTy2 == false && a.get("DICT_TYPE").equals("perMan_line")){
                        if(perManLine.equals("") || a.get("DICT_DATA").toString().indexOf(perManLine)==-1){
                            resMap.put("state","406");
                            resMap.put("reason","法人性质不正确！");
                            return  resMap;
                        }else
                            perManLineTy2 = true;
                        continue;
                    }
                    else if(legalCardTypeTy2 == false && a.get("DICT_TYPE").equals("card_type") ){
                        if(legalCardType.equals("") || a.get("DICT_DATA").toString().indexOf(legalCardType)==-1){
                            resMap.put("state","406");
                            resMap.put("reason","法人证件类型不正确！");
                            return  resMap;
                        }else
                            legalCardTypeTy2 = true;
                        continue;
                    }
                    else if(depermentCodeTy2 == false && a.get("DICT_TYPE").equals("deperment_code") ){
                        if(departmentCode.equals("") || a.get("DICT_DATA").toString().indexOf(departmentCode)==-1){
                            resMap.put("state","406");
                            resMap.put("reason","部门编号不正确！");
                            return  resMap;
                        }else
                            depermentCodeTy2 = true;
                        continue;
                    }
                }
                if(!( ("111".equals(legalCardType) && CheckIDUtil.isIDNumber(legalIdNumber)) || (("553".equals(legalCardType) && CardsUtil.isPassPortCard(legalIdNumber))) || ("511".equals(legalCardType) && CardsUtil.isTWCard(legalIdNumber)) || ("516".equals(legalCardType) && CardsUtil.isHMCard(legalIdNumber)) || ("155".equals(legalCardType) && CardsUtil.isTWDJCard(legalIdNumber)) || ("156".equals(legalCardType) && CardsUtil.isHKDJCard(legalIdNumber)) ) ){
                    resMap.put("state","406");
                    resMap.put("reason","证件号格式不正确！");
                    //日志结果
                    joinSystemRegisterlog.setStatus("406");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("注册国内机构用户信用代码"+unifiedSocialCredItCode+"证件号格式不正确！");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                    return  resMap;
                }
                if(!unionId.equals("")){
                    if(wechatUserInfoMapper.selectWechatUserInfoByIdAndUserType(unionId,userType)!=null){
                        resMap.put("state","409");
                        resMap.put("reason","微信标识已存在");
                        //日志结果
                        joinSystemRegisterlog.setStatus("404");
                        //日志原因
                        joinSystemRegisterlog.setOpertionmes("注册国内机构用户信用代码"+unifiedSocialCredItCode+"微信标识已存在！");
                        dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                        return  resMap;
                    }
                }
                break;
            case "02":
                //海外机构

                /*国家*/
                if("".equals(englishName)){
                    resMap.put("state","404");
                    resMap.put("reason","机构/院校英文名称格式不正确！");
                    //日志结果
                    joinSystemRegisterlog.setStatus("404");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("注册海外机构用户信用代码"+unifiedSocialCredItCode+"机构/院校英文名称格式不正确！");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                    return  resMap;
                }
                else if("".equals(province)){
                    resMap.put("state","404");
                    resMap.put("reason","所在省份不能为空！");
                    //日志结果
                    joinSystemRegisterlog.setStatus("404");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("注册海外机构用户信用代码"+unifiedSocialCredItCode+"机构/所在省份不能为空！");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                    return  resMap;
                }
                else if("".equals(address)){
                    resMap.put("state","404");
                    resMap.put("reason","详细地址不能为空！");

                    //日志结果
                    joinSystemRegisterlog.setStatus("404");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("注册海外机构用户信用代码"+unifiedSocialCredItCode+"详细地址不能为空！");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                    return  resMap;
                }


                if(!unionId.equals("")){
                    resMap.put("state","409");
                    resMap.put("reason","不支持绑定微信");
                    //日志结果
                    joinSystemRegisterlog.setStatus("404");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("注册海外机构用户信用代码"+unifiedSocialCredItCode+"不支持绑定微信");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                    return  resMap;
                }

                Map codeMap = checkNationalityServer.selectNationalityAllCode(new HashMap());
                String allcode =  codeMap.get("THREECODE").toString();
                if(allcode.indexOf(country) == -1){
                    resMap.put("state","406");
                    resMap.put("reason","国籍不正确！");

                    //日志结果
                    joinSystemRegisterlog.setStatus("404");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("注册海外机构用户信用代码"+unifiedSocialCredItCode+"国籍不正确！");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                    return  resMap;
                }

                break;
            case "03":
                //海外学校
                /*字典查询  大使馆，冰增加大使馆名称属性，修改该数据未审批状态*/
                List<String> paramList03 = new ArrayList<String>() {
                    {
                        this.add("consulate_info");
                    }
                };
                List<Map> disctDataList03 = dictDataMapper.selectDictDataByDictType(paramList03);
                if(disctDataList03.size() == 0){
                    resMap.put("status","500");
                    resMap.put("state","500");
                    resMap.put("reason","程序错误！");
                    return resMap;
                }
                String consulateId = map.get("consulateId")==null?"":map.get("consulateId").toString();
               boolean consulateTy =false;

                for (Map a:disctDataList03) {
                   if(consulateTy == false && a.get("DICT_TYPE").equals("consulate_info")){
                        if(consulateId.equals("") || a.get("DICT_DATA").toString().indexOf(consulateId)==-1){
                            resMap.put("state","406");
                            resMap.put("reason","大使馆格式不正确！");
                            return  resMap;
                        }
                    }
                }

                if(!unionId.equals("")){
                    resMap.put("state","409");
                    resMap.put("reason","不支持绑定微信");
                    //日志结果
                    joinSystemRegisterlog.setStatus("404");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("注册海外学联用户信用代码"+unifiedSocialCredItCode+"不支持绑定微信");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                    return  resMap;
                }

                //20210204修改  普通用户注册状态 00   海外学联  状态  01
                map.put("status","01");
                map.put("state","01");

                //大使馆id找到大使馆信息
                Map consuMap = new HashMap();
                consuMap.put("DICT_TYPE","consulate_info");
                consuMap.put("DICT_CODE",consulateId);
                Map dataMap = dictDataMapper.selectDataByDataId(consuMap);
                map.put("CONSULATE_NAME",dataMap.get("DICT_LABEL"));


                if("".equals(englishName)){
                    resMap.put("state","404");
                    resMap.put("reason","机构/院校英文名称不能为空！");
                    //日志结果
                    joinSystemRegisterlog.setStatus("404");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("注册海外学联用户信用代码"+unifiedSocialCredItCode+"机构/院校英文名称不能为空！");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                    return  resMap;
                }

                else if("".equals(address)){
                    resMap.put("state","404");
                    resMap.put("reason","详细地址不能为空！");

                    //日志结果
                    joinSystemRegisterlog.setStatus("404");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("注册海外学联用户信用代码"+unifiedSocialCredItCode+"详细地址不能为空！");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                    return  resMap;
                }

                Map codeMap2 = checkNationalityServer.selectNationalityAllCode(new HashMap());
                String allcode2 =  codeMap2.get("THREECODE").toString();
                if(allcode2.indexOf(country) == -1){
                    resMap.put("state","406");
                    resMap.put("reason","国籍不正确！");

                    //日志结果
                    joinSystemRegisterlog.setStatus("404");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("注册海外学联用户信用代码"+unifiedSocialCredItCode+"国籍不正确！");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                    return  resMap;
                }

                break;
        }

        if(!isEmail(hostPersonEmail)){
            /*验证邮箱*/
            resMap.put("state","406");
            resMap.put("reason","联系人邮箱格式不正确！");
            //日志结果
            joinSystemRegisterlog.setStatus("406");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("注册海外学联用户信用代码"+unifiedSocialCredItCode+"联系人邮箱格式不正确！");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
            return  resMap;
        }


        String id = UUID.randomUUID().toString().replace("-","");
        String userId = UUID.randomUUID().toString().replace("-","");
        map.put("id",id);
        map.put("organUserId",userId);
//        //状态改成未激活
//        map.put("activeFlag","01");



       Map rmap = organUserInfoMapper.checkUserNameAndDeptcode(map);
        if(!rmap.get("HOSTEMAIL").toString().equals("0")){
            resMap.put("state","409");
            resMap.put("reason","联系人邮箱已存在！");
            return  resMap;
        }
        else if(!unifiedSocialCredItCode.equals("") && !rmap.get("UN").toString().equals("0")){
            resMap.put("state","409");
            resMap.put("reason","该部门社会信用代码已存在！");
            return  resMap;
        }
        //password = desObj.strEnc(password, key);
        password = MD5Format.MD5(password);
        map.put("password",password);
        try {
            if(legalCardType.equals("111") && userInterfaceServer.certTwoData(legalName,legalIdNumber)){
                map.put("authLevel","3");
            }
            organUserInfoMapper.insertOrganUserLogin(map);
            organUserInfoMapper.insertOrganUserInfo(map);

            if(!unionId.equals("")){
                map.put("userType",userType);
                organUserInfoMapper.insertWEchartInfo(map);
            }
                resMap.put("state","200");
                resMap.put("reason","注册成功");

                //日志结果
                joinSystemRegisterlog.setStatus("200");
                //日志原因
                joinSystemRegisterlog.setOpertionmes("注册成功！");
                dockedLogService.sysLogaddSave(joinSystemRegisterlog);

        }catch (Exception e){
            e.printStackTrace();
            // 加入下行代码手动回滚
            // @Transactional 为方法加上事务,try catch 捕获到异常手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            resMap.put("state","500");
            resMap.put("reason","注册失败");

            //日志结果
            joinSystemRegisterlog.setStatus("500");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("注册失败！");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
        }

        return resMap;
    }

    @Override
    public Map organUserLogin(Map map) {

        //返回结果
        Map resMap = new HashMap();

        String joinsysCode = map.get("sysuqid").toString();
        JoinSystemInfo sysInfo = new JoinSystemInfo();
        sysInfo.setJoinsysCode(joinsysCode);
        if(systemInfoService.checkSystemCode(joinsysCode)==null || !systemInfoService.checkSystemCode(joinsysCode)){
            resMap.put("state","404");
            resMap.put("reason","获取系统标识失败或不正确,请联系管理员！");
            return  resMap;
        }

        List<JoinSystemInfo> sysInfolist = dockedLogService.sysInfolist(sysInfo);
        if(sysInfolist.size() == 0){
            resMap.put("state","404");
            resMap.put("reason","获取系统标识失败或不正确！");
            return  resMap;
        }



        JoinSystemRegisterlog joinSystemRegisterlog = new JoinSystemRegisterlog();
        joinSystemRegisterlog.setJoinsysCode(joinsysCode);
        joinSystemRegisterlog.setJoinsysName(sysInfolist.get(0).getJoinsysName());
        //接口id
        joinSystemRegisterlog.setSysmethodId("124");
        //接口名称
        joinSystemRegisterlog.setSysmethodName("对接系统机构用户登陆接口");
        joinSystemRegisterlog.setRealm(sysInfolist.get(0).getRealmPlace());
        joinSystemRegisterlog.setOpertionTime(new Date());



        String parameter = "";
        for (Object key : map.keySet()) {
            parameter+=key + ",";
        }
        joinSystemRegisterlog.setParameter(parameter);



        //拿到登陆的信息
        //校验登陆信息   先去redis找    再到数据库里找
        String userName = map.get("userName")==null?"":map.get("userName").toString();
        //String mappassword = desObj.strEnc(map.get("password").toString(), key);
        String mappassword = MD5Format.MD5(map.get("password").toString());
        map.put("password",mappassword);
        String departmentCode = map.get("departmentCode")==null?"":map.get("departmentCode").toString();
        List<String> paramList2 = new ArrayList<String>() {
            {
                this.add("deperment_code");
            }
        };
        List<Map> disctDataList2 = dictDataMapper.selectDictDataByDictType(paramList2);
        if(disctDataList2.size() == 0){
            resMap.put("status","500");
            resMap.put("state","500");
            resMap.put("reason","程序错误！");
            return resMap;
        }
        for (Map a:disctDataList2) {
            if(a.get("DICT_TYPE").toString().equals("deperment_code") && a.get("DICT_DATA").toString().indexOf(departmentCode)==-1){
                    resMap.put("state","406");
                    resMap.put("reason","部门类型不正确！");
                    return  resMap;
            }
        }


        /*大致判断用户名是不是社会信用代码*/
        if(UnifiedCreditCodeUtil.checkUnifiedCreditCode(userName)){
            //社会信用代码格式的用户名
            map.put("loginType","unCode");
        }else{
            map.put("loginType","hostEmail");
        }

        Set<String> unkeys=redisTemplate.keys(userName+"##"+departmentCode);
        List<Object> jsonList=redisTemplate.opsForValue().multiGet(unkeys);
        if(jsonList.size()>0 ){
            for (Object userMap:jsonList){
                Map maps= JSONObject.toJavaObject((JSONObject)userMap,Map.class);
                String password = maps.get("password")==null?"":maps.get("password").toString();
                if(mappassword.equals(password) ){
                    String token = tokenService.getToken( userName+"##"+departmentCode);
                    resMap.put("state","200");
                    resMap.put("reason","登陆成功");
                    resMap.put("token",token);

                    //日志结果
                    joinSystemRegisterlog.setStatus("200");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("个人登录接口用户名"+userName+"登陆成功");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                    return resMap;
                }
            }
        }



        //登陆
        /*只查可用的，没有删除的*/
        map.put("status","00");
        map.put("state","00");
        map.put("delFlag",0);
       List<Map> list= organUserInfoMapper.organUserLogin(map);

        if(list.size() > 0){
           //登陆成功
            Map userMap = list.get(0);
            String token ="";
            String rkey = "";

            token = tokenService.getToken( userName+"##"+departmentCode);
            rkey = userName+"##"+departmentCode;


            /*登陆成功  生成token*/
            resMap.put("state","200");
            resMap.put("reason","登陆成功");
            resMap.put("token",token);
            Map rvalue = new HashMap();
            rvalue.put("password",userMap.get("PASSWORD"));
            rvalue.put("status",userMap.get("STATUS"));
            rvalue.put("del_flag",userMap.get("DEL_FLAG"));
            rvalue.put("userType","organ");
            rvalue.put("userId",userMap.get("OEG_USER_ID"));
            rvalue.put("loginType",map.get("loginType"));

            redis.set(rkey,rvalue);

            //organUserInfoMapper.updateOrganUserInfoByUserID(userMap);


            //日志结果
            joinSystemRegisterlog.setStatus("200");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("个人登录接口用户名"+userName+"登陆成功");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
        }else {
            resMap.put("state","406");
            resMap.put("reason","用户名或密码错误！");

            //日志结果
            joinSystemRegisterlog.setStatus("406");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("个人登录接口用户名"+userName+"登陆成功");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
        }

        //并返回结果
        //记录日志


        return resMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map updateOrganUser(Map map) {


        Map resMap = new HashMap();

        String joinsysCode = map.get("sysuqid").toString();
        JoinSystemInfo sysInfo = new JoinSystemInfo();
        sysInfo.setJoinsysCode(joinsysCode);
        if(systemInfoService.checkSystemCode(joinsysCode)==null || !systemInfoService.checkSystemCode(joinsysCode)){
            resMap.put("state","404");
            resMap.put("reason","获取系统标识失败或不正确,请联系管理员！");
            return  resMap;
        }

        List<JoinSystemInfo> sysInfolist = dockedLogService.sysInfolist(sysInfo);
        if(sysInfolist.size() == 0){
            resMap.put("state","404");
            resMap.put("reason","获取系统标识失败或不正确！");
            return  resMap;
        }



        JoinSystemRegisterlog joinSystemRegisterlog = new JoinSystemRegisterlog();
        joinSystemRegisterlog.setJoinsysCode(joinsysCode);
        joinSystemRegisterlog.setJoinsysName(sysInfolist.get(0).getJoinsysName());
        //接口id
        joinSystemRegisterlog.setSysmethodId("125");
        //接口名称
        joinSystemRegisterlog.setSysmethodName("对接系统修改个人用户接口");
        joinSystemRegisterlog.setRealm(sysInfolist.get(0).getRealmPlace());
        joinSystemRegisterlog.setOpertionTime(new Date());


        String parameter = "";
        for (Object key : map.keySet()) {
            parameter+=key + ",";
        }
        joinSystemRegisterlog.setParameter(parameter);






        String oldunifiedSocialCredItCode = map.get("oldunifiedSocialCredItCode") == null?"":map.get("oldunifiedSocialCredItCode").toString();
        String oldDepartmentCode = map.get("oldDepartmentCode") == null?"":map.get("oldDepartmentCode").toString();
        String oldhostEmail = map.get("oldhostEmail") == null?"":map.get("oldhostEmail").toString();
        String departmentCode = map.get("departmentCode")==null?"":map.get("departmentCode").toString();

        if("".equals(oldunifiedSocialCredItCode) && "".equals(oldhostEmail)){
            resMap.put("state","406");
            resMap.put("reason","目标社会信用代码或联系人邮箱不能为空！");
            return  resMap;
        }
        if(!"".equals(oldunifiedSocialCredItCode) && !UnifiedCreditCodeUtil.checkUnifiedCreditCode(oldunifiedSocialCredItCode)){
            resMap.put("state","406");
            resMap.put("reason","目标社会信用代码不正确！");

            //日志结果
            return  resMap;
        }

        List<Map> usrmap = organUserInfoMapper.selectOrganAllInfo(map);
        if(usrmap.size() != 1 ){
            resMap.put("state","404");
            resMap.put("reason","目标部门社会信用代码或联系人邮箱不正确！");

            //日志结果
            joinSystemRegisterlog.setStatus("406");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("修改机构用户社会信用代码"+oldunifiedSocialCredItCode+"部门编号："+oldDepartmentCode+"目标部门社会信用代码或联系人邮箱不正确！");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
            return  resMap;
        }else {
            map.putAll(usrmap.get(0));
        }



//        legalCardType法人代表证件类型、
//        legalIdNumber 法人代表证件号码、
//        perManMobile 法人代表手机号、
//        hostPersionEmail 联系人邮箱
//        unifiedSocialCredItCode 社会信用代码

        String legalCardType = map.get("legalCardType") == null?"":map.get("legalCardType").toString();
        String legalIdNumber = map.get("legalIdNumber") == null?"":map.get("legalIdNumber").toString();
        String hostPersonEmail = map.get("hostPersonEmail") == null?"":map.get("hostPersonEmail").toString();
        String hostPersonTelephone = map.get("hostPersonTelephone") == null?"":map.get("hostPersonTelephone").toString();

        if(legalCardType.equals("") && legalIdNumber.equals("")){
        }
        else if((!legalCardType.equals("") && !legalIdNumber.equals(""))){
            if(!( ("111".equals(legalCardType) && CheckIDUtil.isIDNumber(legalIdNumber)) || (("553".equals(legalCardType) && CardsUtil.isPassPortCard(legalIdNumber))) || ("511".equals(legalCardType) && CardsUtil.isTWCard(legalIdNumber)) || ("516".equals(legalCardType) && CardsUtil.isHMCard(legalIdNumber)) || ("155".equals(legalCardType) && CardsUtil.isTWDJCard(legalIdNumber)) || ("156".equals(legalCardType) && CardsUtil.isHKDJCard(legalIdNumber)) ) ){
            }
            else {
                resMap.put("state","406");
                resMap.put("reason","法人证件号格式不正确！");
                //日志结果
                return  resMap;
            }
        }
        else {
            resMap.put("state","406");
            resMap.put("reason","证件号及类型格式不正确！");
            //日志结果
            joinSystemRegisterlog.setStatus("406");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("修改机构用户社会信用代码"+oldunifiedSocialCredItCode+"部门编号："+oldDepartmentCode+"法人证件类型不正确！");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
            return  resMap;
        }

        if(!"".equals(hostPersonEmail)  && !isEmail(hostPersonEmail) && UnifiedCreditCodeUtil.checkUnifiedCreditCode(hostPersonEmail)){
            resMap.put("state","406");
            resMap.put("reason","联系人邮箱不正确！");

            //日志结果
            joinSystemRegisterlog.setStatus("406");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("修改机构用户社会信用代码"+oldunifiedSocialCredItCode+"部门编号："+oldDepartmentCode+"联系人邮箱不正确！");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
            return  resMap;
        }


        /*报所有能查字典类型的
        全部放在这里*/
        List<String> paramList = new ArrayList<String>();
        String perManType = map.get("legalType")==null?"":map.get("legalType").toString();
        map.put("perManType",perManType);
        String perManLine = map.get("perManLine")==null?"":map.get("perManLine").toString();
        String country = map.get("country")==null?"":map.get("country").toString();
        String consulateId = map.get("consulateId")==null?"":map.get("consulateId").toString();
        if(!perManType.equals("")){
            paramList.add("perMan_type");
        }
        if(!perManType.equals("")){
            paramList.add("perMan_line");
        }
        if(!consulateId.equals("")){
            paramList.add("consulate_info");
        }
        if(!departmentCode.equals("") || !oldDepartmentCode.equals("")){
            paramList.add("deperment_code");
        }
        boolean perManTypeTy = false;
        boolean perManLineTy = false;
        boolean consulateTy = false;
        boolean departmentCodeTy = false;
        boolean oldDepartmentCodeTy = false;
        if(departmentCode.equals("")){
            departmentCodeTy = true;
        }
        if(oldDepartmentCode.equals("")){
            oldDepartmentCodeTy = true;
        }


        if(paramList.size()>0){
            List<Map> disctDataList = dictDataMapper.selectDictDataByDictType(paramList);
            if(disctDataList.size() == 0){
                resMap.put("status","500");

                resMap.put("state","500");
                resMap.put("reason","程序错误！");
                return resMap;
            }
            for (Map a:disctDataList) {
                if(perManTypeTy == false && a.get("DICT_TYPE").equals("perMan_type")){
                    if(a.get("DICT_DATA").toString().indexOf(perManType)==-1){
                        resMap.put("state","406");
                        resMap.put("reason","法人类型不正确！");
                        //日志结果
                        joinSystemRegisterlog.setStatus("404");
                        return  resMap;
                    }else
                        perManTypeTy = true;
                    continue;
                }
                if(departmentCodeTy == false && a.get("DICT_TYPE").equals("deperment_code")){
                    if(a.get("DICT_DATA").toString().indexOf(departmentCode)==-1){
                        resMap.put("state","406");
                        resMap.put("reason","法人类型不正确！");
                        //日志结果
                        joinSystemRegisterlog.setStatus("404");
                        return  resMap;
                    }else
                        departmentCodeTy = true;
                    continue;
                }
                if(oldDepartmentCodeTy == false && a.get("DICT_TYPE").equals("deperment_code")){
                    if(a.get("DICT_DATA").toString().indexOf(oldDepartmentCode)==-1){
                        resMap.put("state","406");
                        resMap.put("reason","法人类型不正确！");
                        //日志结果
                        joinSystemRegisterlog.setStatus("404");
                        return  resMap;
                    }else
                        oldDepartmentCodeTy = true;
                    continue;
                }




                if(perManLineTy == false && a.get("DICT_TYPE").equals("perMan_line")){
                    if(a.get("DICT_DATA").toString().indexOf(perManLine)==-1){
                        resMap.put("state","406");
                        resMap.put("reason","法人性质不正确！");
                        //日志结果
                        joinSystemRegisterlog.setStatus("404");
                        return  resMap;
                    }else
                        perManLineTy = true;
                    continue;
                }
                else if(consulateTy == false && a.get("DICT_TYPE").equals("consulate_info")){
                    if(a.get("DICT_DATA").toString().indexOf(country)==-1){
                        resMap.put("state","406");
                        resMap.put("reason","婚姻状态不正确！");
                        //日志结果
                        joinSystemRegisterlog.setStatus("404");
                        return  resMap;
                    }else
                        consulateTy = true;
                    continue;
                }
            }
        }


        /*所有国家代码*/

        if(!country.equals("")){
            Map codeMap = checkNationalityServer.selectNationalityAllCode(new HashMap());
            String allcode =  codeMap.get("THREECODE").toString();
            if(allcode.indexOf(country) == -1){
                resMap.put("state","406");
                resMap.put("reason","国籍不正确！");

                //日志结果
                joinSystemRegisterlog.setStatus("406");
                //日志原因
                joinSystemRegisterlog.setOpertionmes("修改机构用户社会信用代码"+oldunifiedSocialCredItCode+"部门编号："+oldDepartmentCode+"国籍不正确！");
                dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                return  resMap;
            }
        }

        /*
        判断修改的字段是否符合长度
        */
//        if(!"".equals(hostPersionTelephone) &&!isMobileNO(hostPersionTelephone)){
//            resMap.put("state","404");
//            resMap.put("reason","联系人手机号格式不正确！");
//
//            //日志结果
//            joinSystemRegisterlog.setStatus("404");
//            //日志原因
//            joinSystemRegisterlog.setOpertionmes("联系人手机号格式不正确！");
//            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
//            return  resMap;
//        }


        //拿到之前的   社会信用代码，用户名，部门
        //如果 修改的属性  没有这三个  把原来的属性拿过来
        //保证  修改前有值   修改后的值
        String unifiedSocialCredItCode = map.get("unifiedSocialCredItCode") == null?"":map.get("unifiedSocialCredItCode").toString();
        if((!unifiedSocialCredItCode.equals("") || !hostPersonEmail.equals("")) && organUserInfoMapper.checkUserOldNameAndDeptcode(map)!=null){
            resMap.put("state","409");
            resMap.put("reason","用户名已存在！");

            //日志结果
            joinSystemRegisterlog.setStatus("409");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("修改机构用户社会信用代码"+oldunifiedSocialCredItCode+"部门编号："+oldDepartmentCode+"用户名已存在！");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
            return  resMap;
        }


        /*通过  depentmentCode 和用户名找到原来用户信息*/
        try{
          //  organUserInfoMapper.updateOrganUserLogin(map);
            organUserInfoMapper.updateOrganUserInfo(map);
            organUserInfoMapper.updateOrganUserSupply(map);
            resMap.put("state","200");
            resMap.put("reason","更新成功");

             String userName = map.get("userName")==null?"":map.get("userName").toString();
            String depec = map.get("OOLDPHONE")==null?"":map.get("OOLDPHONE").toString();
            String name = map.get("OOLDNAME")==null?"":map.get("OOLDNAME").toString();

            if(!departmentCode.equals("")){
               String unceode = map.get("OOUNCODE")==null?"":map.get("OOUNCODE").toString();
                String keyname = name +"##"+depec;
                String keyUncode = unceode +"##"+depec;
                redis.delete(keyname);
                redis.delete(keyUncode);
            }else if(!userName.equals("")) {
                String keyname = name +"##"+depec;
                redis.delete(keyname);
            }



            //日志结果
            joinSystemRegisterlog.setStatus("200");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("修改机构用户社会信用代码"+oldunifiedSocialCredItCode+"部门编号："+oldDepartmentCode+"更新成功！");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);


            }catch (Exception e){
                e.printStackTrace();
                // 加入下行代码手动回滚
                // @Transactional 为方法加上事务,try catch 捕获到异常手动回滚事务
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            resMap.put("state","500");
            resMap.put("reason","更新失败");

            //日志结果
            joinSystemRegisterlog.setStatus("500");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("修改机构用户社会信用代码"+oldunifiedSocialCredItCode+"部门编号："+oldDepartmentCode+"更新失败！");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);

            }


        return resMap;
    }




    /*判断手机号*/
    public  boolean isMobileNO(String mobiles) {
        String telRegex = "[1][3578]\\d{9}";
        // "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else
            return mobiles.matches(telRegex);
    }

    public  boolean isEmail(String mobiles) {
        String telRegex = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        // "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else
            return mobiles.matches(telRegex);
    }
}



