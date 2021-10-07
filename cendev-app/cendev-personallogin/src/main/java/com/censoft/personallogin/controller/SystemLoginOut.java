package com.censoft.personallogin.controller;

import com.google.gson.Gson;
import okhttp3.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 退出系统
 * 以OkHttpClient 方式调用为例
 **/
public class SystemLoginOut {
    /**
     * 参数说明：
     *  response 当前响应
     *  url 登出系统接口（可能发生变动，需动态调用）
     *  url 示例 ：
     *  String url = "http://registerApi.censoft.com.cn/otherlogin/token/countToken?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjcmVhdFRpbWUiOjE2MTEyMjAwMDMsImFjY291bnROYW1lIjoiZGV2IiwiZXhwIjoxNjExMjIzNjAzfQ.hmCTPAl4v0f6RgNWd1p2w_WxsgRoGiR0JX_p-Y9C0JA";
     * 返回数据说明
     *  返回数据类型为Map ，如果Map为空，说明接口调用失败
     *
     **/
    public static Map SystemLoginOutMethod(HttpServletResponse response, String url){
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        MediaType mediaType = MediaType.parse("text/plain");

        Map res = new HashMap();
        try {
			OkHttpClient client = new OkHttpClient().newBuilder()
					.build();
			RequestBody body = RequestBody.create(mediaType, "");
			Request request = new Request.Builder()
					.url(url)
                    .method("POST", body)
					.build();
			Response restresponse = client.newCall(request).execute();
            InputStream inputStream = restresponse.body().byteStream();
            //获取响应数据，判断token是否有效
            if (inputStream != null) {
                bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
                Gson gson = new Gson();
                res=gson.fromJson(stringBuilder.toString(), res.getClass());
                System.out.println(res);
            } else {
                //获取数据为空
                return null;
            }
            //将接口response中的header添加到本次响应中
            Set<String> names = restresponse.headers().names();
			for (String name : names) {
				response.setHeader(name,restresponse.header(name));
			}
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
        return res;
    }

    public static void main(String[] args) {
        String url = "http://registerApi.censoft.com.cn/otherlogin/token/countToken?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjcmVhdFRpbWUiOjE2MTEyMjAwMDMsImFjY291bnROYW1lIjoiZGV2IiwiZXhwIjoxNjExMjIzNjAzfQ.hmCTPAl4v0f6RgNWd1p2w_WxsgRoGiR0JX_p-Y9C0JA";
        // 实际调用需要换成response 对象
        SystemLoginOut.SystemLoginOutMethod(null,url);
    }
}
