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
package com.censoft.generalUser.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.censoft.cendevbackmanage.entity.JoinSystemInfo;
import com.censoft.cendevbackmanage.entity.JoinSystemRegisterlog;
import com.censoft.cendevbackmanage.feign.*;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.common.utils.MD5Format;
import com.censoft.generalUser.entity.PersonalUserInfoToRedis;
import com.censoft.generalUser.mapper.DictDataMapper;
import com.censoft.generalUser.mapper.GeneralUserInfoMapper;
import com.censoft.generalUser.service.GeneralUserService;
import com.censoft.otherlogin.feign.RemoteTokenServer;
import com.censoft.util.*;
import com.censoft.weChat.mapper.WechatUserInfoMapper;
import jdk.internal.dynalink.support.NameCodec;
import org.apache.http.util.TextUtils;
import org.apache.poi.ss.formula.functions.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;

/**
 * 〈一句话功能简述〉<br>
 * 〈查询系统在线用户Redis〉
 *
 * @author XG-X1
 * @create 2019/11/8
 * @since 1.0.0
 */
@Service
public class GeneralUserServiceImpl  implements GeneralUserService {

    @Autowired
    public GeneralUserInfoMapper generalUserInfoMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisUtils redis;
    @Autowired
    private JoinSystemServer systemInfoService;
    @Autowired
    private RemoteTokenServer tokenService;
    @Autowired
    private DockedLogServer dockedLogService;

    @Autowired
    private DictDataMapper dictDataMapper;
    @Autowired
    private CheckNationalityServer checkNationalityServer;
    @Autowired
    private UserInterfaceServer userInterfaceServer;
    @Autowired
    private WechatUserInfoMapper wechatUserInfoMapper;
    @Autowired
    private RemoteMsgSendService remoteMsgSendService;
    //设置redis前缀
    private static final String wechat_redis_key = "wechat_";
    //设置redis前缀
    private static final String Alipay_redis_key = "#Alipay_";

    DesNewUtils desObj = new DesNewUtils();
    String key = "cen)%)(!";
    @Override
    public Map checkGeneralUser(Map map) {
        return generalUserInfoMapper.checkGeneralUser(map);
    }


    @Override
    public Integer checkUserEmail(Map map) {
        /*只校验实名的*/
        map.put("emailIscheck",1);
        return generalUserInfoMapper.checkUserEmail(map);
    }

    @Override
    public Integer checkUserPhone(Map map) {
        /*只校验实名的*/
        map.put("phoneIscheck",1);
        return generalUserInfoMapper.checkUserPhone(map);
    }
    @Override
    public Integer checkUserName(Map map) {
        return generalUserInfoMapper.checkUserName(map);
    }
    /*验证个人昵称是否注册*/
    @Override
    public Integer checkUserNickName(String nickname) {
        return generalUserInfoMapper.checkUserNickName(nickname);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map registerGeneralUser(Map map) {

        //返回结果
        Map resMap = new HashMap();

        //拿到注册的信息 首先看系统名是否存在  并返回对接系统的详细信息

        //验证  用户名，手机号，邮箱格式是否正确
        //验证  用户名，手机号，邮箱是否重复。。
        //并返回结果


        String joinsysCode = map.get("sysuqid").toString();
        JoinSystemInfo sysInfo = new JoinSystemInfo();
        sysInfo.setJoinsysCode(joinsysCode);
        if(systemInfoService.checkSystemCode(joinsysCode) == null || !systemInfoService.checkSystemCode(joinsysCode)){
            resMap.put("state","404");
            resMap.put("reason","获取系统标识失败或不正确！");
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
        joinSystemRegisterlog.setSysmethodId("123");
        //接口名称
        joinSystemRegisterlog.setSysmethodName("对接系统个人用户注册接口");
        joinSystemRegisterlog.setRealm(sysInfolist.get(0).getRealmPlace());
        joinSystemRegisterlog.setOpertionTime(new Date());

        String parameter = "";
        for (Object key : map.keySet()) {
            parameter+=key + ",";
        }
        joinSystemRegisterlog.setParameter(parameter);


        String phone = map.get("phone") == null?"":map.get("phone").toString();
        String email = map.get("email") == null?"":map.get("email").toString();
        String idNumber = map.get("idNumber") == null?"":map.get("idNumber").toString();
        String cardType = map.get("cardType") == null?"01":map.get("cardType").toString();
        String realName = map.get("realName") == null?"":map.get("realName").toString();
        String phoneIsCheck = map.get("phoneIsCheck") == null?"0":map.get("phoneIsCheck").toString();
        String nickName = map.get("nickName") == null?"0":map.get("nickName").toString();
        /*外派学号特殊处理*/
        if(map.get("ovestuId") != null){
            map.put("userName",map.get("ovestuId"));
        }


        /*通过业务获取
        要出查询的字典类型字典类型
        身份证类型，国籍
        */

        List<String> paramList = new ArrayList<String>() {
            {
                this.add("card_type");
            }
        };
        List<Map> disctDataList = dictDataMapper.selectDictDataByDictType(paramList);
        if(disctDataList.size() == 0){
            resMap.put("status","500");

            resMap.put("state","500");
            resMap.put("reason","程序错误！");
            return resMap;
        }
        //身份证类型是否正确
        boolean cardTypeTy = false;
        //国籍是否正确
        for (Map a:disctDataList) {
            if(cardTypeTy == false && a.get("DICT_TYPE").equals("card_type")){

                if(a.get("DICT_DATA").toString().indexOf(cardType)==-1){
                    resMap.put("state","406");
                    resMap.put("reason","证件类型格式不正确！");
                    return  resMap;
                }else
                    cardTypeTy = true;
                continue;
            }
        }

        /*验证手机号*/
//        String countryCode = map.get("countryCode")==null?"86":map.get("countryCode").toString();
//        if(!PhoneUtil.checkPhoneNumber(phone,Integer.parseInt(countryCode))){
//
        if(phoneIsCheck.equals("1")){
            if(!isMobileNO(phone)){
                resMap.put("state","406");
                resMap.put("reason","手机格式不正确！");
                //日志结果
                joinSystemRegisterlog.setStatus("406");
                //日志原因
                joinSystemRegisterlog.setOpertionmes("注册个人账号证件号"+idNumber+"手机格式不正确！");
                dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                return  resMap;
            }
        }
        else if(!isEmail(email)){
            /*验证邮箱*/
            resMap.put("state","406");
            resMap.put("reason","邮箱格式不正确！");
            //日志结果
            joinSystemRegisterlog.setStatus("406");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("注册个人账号证件号"+idNumber+"邮箱格式不正确！");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
            return  resMap;
        }
        else if(!( ("111".equals(cardType) && CheckIDUtil.isIDNumber(idNumber)) )){
            /*验证邮箱*/
            resMap.put("state","406");
            resMap.put("reason","证件号格式不正确！");

            //日志结果
            joinSystemRegisterlog.setStatus("406");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("注册个人账号证件号"+idNumber+"证件号格式不正确！");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
            return  resMap;
        }
        /*校验 国籍是否正确*/
        if(checkNationalityServer.selectNationalityListByCode(map) == null || checkNationalityServer.selectNationalityListByCode(map).size() == 0 ){
            /*验证邮箱*/
            resMap.put("state","406");
            resMap.put("reason","国籍不正确！");

            //日志结果
            joinSystemRegisterlog.setStatus("406");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("注册个人账号证件号"+idNumber+"国籍不正确！");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
            return  resMap;
        }

        /*个人昵称格式是否正确*/
        if (!isNickNameString(nickName)){
               resMap.put("state","406");
               resMap.put("reason","个人昵称格式不正确!请输入20字符以内的昵称");
               //日志结果
                joinSystemRegisterlog.setStatus("406");
                //日志原因
               joinSystemRegisterlog.setOpertionmes("注册个人账号证件号"+nickName+"个人昵称格式不正确！");
               dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                return  resMap;
        }
        /*验证昵称是否已经存在*/
        if( checkUserNickName(nickName)>0){
            resMap.put("reason","该昵称已经存在,请重新输入!");
            resMap.put("state","406");
            //日志结果
            joinSystemRegisterlog.setStatus("406");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("注册个人账号证件号"+nickName+"个人昵称格式已存在！");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
            return  resMap;
        }


        /*校验  用户名，手机号，邮箱，学号，微信号是否重复*/
        Map<String,Object> checkMap = generalUserInfoMapper.checkGeneralUser(map);
        for (Map.Entry<String,Object> entry:checkMap.entrySet()
        ) {
            BigDecimal value = (BigDecimal)entry.getValue();
            if(value.compareTo(new BigDecimal(0))==1){
                String errorName ="";
                switch (entry.getKey()){
                    case "IDNUMBER":
                        errorName = "身份证";
                        break;
                    case "PHONE":
                        errorName = "手机号";
                        break;
                    case "EMAIL":
                        errorName = "邮箱";
                        break;
                    case "NAME":
                        errorName = "外派学号";
                        break;
                    case "NICKNAME":
                        errorName = "个人昵称";
                        break;
                }
                resMap.put("state","409");
                resMap.put("reason",errorName+"已存在！");

                //日志结果
                joinSystemRegisterlog.setStatus("409");
                //日志原因
                joinSystemRegisterlog.setOpertionmes("注册个人账号证件号"+idNumber+errorName+"已存在！");
                dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                return  resMap;
            }
        }
        String unionId = map.get("unionId") == null?"":map.get("unionId").toString();
        String userType = "00";
        if(!unionId.equals("")){
            if(wechatUserInfoMapper.selectWechatUserInfoByIdAndUserType(unionId,userType)!=null){
                resMap.put("state","409");
                resMap.put("reason","微信标识已存在");
                //日志结果
                joinSystemRegisterlog.setStatus("404");
                //日志原因
                joinSystemRegisterlog.setOpertionmes("注册个人账号证件号"+idNumber+"微信标识已存在！");
                dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                return  resMap;
            }
        }
        //注册个人用户  成功200
        try {
            String id = UUID.randomUUID().toString().replace("-","");
            String userId = UUID.randomUUID().toString().replace("-","");
            String password = map.get("password")==null?"":map.get("password").toString();
            String sendMsgPassword = password;
            //password = desObj.strEnc(password, key);
            password = MD5Format.MD5(password);
            map.put("password",password);
            map.put("id",id);
            map.put("userId",userId);
            //状态改成未激活
            map.put("activeFlag","1");
            map.put("IS_LABOUR_REAL","1");
            if(cardType.equals("111") && userInterfaceServer.certTwoData(realName,idNumber)){
                map.put("authLevel","3");
                map.put("IS_LABOUR_REAL","0");
            }
            //拿身份证注册的时候  需要根据身份证  获取生日等信息

            if("111".equals(cardType)){
                //通过身份证获取出生日期  以及 性别
                String[] birthAndSexByIdNo = commonUtil.getBirthAndSexByIdNo(idNumber);
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                map.put("gender",birthAndSexByIdNo[1]);
                map.put("dateofbirth",sf.parse(birthAndSexByIdNo[0]));

            }

            generalUserInfoMapper.insertPersionalUserLogin(map);
            generalUserInfoMapper.insertPersionalUserInfo(map);
            generalUserInfoMapper.insertPersionalUserExt(map);

            if(!unionId.equals("")){
                map.put("userType",userType);
                generalUserInfoMapper.insertWEchartInfo(map);
            }

            resMap.put("state","200");
            resMap.put("reason","注册成功");

            // 04-26 新增逻辑，如果外派学号不为空，则发送短信邮箱通知 ,由于邮箱内容太长，暂时取消了
//            if(map.get("ovestuId") != null && map.get("ovestuId") != ""){
//                sendMsg(phone,email,realName,sendMsgPassword);
//            }

            //日志结果
            joinSystemRegisterlog.setStatus("200");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("注册个人账号证件号"+idNumber+"成功");
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
            joinSystemRegisterlog.setOpertionmes("注册个人账号"+idNumber+"失败");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
        }



        return resMap;
    }

    @Override
    public Map generalUserLogin(Map map) {
        /*
         *
         * 1.先校验参数
         *
         * 2. 先去redis里面  key找
         *
         * 3 再去数据库里面找
         *
         * 4登陆成功 生成token
         *
         * 5.插入redis
         * */


        //返回结果
        Map resMap = new HashMap();
        String joinsysCode = map.get("sysuqid").toString();
        JoinSystemInfo sysInfo = new JoinSystemInfo();
        sysInfo.setJoinsysCode(joinsysCode);
        if(systemInfoService.checkSystemCode(joinsysCode) == null || !systemInfoService.checkSystemCode(joinsysCode)){
            resMap.put("state","404");
            resMap.put("reason","获取系统标识失败或不正确,请联系管理员！");
            return  resMap;
        }

        List<JoinSystemInfo> sysInfolist = dockedLogService.sysInfolist(sysInfo);
        if(sysInfolist==null || sysInfolist.size() == 0){
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
        joinSystemRegisterlog.setSysmethodName("对接系统个人用户登陆接口");
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
        List<Object> jsonList= new ArrayList<>();
        Set<String> keys=redisTemplate.keys(userName);
        jsonList=redisTemplate.opsForValue().multiGet(keys);

        /*确定是那种登陆方式*/
         if(isMobileNO(userName)){
            map.put("loginType","phone");
        }else if(isEmail(userName)){
            map.put("loginType","email");
        }else {
            map.put("loginType","idNumber");
        }

        //String mappassword = desObj.strEnc(map.get("password").toString(), key);
        String mappassword =  MD5Format.MD5(map.get("password").toString());
        map.put("password",mappassword);
      /*  if(jsonList.size()>0){
            Map userMap= JSONObject.toJavaObject((JSONObject)jsonList.get(0),Map.class);

            *//*1匹配密码*//*
            String password = userMap.get("password")==null?"":userMap.get("password").toString();

            if(mappassword.equals(password)){
                *//*登陆成功  生成token*//*
                String  token = tokenService.getToken(userName);




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

        }*/

        //登陆
        /*只查可用的，没有删除的  手机和邮箱是实名验证过的*/
        map.put("status","0");
        map.put("state","0");
        map.put("delFlag",0);

        map.put("emailIscheck",1);
        map.put("phoneIscheck",1);
        List<Map> list= generalUserInfoMapper.generalUserLogin(map);
        if(list.size()==0){
            map.put("loginType","userName");
            list= generalUserInfoMapper.generalUserLogin(map);
        }


        if(list.size() > 0){
            //登陆成功

            /*登陆成功  生成token*/
            /*普通用户
            #perUsertel  #perUseremail #perUsercard #perUsername#*/
            Map userMap = list.get(0);
            String token = tokenService.getToken(userName);
            generalUserInfoMapper.updateGeneralUserInfoByUserId(userMap);

            resMap.put("state","200");
            resMap.put("reason","登陆成功");
            resMap.put("token",token);


            //插入 redis
            /*#a_id_21#a_tel_123#a_email_12#a_name_21*/
            //String rkey = "#a_id_"+userMap.get("idnumber")+"#a_tel_"+userMap.get("phone")+"#a_email_"+userMap.get("email")+"#a_name_"+userMap.get("name");
            String rkey = userName;


            Map rvalue = new HashMap();
            rvalue.put("password",userMap.get("PASSWORD"));
            rvalue.put("status",userMap.get("STATUS"));
            rvalue.put("state",userMap.get("STATUS"));
            rvalue.put("del_flag",userMap.get("DEL_FLAG"));
            rvalue.put("userType","personal");
            rvalue.put("userId",userMap.get("USER_ID"));
            rvalue.put("gender",userMap.get("gender"));
            rvalue.put("dateOfBirth",userMap.get("date_of_birth"));



            redis.set(rkey,rvalue);

            //日志结果
            joinSystemRegisterlog.setStatus("200");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("个人登录接口用户名"+userName+"登陆成功");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);

        }else {
            resMap.put("state","406");
            resMap.put("reason","个人登录接口"+userName+"用户名或密码错误！");



            //日志结果
            joinSystemRegisterlog.setStatus("200");
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
    public Map updateGeneralUser(Map map) {
        Map resMap = new HashMap();
        String joinsysCode = map.get("sysuqid").toString();
        JoinSystemInfo sysInfo = new JoinSystemInfo();
        sysInfo.setJoinsysCode(joinsysCode);

        if(systemInfoService.checkSystemCode(joinsysCode) == null || !systemInfoService.checkSystemCode(joinsysCode)){
            resMap.put("state","404");
            resMap.put("reason","获取系统标识失败或不正确,请联系管理员！");
            return  resMap;
        }

        List<JoinSystemInfo> sysInfolist = dockedLogService.sysInfolist(sysInfo);
        if(sysInfolist == null || sysInfolist.size() == 0){
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

        boolean marriageNewTy = false;
        //政治面貌是否正确
        boolean politicalTy = false;
        //学历是否正确
        boolean lastSchoolTy = false;

        boolean cardTypeTy = false;
        //性别是否正确
        boolean genderTy = false;
        List<String> paramList = new ArrayList<String>();
        String marriageNew = map.get("maritalStatus")==null?"":map.get("maritalStatus").toString();
        String political = map.get("politicsStatus")==null?"":map.get("politicsStatus").toString();
        String lastSchool = map.get("lastDegree")==null?"":map.get("lastDegree").toString();
        String cardType = map.get("cardType") == null?"111":map.get("cardType").toString();
        if(!marriageNew.equals("")){
            paramList.add("marriage_new");
        }
        if(!political.equals("")){
            paramList.add("political");
        }
        if(!lastSchool.equals("")){
            paramList.add("last_school");
        }
        if(!cardType.equals("")){
            paramList.add("card_type");
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
                if(marriageNewTy == false && a.get("DICT_TYPE").equals("marriage_new")){
                    if(a.get("DICT_DATA").toString().indexOf(marriageNew)==-1){
                        resMap.put("state","404");
                        resMap.put("reason","婚姻状态不正确！");
                        //日志结果
                        joinSystemRegisterlog.setStatus("404");
                        return  resMap;
                    }else
                        marriageNewTy = true;
                    continue;


                }
                else if(politicalTy == false && a.get("DICT_TYPE").equals("political") ){

                    if(a.get("DICT_DATA").toString().indexOf(political)==-1){
                        resMap.put("state","404");
                        resMap.put("reason","政治面貌不正确！");
                        return  resMap;
                    }else
                         politicalTy = true;
                    continue;
                }
                else if(lastSchoolTy == false && a.get("DICT_TYPE").equals("last_school") ){
                    if(a.get("DICT_DATA").toString().indexOf(lastSchool)==-1){
                        resMap.put("state","404");
                        resMap.put("reason","最后学历不正确！");
                        return  resMap;
                    }else
                        lastSchoolTy = true;
                    continue;
                }
                else if(cardTypeTy == false && a.get("DICT_TYPE").equals("card_type")){

                    if(a.get("DICT_DATA").toString().indexOf(cardType)==-1){
                        resMap.put("state","406");
                        resMap.put("reason","证件类型格式不正确！");
                        return  resMap;
                    }else
                        cardTypeTy = true;
                    continue;
                }
            }

        }

        String phone = map.get("phone") == null?"":map.get("phone").toString();
        String email = map.get("email") == null?"":map.get("email").toString();
        String phoneIsCheck = map.get("phoneIsCheck") == null?"":map.get("phoneIsCheck").toString();
        String emailIsCheck = map.get("emailIsCheck") == null?"":map.get("emailIsCheck").toString();
        String userId = map.get("userId") == null?"":map.get("userId").toString();

        /*先通过  userID获取
         * 返回userid  和  三个版本号
         * */
        Map umapList = generalUserInfoMapper.getGeneralUserInfoByUserId(map);
            if(umapList.size() == 0){
                resMap.put("state","404");
                resMap.put("reason","目标账户不存在！");

                //日志结果
                joinSystemRegisterlog.setStatus("404");
                //日志原因
                joinSystemRegisterlog.setOpertionmes("修改个人用户标识"+userId+"目标不存在或已禁用！");
                dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                return  resMap;
            }
        map.putAll(umapList);

        String idNumber = map.get("idNumber") == null?"":map.get("idNumber").toString();
        /*检验身份证*/
        if(!"".equals(idNumber) && !idNumber.equals(map.get("OOLDIDNUMBER"))){
            if(!("111".equals(cardType) && CheckIDUtil.isIDNumber(idNumber))){
                resMap.put("state","406");
                resMap.put("reason","证件号格式不正确！");

                //日志结果
                joinSystemRegisterlog.setStatus("406");
                //日志原因
                joinSystemRegisterlog.setOpertionmes("注册个人账号证件号"+idNumber+"证件号格式不正确！");
                dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                return  resMap;
            }
            else if(generalUserInfoMapper.checkUserIDnumber(map) > 0 ){
                resMap.put("state","406");
                resMap.put("reason","证件号已存在！");

                //日志结果
                joinSystemRegisterlog.setStatus("406");
                //日志原因
                joinSystemRegisterlog.setOpertionmes("注册个人账号证件号"+idNumber+"证件号已存在！");
                dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                return  resMap;
            }
        }

        /*验证手机号*/
        //String countryCode = map.get("countryCode")==null?"86":map.get("countryCode").toString();
        if(!"".equals(phone) && !phone.equals(map.get("OOLDPHONE"))){
            if(!isMobileNO(phone)){
                resMap.put("state","406");
                resMap.put("reason","修改后手机格式不正确！");
                //日志结果
                joinSystemRegisterlog.setStatus("406");
                //日志原因
                joinSystemRegisterlog.setOpertionmes("修改个人用户标识"+userId+"修改后手机格式不正确！");
                dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                return resMap;
            }
            //实名后手机
            if(phoneIsCheck.equals("1")){
                if(generalUserInfoMapper.checkUserPhone(map) > 0){
                    resMap.put("state","409");
                    resMap.put("reason","手机号已存在！");
                    //日志结果
                    joinSystemRegisterlog.setStatus("409");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("修改个人用户标识"+userId+"手机号已存在");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                    return resMap;
                }
            }
        }
        if(!"".equals(email) && !email.equals(map.get("OOLDEMAIL"))){
            if(!isEmail(email)){
                /*验证邮箱*/
                resMap.put("state","406");
                resMap.put("reason","邮箱格式不正确！");

                //日志结果
                joinSystemRegisterlog.setStatus("406");
                //日志原因
                joinSystemRegisterlog.setOpertionmes("修改个人用户标识"+userId+"邮箱格式不正确");
                dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                return resMap;
            }
            if(emailIsCheck.equals("1")){
                if(generalUserInfoMapper.checkUserEmail(map) > 0){
                    resMap.put("state","409");
                    resMap.put("reason","修改后邮箱已存在！");

                    //日志结果
                    joinSystemRegisterlog.setStatus("409");
                    //日志原因
                    joinSystemRegisterlog.setOpertionmes("修改个人用户标识"+userId+"修改后邮箱已存在！");
                    dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                    return resMap;
                }
            }
        }

        String token = map.get("token") == null?"":map.get("token").toString();
        String mapName = "";
        if(!"".equals(token)){
            mapName = tokenService.getAccoutName(token);
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
        }
        try{
            generalUserInfoMapper.updateGeneralUserLogin(map);
            generalUserInfoMapper.updateGeneralUserInfo(map);
            generalUserInfoMapper.updateGeneralUserExtra(map);
            /*修改手机号    得先返回原来的手机号和邮箱和用户名，redis里面干掉
             * 修改邮箱
             * */
            // 获取修改后的用户信息
            PersonalUserInfoToRedis userId1 = generalUserInfoMapper.getUserInfoById(map.get("userId").toString());
            // 刷新redis
            flushRedisUserInfo(map,userId1);

            resMap.put("state","200");
            resMap.put("reason","更新成功");

            //日志结果
            joinSystemRegisterlog.setStatus("200");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("修改个人用户标识"+userId+"更新成功");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);

        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            resMap.put("state","500");
            resMap.put("reason","更新失败");

            //日志结果
            joinSystemRegisterlog.setStatus("500");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("修改个人用户标识"+userId+"更新失败");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
            return resMap;
            // 加入下行代码手动回滚
            // @Transactional 为方法加上事务,try catch 捕获到异常手动回滚事务
        }

        return resMap;
    }

    public void flushRedisUserInfo(Map map,PersonalUserInfoToRedis personalUserInfoToRedis){

        personalUserInfoToRedis.setUserType("personal");


        if(map.get("OOLDPHONE") !=null && redis.get(map.get("OOLDPHONE").toString())!=null ){
            redis.delete(map.get("OOLDPHONE").toString());
            personalUserInfoToRedis.setName(personalUserInfoToRedis.getPhone());
            redis.set(personalUserInfoToRedis.getPhone(),personalUserInfoToRedis,604800L);
        }
        if(map.get("OOLDEMAIL") !=null  && redis.get(map.get("OOLDEMAIL").toString())!=null ){
            redis.delete(map.get("OOLDEMAIL").toString());
            personalUserInfoToRedis.setName(personalUserInfoToRedis.getEmail());
            redis.set(personalUserInfoToRedis.getEmail(),personalUserInfoToRedis,604800L);
        }
        if(map.get("OOLDNAME")!=null  && redis.get(map.get("OOLDNAME").toString())!=null ){
            redis.delete(map.get("OOLDNAME").toString());
            personalUserInfoToRedis.setName(personalUserInfoToRedis.getUserName());
            redis.set(personalUserInfoToRedis.getUserName(),personalUserInfoToRedis,604800L);
        }
        if(map.get("OOLDIDNUMBER")!=null  && redis.get(map.get("OOLDIDNUMBER").toString())!=null ){
            redis.delete(map.get("OOLDIDNUMBER").toString());
            personalUserInfoToRedis.setName(personalUserInfoToRedis.getCardNo());
            redis.set(personalUserInfoToRedis.getCardNo(),personalUserInfoToRedis,604800L);
        }
        if(map.get("OOLDWECHAR_ID")!=null  && redis.get(map.get("OOLDWECHAR_ID").toString())!=null ){
            redis.delete( wechat_redis_key + map.get("OOLDWECHAR_ID").toString());
            personalUserInfoToRedis.setName(map.get("OOLDWECHAR_ID").toString());
            redis.set(map.get("OOLDWECHAR_ID").toString(),personalUserInfoToRedis,604800L);
        }

        if(map.get("OOLDALIPAY_ID")!=null  && redis.get(map.get("OOLDALIPAY_ID").toString())!=null ){
            redis.delete( Alipay_redis_key + map.get("OOLDALIPAY_ID").toString());
            personalUserInfoToRedis.setName(map.get("OOLDALIPAY_ID").toString());
            redis.set(map.get("OOLDALIPAY_ID").toString(),personalUserInfoToRedis,604800L);
        }
    }

    @Override
    @Transactional
    public Map registeUserGroup(Map map) {
        Map<String,Object> resMap = new HashMap();

        List<Map> list = new ArrayList<>();
        try{
            list =  map.get("userGroup")==null?new ArrayList<>():(List<Map>) map.get("userGroup");
            if(map.get("sysuqid") == null){
                resMap.put("state","404");
                resMap.put("reason","系统标识不能为空！");
                return  resMap;
            }
            else if(list.size() > 100){
                resMap.put("state","404");
                resMap.put("reason","注册用户注册数量超过单次注册上限！");
                return  resMap;
            }
        }catch (Exception e){
            resMap.put("state","500");
            resMap.put("reason","传参错误，请联系管理员！");
            return  resMap;
        }

        String joinsysCode = map.get("sysuqid").toString();
        JoinSystemInfo sysInfo = new JoinSystemInfo();
        sysInfo.setJoinsysCode(joinsysCode);

        if(joinsysCode.length() > 20 || isSpecicalString(joinsysCode) || !systemInfoService.checkSystemCode(joinsysCode)){
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
        joinSystemRegisterlog.setSysmethodId("126");
        //接口名称
        joinSystemRegisterlog.setSysmethodName("对接系统批量注册个人用户接口");
        joinSystemRegisterlog.setRealm(sysInfolist.get(0).getRealmPlace());
        joinSystemRegisterlog.setOpertionTime(new Date());

        String parameter = "";
        for (Object key : map.keySet()) {
            parameter+=key + ",";
        }
        joinSystemRegisterlog.setParameter(parameter);

        /*不符合标准的用户信息*/
        List<Map> errorList= new ArrayList<>();
        /*符合标准的用户信息*/
        List<Map> userList= new ArrayList<>();

        List<String> paramList = new ArrayList<String>() {
            {
                this.add("card_type");
            }
        };
        List<Map> disctDataList = dictDataMapper.selectDictDataByDictType(paramList);
        if(disctDataList.size() == 0){
            resMap.put("status","500");

            resMap.put("state","500");
            resMap.put("reason","程序错误！");
            return resMap;
        }
        /*所有国家代码*/
        Map codeMap=checkNationalityServer.selectNationalityAllCode(map);
        String allcode =  codeMap.get("THREECODE").toString();

        String lastIdNumberRes = "";
        for (Map userMap:list) {
            String phone = userMap.get("phone") == null?"":userMap.get("phone").toString();
            String email = userMap.get("email") == null?"":userMap.get("email").toString();
            String idNumber = userMap.get("idNumber") == null?"":userMap.get("idNumber").toString();
            String cardType = userMap.get("cardType") == null?"":userMap.get("cardType").toString();
            String nationality = userMap.get("nationality") == null?"":userMap.get("nationality").toString();
            String phoneIsCheck = userMap.get("phoneIsCheck") == null?"0":userMap.get("phoneIsCheck").toString();
            String emailIsCheck = map.get("emailIsCheck") == null?"":map.get("emailIsCheck").toString();
            //原因
            String reason = "";
            userMap.put("state","200");
            if(cardType.equals("")){
                /*验证邮箱*/
                reason += "身份证类型格式不正确！";
            }
            else if( !phoneIsCheck.equals("1") && !phoneIsCheck.equals("0")){
                /*验证邮箱*/
                reason += "手机实名标识格式不正确！";
            }
            else if(userMap.get("emailIsCheck")!=null && !userMap.get("emailIsCheck").toString().equals("1") && !userMap.get("emailIsCheck").toString().equals("0")){
                /*验证邮箱*/
                reason += "邮箱实名标识格式不正确！";
            }
            else if(userMap.get("userName") != null){
               if(("".equals(userMap.get("userName").toString()) || userMap.get("userName").toString().length() > 20) || isSpecicalString(userMap.get("userName").toString())){
                   /*验证邮箱*/
                   reason += "外派学号格式不正确！";
               }
            }
            else if(userMap.get("realName") == null || "".equals(userMap.get("realName").toString()) || userMap.get("realName").toString().length() > 1000 || isSpecicalString(userMap.get("realName").toString())){
                /*验证邮箱*/
                reason += "真实姓名格式不正确！";
            }
            /*验证手机号*/
            //String countryCode = userMap.get("countryCode")==null?"86":userMap.get("countryCode").toString();
            else if(phone.equals("") || phone.length() > 20 || (phoneIsCheck.equals("1") && isMobileNO(phone))){
                reason += "手机格式不正确！";
            }
            else if(email.equals("") || email.length()>50 || !isEmail(email)){
                /*验证邮箱*/
                reason += "邮箱格式不正确！";
            }
 /*通过业务获取
        要出查询的字典类型字典类型
        身份证类型，国籍
        */
            //身份证类型是否正确
            boolean cardTypeTy = false;
            for (Map a:disctDataList) {
                if(cardTypeTy == false && a.get("DICT_TYPE").equals("card_type")){
                    if(a.get("DICT_DATA").toString().indexOf(cardType)==-1){
                        reason += "证件类型格式不正确！";
                    }else
                        cardTypeTy = true;
                    continue;
                }
            }

            if(nationality.equals("") || allcode.indexOf(nationality) == -1){
                reason += "国籍格式不正确！";
            }

            else if(! ("111".equals(cardType) && CheckIDUtil.isIDNumber(idNumber)) ){
                reason += "证件号格式不正确！";
            }




            /*校验  用户名，手机号，邮箱，学号是否重复*/
            Map<String,Object> checkMap = generalUserInfoMapper.checkGeneralUser(userMap);
            boolean feildType =true;
            for (Map.Entry<String,Object> entry:checkMap.entrySet()
            ) {
                //正常标识
                BigDecimal value = (BigDecimal)entry.getValue();
                if( feildType == true && value.compareTo(new BigDecimal(0))==1){
                    String errorName ="";
                    switch (entry.getKey()){
                        case "IDNUMBER":
                            errorName = "证件号";
                            break;
                        case "PHONE":
                            errorName = "手机号";
                            break;
                        case "EMAIL":
                            errorName = "邮箱";
                            break;
                        case "NAME":
                            errorName = "用户名";
                            break;
                    }
                    reason += errorName+"已存在！";
                    feildType=false;
                    break;
                }
            }
            if(!feildType || !userMap.get("state").equals("200")){
                userMap.put("reason",reason);
                errorList.add(userMap);
            }

            else {
                lastIdNumberRes += idNumber+",";
                String id = UUID.randomUUID().toString().replace("-","");
                String userId = UUID.randomUUID().toString().replace("-","");
                userMap.put("id",id);
                userMap.put("userId",userId);
                userMap.put("activeFlag","01");
                String password = userMap.get("password").toString();
              //  userMap.put("password",desObj.strEnc(password, key));
                userMap.put("password",MD5Format.MD5(password));
                userMap.put("IS_LABOUR_REAL","1");
                if(cardType.equals("111") && userInterfaceServer.certTwoData(userMap.get("realName").toString(),idNumber)){
                    userMap.put("authLevel","3");
                    userMap.put("IS_LABOUR_REAL","0");
                }
                if("111".equals(cardType)){
                    //通过身份证获取出生日期  以及 性别
                    String[] birthAndSexByIdNo = commonUtil.getBirthAndSexByIdNo(idNumber);
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                    userMap.put("gender",birthAndSexByIdNo[1]);
                    try {
                        userMap.put("dateofbirth",sf.parse(birthAndSexByIdNo[0]));
                    } catch (ParseException e) {
                        resMap.put("state","500");
                        resMap.put("reason","获取生日等信息异常");
                        joinSystemRegisterlog.setStatus("500");
                        //日志原因
                        joinSystemRegisterlog.setOpertionmes("批量注册个人用户证件号"+lastIdNumberRes+"更新失败");
                        dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                        return resMap;
                    }

                }
                userList.add(userMap);
            }
        }
        if(userList.size()>0){
            try{
                generalUserInfoMapper.registeUserGroup(userList);
                generalUserInfoMapper.registeUserInfoGroup(userList);
                generalUserInfoMapper.registeUserExtGroup(userList);

            }catch (Exception e){
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                resMap.put("state","500");
                resMap.put("reason","注册失败");

                //日志结果
                joinSystemRegisterlog.setStatus("500");
                //日志原因
                joinSystemRegisterlog.setOpertionmes("批量注册个人用户证件号"+lastIdNumberRes+"更新失败");
                dockedLogService.sysLogaddSave(joinSystemRegisterlog);
                return resMap;
            }
        }

        if(errorList.size()>0){
            resMap.put("userGroup",errorList);
        }else {
            resMap.put("state","200");
            resMap.put("reason","注册成功！");

            //日志结果
            joinSystemRegisterlog.setStatus("200");
            //日志原因
            joinSystemRegisterlog.setOpertionmes("批量注册个人用户证件号"+lastIdNumberRes+"注册成功");
            dockedLogService.sysLogaddSave(joinSystemRegisterlog);
        }


        resMap.put("successNum",userList.size());
        resMap.put("errorNum",errorList.size());
        return resMap;
    }

    @Override
    public Map showUserStatusGroup(Map maps) {
        Map<String,Object> resMap = new HashMap();



        String joinsysCode = maps.get("sysuqid") == null?"":maps.get("sysuqid").toString();
        JoinSystemInfo sysInfo = new JoinSystemInfo();
        sysInfo.setJoinsysCode(joinsysCode);
        if(joinsysCode.equals("") || joinsysCode.length()>20 || isSpecicalString(joinsysCode) || !systemInfoService.checkSystemCode(joinsysCode)){
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
        joinSystemRegisterlog.setSysmethodId("127");
        //接口名称
        joinSystemRegisterlog.setSysmethodName("对接系统批量查询个人用户接口");
        joinSystemRegisterlog.setRealm(sysInfolist.get(0).getRealmPlace());
        joinSystemRegisterlog.setOpertionTime(new Date());
        String parameter = "";
        for (Object key : maps.keySet()) {
            parameter+=key + ",";
        }
        joinSystemRegisterlog.setParameter(parameter);

        List<String> list = new ArrayList<>();
        try{
            list = maps.get("userGroup")==null?new ArrayList<>(): (List<String>) maps.get("userGroup");
            if(list.size() > 100){
                resMap.put("state","406");
                resMap.put("reason","查询用户数量超过单次查询上限！");
                return  resMap;
            }else if(list.size() == 0){
                resMap.put("state","404");
                resMap.put("reason","查询用户信息不能为空！");
                return  resMap;
            }
        }catch (Exception e){
            resMap.put("state","500");
            resMap.put("reason","传参错误，请联系管理员！");
            return  resMap;
        }




        List<Map> userMap = new ArrayList<>();
        for (String userName:list) {
            /*先找出不存在的用户*/
            if(userName != null && userName.length() < 50){
                Map map = new HashMap();
                if(CheckIDUtil.isIDNumber(userName)){
                    map.put("loginType","idNumber");
                }else if(isMobileNO(userName)){
                    map.put("loginType","phone");
                }else if(isEmail(userName)){
                    map.put("loginType","email");
                }else {
                    map.put("loginType","idNumber");
                }
                map.put("userName",userName);
                userMap.add(map);
            }
        }
        List<Map> zhengchanglist = generalUserInfoMapper.showUserStatusGroup(userMap);
        List<String> errorlist = new ArrayList<>();
        List<String> zglist = new ArrayList<>();
        for (Map a:zhengchanglist) {
            zglist.add(a.get("USERNAME").toString());
        }

        errorlist.addAll(zglist);

        //得到所有  用户名不在在的集合
        list.removeAll(errorlist);

        List<Map>  errorList = new ArrayList<>();
        for (String ss:list) {
            HashMap map = new HashMap();
            map.put("userName",ss);
            map.put("isExist","不存在");
            errorList.add(map);
        }

        //合并   账号不存在和存在的集合
        zhengchanglist.addAll(errorList);
        resMap.put("userGroup",zhengchanglist);
        return  resMap;
    }

    /**
    * @Description 发送短信邮箱通知，暂时写死了
    * @Parm
    * @return
    **/
    public void sendMsg(String phone, String emil,String name ,String password){
        Map map = new HashMap();
//        map.put("mailTo",emil);
//        map.put("phoneTo",phone);
//        map.put("sendType","1");
//        map.put("flag","flag");
//        map.put("subjectTitle","登录：发送初始密码");
//        map.put("emailContent",msgContent(name, password ,"mail"));
//        map.put("phoneContent",msgContent(name, password ,"phone"));

        map.put("sendType", "1");
        map.put("subjectTitle", "统一用户注册系统信息发送");
        map.put("phoneTo", phone);
        map.put("phoneContent",msgContent(name, password ,"phone"));
        map.put("mailTo", emil);
        map.put("flag","flag");
        map.put("name",name);
        map.put("password",password);

        boolean b = remoteMsgSendService.SendMsg(map);
    }

    public String msgContent(String name,String password,String type){
        if(type.equals("mail")){
            return name+"： <br/> 您好，<br/>\n" +
                    "感谢您选择教育部留学服务中心作为办理派出手续的服务机构。<br/>\n" +
                    "您的初始登录密码为："+password+"，请使用CSC学号（12位数字）作为<br/>\n" +
                    "用户名登录留学政务服务平台公派留学板块办理派出手续。<br/><hr>\n" +
                    "<strong>此邮件为自动发送邮件，咨询邮箱为：chuguo1@cscse.edu.cn。<br/>\n" +
                    "教育部留学服务中心出国处</strong>\n" +
                    "<br/>各位留学人员：<br/>\n" +
                    "   &emsp;大家好！当前，全球新冠肺炎疫情形势依然十分严峻，部分国家安全形势复杂多变，国际旅行仍存在较大风险。特别提醒广大留学人员，请在充分做好安全风险评估后，办理出国留学派出手续。<br/>\n" +
                    "受教育部委托，教育部留学服务中心负责开展“平安留学”行前培训工作。根据教育部关于做好国家公派留学人员行前培训工作的指示，每位国家公派留学人员应于派出前接受培训。对于受疫情影响，未能在派出前参加线下和线上培训的公派留学人员，请按照通知要求自学完成留学安全、心理、健康、领保等培训内容。<br/>\n" +
                    "&emsp;&emsp;留学梦报国志<br/>\n" +
                    "&emsp;&emsp;航天英雄杨利伟：无论你在哪里，祖国都和你在一起http://www.cgpx.org/palx/406280/407639/index.html<br/>\n" +
                    " \n" +
                    "&emsp;&emsp;留学安全<br/>\n" +
                    "&emsp;&emsp;中国人民公安大学教授 王大伟：出国留学平安讲座<br/>\n" +
                    "&emsp;&emsp;第一讲：http://www.cgpx.org/palx/spkt/aqff/487197/index.html<br/>\n" +
                    "&emsp;&emsp;第二讲：http://www.cgpx.org/palx/spkt/aqff/487193/index.html<br/>\n" +
                    "&emsp;&emsp;第三讲：http://www.cgpx.org/palx/spkt/aqff/487189/index.html<br/>\n" +
                    " \n" +
                    "&emsp;&emsp;留学心理<br/>\n" +
                    "&emsp;&emsp;清华大学教授 彭凯平：适应文化差异给留学带来的心理冲突、战胜留学心理问题的方法<br/>\n" +
                    "&emsp;&emsp;第一讲：http://www.cgpx.org/palx/spkt/xljk/487206/index.html<br/>\n" +
                    "&emsp;&emsp;第二讲：http://www.cgpx.org/palx/spkt/xljk/487203/index.html<br/>\n" +
                    "&emsp;&emsp;第三讲：http://www.cgpx.org/palx/spkt/xljk/487200/index.html<br/>\n" +
                    " \n" +
                    "&emsp;&emsp;留学健康<br/>\n" +
                    "&emsp;&emsp;北京大学第三医院院长、中国工程院院士乔杰：如何健康留学、如何成功留学！<br/>\n" +
                    "&emsp;&emsp;http://www.cgpx.org/palx/spkt/rcbj/487209/index.html<br/>\n" +
                    " \n" +
                    "&emsp;&emsp;领事保护<br/>\n" +
                    "&emsp;&emsp;请登陆中国领事服务网http://cs.mfa.gov.cn/ 领事保护板块http://cs.mfa.gov.cn/zggmzhw/lsbh/ 学习领事保护相关知识。<br/>\n" +
                    " \n" +
                    "&emsp;&emsp;疫情防控<br/>\n" +
                    "&emsp;&emsp;当前，海外疫情形势依然严峻， 附上由教育部留学服务中心组织专家精心编制的《海外留学人员新型冠状病毒肺炎防控指南》：http://www.cgpx.org/palx/uiFramework/commonResource/zip/zip/zip/pdfjs-1/web/viewer.html?file=/palx/resource/cms/article/405798/473625/2020120813323149052.pdf ，希望广大留学人员保持高度的警惕性，不断加强日常科学防护，增强安全防范意识。<br/>\n" +
                    "&emsp;如有关于线上培训的相关问题，请咨询xqpx@cscse.edu.cn。祝各位留学人员学有所成，健康平安！<br/>\n";
        }
        else if(type.equals("phone")){
            return "【教留服】您的初始登录密码为："+password+",请使用CSC学号（12位数字）作为用户名登录留学政务服务平台公派留学板块办理派出手续。";
        }
        return null;
    }


    /*判断手机号*/
    public  boolean isMobileNO(String mobiles) {
        String telRegex = "[1][34578]\\d{9}";
        // "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else
            return mobiles.matches(telRegex);
    }
    /*判断手机号*/
    public  boolean isEmail(String mobiles) {
        String telRegex = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        // "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else
            return mobiles.matches(telRegex);
    }
    /*判断个人昵称*/
    public static  boolean isNickNameString(String nick){
        String nameRegex = "^[\u4E00-\u9FA5A-Za-z0-9_]+$";
        if (TextUtils.isEmpty(nick)){
            return false;
        }else
            return nick.matches(nameRegex);
    }


    /*特殊字符*/
    public  boolean isSpecicalString(String str) {
        // "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String regEx="[^%&',;=?$<>!@#^*()_\\-+:\\x22]+";
        java.util.regex.Pattern p=java.util.regex.Pattern.compile(regEx);
        Matcher m=p.matcher(str);
        if(!m.matches()) {
           return true;
        }else {
            return false;
        }
    }



    public static void main(String[] args) {
       boolean flag= isNickNameString("测试昵称1");
//        String telRegex = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
//        System.out.println("wzp406599321@163.com".matches(telRegex));
//
        String cardType = "111";
        String idNumber = "13013219940616001X";

        System.out.println("111".equals(cardType) && !CheckIDUtil.isIDNumber(idNumber));

//        if(("00".equals(cardType) && !CheckIDUtil.isIDNumber(idNumber)) || (("01".equals(cardType) && !CardsUtil.isPassPortCard(idNumber))) || (("02".equals(cardType) && !CardsUtil.isTWCard(idNumber))) || (("03".equals(cardType) && !CardsUtil.isHMCard(idNumber)))){
//            System.out.println("11");
//        }
//        if(!(("00".equals(cardType) && CheckIDUtil.isIDNumber(idNumber)) || ("01".equals(cardType) && CardsUtil.isPassPortCard(idNumber)) || ("02".equals(cardType) && CardsUtil.isTWCard(idNumber)) || ("03".equals(cardType) && CardsUtil.isHMCard(idNumber)) )){
//            System.out.println("222");
//        }
//         if("810000199408230021".matches("^810000(?:19|20)\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])\\d{3}[\\dX]$")
//       ){
//            System.out.println("1ss1");
//        }
        // "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
       // System.out.println("14 93346916".matches(telRegex));


       /* boolean ss = CheckIDUtil.isIDNumber("101032193107120100");
        System.out.println("含有特殊字符");
        String str = "Milton Henry Shirley";
        String regEx="^[\\u4E00-\\u9FA5A-Za-z0-9]+$";
        String regEx2="^[\\u4E00-\\u9FA5A-Za-z·\\s]+$";
        String regE2="[^%&',;=?$<>!@#^*()_\\-+:\\x22]+";
        java.util.regex.Pattern p=java.util.regex.Pattern.compile(regE2);
        Matcher m=p.matcher(str);
        if(!m.matches()) {
            System.out.println("含有特殊字符");
        }else {
            System.out.println("没有特殊字符");
        }*/
    }

}


