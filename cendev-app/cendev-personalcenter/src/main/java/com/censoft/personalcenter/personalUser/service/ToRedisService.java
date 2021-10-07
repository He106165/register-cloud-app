package com.censoft.personalcenter.personalUser.service;

import com.censoft.common.constant.Constants;
import com.censoft.common.json.JSONObject;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.system.domain.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToRedisService {

    @Autowired
    private RedisUtils          redis;


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



    /**
    * @Description 更新redis中用户的信息
    * @Parm
    * @return
    **/
    public void flashUserInfo(String key,Object object){
        redis.set(key, object, EXPIRE);
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

    public void setToReids(String key,Object ob,long expire){
        redis.set(key,ob,expire);
    }


    public JSONObject getUserInfoJson(String redisKey){
        return redis.get(redisKey,JSONObject.class);
    }

}
