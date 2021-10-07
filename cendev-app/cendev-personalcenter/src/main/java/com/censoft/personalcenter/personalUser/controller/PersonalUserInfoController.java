package com.censoft.personalcenter.personalUser.controller;

import com.censoft.common.core.controller.BaseController;
import com.censoft.common.core.domain.R;
import com.censoft.common.log.annotation.OperLogRes;
import com.censoft.common.log.enums.BusinessType;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.common.utils.StringUtils;
import com.censoft.dfs.feign.RemoteDFSService;
import com.censoft.personalcenter.orgenUser.domain.OrgenUserInfo;
import com.censoft.personalcenter.orgenUser.service.IOrgenUserInfoService;
import com.censoft.personalcenter.personalUser.domain.PersonalUserInfo;
import com.censoft.personalcenter.personalUser.domain.PersonalUserLogin;
import com.censoft.personalcenter.personalUser.mapper.PersonalUserInfoMapper;
import com.censoft.personalcenter.personalUser.service.IPersonalUserInfoService;
import com.censoft.personalcenter.personalUser.service.IPersonalUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 个人用户信息 提供者
 *
 * @author cendev
 * @date 2020-10-20
 */
@RestController
@RequestMapping("personalCenter")
public class PersonalUserInfoController extends BaseController {

    @Autowired
    private IPersonalUserInfoService personalUserInfoService;


    @Autowired
    private RemoteDFSService remoteDFSService;

    @Autowired
    private IOrgenUserInfoService orgenUserInfoService;

    @Autowired
    private IPersonalUserLoginService userLoginService;

    @Autowired
    private RedisUtils redis;
    @Autowired
    private PersonalUserInfoMapper personalUserInfoMapper;

    //设置redis前缀
    private static final String wechat_redis_key = "wechat_";
    //设置redis前缀
    private static final String Alipay_redis_key = "#Alipay_";

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{userId}")
    public PersonalUserInfo get(@PathVariable("userId") String userId) {
        return personalUserInfoService.selectPersonalUserInfoById(userId);

    }

    /**
     * 查询个人用户信息列表
     */
    @GetMapping("list")
    public R list(PersonalUserInfo personalUserInfo) {
        startPage();
        return result(personalUserInfoService.selectPersonalUserInfoList(personalUserInfo));
    }


    /**
     * 新增保存个人用户信息
     */
    @PostMapping("save")
    public R addSave(@RequestBody PersonalUserInfo personalUserInfo) {
        return toAjax(personalUserInfoService.insertPersonalUserInfo(personalUserInfo));
    }

    /**
     * 修改保存个人用户信息
     */
    @PostMapping("update")
    public R editSave(@RequestBody PersonalUserInfo personalUserInfo) {
        return toAjax(personalUserInfoService.updatePersonalUserInfo(personalUserInfo));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(personalUserInfoService.deletePersonalUserInfoByIds(ids));
    }

    /**
     * @return
     * @Description 修改用户头像
     * @Parm
     **/
    @PostMapping("updateUserAvatar")
    @Transactional
    @OperLogRes(opModel = "个人中心", opContent = "修改头像信息", opType = BusinessType.UPDATE)
    public R updateUserAuthor(MultipartFile file) {
        //获取当前登录人
        String loginName = getLoginName();
        if (file == null) return R.error("头像不能为空！");
        if (getUserType() == null || getUserId() == null) return R.error("登陆超时，请重新登陆");
        try {
            // 上传文件
            R upload = remoteDFSService.upload(file);
            if (upload.get("code").equals(500)) return R.error("上传失败,请联系管理员");
            // 修改用户头像信息
            if (getUserType().equals("personal")) {
                PersonalUserInfo personalUserInfo = new PersonalUserInfo();
                personalUserInfo.setUserId(getUserId());
                personalUserInfo.setAvatarUrl(upload.get("url").toString());
                if (personalUserInfoService.updatePersonalUserInfo(personalUserInfo) > 0) {
                    //刷新缓存里的个人数据
                    redis.fulshCurrentInfo(getLoginName(), "avatarUrl", upload.get("url").toString());
                    //清除redis 中其他登录方式里的个人信息
                    //根据userId查询登录表内容
                    PersonalUserLogin pp = personalUserInfoMapper.selectLoginInfo(getUserId());
                    //redis中微信扫码登录的key
                    String wechatKey = new StringBuilder(wechat_redis_key).append(pp.getWecharId()).toString();
                    //redis中支付宝扫码登录的key
                    String AlipaytKey = new StringBuilder(Alipay_redis_key).append(pp.getAlipayId()).toString();
                    //删除redis信息
                    if (StringUtils.isNotBlank(pp.getPhone()) && !pp.getPhone().equals(loginName)) {
                        redis.delete(pp.getPhone());
                    }
                    if (StringUtils.isNotBlank(pp.getEmail()) && !pp.getEmail().equals(loginName)) {
                        redis.delete(pp.getEmail());
                    }
                    if (StringUtils.isNotBlank(pp.getIdnumber()) && !pp.getIdnumber().equals(loginName)) {
                        redis.delete(pp.getIdnumber());
                    }
                    if (StringUtils.isNotBlank(pp.getName()) && !pp.getName().equals(loginName)) {
                        redis.delete(pp.getName());
                    }
                    if (StringUtils.isNotBlank(pp.getWecharId()) && !wechatKey.equals(loginName)) {
                        redis.delete(wechatKey);
                    }
                    if (StringUtils.isNotBlank(pp.getAlipayId()) && !AlipaytKey.equals(loginName)) {
                        redis.delete(AlipaytKey);
                    }
                    return R.ok().put("imgUrl", upload.get("url").toString());
                } else {
                    return R.error("修改失败，请联系管理员");
                }
            }
            if (getUserType().equals("organ")) {
                OrgenUserInfo orgenUserInfo = new OrgenUserInfo();
                orgenUserInfo.setOrgUserId(getUserId());
                orgenUserInfo.setAvatarUrl(upload.get("url").toString());
                if (orgenUserInfoService.updateOrgenUserInfo(orgenUserInfo) > 0) {
                    //刷新缓存里的个人数据
                    redis.fulshCurrentInfo(getLoginName(), "avatarUrl", upload.get("url").toString());
                    //清除redis 中其他登录方式里的个人信息
                    //根据userId查询登录表内容
                    PersonalUserLogin pp = personalUserInfoMapper.selectLoginInfo(getUserId());
                    //redis中微信扫码登录的key
                    String wechatKey = new StringBuilder(wechat_redis_key).append(pp.getWecharId()).toString();
                    //redis中支付宝扫码登录的key
                    String AlipaytKey = new StringBuilder(Alipay_redis_key).append(pp.getAlipayId()).toString();
                    //删除redis信息
                    if (StringUtils.isNotBlank(pp.getPhone()) && !pp.getPhone().equals(loginName)) {
                        redis.delete(pp.getPhone());
                    }
                    if (StringUtils.isNotBlank(pp.getEmail()) && !pp.getEmail().equals(loginName)) {
                        redis.delete(pp.getEmail());
                    }
                    if (StringUtils.isNotBlank(pp.getIdnumber()) && !pp.getIdnumber().equals(loginName)) {
                        redis.delete(pp.getIdnumber());
                    }
                    if (StringUtils.isNotBlank(pp.getName()) && !pp.getName().equals(loginName)) {
                        redis.delete(pp.getName());
                    }
                    if (StringUtils.isNotBlank(pp.getWecharId()) && !wechatKey.equals(loginName)) {
                        redis.delete(wechatKey);
                    }
                    if (StringUtils.isNotBlank(pp.getAlipayId()) && !AlipaytKey.equals(loginName)) {
                        redis.delete(AlipaytKey);
                    }
                    return R.ok().put("imgUrl", upload.get("url").toString());
                } else {
                    return R.error("修改失败，请联系管理员");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改头像失败，请联系管理员");
        }
        return R.ok();
    }


    /**
     * @return
     * @Description 修改联系方式，需要修改登陆表和个人信息表里的联系方式。
     * @Parm
     **/
    @RequestMapping(value = "updataContentWay")
//	@Transactional
    @OperLogRes(opModel = "个人中心", opContent = "修改联系方式", opType = BusinessType.UPDATE)
    public R updataContentWay(String way, String code) {
        //获取当前登录人
        String loginName = getLoginName();
        PersonalUserInfo personalUserInfo = new PersonalUserInfo();
        PersonalUserLogin personalUserLogin = new PersonalUserLogin();
        personalUserLogin.setUserId(getUserId());
        personalUserInfo.setUserId(getUserId());
        // 联系方式是邮箱还是手机号
        String method;
        String redisCode;
        // 验证邮箱  还是手机号
        if (way.indexOf("@") > -1) {
            redisCode = redis.get("smg:email:" + way);
            personalUserInfo.setEmail(way);
            personalUserLogin.setEmail(way);
            personalUserLogin.setEmailIscheck(1L);
            method = "email";
        } else {
            redisCode = redis.get("smg:phone:" + way);
            personalUserInfo.setPhone(way);
            personalUserLogin.setPhone(way);
            personalUserLogin.setPhoneIscheck(1L);
            method = "phone";
        }
        if (!code.equals(redisCode)) {
            return R.error("验证失败，请重新发送验证码");
        }

        if (personalUserInfoService.updatePersonalUserInfo(personalUserInfo) > 0 && userLoginService.updateLoginupdataContentWay(personalUserLogin) > 0) {
            //刷新缓存里的个人数据
            redis.fulshCurrentInfo(getLoginName(), method, way);
            //清除redis 中其他登录方式里的个人信息
            //根据userId查询登录表内容
            PersonalUserLogin pp = personalUserInfoMapper.selectLoginInfo(getUserId());
            if (pp!=null){
            //redis中微信扫码登录的key
            String wechatKey = new StringBuilder(wechat_redis_key).append(pp.getWecharId()).toString();
            //redis中支付宝扫码登录的key
            String AlipaytKey = new StringBuilder(Alipay_redis_key).append(pp.getAlipayId()).toString();
            //删除redis信息
            if (StringUtils.isNotBlank(pp.getPhone()) && !pp.getPhone().equals(loginName)) {
                redis.delete(pp.getPhone());
            }
            if (StringUtils.isNotBlank(pp.getEmail()) && !pp.getEmail().equals(loginName)) {
                redis.delete(pp.getEmail());
            }
            if (StringUtils.isNotBlank(pp.getIdnumber()) && !pp.getIdnumber().equals(loginName)) {
                redis.delete(pp.getIdnumber());
            }
            if (StringUtils.isNotBlank(pp.getName()) && !pp.getName().equals(loginName)) {
                redis.delete(pp.getName());
            }
            if (StringUtils.isNotBlank(pp.getWecharId()) && !wechatKey.equals(loginName)) {
                redis.delete(wechatKey);
            }
            if (StringUtils.isNotBlank(pp.getAlipayId()) && !AlipaytKey.equals(loginName)) {
                redis.delete(AlipaytKey);
            }
            }
            return R.ok();
        } else {
            return R.error("修改失败，请联系管理员");
        }
    }
}
