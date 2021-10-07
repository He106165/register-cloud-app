package com.censoft.sendMag.emailManage.Util;

import com.censoft.common.utils.bean.BeanUtils;
import com.censoft.sendMag.emailManage.domain.MailSendInfo;
import com.censoft.sendMag.emailManage.domain.MailSendLog;
import com.censoft.sendMag.emailManage.domain.NoteSendInfo;
import com.censoft.sendMag.emailManage.domain.NoteSendLog;
import net.sf.json.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class MsgSend {


    public static String getToken(String url,String name ,String password) {
        try{
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            JSONObject data= new JSONObject();
            data.put("userName",name);
            data.put("passWord",password);
            RequestBody body = RequestBody.create(mediaType,data.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            String token1 = JSONObject.fromObject(string).get("data").toString();
            return token1;
        } catch (IOException e){
            return null;
        }
    }


    public  static boolean sendMsg(String data,String token,String url){
        try{
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType , "sendEmailAndSmsDto="+data );
            Request request = new Request.Builder()
                    .url(url)
                    .method("POST", body)
                    .addHeader("Authorization", token)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            String success = JSONObject.fromObject(string).get("success").toString();
            if(success.equals("true")) return true;
//
//
//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .build();
//            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//            RequestBody body = RequestBody.create(mediaType, "sendEmailAndSmsDto={
//                    \"mailTo\":\"www.1061657846@qq.com\",
//                    \"phoneTo\":\"13601270693\",
//                    \"subjectTitle\":\"测试\",
//                    \"emailContent\":\"邮件测试\",
//                    \"phoneContent\":\"测试短信 【教留服】\",
//                    \"sendType\":\"1\",
//                    \"type\":\"1\"
//        }");
//        Request request = new Request.Builder()
//                .url("http://rzzcyh.crs.jsj.edu.cn:8081/interface_tomcat/api/emailAndSms/sendEmailAndSms")
//                .method("POST", body)
//                .addHeader("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0Iiwicm9sZXMiOiJ1c2VyIiwiaWF0IjoxNjA1ODYzMzAxLCJleHAiOjE2MDU5NDk3MDF9.SPhyAtUWViSRZ1Ja9E0zxNN4Jf4UrolSvXI6myQ6znM")
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .build();
//        Response response = client.newCall(request).execute();



        }catch (IOException e){
            System.out.println("发送失败短信或邮件失败");
            return false;
        }
        return false;
    }

    public static  void initData(MailSendInfo mailSendInfo, MailSendLog mailSendLog, NoteSendInfo noteSendInfo, NoteSendLog noteSendLog, boolean status, Map map){
        // 类型为1 同时发送
        if(map.get("sendType").equals("1")){
            //填充 邮件发送信息
            mailSendInfo.setContent(map.get("emailContent").toString());
            mailSendInfo.setReveiceEmail(map.get("mailTo").toString());
            mailSendInfo.setTheme(map.get("subjectTitle").toString());
            //邮件日志
            BeanUtils.copyBeanProp(mailSendLog,mailSendInfo);
            mailSendLog.setStatus(status == false ? "0" : "1");

            //填充 短信发送信息
            noteSendInfo.setContent(map.get("phoneContent").toString());
            noteSendInfo.setReveicePhone(map.get("phoneTo").toString());
            BeanUtils.copyBeanProp(noteSendLog,noteSendInfo);
            noteSendLog.setStatus(status == false ? "0" : "1");
        }
        // 类型为2 邮件
        if(map.get("sendType").equals("2")){
            //填充 邮件发送信息
            mailSendInfo.setContent(map.get("emailContent").toString());
            mailSendInfo.setReveiceEmail(map.get("mailTo").toString());
            mailSendInfo.setTheme(map.get("subjectTitle").toString());
            //邮件日志
            BeanUtils.copyBeanProp(mailSendLog,mailSendInfo);
            mailSendLog.setStatus(status == false ? "0" : "1");
        }
        // 类型为3 短信
        if(map.get("sendType").equals("3")){
            //填充 短信发送信息
            noteSendInfo.setContent(map.get("phoneContent").toString());
            noteSendInfo.setReveicePhone(map.get("phoneTo").toString());
            BeanUtils.copyBeanProp(noteSendLog,noteSendInfo);
            noteSendLog.setStatus(status == false ? "0" : "1");
        }

    }




    public static void main(String[] args) {
        String userName="test";
        String passWord="test123456";
        String url ="http://rzzcyh.crs.jsj.edu.cn:8081/interface_tomcat/tokenLogin";
        System.out.println(getToken(url, userName, passWord));
//
//        "sendEmailAndSmsDto={
//                \"mailTo\":\"www.1061657846@qq.com\",
//                \"phoneTo\":\"13601270693\",
//                \"subjectTitle\":\"测试\",
//                \"emailContent\":\"邮件测试\",
//                \"phoneContent\":\"测试短信 【教留服】\",
//                \"sendType\":\"1\",
//                \"type\":\"1\"
//    }"
    }
}
