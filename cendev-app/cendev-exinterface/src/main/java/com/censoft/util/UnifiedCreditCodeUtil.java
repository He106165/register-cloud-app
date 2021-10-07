package com.censoft.util;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Description：统一信用代码的util
 * @Author: lcj
 * @Date: Created in 2020-06-30 10:42:26
 */
public class UnifiedCreditCodeUtil {

    /**
     * 最后一位编码
     */
    private static final List<String> LAST_CODE = Arrays.asList(
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "J", "K",
            "L", "M", "N", "P", "Q", "R", "T", "U", "W", "X", "Y"
    );

    /**
     * 加权因子
     */
    private static final Integer[] FACTOR = {1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};

    public static boolean checkUnifiedCreditCode(String unifiedCreditCode) {
        unifiedCreditCode = trim(unifiedCreditCode);
        // 校验身份证
        if (StringUtils.isBlank(unifiedCreditCode) || unifiedCreditCode.length() != 18) {
            return false;
        }
        final String upperCaseCode = unifiedCreditCode.toUpperCase();
        // 统一社会信用代码由18位阿拉伯数字或英文大写字母表示（不包括I,O,Z,S,V以防止和阿拉伯字母混淆）-->V：？？？关我毛事？
        if (upperCaseCode.contains("I") || upperCaseCode.contains("O") || upperCaseCode.contains("Z") || upperCaseCode.contains("S") || upperCaseCode.contains("V")) {
            return false;
        }
        char[] chars = upperCaseCode.toCharArray();
        int sumCode = 0;
        for (int i = 0; i < 17; i++) {
            String code = String.valueOf(chars[i]);
            int lastCodeIndex = LAST_CODE.indexOf(code);
            Integer factorNum = FACTOR[i];
            sumCode += (lastCodeIndex * factorNum);
        }
        int modCode = 31 - sumCode % 31;
        String lastCode = LAST_CODE.get(modCode % 31);
        return lastCode.equals(String.valueOf(chars[17]));
    }

    /**
     * 去空格
     *
     * @param str 处理字符串
     * @return 结果字符串
     */
    private static String trim(String str) {
        return str.replaceAll("\n", "").replace(" ", "").trim();
    }
}