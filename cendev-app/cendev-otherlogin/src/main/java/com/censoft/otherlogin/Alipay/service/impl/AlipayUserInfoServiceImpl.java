package com.censoft.otherlogin.Alipay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.censoft.cendevbackmanage.feign.RemoteMsgSendService;
import com.censoft.cendevbackmanage.feign.UserInterfaceServer;
import com.censoft.common.core.domain.R;
import com.censoft.common.core.text.Convert;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.common.utils.MD5Format;
import com.censoft.otherlogin.Alipay.domain.AlipayUserInfo;
import com.censoft.otherlogin.Alipay.domain.PersonalUserInfo;
import com.censoft.otherlogin.Alipay.mapper.AlipayUserInfoMapper;
import com.censoft.otherlogin.Alipay.service.AlipayLoginService;
import com.censoft.otherlogin.Alipay.service.DockedLogService;
import com.censoft.otherlogin.Alipay.service.IAlipayUserInfoService;
import com.censoft.otherlogin.binding.mapper.BingdingMapper;
import com.censoft.otherlogin.feign.RemoteTokenServer;
import com.censoft.otherlogin.util.DesNewUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 支付宝信息Service业务层处理
 *
 * @author cendev
 * @date 2020-10-21
 */
@Service
public class AlipayUserInfoServiceImpl implements IAlipayUserInfoService {
    @Autowired
    private AlipayUserInfoMapper alipayUserInfoMapper;
    @Autowired
    private AlipayLoginService alipayLoginService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisUtils redis;
    @Autowired
    private RemoteTokenServer tokenService;
    @Autowired
    private DockedLogService dockedLogService;
    @Autowired
    private UserInterfaceServer userInterfaceServer;
    @Autowired
    private BingdingMapper bingdingMapper;

    @Autowired
    private RemoteMsgSendService remoteMsgSendService;



    DesNewUtils desObj = new DesNewUtils();
    String key = "cen)%)(!";
    /**
     * 查询支付宝信息
     *
     * @param id 支付宝信息ID
     * @return 支付宝信息
     */
    @Override
    public AlipayUserInfo selectAlipayUserInfoById(String id) {
        return alipayUserInfoMapper.selectAlipayUserInfoById(id);
    }

    /**
     * 查询支付宝信息列表
     *
     * @param alipayUserInfo 支付宝信息
     * @return 支付宝信息
     */
    @Override
    public List<AlipayUserInfo> selectAlipayUserInfoList(AlipayUserInfo alipayUserInfo) {
        return alipayUserInfoMapper.selectAlipayUserInfoList(alipayUserInfo);
    }

    /**
     * 新增支付宝信息
     *
     * @param alipayUserInfo 支付宝信息
     * @return 结果
     */
    @Override
    public int insertAlipayUserInfo(AlipayUserInfo alipayUserInfo) {
        return alipayUserInfoMapper.insertAlipayUserInfo(alipayUserInfo);
    }

    /**
     * 修改支付宝信息
     *
     * @param alipayUserInfo 支付宝信息
     * @return 结果
     */
    @Override
    public int updateAlipayUserInfo(AlipayUserInfo alipayUserInfo) {
        return alipayUserInfoMapper.updateAlipayUserInfo(alipayUserInfo);
    }

    /**
     * 删除支付宝信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAlipayUserInfoByIds(String ids) {
        return alipayUserInfoMapper.deleteAlipayUserInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除支付宝信息信息
     *
     * @param id 支付宝信息ID
     * @return 结果
     */
    public int deleteAlipayUserInfoById(String id) {
        return alipayUserInfoMapper.deleteAlipayUserInfoById(id);
    }



    public String bindAlipayInfo(Map map, HttpServletResponse response, RedirectAttributes attr) {
        if (map.get("auth_code") != null && !map.get("auth_code").toString().equals("")) {
            String accessToken = alipayLoginService.getAccessToken(map.get("auth_code").toString());
            String  alipayid = alipayLoginService.getAlipayIDByToken(accessToken);

            /*token取当前登录人id*/
            String userName = tokenService.getAccoutName(map.get("raw").toString())==null?"":tokenService.getAccoutName(map.get("raw").toString());
            List<Object> jsonList= new ArrayList<>();
            Set<String> keys=redisTemplate.keys(userName);
            jsonList=redisTemplate.opsForValue().multiGet(keys);
            if (!alipayid.equals("") && jsonList.size()>0) {
                Map userMap= JSONObject.toJavaObject((JSONObject)jsonList.get(0),Map.class);
                String userId = userMap.get("userId")==null?"":userMap.get("userId").toString();

                //成功
                Map paramMap = new HashMap(){{
                    put("alipayID",alipayid);
                    put("userId",userId);
                }};
                /*绑定账号 不能重复绑定*/
                if(bingdingMapper.checkAliUser(paramMap)!= null){
                    return bindingalipayUserAlipayErrorUrl;
                }

                bingdingMapper.bindAlipayInfo(paramMap);
                return bindingalipayUserUrl;
            } else {
                return bindingalipayUserErrorUrl;
            }
        }
        return bindingalipayUserErrorUrl;
    }


    @Value("${alipayUrl.alipayCurrentUrl}") private String  alipayCurrentUrl;
    @Value("${alipayUrl.alipayErrorUrl}") private  String  alipayErrorUrl;
    @Value("${alipayUrl.alipayunUserUrl}") private  String  alipayunUserUrl;
    @Value("${alipayUrl.checkAliLoginUrl}") private  String  checkAliLoginUrl;
    @Value("${alipayUrl.bindingalipayUserUrl}") private  String  bindingalipayUserUrl;
    @Value("${alipayUrl.bindingalipayUserErrorUrl}") private  String  bindingalipayUserErrorUrl;
    @Value("${alipayUrl.bindingalipayUserAlipayErrorUrl}") private  String  bindingalipayUserAlipayErrorUrl;


    @Override
    public String AlipayLogin(Map map, HttpServletResponse response, RedirectAttributes attr ,int proInfoTimeOut) {

        if (map.get("auth_code") != null && !map.get("auth_code").toString().equals("")) {
            String accessToken = alipayLoginService.getAccessToken(map.get("auth_code").toString());
            //获取用户信息
            //获取用户信息
            AlipayUserInfo alipayUser = alipayLoginService.getUserInfoByToken(accessToken);

            if(alipayUser == null ){
                return  alipayErrorUrl;
            }else{
                List<AlipayUserInfo> list = alipayUserInfoMapper.selectAlipayUserInfoList(alipayUser);
                if(list.size() == 0)
                    alipayUserInfoMapper.insertAlipayUser(alipayUser);

            }

            //去redis里面查找
            String key = "#Alipay_" + alipayUser.getUserid();
            List<Object> jsonList = new ArrayList<>();
            Set<String> keys = redisTemplate.keys(key);
            jsonList = redisTemplate.opsForValue().multiGet(keys);

            if (jsonList.size() > 0) {
                Map userMap = JSONObject.toJavaObject((JSONObject) jsonList.get(0), Map.class);
                //*1支付宝登陆的密码，直接生成token
                if (userMap.get("userType") != null) {
                    String token = tokenService.getToken(key);
                    attr.addAttribute("token",token);
                    attr.addAttribute("url",alipayCurrentUrl);
                    return checkAliLoginUrl;
                }
            }
            map.put("userName",alipayUser.getUserid());
            List<Map> userList = alipayUserInfoMapper.AlipayUserLogin(map);
            String token = tokenService.getToken(key);


            if (userList.size() == 1) {
                attr.addAttribute("token",token);
                userList.get(0).put("userType","personal");
                PersonalUserInfo personUser = JSON.parseObject(JSON.toJSONString(userList.get(0)), PersonalUserInfo.class);
                personUser.setName(personUser.getUserName());
                personUser.setUserType("personal");
                // 设置redis 以及过期时间
                redis.set(key,personUser,proInfoTimeOut);

                attr.addAttribute("url",alipayCurrentUrl);
                return checkAliLoginUrl;
            } else if (userList.size() == 0) {
                //token = strTo16(token);
                //attr.addAttribute("aliyUid",alipayUser.getUserid());
                attr.addAttribute("url",alipayunUserUrl+"?wt="+token);
                return checkAliLoginUrl;
            }
            return alipayErrorUrl;
        }
        return  alipayErrorUrl;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public R insertAlipayUser(Map map, HttpServletResponse response) {
        if(map.get("checkCode") == null) return R.error("验证码不能为空");
        String code = redis.get("bindAliPay_"+map.get("phone")).replace("\"","");
        if(code == null) return R.error("验证码已过期");

        if(!code.equals(map.get("checkCode"))) return R.error("验证码输入错误");


        String  token= map.get("Alipay_ID") == null?"":map.get("Alipay_ID").toString();
        String Alipay_ID = tokenService.getAccoutName(token);
        map.put("Alipay_ID",Alipay_ID.replace("#Alipay_",""));
        Integer tokenConfigTime = Integer.valueOf( tokenService.getTokenConfigTime());

        tokenConfigTime = tokenConfigTime * 60 *1000;
        String id = UUID.randomUUID().toString().replace("-", "");
        String userId = UUID.randomUUID().toString().replace("-", "");
        String password = map.get("password") == null ? "" : map.get("password").toString();
//        String cardType = map.get("cardType") == null ? "" : map.get("cardType").toString();
//        String userName = map.get("userName") == null ? "" : map.get("userName").toString();

        //password = desObj.strEnc(password, key);
        password = MD5Format.MD5(password);
        map.put("password", password);
        map.put("id", id);
        map.put("userId", userId);

        //取支付宝 昵称
        AlipayUserInfo info = new AlipayUserInfo();
        info.setUserid(Alipay_ID.replace("#Alipay_",""));
        List<AlipayUserInfo> alpaylist =  alipayUserInfoMapper.selectAlipayUserInfoList(info);
        if(alpaylist.size()>0 && alpaylist.get(0).getNickname()!=null){
            map.put("nickName",alpaylist.get(0).getNickname());
        }

        try {
            map.put("phoneIscheck",1);
            alipayUserInfoMapper.insertPersionalUserLogin(map);
            alipayUserInfoMapper.insertPersionalUserInfo(map);
            PersonalUserInfo personUser = JSON.parseObject(JSON.toJSONString(map), PersonalUserInfo.class);
            String rkey = Alipay_ID;
            personUser.setName(personUser.getUserName());
            personUser.setUserType("personal");
            redis.set(rkey,personUser,tokenConfigTime);

        } catch (Exception e) {
            e.printStackTrace();
            // 加入下行代码手动回滚
            // @Transactional 为方法加上事务,try catch 捕获到异常手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error();
        }
        return R.ok();
    }



//    public static String getCookies(String url) throws IOException {
//
//        // 全局请求设置
//
//        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
//
//        // 创建cookie store的本地实例
//
//        CookieStore cookieStore = new BasicCookieStore();
//
//        // 创建HttpClient上下文
//
//        HttpClientContext context = HttpClientContext.create();
//
//        context.setCookieStore(cookieStore);
//
//
//
//        // 创建一个HttpClient
//
//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
//
//                .setDefaultCookieStore(cookieStore).build();
//
//
//
//        CloseableHttpResponse res = null;
//
//
//
//        // 创建一个get请求用来获取必要的Cookie，如_xsrf信息
//
//        HttpGet get = new HttpGet(url);
//
//
//
//        res = httpClient.execute(get, context);
//
//        // 获取常用Cookie,包括_xsrf信息
//
//        StringBuffer cookie=new StringBuffer();
//
//        for (org.apache.http.cookie.Cookie c : cookieStore.getCookies()) {
//
//            //拼接所有cookie变成一个字符串；
//
//            cookie.append(c.getName()+"="+c.getValue()+";");
//
//            System.out.println(c.getName() + ": " + c.getValue());
//
//        }
//
//
//
//        String cookieres=cookie.toString();
//
//        cookieres=cookieres.substring(0,cookieres.length()-1);
//
//        res.close();
//
//        return cookieres;
//
//    }
//
//
//
//    public static void delCookies(String url) throws IOException {
//
//        // 全局请求设置
//        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
//        // 创建cookie store的本地实例
//        CookieStore cookieStore = new BasicCookieStore();
//        // 创建HttpClient上下文
//        HttpClientContext context = HttpClientContext.create();
//        context.setCookieStore(cookieStore);
//        // 创建一个HttpClient
//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
//                .setDefaultCookieStore(cookieStore).build();
//        // 创建一个get请求用来获取必要的Cookie，如_xsrf信息
//        HttpGet get = new HttpGet(url);
//        httpClient.execute(get, context);
//        cookieStore.clear();
//    }


    @Override
    public boolean sendMsg(String phone){
        Map<String,String> map = new HashMap<>();
        //生成6位的随机数
        String validateCode = this.generateCaptcha();
        map.put("subjectTitle", "统一用户注册系统信息发送");
        map.put("sendType", "3");
        map.put("phoneTo", phone);
        map.put("phoneContent", "验证码为: " + validateCode + ",您正在绑定支付宝登陆,感谢您的支持!【教留服】");
        ValueOperations forValue = redisTemplate.opsForValue();
        String key = "bindAliPay_"+ phone;
        forValue.set(key, validateCode);
        redisTemplate.expire(key, 5, TimeUnit.MINUTES);
        return remoteMsgSendService.SendMsg(map);
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
}
