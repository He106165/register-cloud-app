package com.censoft.smallRoutine.util;


import java.io.IOException;
import java.nio.charset.Charset;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Slf4j
public class HttpClientUtil {


    public static String sendPut(String url,  String encoding,String token) throws ParseException, IOException {
        String body = "";
        CloseableHttpResponse response = null;
        // System.err.println(putData.toJSONString());//打印了一下我推送的json数据

        //client 客户端   ---------------------------- -----   // 创建默认的httpClient实例.
        CloseableHttpClient client = HttpClients.createDefault();
        System.out.println("client 客户端:" + client);
        //PUT     - 向指定资源位置上传其最新内容---------------// 创建httppost
        //向指定资源位置上传内容
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // httpPost.setEntity(new StringEntity(JSONObject.toJSONString(putData)));

        //StringEntity是httpPut对象的一个实现类
       //  httpPost.setEntity(new StringEntity(putData.toString(), encoding));
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Authorization", token);

        //客户端执行。发送数据
        response = client.execute(httpPost);
        //通过response里的getEntity()方法获取客户端的返回值，然后return进行返回；
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        response.close();
        return body;

    }
}


