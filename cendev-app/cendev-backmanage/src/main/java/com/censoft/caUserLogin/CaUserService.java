package com.censoft.caUserLogin;

import com.censoft.common.constant.Constants;
import com.censoft.common.log.enums.BusinessType;
import com.censoft.common.log.enums.OperatorType;
import com.censoft.common.log.publish.PublishFactory;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.system.domain.SysUser;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CaUserService {

    @Autowired
    private RedisUtils redis;


    @Autowired

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

    public Map<String, Object> saveToRides(String token, JSONObject jsonObject,OperatorType operatorType)
    {

        // 保存或更新用户token
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token", token);
        //个人用户
        map.put("expire", EXPIRE);
        String loginName=jsonObject.get("loginName").toString();
        redis.set(Constants.ACCESS_TOKEN + token, jsonObject, EXPIRE);
        PublishFactory.recordLogininfor(loginName,
                Constants.LOGIN_SUCCESS,
                "success",
                BusinessType.LOGIN.toString(),jsonObject.get("userId").toString(), operatorType.toString()
        );
        return map;
    }

    public JSONObject getToRedis(String key){
        if(key==null) return null;
        String s = redis.get(key);

        return redis.get(key,JSONObject.class);
    }
}
