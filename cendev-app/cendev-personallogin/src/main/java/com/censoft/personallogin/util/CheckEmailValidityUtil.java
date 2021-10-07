package com.censoft.personallogin.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 校验邮箱：1、格式是否正确 2、是否真实有效的邮箱地址
 * 步骤：
 * 1、从dns缓存服务器上查询邮箱域名对应的SMTP服务器地址
 * 2、尝试建立Socket连接
 * 3、尝试发送一条消息给SMTP服务器
 * 4、设置邮件发送者
 * 5、设置邮件接收者
 * 6、检查响应码是否为250(为250则说明这个邮箱地址是真实有效的)
 * @author Michael Ran
 *
 */
public class CheckEmailValidityUtil {

    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }

    public static void main(String[] args) {
        System.out.println(checkEmail("wzp406599321@163.com"));
    }
}
