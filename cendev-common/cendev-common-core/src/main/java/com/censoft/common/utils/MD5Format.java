package com.censoft.common.utils;/**
 * @Author: hepengfei
 * @Date: 2021/3/8 16:08
 */

import com.qiniu.util.Md5;

/**
 * @ClassName
 * @Description TODO
 * @Author www10
 * @Date 2021/3/8  16:08
 * @Version 1.0
 **/
public class MD5Format {

    public static String MD5(String input){
        if(input == null || input == "")
            throw new RuntimeException("run time error,password is null,md5 is error");

        return Md5.md5(input.getBytes());
    }

    public static void main(String[] args) {
        System.out.println(MD5("qwer1234"));
    }
}
