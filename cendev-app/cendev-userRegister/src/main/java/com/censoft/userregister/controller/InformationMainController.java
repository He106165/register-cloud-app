package com.censoft.userregister.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.censoft.cendevbackmanage.feign.UserInterfaceServer;
import com.censoft.common.core.controller.BaseController;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.system.domain.SysDictData;
import com.censoft.userregister.dao.InformationMainMapper;
import com.censoft.userregister.domain.*;
import com.censoft.userregister.service.InformationMainService;
import com.censoft.userregister.util.ResultUtil;
import com.censoft.userregister.util.StringUtilss;
import com.censoft.userregister.util.commonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

/**
 * @创建人:wqgeng
 * @创建时间:2020-12-1715:31
 * @描述:信息维护
 */
@RestController
@RequestMapping("informationMain")
@Api("信息维护界面")
public class InformationMainController extends BaseController {
    @Autowired
    private InformationMainService informationMainService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private InformationMainMapper informationMainMapper;
    @Autowired
    private UserInterfaceServer userInterfaceServer;
    @Autowired
    private RedisUtils redisUtils;
    //设置redis前缀
    private static final String wechat_redis_key = "wechat_";
    //设置redis前缀
    private static final String Alipay_redis_key = "#Alipay_";


    @ApiOperation(value = "信息维护(回显)", notes = "信息维护(回显)")
    @PostMapping("queryInfo")
    public ResultUtil queryInfo(@RequestBody PersonalUserInfo personalUserInfo) {
        //获取当前登录人的登录userId
        String userId = getUserId();
        personalUserInfo.setUserId(userId);
        //登录名  redis的key
        String loginName = getLoginName();
        //redis 中获取数据
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        String rediskey = forValue.get(loginName);
        //当缓存中有用户信息 直接查询缓存
        if (StringUtils.isNotBlank(rediskey)) {
            // JSONObject jsonObject = new JSONObject(rediskey);
            Map map = JSON.parseObject(rediskey, Map.class);

            return ResultUtil.success(map);
        } else {
            //缓存中没有用户信息 则查询数据库将信息返回
            //信息维护 基本信息回显功能
            return informationMainService.queryInfo(personalUserInfo);
        }
    }

    @ApiOperation(value = "信息维护(修改基本信息)", notes = "信息维护(修改基本信息)")
    @PostMapping("updateInfo")
    public ResultUtil updateInfo(@RequestBody PersonalUserInfo personalUserInfo) throws ParseException {
        try {
            //获取当前登录用户ID
            String userId = getUserId();
            personalUserInfo.setUserId(userId);
            // 修改当前登录redis中的信息  将非当前登录的redis个人信息删除
            //登录名  redis的key
            String loginName = getLoginName();
            ValueOperations<String, String> forValue = redisTemplate.opsForValue();
            //将redis里边的数据取出 转为json对象形式
            String s = forValue.get(loginName);
            JSONObject redisJson = JSON.parseObject(s);
            //将实体类转为json格式
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(personalUserInfo));
            jsonObject.forEach((k, v) -> {
                redisJson.put(k, v);
            });
            //将redisJson改为String格式
            //将redis的值更改后重新放入 redis中
            forValue.set(loginName, redisJson.toJSONString());
            //将redis中的所有登录方式的用户信息 删除
            //根据证件号查询用户信息
            Authentication authentication = new Authentication();
            authentication.setCardNo(personalUserInfo.getCardNo());
            PersonalUserLogin pp = informationMainMapper.selectCardInfo(authentication);
            if (pp != null) {
                //redis中微信扫码登录的key
                String wechatKey = new StringBuilder(wechat_redis_key).append(pp.getWecharId()).toString();
                //redis中支付宝扫码登录的key
                String AlipaytKey = new StringBuilder(Alipay_redis_key).append(pp.getAlipayId()).toString();
                //删除redis信息
                if (StringUtils.isNotBlank(pp.getPhone()) && !pp.getPhone().equals(loginName)) {
                    redisUtils.delete(pp.getPhone());
                }
                if (StringUtils.isNotBlank(pp.getEmail()) && !pp.getEmail().equals(loginName)) {
                    redisUtils.delete(pp.getEmail());
                }
                if (StringUtils.isNotBlank(pp.getIdnumber()) && !pp.getIdnumber().equals(loginName)) {
                    redisUtils.delete(pp.getIdnumber());
                }
                if (StringUtils.isNotBlank(pp.getName()) && !pp.getName().equals(loginName)) {
                    redisUtils.delete(pp.getName());
                }
                if (StringUtils.isNotBlank(pp.getWecharId()) && !wechatKey.equals(loginName)) {
                    redisUtils.delete(wechatKey);
                }
                if (StringUtils.isNotBlank(pp.getAlipayId()) && !AlipaytKey.equals(loginName)) {
                    redisUtils.delete(AlipaytKey);
                }
            }
            //信息维护 基本信息修改功能
            return informationMainService.updateInfo(personalUserInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.result("系统错误");
    }

    @ApiOperation(value = "新增、修改扩展信息", notes = "新增、修改扩展信息")
    @PostMapping("saveOrUpdate")
    public ResultUtil saveOrUpdate(@RequestBody PersonalExtendInfo personalExtendInfo) {
        String userId = getUserId();
        personalExtendInfo.setUserId(userId);
        //信息维护 基本信息修改功能 之前未填写为新增 已填写为修改
        return informationMainService.saveOrUpdate(personalExtendInfo);
    }

    @ApiOperation(value = "查询用户是否维护了扩展信息", notes = "查询用户是否维护了扩展信息")
    @PostMapping("queryExtendCount")
    public ResultUtil queryExtendCount(@RequestBody PersonalExtendInfo personalExtendInfo) {
        String userId = getUserId();
        personalExtendInfo.setUserId(userId);
        //查询扩展信息
        return informationMainService.queryExtendCount(personalExtendInfo);
    }

    @ApiOperation(value = "查询扩展信息", notes = "查询扩展信息")
    @PostMapping("queryExtendInfo")
    public ResultUtil queryExtendInfo(@RequestBody PersonalExtendInfo personalExtendInfo) {
        String userId = getUserId();
        personalExtendInfo.setUserId(userId);
        //查询扩展信息
        return informationMainService.queryExtendInfo(personalExtendInfo);
    }

    @ApiOperation(value = "新增留学经历", notes = "新增留学经历")
    @PostMapping("insertAbroafdstudyInfo")
    public ResultUtil insertAbroafdstudyInfo(@RequestBody PersonalAbroafdstudyInfo personalAbroafdstudyInfo) {
        String userId = getUserId();
        personalAbroafdstudyInfo.setUserId(userId);
        //新增留学经历
        return informationMainService.insertAbroafdstudyInfo(personalAbroafdstudyInfo);
    }

    @ApiOperation(value = "查询留学经历", notes = "查询留学经历")
    @PostMapping("queryAbroafdstudyInfo")
    public ResultUtil queryAbroafdstudyInfo() {
        String userId = getUserId();
        PersonalAbroafdstudyInfo personalAbroafdstudyInfo = new PersonalAbroafdstudyInfo();
        personalAbroafdstudyInfo.setUserId(userId);
        //查询留学经历
        return informationMainService.queryAbroafdstudyInfo(personalAbroafdstudyInfo);
    }

    @ApiOperation(value = "根据ID查询留学经历用于编辑回显", notes = "根据ID查询留学经历用于编辑回显")
    @PostMapping("queryAbroafdstudyInfoById")
    public ResultUtil queryAbroafdstudyInfoById(@RequestBody PersonalAbroafdstudyInfo personalAbroafdstudyInfo) {
        //查询留学经历
        return informationMainService.queryAbroafdstudyInfoById(personalAbroafdstudyInfo);
    }

    @ApiOperation(value = "修改留学经历", notes = "修改留学经历")
    @PostMapping("updateAbroafdstudyInfo")
    public ResultUtil updateAbroafdstudyInfo(@RequestBody PersonalAbroafdstudyInfo personalAbroafdstudyInfo) {
        //修改留学经历
        return informationMainService.updateAbroafdstudyInfo(personalAbroafdstudyInfo);
    }

    @ApiOperation(value = "根据Id删除留学经历", notes = "根据Id删除留学经历")
    @PostMapping("deleteAbroafdstudyInfoById")
    public ResultUtil deleteAbroafdstudyInfoById(@RequestBody PersonalAbroafdstudyInfo personalAbroafdstudyInfo) {
        //根据Id删除留学经历
        return informationMainService.deleteAbroafdstudyInfoById(personalAbroafdstudyInfo);
    }

    @ApiOperation(value = "查询实名信息", notes = "查询实名信息")
    @PostMapping("queryRealNameInformation")
    public ResultUtil queryRealNameInformation(@RequestBody PersonalUserInfo personalUserInfo) {
        String userId = getUserId();
        personalUserInfo.setUserId(userId);
        //查询实名信息
        return informationMainService.queryRealNameInformation(personalUserInfo);
    }

    @ApiOperation(value = "实名认证", notes = "实名认证")
    @PostMapping("authentication")
    public ResultUtil authentication(@RequestBody Authentication authentication) {
        String loginName = getLoginName();
        authentication.setLoginName(loginName);
        return informationMainService.authentication(authentication);
    }

    @ApiOperation(value = "查询字典信息", notes = "查询字典信息")
    @GetMapping(value = "/type/{dictType}")
    public ResultUtil queryNationality(@PathVariable String dictType) {
        List<SysDictData> data = informationMainService.selectDictDataByType(dictType);
        if (StringUtilss.isNull(data)) {
            data = new ArrayList<SysDictData>();
        }
        return ResultUtil.success(data);
    }
}
