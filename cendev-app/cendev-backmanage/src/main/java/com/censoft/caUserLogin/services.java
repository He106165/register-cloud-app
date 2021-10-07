//package com.censoft.caUserLogin;
//
//import com.sca.sign.AuthInfo;
//import com.sca.sign.Authentication;
//import com.sca.sign.SignException;
//
//public class services {
//
//    public static final int AUTHTYPE_LOCAL			=	0;		//本地帐号
//    public static final int AUTHTYPE_CA				=	1;		//数字证书
//    public static final int AUTHTYPE_RADIUS			=	2;		//Radius
//    public static final int AUTHTYPE_LDAP			=	3;		//LDAP
//    public static final int AUTHTYPE_PROXY			=	4;		//代理认证
//
//    public static void main(String[] args) {
//
//        String token = "ST-2-cuijRt2jqOsVenvwZoobIFCvrfnIYqoiJlj";
//
//        String account = "";
//        int authType = -1;
//
//        Authentication auth = new Authentication("127.0.0.1"/*安全认证网关地址*/, 80);
//
//        try {
//            //取认证信息
//            AuthInfo authInfo = auth.GetAuthInfo(token);
//            if(authInfo != null) {
//                authType = authInfo.getAuthType();
//                if(authType == AUTHTYPE_CA) {
//                    //证书认证
//                    String certInfo = authInfo.getUserInfo();
//                    String item = "CN=";
//                    String value = "";
//                    int pos1 = certInfo.indexOf(item);
//                    if(pos1 >= 0) {
//                        int pos2 = certInfo.indexOf(",", pos1);
//                        if(pos2 > 0) {
//                            value = certInfo.substring(pos1 + item.length(), pos2);
//                        } else {
//                            value = certInfo.substring(pos1 + item.length());
//                        }
//                    }
//
//                    account = value;
//                } else {
//                    //其他认证类型
//                    account = authInfo.getUserInfo();
//                }
//
//                System.out.println(account);
//
//            } else {
//                //连接安全认证网关失败
//                System.out.println("连接安全认证网关失败");
//            }
//        } catch(SignException e) {
//            //没有找到令牌所对应的认证信息
//            System.out.println("没有找到令牌所对应的认证信息");
//        }
//
//
//    }
//}
