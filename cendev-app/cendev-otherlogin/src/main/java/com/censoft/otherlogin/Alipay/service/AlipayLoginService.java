package com.censoft.otherlogin.Alipay.service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.censoft.common.json.JSONObject;
import com.censoft.otherlogin.Alipay.domain.AlipayUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Service
public class AlipayLoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlipayLoginService.class);



    /**支付宝网关*/
    private static final String ALIPAY_BORDER_DEV = "https://openapi.alipaydev.com/gateway.do";
    private static final String ALIPAY_BORDER_PROD = "https://openapi.alipay.com/gateway.do";
    /**appID**/
    private static final String APP_ID_DEV = "xxxxxx";

    //测试环境
     private static final String APP_ID_PROD = "2021001194602100";
    /**私钥*/
    private static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCKzu5cG0GUg+LDmxFVB8Z5m4gQFZV7onStVq7wA71ul7dWzpGf0VVM1mqkT8J394hXSHC9/dZUOG97ERvON/a1EOkhZewhr0XH2aUKryVlUXFlz4iV2mKNfaz4ksP8+kREKtvWhtkK0coeGqDUgMEPl7PIZ0ZjdcoZwAKDciHw+7bLRDv9DOVcDLn5sWeRDvdEXh3U4kNrExMyP6Zvoo44dXHwhMdcBnOR0iYSucLqlWG2i4qx/7wA+yyJ9sOOxqt9/NzzeTv2puOfsboAZJrEI7sMVEiFMj6R/gHgChpILvbNKLV1CZfDp4u3ioVsY2da9CamJFvefWeKuluIUY6VAgMBAAECggEASvTfpoFZcpedlr9gJqiiNv0UBouBQ1hA5XHP2fBLFTN8X7YA0Wq7Q1juEXi4rQvQZkHKD+7JFIW9PZnJiByUbsaI/e4KC06Ywgj3pMNb0FCgibf+K93YJkvdYgXvK+cBgkYanMgI4FzVZlg+zUIR/xeOkAOk8QSkdC9JrC2vwm9/OXM0z10ZmLckGAe4OvbBjofS2soNPVNEr3yqL3e2QbISbezjJ/snHXT+bPAfJqaV8kKTLvirG/XHeb2z97b0Qrlav9ftqQQTZ/nBJQLDFTkj6pIepUdml3EYx7IYDXKZBYJiUhbrzHn0lGeB+Tm5ZWRZ9BkOPcyE63NVVWfkaQKBgQDGDfox3J6epnlSFF92OJqLDWwre/ffIkZSeJtocpLb16JzmpZ4z+GspKbbxSEhMSBkFD/bwrQnrSJf8OH0ljBg2ArcmpUrNOwPVcRzR3bSqlZBu8+Byt0mazGKPs9I1eB7apTq8LQXwRjnjVfExzXZWNASISXBCu0VQr2QD65gcwKBgQCza3zVJKB//KYMZUy7jQlYkrdQnLtmyq8jV/3XRO+YgBmqaRcyv9VULm47SVa/KPHEd6CrLTAMJEWRpZ2vsmJ/pP5GA21g+LF3P7CmYANaD+fEbmEIeM8zJ/Gh4hwMQnkG771bZ3Ztto/ZULllxTmwNzHQfZ+UqDp+7X4Grkm61wKBgBQv6IicjC5vmX4cRX6vVlFGFPh5slI0FCbj1DNZOk7eM9jEsQGvbrlCLx/HgBD6COsqhTfJKMPCifZtfxqHFHdKjhDeUMYOpuEJRPTbG+ltI02f7w6ztsbrtij5LfemlTL/Mh6gWjrxm7A+X+vWwk5dFxhB+K8she3c16WPc56xAoGAVr2siE9A2R+IF44R3sp8rjmgLnTXlQaATZIIwr4tJ4km32AXOw3LUPtxh3AZZCmjWNyM7LTX+ca/a36qEXsoa5WU6LxXcP5KreA4wvbAKBZwcmL6vmHJqm2xQBZUgQjSAix9W2dVtg0A1otUVoNXNH46sxVIxVHfleWze/9e3dkCgYEAgqlctO2Tbnh/XLAjRGbkkz+UsE16BYsWut4vWIBHV4DINlqCMCe//1TfPjt4/vkt/fhfVbgmDOAWioWUBpvHkxlPM2JMpYQy5rLuFFzoqrTjVOkEcvsLVrxrPe4AlTqxvZ1dKjOLAcso0DEgqmHsio0L/SGUR3La7t1zlv15Y3w=";
    /**公钥*/
    private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmecmP+SvtkH3uxX6LDxPCT7eJt2VMygK0551HOJoihSCL+g41Xs1N6pFCg1QOjkStrAIBTKHser4e4ietKHN/PEyGRNEr6kh9wRVdqh5E59BJZut1fsbv77W7zOXNMTgeyG+WOsy17D/WatXKo7liydEpvk+5QUkl5112BDde++c9auWKkly4vLWbQZuTqb4VUP1AAjTMVDitNYzuuDP3HLRaMM28rXhoOoN7ZeWPaaqGJXEjbPNh3Skm94b6jYbA3cM367d53hFnU160KNGzRI8EYnLuRReCdJCfVfHYDd81HE7L3INvFuCwj1CyrXBY3CdVciHYgPb93r//HL37QIDAQAB";

//    private static final String APP_ID_PROD = "2021002116661993";
//   // *私钥
//    private static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCT+hQ54aXWwmv10ZhwrcLJMk/E7drpzj4lwLs6rSqplrMo8cTtnAMxs6URpV7n4OPj5I5mrkF793iLv4KmxFBgfuqCtVZC1SF9tuYHTeCigUvUQJZ2zOPnHam+C1Te/yNsnUZCemJKv7bWIA7okyVG2JUrt3Xm8GF4CU7J85QS9ywXqe9gEfDOHim2hkPgw+2GTHEK8gZc04fgTa7K6CCqoPpL9llTOCPiOkZ//Iu0speimOAbeOPqAiQio0fsn7CHs4vLzMc+qy90rP1aj+hR7PUi7NgPSmrkwiNGs3hHZN80OS+jSLyT/3yalpM9x61yQX0NI/xF8KzbiTGH7mgFAgMBAAECggEAEs04mq4gCyyC2SX7b320fSnmVsg+A20QGm3lnCv6erfKJc6qTeJ/yWXg+GABsgayDfexXDyR13V3HJKFwDi7WbdmhpsRzcYUzktZ6N2tSBiTkPFv7MJVxrzPH3WDIB5c2uUU00eHi6XWTliebFCd8OV99qMF2QT77WuJVkGbs9j/5r2EwC/EpKPPODpH6nUkQQAqWcgB1uPpK6verfUmBsiv5gB1O6bduBJgRG6kaUjMw6jFhC9pI+Md2EaQLYLXLupeiuo46D3hcptQoX1mVCR2gjIRE2u3DyGoPrk+yTFgELRHMoMCBGpyUccOueswNmm+V6WJtdRJGzcDfA/k4QKBgQDkxISIKlhyqcaQpyMo+gkQ/3JhpEjy+ObiLHWlj1WruJmVXdiKAj4BbEatc6wKBcEZKog2iL5vJII2nbsvq4IM2wiPTBkxPVW7eS6cejLrDnfXfbg0zOcy0CaGXiUtM47RUJm5ft/rTXUp2jzkCb+c2R6P6bU0wJZS7zZDJ4PZWQKBgQCll4mE6sGdPeBBp9P9hMeg+PVRl9a9xfEiSZf0w08w2de4l6e3o/srId2TPlYy688HemKWi1oobTJmumsUCky/Ybq2fMnB5Hjo4moUm65+3GGinvJwXTPEbb3Zdg1uolOiCfrlMZeP1zRPu0v89DX9y6YtYUrElsjYSzG80OUCjQKBgQDJUvS7jGCYUDbyGFTF+/08m0HDEjwsr8UoqC3EzLZ9UBDBNXyaP8znna05ekGd9GByUE7dh0voLb32aKtSEWJ7A8utqC/HeI3ZVZUimhvzZqEq4HXa6bRRsIMm3vViqSv3ozIV38UZo+oHMgtCOB4L37uYtPkGerIQPHqJcN4EyQKBgGE/VWvR0lLd+82TGlRQnkxS6P86BMr+bFaOkccUzHUL+jxWFBEtJFx4TwAiI81T3wmtPijbPd9wAaGC6U+jnN7oLhbtwi80Vi2uik3//8JQ+qXeYtLKaZR6PnxvcAQBIMyQv9/J+BBpejc4769mXC7oYndUI+zAAMPFpTgWFeghAoGAKh6NMTctfFxJmvqw3A/mznumZsEdx4mjmPWPit+1D7hU6hTiWgYsJHezL0EaG/i/sYaK/LvVQY/nVRCGJMTxaVanLbxsungLEfE4qIzwcgAH9UL+MyCYpI6D1pKkaj4bFVgN8oZuNjCN/KKEXTU6V2twkt+f8fx9UkY9hgL4drM=";
//   // *公钥
//    private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzMlCwKmb4uIuiWx+zZWnG/r6lIcEcqwrQ+h1uHvhlf8HE2sL2QIyE8e2Yf5mwUsJGuth+UxIh5D0HCEG57stjB9XIlFRAtisQn52yVw1Z2YB8u8Jjej5GqO01PMzMWunIElsW3DCz9yHdoylB1u4TABtCQrVHTJAWDWvTDsfJNxeuwwCiKKUya6GC+ulTlKgk7KFlquF1+BH1eXDa0+t/w7WL+QBsb75VyWmk4kbvsKJ9ASEXknQcMcO+Ag+s0gSqOT3tQllHNRpZXVVYHO4AF1sQGkOKeX/gtsF7MqP5aYbl/LPhCfRCRMcok259Hq+ESxj0sFovcLA+6TElxqFXwIDAQAB";

//正式环境

//private static final String APP_ID_PROD = "2021002116661993";
//    // private static final String APP_ID_PROD = "2021002115613048";
////*私钥
//
//    private static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC7kwbOPsJOH+uyBSUWXqK6WEcWcyPtE7oMqkotqnQlokHN09KkRKLhi8e1oGLl5rtlNfwc2KJuzqwNX9h10qW+vEMdrmq1H5kWsNBPVpaCyVkND/Zj8YBaI6ckfznepsCcm5VLjuz2QVz6wRBZKZF8Ye2Su5XBiuZhZ8R0q29PNkCeFOHhJ7YTpf4DyAYxTkGeF/2wrPv9wFIATJ8XQH0Y4QrQlVk36QsXhejcnfk4UxwJKUvVKXDYMHGIunSxMlehpYZm3nss1UQA7pSsmMgysWOdFE/KGJqgv1OxosMD7siQfuEqJVMjVxZEp2JnTMbWrk7sS457jZicI7hVuNBxAgMBAAECggEAHxOq6vzG4H61ZBcqH9yqBUme7X9CEoNULckA5TP6bknFsT5JBeJBQjEQc20RNZnWrlwVWwohT056fvTEPjEaONGkJQTCtSN5D6NXv+1OnniRgnKEleZrJ4QEqy1Q/yyNeOTmBVC2vc3yg7D87mjOq9blr4vYseo7ZSP2DoZguLSxWN6MWb20a1DpO9bQoE4o/VI05c4b/m5fDZvwZHUojb3d6FAICgXyrDJyMgPXpt63Vr/zrACgNVBRz8+6IRf21aQ25pnQZ9CaUEto3JTxmurjaN17w7hodguMekrIoWEpmKeRfLmME9ufVMRCX61zX8TvA8RqQ3BL0yKkapkrCQKBgQDzY5z3+r9YzZJ0alMzrTPnfYMGLN5dBOhWBZd0s1musyRq7s3cU5dzHTXQECW4ncQBmly08TBzSqlCUjFTHfpo3sidiWTmlgW6UXxkKY59tUUmSqp+VnX/OBPGdhZq33vru3BOTuSNKEHB7jYpFZs3t+6QfsjbWfzJdlXh6ZOsIwKBgQDFSxGzqnkx0hfEtoyfzINhiu76oHP+0uA/mU+YcFBQPSCNcMCMQ0mbsxVthh1k5Uoor5sRTceRYfH2S3ulfjuQsqoQjLrpkdXyoR4iLV2bpUGmz8Z2Fe+O8xFAO80o18fn3uJ7LZtif+Bk+VKL+1vCxbb0Zxxr7ogaXzoRJbTgWwKBgEkRf79m1Ok5Fh3DCeV6uxllMd+5uqwcXA/vL9AGRAdRmr5LoRvjMJWLkGs9XcW1/3EnwWtE+XGzWWsit9InLtUrBPlelR8wrNmJuxlq7G9K606aneK93EDZuf4b15EQNOjJZebsgmrqSguJk1L0PJwWqRVSKsTHmD93OkNPV1kXAoGBAK8SBTxEGtVt0++X+cNXbIIjEhtORIAsShfFiI8BoxMAmyFHm2Ub1HaYfHw61VyR5rOh2F2LgnrsyhQ/ORJKqLqnACg9aSMYaRyrghzb2sPh6hcHKtngbzqPUJCKQGxsxhRogNrDRq4qQUn4wBo9S1jTr5zC3sKb4SRduh47Mn0fAoGAYebq3z6sZK9+oFc2NVPYFpBrmpxaL6j8ckO0j0P/84HfhhIw28jAXSzf9DkzDALpZuCTMOGcXWBp/kFqPjhvrzKp1W9p2oHqHaan4QchlblsLCzuJga/q2SVghAT/s5cJenJew30Xt3X1fU+Udu29D+MbgP1cZO+Pr58kEXx0cI=";
////*公钥
//
//    private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzMICwKmb4uIuiWx+zZWnG/r6lIcEcqwrQ+h1uHvhlf8HE2sL2QIyE8e2Yf5mwUsJGuth+UxIh5D0HCEG57stjB9XIlFRAtisQn52yVw1Z2YB8u8Jjej5GqO01PMzMWunIEIlsW3DCz9yHdoylB1u4TABtCQrVHTJAWDWvTDsfJNxeuwwCiKKUya6GC+ulTIKgk7KFlquF1+BH1eXDa0+t/w7WL+QBsb75VyWmk4kbvsKJ9ASEXknQcMcO+Ag+s0gSqOT3tQIHNRpZXVWYHO4AF1sQGkOKeX/gtsF7MqP5aYbl/LPhCfRCRMcok259Hq+ESxjOsFovcLA+6TElxqFXwIDAQAB";


    /**Alipay客户端*/
    private AlipayClient alipayClient;

    public void afterPropertiesSet() throws Exception {
        alipayClient = new DefaultAlipayClient(ALIPAY_BORDER_PROD, APP_ID_PROD, APP_PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");
    }

    /**
     * 根据auth_code获取用户的user_id和access_token
     * @param authCode
     * @return
     */
    public String getAccessToken(String authCode) {
        alipayClient = new DefaultAlipayClient(ALIPAY_BORDER_PROD, APP_ID_PROD, APP_PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");

        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(authCode);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            return oauthTokenResponse.getAccessToken();
        } catch (Exception e) {

            LOGGER.error("使用authCode获取信息失败！", e);
            return null;
        }
    }

    /**
     * 根据access_token获取用户信息
     * @param token
     * @return
     */
    public AlipayUserInfo getUserInfoByToken(String token) {
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest ();
        try {
            AlipayUserInfoShareResponse response =  alipayClient.execute(request, token);
            if (response.isSuccess()) {
                //打印响应信息
//                System.out.println(ReflectionToStringBuilder.toString(response));
                AlipayUserInfo alipayUser= new AlipayUserInfo();
                if(response.getBody()!=null){
                    String jsonString = JSON.toJSONString(response.getBody());
                    String json = (String) JSON.parse(jsonString);
                    //封装支付宝对象信息
                    Map<String,Object> map=JSON.parseObject(json, Map.class);
                    Map<String,Object> usermap = (Map<String, Object>) map.get("alipay_user_info_share_response");

                    String u = usermap.get("user_id").toString();
                    String nickname = usermap.get("nick_name")==null?"":usermap.get("nick_name").toString();
                    alipayUser.setUserid(usermap.get("user_id").toString());
                    alipayUser.setNickname(nickname);
                    alipayUser.setContent(json);
                }
                return alipayUser;
            }
            LOGGER.error("根据 access_token获取用户信息失败!");
            return null;

        } catch (Exception e) {
            LOGGER.error("根据 access_token获取用户信息抛出异常！", e);
            return null;
        }
    }


    public String getAlipayIDByToken(String token) {
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest ();
        try {
            AlipayUserInfoShareResponse response =  alipayClient.execute(request, token);
            if (response.isSuccess()) {
                //打印响应信息
//                System.out.println(ReflectionToStringBuilder.toString(response));
                if(response.getBody()!=null){
                    String jsonString = JSON.toJSONString(response.getBody());
                    String json = (String) JSON.parse(jsonString);
                    //封装支付宝对象信息
                    Map<String,Object> map=JSON.parseObject(json, Map.class);
                    Map<String,Object> usermap = (Map<String, Object>) map.get("alipay_user_info_share_response");
                    return usermap.get("user_id")==null?"":usermap.get("user_id").toString();
                }
            }
            LOGGER.error("根据 access_token获取用户信息失败!");
            return "";

        } catch (Exception e) {
            LOGGER.error("根据 access_token获取用户信息抛出异常！", e);
            return "";
        }
    }





    public static void main(String[]a){

        String jsonString = JSON.toJSONString(" {\"alipay_user_info_share_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"avatar\":\"https:\\/\\/tfs.alipayobjects.com\\/images\\/partner\\/T1ZLFfXehaXXXXXXXX\",\"city\":\"北京市\",\"gender\":\"m\",\"province\":\"北京\",\"user_id\":\"2088812726716264\"},\"sign\":\"KNf6aTUtXskKiRIZlnjS2ThJG6rmsOyxhtralgy7chudAv8WQtWDpFe2QGFiYFMsU78xT89F/Oz4nGmPhcs6XV2BTb/O/lWsRiBNUdFkkjQ0tkMuzPbrp2y3th/WvK9WbtieyuQx/kbS/9pSnOvWOGUZEgSjYCWB/Qu04jQksdjg+to/teoug3gtFybKyN9hhSAqpQ3G9ahBr5tJS5rtlSkPsPnkbfUCTgNc6+dIqSSANlN2OjX+sXUX1udYa5olMjlogn558cXx1NrTtTUiFErO6A2hxd3lPZDDg7ckuYYQHxDQfFuk9s9SsZioaSuegzCqj+bjVl2v+JjosD3zhQ==\"}");
        String json = (String) JSON.parse(jsonString);
        //封装支付宝对象信息
        Map map=JSON.parseObject(json.toString(), Map.class);

        AlipayUserInfo alipayUser=JSON.parseObject(map.get("alipay_user_info_share_response").toString(), AlipayUserInfo.class);
        System.out.println("ss");




    }



}