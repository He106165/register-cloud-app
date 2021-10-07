package com.censoft.Util;

import com.censoft.userManage.AboutUserInterface.CertUserInfo;
import gov.zwfw.iam.client.TacsHttpClient;
import gov.zwfw.iam.real.client.RealClient;
import gov.zwfw.iam.real.client.TacsRealClient;
import gov.zwfw.iam.real.request.NaturalRequest;
import gov.zwfw.iam.real.response.RealResult;
import net.sf.json.JSONObject;

public class BackManageMethod {


    /**
     * 实名认证公用方法 自然人初级实名
     * 是否通过
     * */
    public static boolean certFourData(CertUserInfo user,String url){

        NaturalRequest request = new NaturalRequest(user.getUserName(), user.getCertNo(), user.getEffDate(), user.getExpDate());
        // linux 环境下的证书路径
        TacsHttpClient.init("/usr/local/register/file",url);
        RealClient realClient = null;
        try {
            realClient = TacsRealClient.getInstance();
            RealResult result = realClient.simplePattern(request);
            if (result != null) {
                JSONObject jsonObject=JSONObject.fromObject(result);
                System.out.println(jsonObject);
                if(jsonObject.get("code").equals("70000")) return true;
          }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    /**
     * 实名认证接口   俩项实名
     * */

    public static boolean certTwoData(String userName,String certNo,String url){
        // linux 环境下的证书路径
        TacsHttpClient.init("/usr/local/register/file",url);
        try{
            TacsRealClient realInstance = TacsRealClient.getInstance();
            NaturalRequest naturalRequest = new NaturalRequest();
            naturalRequest.setCertNo(certNo);
            naturalRequest.setUserName(userName);
            RealResult realResult = realInstance.simpleTwoPattern(naturalRequest);
            JSONObject jsonObject=JSONObject.fromObject(realResult);
            if(jsonObject.get("code").equals("70000")) return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }





        public static void main(String[] args) throws  Exception{
        TacsHttpClient.init("/file","http://202.205.188.33:8443/uc-agent");
        TacsRealClient realInstance = TacsRealClient.getInstance();
        NaturalRequest naturalRequest = new NaturalRequest();
        naturalRequest.setCertNo("");
        naturalRequest.setUserName("");
        RealResult realResult = realInstance.simpleTwoPattern(naturalRequest);
        System.out.println(JSONObject.fromObject(realResult));




//        CertUserInfo user=new CertUserInfo();
//        user.setCertNo("152625199504262510");
//        user.setEddDate("20160607");
//        user.setUserName("贺鹏飞");
//        user.setExpDate("20260607");
//        certification(user,"http://202.205.188.33:8443/uc-agent");
    }

}
