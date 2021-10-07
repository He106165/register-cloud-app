package com.censoft.otherlogin.orgenUser.service;

import com.censoft.common.constant.Constants;
import com.censoft.common.log.enums.BusinessType;
import com.censoft.common.log.enums.OperatorType;
import com.censoft.common.log.publish.PublishFactory;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.otherlogin.orgenUser.domain.OrgenUserInfo;
import com.censoft.system.domain.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ToRedisOrgService {

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

    public Map<String, Object> saveToRides(String token,String username,String JoinsysCode,  OrgenUserInfo userInfoById ,Integer time)
    {
        // 保存或更新用户token
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", username);
        map.put("token", token);
        //个人用户
        map.put("userType", "organ");
        map.put("expire", time);
        map.put("systemRetUrl",null);
        if(JoinsysCode!=null && JoinsysCode!=""){
            String systemRetUrl = toJoinSystemService.getSystemRetUrl(JoinsysCode);
            map.put("systemRetUrl",systemRetUrl);
        }
        userInfoById.setUserType("organ");
        userInfoById.setName(username);
        redis.set(username, userInfoById, time);

        PublishFactory.recordLogininfor(username,
                Constants.LOGIN_SUCCESS,
                "success",
                BusinessType.LOGIN.toString(),userInfoById.getUserId(), OperatorType.ORGAN.toString()
        );

        return map;
    }

    public Map<String, Object> getToRedis(String username,String token){
        OrgenUserInfo personalUserInfo = redis.get(username, OrgenUserInfo.class);
        if (personalUserInfo != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userId", username);
            map.put("token", token);
            //个人用户
            map.put("userType", "organ");
            map.put("expire", EXPIRE);
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

    public OrgenUserInfo getUserInfo(String redisKey){
       return redis.get(redisKey,OrgenUserInfo.class);
    }

}
