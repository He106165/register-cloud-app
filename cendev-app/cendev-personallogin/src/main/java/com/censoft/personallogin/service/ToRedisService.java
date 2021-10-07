package com.censoft.personallogin.service;

import com.censoft.common.constant.Constants;
import com.censoft.common.json.JSONObject;
import com.censoft.common.log.enums.BusinessType;
import com.censoft.common.log.enums.OperatorType;
import com.censoft.common.log.publish.PublishFactory;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.personallogin.domain.PersonalUserInfo;
import com.censoft.system.domain.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ToRedisService {

    @Autowired
    private RedisUtils          redis;

    @Autowired
    private ToJoinSystemService toJoinSystemService;

    /**
     * 12小时后过期
     */
    private final static long   EXPIRE        = 12 * 60 * 60;

    private final static String ACCESS_TOKEN  = Constants.ACCESS_TOKEN;

    private final static String ACCESS_USERID = Constants.ACCESS_USERID;

    private final static String USER_PERMS = Constants.USER_PERMS;

    public SysUser queryByToken(String token)
    {
        return redis.get(ACCESS_TOKEN + token, SysUser.class);
    }

    public Map<String, Object> saveToRides(String token,String username,String joinsysCode,  PersonalUserInfo userInfoById ,Integer time)
    {
        // 保存或更新用户token
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", username);
        map.put("token", token);
        //个人用户
        map.put("userType", "personal");
        map.put("expire", time);
        map.put("name", userInfoById.getUserName());
        map.put("systemRetUrl",null);
        //查询应用系统的返回url
        if(joinsysCode!=null && joinsysCode!=""){
            String systemRetUrl = toJoinSystemService.getSystemRetUrl(joinsysCode);
            map.put("systemRetUrl",systemRetUrl);
        }
        userInfoById.setUserType("personal");
        userInfoById.setName(username);
        redis.set(username, userInfoById, time);

        PublishFactory.recordLogininfor(username,
                Constants.LOGIN_SUCCESS,
                "success",
                BusinessType.LOGIN.toString(),userInfoById.getUserId(), OperatorType.PERSONAL.toString()
        );

        return map;
    }


    /**
    * @Description 更新redis中用户的信息
    * @Parm
    * @return
    **/
    public void flashUserInfo(String key,Object object){
        redis.set(key, object, EXPIRE);
    }

    public Map<String, Object> getToRedis(String username,String token){
        PersonalUserInfo personalUserInfo = redis.get(username, PersonalUserInfo.class);
        if (personalUserInfo != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userId", username);
            map.put("token", token);
            //个人用户
            map.put("userType", "personal");
            map.put("expire", EXPIRE);
            map.put("name", personalUserInfo.getUserName());
            return map;
        }
        return null;
    }

    public void expireToken(long userId)
    {
        String token = redis.get(ACCESS_USERID + userId);
        if (StringUtils.isNotBlank(token))
        {
            redis.delete(ACCESS_USERID + userId);
            redis.delete(ACCESS_TOKEN + token);
            redis.delete(USER_PERMS + ":"+userId);
        }
    }

    /**
    * @Description redis添加信息
    * @Parm k v
    * @return
    **/
    public void setToReids(String key,Object ob,long expire){
        redis.set(key,ob,expire);
    }

    public void setToReids(String key,Object ob){
        redis.set(key,ob);
    }

    public PersonalUserInfo getPersonUserInfo(String redisKey){
       return redis.get(redisKey,PersonalUserInfo.class);
    }

    public JSONObject getUserInfoJson(String redisKey){
        return redis.get(redisKey,JSONObject.class);
    }


    public String getForRedis(String key){
        return redis.get(key);
    }
}
