package com.censoft.smallRoutine.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.censoft.common.core.domain.R;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.smallRoutine.service.ExternalService;
import com.censoft.smallRoutine.util.HttpClientUtil;

import com.google.gson.JsonObject;
import com.sun.org.apache.regexp.internal.RE;
import okhttp3.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


/**
 * 小程序对外接口Api提供
 */

@Controller
@RequestMapping("/ExternalSmallRountine")
public class ExternalController {

    //redis获取token的名称
    @Value("${EXTTOKEN}")
    private String EXTTOKEN;

    //token地址
    @Value("${GETEXTTOKEN_URL}")
    private String GETEXTTOKEN_URL;
    //cookie的过期时间
    @Value("${EXTCookieTimeOut}")
    private String EXTCookieTimeOut;
    //认证业务数据接口地址
    @Value("${GETAuthentication_URL}")
    private String GETAuthentication_URL;
    //公派业务数据接口地址
    @Value("${GETVOCATIONAL_URL}")
    private String GETVOCATIONAL_URL;
    //存档业务数据接口
    @Value("${GETARCHIVE_URL}")
    private String GETARCHIVE_URL;
    //就业业务数据接口
    @Value("${GETEMPLOYMENT_URL}")
    private String GETEMPLOYMENT_URL;

    //认证证书接口
    @Value("${GETCERTIFICATION_URL}")
    private String GETCERTIFICATION_URL;
    //账号
    @Value("${ACCOUNT_NUMBER}")
    private String ACCOUNT_NUMBER;
    //密码
    @Value("${PASSWORD}")
    private String PASSWORD;
    //redis的 登录的保存时间
    @Value("${REDIS.PROINFO}")
    private String loginTime;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ExternalService externalService;


    /**
     * 获取认证业务数据接口
     */
    @PostMapping(value = "/selectAuthenticationByIdnumber")
    @ResponseBody
    public JSONObject selectAuthenticationByIdnumber(String idnumber) {

        // 定义返回结果
        String res = null;
        // 获取token
        String token = getToken();

        // 调用  HttpClientUtil 方法 调用接口
        try {
          /*  //判断idnumber是否为空
            if (idnumber == null || idnumber == "") {
                return emptyBackError();
            }*/
            //采用跟postman方式拼接的方式传递参数
            GETAuthentication_URL = GETAuthentication_URL + "?idnumber=" + idnumber;
            //通过HttpClientUtil类调用sendPut方法得到客户端的返回信息，
            res = HttpClientUtil.sendPut(GETAuthentication_URL,  "utf-8", token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (res == null) {
            return comeBackError();
        }
        return JSONObject.parseObject(res);
    }

    /**
     * 获取公派业务数据接口
     */
    @PostMapping(value = "/selectvocationalByIdnumber")
    @ResponseBody
    public JSONObject selectvocationalByIdnumber(String idnumber) {

        //定义返回结果
        String res = null;
        // 获取token
        String token = getToken();
        // 调用  HttpClientUtil 方法 调用接口
        try {
           /* //判断idnumber是否为空
            if (idnumber == null || idnumber == "") {
                return  emptyBackError();
            }*/
            //采用跟postman方式拼接的方式传递参数
            GETVOCATIONAL_URL = GETVOCATIONAL_URL + "?idnumber=" + idnumber;
            //通过HttpClientUtil类调用sendPut方法得到客户端的返回信息，
            res = HttpClientUtil.sendPut(GETVOCATIONAL_URL, "utf-8", token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (res == null) {
            return comeBackError();
        }
        return JSONObject.parseObject(res);
    }

    /**
     * 获取存档业务数据接口
     */
    @PostMapping(value = "/selectArchivesByIdnumber")
    @ResponseBody
    public JSONObject selectArchivesByIdnumber(String idnumber) {

        // 定义返回结果
        String res = null;
        // 获取token
        String token = getToken();
        //如果有token调用HTTClientUtil方法
        try {
           /* //判断idnumber是否为空
            if (idnumber == null || idnumber == "") {
                return  emptyBackError();
            }*/
            //采用跟postman方式拼接的方式传递参数
            GETARCHIVE_URL = GETARCHIVE_URL + "?idnumber=" + idnumber;
            //通过HttpClientUtil类调用sendPut方法得到客户端的返回信息，
            res = HttpClientUtil.sendPut(GETARCHIVE_URL,  "utf-8", token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (res == null) {
            return comeBackError();
        }
        return JSONObject.parseObject(res);
    }


    /**
     * 获取就业业务数据接口
     */
    @PostMapping(value = "/selectEmploymentByIdnumber")
    @ResponseBody
    public JSONObject selectEmploymentByIdnumber(String idnumber) {
        //定义返回的结果字符串
        String res = null;
        //获取token
        String token = getToken();
        //token不为空 调用HttpClientUtil方法
        try {
           /* //判断idnumber是否为空
            if (idnumber == null || idnumber == "") {
                return  emptyBackError();
            }*/
            //采用跟postman方式拼接的方式传递参数
            GETEMPLOYMENT_URL = GETEMPLOYMENT_URL + "?idnumber=" + idnumber;
            res = HttpClientUtil.sendPut(GETEMPLOYMENT_URL, "utf-8", token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res == null) {
            return comeBackError();
        }
        return JSONObject.parseObject(res);
    }

    /**
     * 查询认证证书接口
     */
    @PostMapping(value = "/selectCertificate")
    @ResponseBody
    public JSONObject selectCertificate(String numbers, String username) {


        //定义返回的字符串
        String res = null;
        // 获取token
        String token = getToken();
        //token不为空 调用HttpClientUtil方法
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        //传入number,username`
        RequestBody body = RequestBody.create(mediaType,"");
        //认证证书的路径
       GETCERTIFICATION_URL = GETCERTIFICATION_URL + "?numbers=" + numbers + "&username=" + username;
        Request request = new Request.Builder()
                .url(GETCERTIFICATION_URL)
                .method("POST", body)
                .addHeader("Authorization", token)
                .build();
        try {
           /* //判断numbers,username是否为空
            if (numbers == null || numbers == ""&& username == null || username == "" ) {
                return  emptyBackError();
            }*/
            Response response = client.newCall(request).execute();
            //打印响应的信息
            res = response.body().string();
            //采用跟postman方式拼接的方式传递参数`
            /*res = HttpClientUtil.sendPut(GETCERTIFICATION_URL,null,"utf-8",token);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res == null) {
            return comeBackError();
        }
        return JSONObject.parseObject(res);

    }


    // 定义返回错误信息
    public JSONObject comeBackError() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "404");
        jsonObject.put("msg", "未查询到当前用户信息");
        return jsonObject;
    }

    /*//定义传入的参数为空的错误信息
    public  JSONObject emptyBackError(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","404");
        jsonObject.put("msg","证件号码不能为空,请重新输入");
        jsonObject.put("msg","证件号以及名字不能为空,请重新输入");
        return jsonObject;
    }*/

    // 获取token方法
    public String getToken() {
        //redis查看是否有token
        String token = redisUtils.get("smallRoutineToken");
        //token为空值的时候
        if (token == null || token == "") {
            BufferedReader in = null;
            StringBuilder result = new StringBuilder();
            net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
            jsonObject.put("userName", ACCOUNT_NUMBER);
            jsonObject.put("passWord", PASSWORD);
            try {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(GETEXTTOKEN_URL)
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                Response response = client.newCall(request).execute();
                in = new BufferedReader(new InputStreamReader(response.body().byteStream(), "utf-8"));
                String line;
                //  获取 响应行数据
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 返回数据json 格式化
            JSONObject jsonObject1 = JSONObject.parseObject(result.toString());
            // 返回json中的data属性
            token = jsonObject1.get("data").toString();
            redisUtils.set("smallRoutineToken", token, 1800L);
        }
        return token;
    }

}
