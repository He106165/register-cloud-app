package com.censoft.userregister.service.impl;

import com.censoft.cendevbackmanage.feign.RemoteMsgSendService;
import com.censoft.userregister.domain.PhoneEmailBo;
import com.censoft.userregister.service.SmsService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @创建人:wqgeng
 * @创建时间:2020-11-1710:27
 * @描述:短信验证信息实现类
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {
    //个人注册
    private static final String prefix_1 = "smg:phone:";
    private static final String prefix_2 = "smg:email:";
    //机构注册
    private static final String prefix_3 = "smg:inland:";
    private static final String prefix_4 = "smg:overseas:";
    private static final String prefix_5 = "smg:schUnion:";
    //忘记密码
    private static final String prefix_6 = "smg:phonePassword:";
    private static final String prefix_7 = "smg:emailPassword:";
    private static final String prefix_8 = "smg:orgenForgetemailPassword:";


    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RemoteMsgSendService remoteMsgSendService;

    /**
     * @Author wqgeng
     * @Description 实现短信发送功能
     * @Date 2020/11/25 9:32
     * @Param [map]
     * @returns booleans
     **/
    @Override
    public boolean send(Map<String, String> map) {
        try {
            //生成6位的随机数
            String validateCode = generateCaptcha();
            //手机、短信同时发送手机号
            String phoneAndEmailCode = generateCaptcha();
            if (StringUtils.isNotBlank(map.get("phone"))) {
                map.put("sendType", "3");
                map.put("phoneTo", map.get("phone"));
                map.put("phoneContent", "验证码为: " + validateCode + ",您正在注册成为新用户,感谢您的支持!【教留服】");
                ValueOperations forValue = redisTemplate.opsForValue();
                String key = this.getPhoneKey(map.get("phone"));
                forValue.set(key, validateCode);
                redisTemplate.expire(key, 5, TimeUnit.MINUTES);
            } else if (StringUtils.isNotBlank(map.get("email"))) {
                map.put("sendType", "2");
                map.put("mailTo", map.get("email"));
                map.put("emailContent", "验证码为: " + validateCode + ",您正在注册成为新用户,感谢您的支持!【教留服】");
                ValueOperations forValue = redisTemplate.opsForValue();
                String key = this.getEmailKey(map.get("email"));
                forValue.set(key, validateCode);
                redisTemplate.expire(key, 5, TimeUnit.MINUTES);
            } else if (StringUtils.isNotBlank(map.get("phone")) && StringUtils.isNotBlank(map.get("email"))) {
                map.put("sendType", "1");
                map.put("mailTo", map.get("email"));
                map.put("phoneTo", map.get("phone"));
                map.put("emailContent", "验证码为: " + validateCode + ",您正在注册成为新用户,感谢您的支持!【教留服】");
                map.put("phoneContent", "验证码为: " + phoneAndEmailCode + ",您正在注册成为新用户,感谢您的支持!【教留服】");
                ValueOperations forValue = redisTemplate.opsForValue();
                String phonekey = this.getPhoneKey(map.get("phone"));
                String emailkey = this.getEmailKey(map.get("email"));
                forValue.set(phonekey, validateCode);
                forValue.set(emailkey, validateCode);
                redisTemplate.expire(phonekey, 5, TimeUnit.MINUTES);
                redisTemplate.expire(emailkey, 5, TimeUnit.MINUTES);
            } else {
                return false;
            }
            map.put("subjectTitle", "统一用户注册系统信息发送");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("填写参数有误");
        }
        //发送短信参数
        return remoteMsgSendService.SendMsg(map);
    }

    //机构注册短信发送
    @Override
    public boolean ogenSend(Map<String, String> map) {
        try {
            //生成6位的随机数
            String validateCode = generateCaptcha();
            if (map.get("orgenFlag").equals("inland")) {
                map.put("sendType", "2");
                //将联系人手机号设置为验证码接受者
                map.put("mailTo", map.get("hostpersionEmail"));
                map.put("emailContent", "验证码为: " + validateCode + ",您正在注册成为新用户,感谢您的支持!【教留服】");
                ValueOperations forValue = redisTemplate.opsForValue();
                String key = this.inlandKey(map.get("hostpersionEmail"));
                forValue.set(key, validateCode);
                redisTemplate.expire(key, 5, TimeUnit.MINUTES);
            } else if (map.get("orgenFlag").equals("overseas")) {
                map.put("sendType", "2");
                //将联系人手机号设置为验证码接受者
                map.put("mailTo", map.get("hostpersionEmail"));
                map.put("emailContent", "验证码为: " + validateCode + ",您正在注册成为新用户,感谢您的支持!【教留服】");
                ValueOperations forValue = redisTemplate.opsForValue();
                String key = this.overseasKey(map.get("hostpersionEmail"));
                forValue.set(key, validateCode);
                redisTemplate.expire(key, 5, TimeUnit.MINUTES);
            } else if (map.get("orgenFlag").equals("schUnion")) {
                map.put("sendType", "2");
                //将联系人手机号设置为验证码接受者
                map.put("mailTo", map.get("hostpersionEmail"));
                map.put("emailContent", "验证码为: " + validateCode + ",您正在注册成为新用户,感谢您的支持!【教留服】");
                ValueOperations forValue = redisTemplate.opsForValue();
                String key = this.schUnionKey(map.get("hostpersionEmail"));
                forValue.set(key, validateCode);
                redisTemplate.expire(key, 5, TimeUnit.MINUTES);
            } else {
                return false;
            }
            map.put("subjectTitle", "统一用户注册系统信息发送");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("填写参数有误");
        }
        //发送短信参数
        return remoteMsgSendService.SendMsg(map);
    }

    //用于找回密码
    @Override
    public boolean findPasswordMsg(Map<String, String> map) {
        try {
            //生成6位的随机数
            String validateCode = generateCaptcha();
            //手机、短信同时发送手机号
            String phoneAndEmailCode = generateCaptcha();
            if (StringUtils.isNotBlank(map.get("phone")) && ("1").equals(map.get("msgFlag"))) {
                map.put("sendType", "3");
                map.put("phoneTo", map.get("phone"));
                map.put("phoneContent", "验证码为: " + validateCode + ",您正在修改密码,感谢您的支持!【教留服】");
                ValueOperations forValue = redisTemplate.opsForValue();
                String key = this.getPhonePasswordKey(map.get("phone"));
                forValue.set(key, validateCode);
                redisTemplate.expire(key, 5, TimeUnit.MINUTES);
            } else if (StringUtils.isNotBlank(map.get("email")) && ("2").equals(map.get("msgFlag"))) {
                map.put("sendType", "2");
                map.put("mailTo", map.get("email"));
                map.put("emailContent", "验证码为: " + validateCode + ",您正在修改密码,感谢您的支持!【教留服】");
                ValueOperations forValue = redisTemplate.opsForValue();
                String key = this.getEmailPasswordKey(map.get("email"));
                forValue.set(key, validateCode);
                redisTemplate.expire(key, 5, TimeUnit.MINUTES);
            } else if (StringUtils.isNotBlank(map.get("phone")) && StringUtils.isNotBlank(map.get("email")) && ("3").equals(map.get("msgFlag"))) {
                map.put("sendType", "1");
                map.put("mailTo", map.get("email"));
                map.put("phoneTo", map.get("phone"));
                map.put("emailContent", "验证码为: " + validateCode + ",您正在修改密码,感谢您的支持!【教留服】");
                map.put("phoneContent", "验证码为: " + phoneAndEmailCode + ",您正在修改密码,感谢您的支持!【教留服】");
                ValueOperations forValue = redisTemplate.opsForValue();
                String phonekey = this.getPhonePasswordKey(map.get("phone"));
                String emailkey = this.getEmailPasswordKey(map.get("email"));
                forValue.set(phonekey, validateCode);
                forValue.set(emailkey, validateCode);
                redisTemplate.expire(phonekey, 5, TimeUnit.MINUTES);
                redisTemplate.expire(emailkey, 5, TimeUnit.MINUTES);
            } else {
                return false;
            }
            map.put("subjectTitle", "统一用户注册系统信息发送");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //发送短信参数
        return remoteMsgSendService.SendMsg(map);
    }

    //通过人工方式找回密码的方式 发送短信
    @Override
    public boolean artificialFindPasswordMsg(Map<String, String> map) {
        try {
            //生成6位的随机数
            String validateCode = generateCaptcha();
            //手机、短信同时发送手机号
            String phoneAndEmailCode = generateCaptcha();
            if (StringUtils.isNotBlank(map.get("phone")) && ("1").equals(map.get("msgFlag"))) {
                map.put("sendType", "3");
                map.put("phoneTo", map.get("phone"));
                map.put("phoneContent", "验证码为: " + validateCode + ",您正在申请人工找回密码并验证手机号!【教留服】");
                ValueOperations forValue = redisTemplate.opsForValue();
                String key = this.getPhonePasswordKey(map.get("phone"));
                forValue.set(key, validateCode);
                redisTemplate.expire(key, 5, TimeUnit.MINUTES);
            } else if (StringUtils.isNotBlank(map.get("email")) && ("2").equals(map.get("msgFlag"))) {
                map.put("sendType", "2");
                map.put("mailTo", map.get("email"));
                map.put("emailContent", "验证码为: " + validateCode + ",您正在申请人工找回密码并验证电子邮箱。教育部留学服务中心");
                ValueOperations forValue = redisTemplate.opsForValue();
                String key = this.getEmailPasswordKey(map.get("email"));
                forValue.set(key, validateCode);
                redisTemplate.expire(key, 5, TimeUnit.MINUTES);
            } else if (StringUtils.isNotBlank(map.get("phone")) && StringUtils.isNotBlank(map.get("email")) && ("3").equals(map.get("msgFlag"))) {
                map.put("sendType", "1");
                map.put("mailTo", map.get("email"));
                map.put("phoneTo", map.get("phone"));
                map.put("emailContent", "验证码为: " + validateCode + ",您正在申请人工找回密码并验证电子邮箱。教育部留学服务中心");
                map.put("phoneContent", "验证码为: " + phoneAndEmailCode + ",您正在申请人工找回密码并验证手机号!【教留服】");
                ValueOperations forValue = redisTemplate.opsForValue();
                String phonekey = this.getPhonePasswordKey(map.get("phone"));
                String emailkey = this.getEmailPasswordKey(map.get("email"));
                forValue.set(phonekey, validateCode);
                forValue.set(emailkey, validateCode);
                redisTemplate.expire(phonekey, 5, TimeUnit.MINUTES);
                redisTemplate.expire(emailkey, 5, TimeUnit.MINUTES);
            } else {
                return false;
            }
            map.put("subjectTitle", "统一用户注册系统信息发送");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //发送短信参数
        return remoteMsgSendService.SendMsg(map);
    }

    /**
     * @param map codeMsg: 信息类型  1：手机号，邮件同时发送  2：邮件；  3：手机号；
     * @return
     */
    @Override
    public boolean sendMsgCount(Map<String, String> map) {
        //生成6位的随机数
        String validateCode = generateCaptcha();
        if (StringUtils.isNotBlank(map.get("phone"))) {
            map.put("sendType", map.get("codeMsg"));
            map.put("phoneTo", map.get("phone"));
            map.put("phoneContent", "验证码为: " + validateCode + ",您正在注册成为新用户,感谢您的支持!【教留服】");
            map.put("mailTo", map.get("phone"));
            map.put("emailContent", "验证码为: " + validateCode + ",您正在注册成为新用户,感谢您的支持! 教育部留学服务中心");
            ;
            map.put("subjectTitle", "统一用户注册系统信息发送");
            ValueOperations forValue = redisTemplate.opsForValue();
            String key = this.getPhoneKey(map.get("phone"));
            forValue.set(key, validateCode);
            redisTemplate.expire(key, 5, TimeUnit.MINUTES);

            HashOperations<String, Object, Object> ooho = redisTemplate.opsForHash();
            String key11 = new StringBuilder("yzPhone_").append(map.get("phone")).toString();
            //获取再redis中的集合
            Map<Object, Object> map11 = ooho.entries(key11);
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = format.format(date);
            //如果集合为空，那么重新插入新的集合，设置次数为1
            if (map11.size() == 0) {
                map11.put("date", currentDate);
                map11.put("count", "1");
                ooho.putAll(key11, map11);
                //24小时过期防止数据永久存在小号内存
                redisTemplate.expire(key11, 24, TimeUnit.HOURS);
                return true;
            }
            //如果集合不为空先判断日期是否与当前日期一致，不一致重置日期和次数
            String dateStr = map11.get("date").toString();
            if (!StringUtils.equals(dateStr, currentDate)) {
                ooho.put(key11, "date", currentDate);
                ooho.put(key11, "count", "1");
                return true;
            }
            String countStr = ooho.get(key11, "count").toString();
            int count = Integer.parseInt(countStr);
            //判断次数是否超过设定次数
            if (count >= 10) {
                return false;
            }
            //次数+1
            ooho.put(key11, "count", String.valueOf(count + 1));
            return true;
        }
        return false;
    }


    // 验证手机号验证码操作
    @Override
    public boolean checkValidatePhoneCode(PhoneEmailBo phoneEmailBo) {
        //判断 手机号 验证码 均不能为空
        if (StringUtils.isAnyEmpty(phoneEmailBo.getPhone(), phoneEmailBo.getValidateCode())) {
            return false;
        }
        //redis中的验证码
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        HashOperations<String, Object, Object> ooho = redisTemplate.opsForHash();
        String countkey = new StringBuilder("countKey3").append(phoneEmailBo.getPhone()).toString();
        Map<Object, Object> map11 = ooho.entries(countkey);
        if (map11.size() == 0) {
            map11.put("count", "1");
            ooho.putAll(countkey, map11);
            //24小时过期防止数据永久存在小号内存
            redisTemplate.expire(countkey, 5, TimeUnit.MINUTES);
        }
        String countStr = ooho.get(countkey, "count").toString();
        int count = Integer.parseInt(countStr);
        //判断次数是否超过设定次数
        if (count > 6) {
            return false;
        } else {
            ooho.put(countkey, "count", String.valueOf(count + 1));
            String countStr22 = ooho.get(countkey, "count").toString();
        }
        String key = this.getPhoneKey(phoneEmailBo.getPhone());
        //通过手机号 获取redis中的验证码
        String validateCodeInRedis = forValue.get(key);
        //校验  对比 redis中的验证码和传入的验证码是否相同
        boolean ok = StringUtils.equals(validateCodeInRedis, phoneEmailBo.getValidateCode());
        return ok;
    }

    // 验证邮箱验证码操作
    @Override
    public boolean checkValidateEmailCode(PhoneEmailBo phoneEmailBo) {
        //判断 邮箱验证码 均不能为空
        if (StringUtils.isAnyEmpty(phoneEmailBo.getEmail(), phoneEmailBo.getValidateCode())) {
            return false;
        }
        //redis中的验证码
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        HashOperations<String, Object, Object> ooho = redisTemplate.opsForHash();
        String countkey = new StringBuilder("countKey2").append(phoneEmailBo.getEmail()).toString();
        Map<Object, Object> map11 = ooho.entries(countkey);
        if (map11.size() == 0) {
            map11.put("count", "1");
            ooho.putAll(countkey, map11);
            //24小时过期防止数据永久存在小号内存
            redisTemplate.expire(countkey, 5, TimeUnit.MINUTES);
        }
        String countStr = ooho.get(countkey, "count").toString();
        int count = Integer.parseInt(countStr);
        //判断次数是否超过设定次数
        if (count > 6) {
            return false;
        } else {
            ooho.put(countkey, "count", String.valueOf(count + 1));
            String countStr22 = ooho.get(countkey, "count").toString();
        }
        String key = this.getEmailKey(phoneEmailBo.getEmail());
        //通过手机号 获取redis中的验证码
        String validateCodeInRedis = forValue.get(key);
        //校验  对比 redis中的验证码和传入的验证码是否相同
        boolean ok = StringUtils.equals(validateCodeInRedis, phoneEmailBo.getValidateCode());
        return ok;
    }


    //同时对手机号和邮箱进行验证
    @Override
    public boolean checkValidatePhoneEmailCode(PhoneEmailBo phoneEmailBo) {
        //判断 手机号 邮箱以及 验证码 均不能为空
        if (StringUtils.isAnyEmpty(phoneEmailBo.getPhone(), phoneEmailBo.getValidateCode1(), phoneEmailBo.getEmail(), phoneEmailBo.getValidateCode())) {
            return false;
        }

        //redis中的验证码
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        String phonekey = this.getPhonePasswordKey(phoneEmailBo.getPhone());
        String emailkey = this.getEmailPasswordKey(phoneEmailBo.getEmail());
        //通过手机号 获取redis中的验证码
        String phoneValidateCodeInRedis = forValue.get(phonekey);
        String emailValidateCodeInRedis = forValue.get(emailkey);
        //校验  对比 redis中的验证码和传入的验证码是否相同
        boolean ok = StringUtils.equals(phoneValidateCodeInRedis, phoneEmailBo.getValidateCode());
        boolean ok1 = StringUtils.equals(emailValidateCodeInRedis, phoneEmailBo.getValidateCode());
        if (ok && ok1) {
            return true;
        }
        return false;
    }

    // 验证手机号验证码操作用于找回密码
    @Override
    public boolean validatePhonePasswordCode(PhoneEmailBo phoneEmailBo) {
        //判断 手机号 验证码 均不能为空
        if (StringUtils.isAnyEmpty(phoneEmailBo.getPhone(), phoneEmailBo.getValidateCode())) {
            return false;
        }
        //redis中的验证码
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();

        HashOperations<String, Object, Object> ooho = redisTemplate.opsForHash();
        String countkey = new StringBuilder("countKey").append(phoneEmailBo.getPhone()).toString();
        Map<Object, Object> map11 = ooho.entries(countkey);
        if (map11.size() == 0) {
            map11.put("count", "1");
            ooho.putAll(countkey, map11);
            //24小时过期防止数据永久存在小号内存
            redisTemplate.expire(countkey, 5, TimeUnit.MINUTES);
        }
        String countStr = ooho.get(countkey, "count").toString();
        int count = Integer.parseInt(countStr);
        //判断次数是否超过设定次数
        if (count > 6) {
            return false;
        } else {
            ooho.put(countkey, "count", String.valueOf(count + 1));
            String countStr22 = ooho.get(countkey, "count").toString();
        }
        String key = this.getPhonePasswordKey(phoneEmailBo.getPhone());
        //通过手机号 获取redis中的验证码
        String validateCodeInRedis = forValue.get(key);
        //校验  对比 redis中的验证码和传入的验证码是否相同
        boolean ok = StringUtils.equals(validateCodeInRedis, phoneEmailBo.getValidateCode());
        //次数+1
        return ok;
    }

    // 验证邮箱验证码操作用于找回密码
    @Override
    public boolean validateEmailPasswordCode(PhoneEmailBo phoneEmailBo) {
        //判断 手机号 验证码 均不能为空
        if (StringUtils.isAnyEmpty(phoneEmailBo.getEmail(), phoneEmailBo.getValidateCode())) {
            return false;
        }
        //redis中的验证码
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        HashOperations<String, Object, Object> ooho = redisTemplate.opsForHash();
        String countkey = new StringBuilder("countKey1").append(phoneEmailBo.getEmail()).toString();
        Map<Object, Object> map11 = ooho.entries(countkey);
        if (map11.size() == 0) {
            map11.put("count", "1");
            ooho.putAll(countkey, map11);
            //24小时过期防止数据永久存在小号内存
            redisTemplate.expire(countkey, 5, TimeUnit.MINUTES);
        }
        String countStr = ooho.get(countkey, "count").toString();
        int count = Integer.parseInt(countStr);
        //判断次数是否超过设定次数
        if (count > 6) {
            return false;
        } else {
            ooho.put(countkey, "count", String.valueOf(count + 1));
            String countStr22 = ooho.get(countkey, "count").toString();
        }
        String key = this.getEmailPasswordKey(phoneEmailBo.getEmail());
        //通过手机号 获取redis中的验证码
        String validateCodeInRedis = forValue.get(key);
        //校验  对比 redis中的验证码和传入的验证码是否相同
        boolean ok = StringUtils.equals(validateCodeInRedis, phoneEmailBo.getValidateCode());
        return ok;
    }

    //机构注册  对于验证码的验证(国内企业)
    @Override
    public boolean checkValidateCodeOrg1(PhoneEmailBo phoneEmailBo) {
        //判断 手机号 验证码 均不能为空
        if (StringUtils.isAnyEmpty(phoneEmailBo.getEmail(), phoneEmailBo.getValidateCode())) {
            return false;
        }
        //redis中的验证码
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        HashOperations<String, Object, Object> ooho = redisTemplate.opsForHash();
        String countkey = new StringBuilder("countKey6").append(phoneEmailBo.getEmail()).toString();
        Map<Object, Object> map11 = ooho.entries(countkey);
        if (map11.size() == 0) {
            map11.put("count", "1");
            ooho.putAll(countkey, map11);
            //24小时过期防止数据永久存在小号内存
            redisTemplate.expire(countkey, 5, TimeUnit.MINUTES);
        }
        String countStr = ooho.get(countkey, "count").toString();
        int count = Integer.parseInt(countStr);
        //判断次数是否超过设定次数
        if (count > 6) {
            return false;
        } else {
            ooho.put(countkey, "count", String.valueOf(count + 1));
            String countStr22 = ooho.get(countkey, "count").toString();
        }
        String key = this.inlandKey(phoneEmailBo.getEmail());
        //通过手机号 获取redis中的验证码
        String validateCodeInRedis = forValue.get(key);
        //校验  对比 redis中的验证码和传入的验证码是否相同
        boolean ok = StringUtils.equals(validateCodeInRedis, phoneEmailBo.getValidateCode());
        return ok;
    }

    //机构注册  对于验证码的验证(海外机构)
    @Override
    public boolean checkValidateCodeOrg2(PhoneEmailBo phoneEmailBo) {
        //判断 手机号 验证码 均不能为空
        if (StringUtils.isAnyEmpty(phoneEmailBo.getEmail(), phoneEmailBo.getValidateCode())) {
            return false;
        }
        //redis中的验证码
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();

        HashOperations<String, Object, Object> ooho = redisTemplate.opsForHash();
        String countkey = new StringBuilder("countKey7").append(phoneEmailBo.getEmail()).toString();
        Map<Object, Object> map11 = ooho.entries(countkey);
        if (map11.size() == 0) {
            map11.put("count", "1");
            ooho.putAll(countkey, map11);
            //24小时过期防止数据永久存在小号内存
            redisTemplate.expire(countkey, 5, TimeUnit.MINUTES);
        }
        String countStr = ooho.get(countkey, "count").toString();
        int count = Integer.parseInt(countStr);
        //判断次数是否超过设定次数
        if (count > 6) {
            return false;
        } else {
            ooho.put(countkey, "count", String.valueOf(count + 1));
            String countStr22 = ooho.get(countkey, "count").toString();
        }
        String key = this.overseasKey(phoneEmailBo.getEmail());
        //通过手机号 获取redis中的验证码
        String validateCodeInRedis = forValue.get(key);
        //校验  对比 redis中的验证码和传入的验证码是否相同
        boolean ok = StringUtils.equals(validateCodeInRedis, phoneEmailBo.getValidateCode());
        return ok;
    }

    //机构注册  对于验证码的验证(海外学联)
    @Override
    public boolean checkValidateCodeOrg3(PhoneEmailBo phoneEmailBo) {
        //判断 手机号 验证码 均不能为空
        if (StringUtils.isAnyEmpty(phoneEmailBo.getEmail(), phoneEmailBo.getValidateCode())) {
            return false;
        }
        //redis中的验证码
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        HashOperations<String, Object, Object> ooho = redisTemplate.opsForHash();
        String countkey = new StringBuilder("countKey4").append(phoneEmailBo.getEmail()).toString();
        Map<Object, Object> map11 = ooho.entries(countkey);
        if (map11.size() == 0) {
            map11.put("count", "1");
            ooho.putAll(countkey, map11);
            //24小时过期防止数据永久存在小号内存
            redisTemplate.expire(countkey, 5, TimeUnit.MINUTES);
        }
        String countStr = ooho.get(countkey, "count").toString();
        int count = Integer.parseInt(countStr);
        //判断次数是否超过设定次数
        if (count > 6) {
            return false;
        } else {
            ooho.put(countkey, "count", String.valueOf(count + 1));
            String countStr22 = ooho.get(countkey, "count").toString();
        }
        String key = this.schUnionKey(phoneEmailBo.getEmail());
        //通过手机号 获取redis中的验证码
        String validateCodeInRedis = forValue.get(key);
        //校验  对比 redis中的验证码和传入的验证码是否相同
        boolean ok = StringUtils.equals(validateCodeInRedis, phoneEmailBo.getValidateCode());
        return ok;
    }

    //机构用户 用于忘记密码找回 使用邮箱发送短信
    @Override
    public boolean ogenForgetSend(Map<String, String> map) {
        //生成6位的随机数
        String validateCode = generateCaptcha();
        if (StringUtils.isNotBlank(map.get("hostpersionEmail"))) {
            map.put("sendType", "2");
            map.put("mailTo", map.get("hostpersionEmail"));
            map.put("emailContent", "验证码为: " + validateCode + ",您正在找回密码,感谢您的支持!【教留服】");
            ValueOperations forValue = redisTemplate.opsForValue();
            String key = this.orgenForgetPasswordKey(map.get("hostpersionEmail"));
            forValue.set(key, validateCode);
            redisTemplate.expire(key, 5, TimeUnit.MINUTES);
            map.put("subjectTitle", "统一用户注册系统信息发送");
            return remoteMsgSendService.SendMsg(map);
        }
        return false;
    }

    // 验证邮箱验证码操作 机构用户用于找回密码
    @Override
    public boolean orgenForgetValidateEmailCode(PhoneEmailBo phoneEmailBo) {
        //判断 邮箱验证码 均不能为空
        if (StringUtils.isAnyEmpty(phoneEmailBo.getEmail(), phoneEmailBo.getValidateCode())) {
            return false;
        }
        //redis中的验证码
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        HashOperations<String, Object, Object> ooho = redisTemplate.opsForHash();
        String countkey = new StringBuilder("countKey5").append(phoneEmailBo.getEmail()).toString();
        Map<Object, Object> map11 = ooho.entries(countkey);
        if (map11.size() == 0) {
            map11.put("count", "1");
            ooho.putAll(countkey, map11);
            //24小时过期防止数据永久存在小号内存
            redisTemplate.expire(countkey, 5, TimeUnit.MINUTES);
        }
        String countStr = ooho.get(countkey, "count").toString();
        int count = Integer.parseInt(countStr);
        //判断次数是否超过设定次数
        if (count > 6) {
            return false;
        } else {
            ooho.put(countkey, "count", String.valueOf(count + 1));
            String countStr22 = ooho.get(countkey, "count").toString();
        }
        String key = this.orgenForgetPasswordKey(phoneEmailBo.getEmail());
        //通过手机号 获取redis中的验证码
        String validateCodeInRedis = forValue.get(key);
        //校验  对比 redis中的验证码和传入的验证码是否相同
        boolean ok = StringUtils.equals(validateCodeInRedis, phoneEmailBo.getValidateCode());
        return ok;
    }


    //生成六位的随机数
    public static String generateCaptcha() {
        /** 生成6位随机数 */
        String captcha = UUID.randomUUID().toString()
                .replaceAll("-", "")
                .replaceAll("[a-z|A-Z]", "")
                .substring(0, 6);
        return captcha;
    }

    //生成短信模板
    public static String msgTemplate() {
        //client.send(mobile, "您的验证码为:" + verifyCode + "，该码有效期为5分钟，该码只能使用一次!");
        String result = "{code:0}";
        return null;
    }

    //发送手机 验证码 设置redis的 key前缀
    public String getPhoneKey(String phone) {
        String key = new StringBuilder(prefix_1).append(phone).toString();
        return key;
    }

    //发送手机 验证码 设置redis的 key前缀
    public String getEmailKey(String email) {
        String key = new StringBuilder(prefix_2).append(email).toString();
        return key;
    }

    //发送手机 验证码 设置redis的 key前缀
    public String inlandKey(String email) {
        String key = new StringBuilder(prefix_3).append(email).toString();
        return key;
    }

    //发送手机 验证码 设置redis的 key前缀
    public String overseasKey(String email) {
        String key = new StringBuilder(prefix_4).append(email).toString();
        return key;
    }

    //发送手机 验证码 设置redis的 key前缀
    public String schUnionKey(String email) {
        String key = new StringBuilder(prefix_5).append(email).toString();
        return key;
    }

    //发送手机 验证码 设置redis的 key前缀
    public String getPhonePasswordKey(String phone) {
        String key = new StringBuilder(prefix_6).append(phone).toString();
        return key;
    }

    //发送手机 验证码 设置redis的 key前缀
    public String getEmailPasswordKey(String email) {
        String key = new StringBuilder(prefix_7).append(email).toString();
        return key;
    }

    //发送手机 验证码 设置redis的 key前缀
    public String orgenForgetPasswordKey(String email) {
        String key = new StringBuilder(prefix_8).append(email).toString();
        return key;
    }

}
