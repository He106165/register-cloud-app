package com.censoft.systemManage.util;
/**
 * 应用系统管理模块公用方法
 * */
public class SystemManageUtil {

    /**
     * 生成应用系统唯一标识
     * systemName 应用系统名称
     * */
    public static String createSystemIdentify(String systemName){
        String result ="";

        try {

            result=EncryDES.byteArr2HexStr(systemName.getBytes()).substring(0,16);

        } catch (Exception e) {

            e.printStackTrace();

        }
        return  result;
    }


}
//e5ba94e794a8e6b3a8e5868ce7b3bbe7bb9f
