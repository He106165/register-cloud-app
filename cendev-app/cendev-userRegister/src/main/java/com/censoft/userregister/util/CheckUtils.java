package com.censoft.userregister.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * <br>
 * <strong>Title :</strong> CheckUtils.java <br>
 * <strong>Description : </strong> <br>
 * <strong>For Examples :</strong> <br>
 * <strong>Create on : 2016年4月21日 下午4:51:40<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Inspur Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author <a href=mailto:duanqp@inspur.com></a><br>
 * @version <strong>V1.0</strong>
 * <p>
 * <PRE>
 * </PRE>
 * <p>
 * -------------------------------------------<br>
 * Change History:[Formatter: author date description] <br/>
 * 1<br>
 * 2<br>
 * 3<br>
 */
public final class CheckUtils {
    /**
     * 邮箱正则
     */
    private static final Pattern emailRegex = Pattern
            .compile("^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))$");
    /**
     * 手机正则
     */
    private static final Pattern phoneRegex = Pattern
            .compile("^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$");
//            .compile("^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$"); //2018年新版手机号规则
//     .compile("^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$"); //2016年新版手机号规则
    // .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"); 旧版手机号规则

    private static final Pattern cryptRegex = Pattern.compile("\\{.*\\}.*");

    private static final String[] ValCodeArr = {"1", "0", "x", "9", "8", "7",
            "6", "5", "4", "3", "2"};
    private static final String[] Wi = {"7", "9", "10", "5", "8", "4", "2",
            "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
    private static final Pattern numericRegex = Pattern.compile("[0-9]*");

    private CheckUtils() {

    }

    /**
     * <br>
     * <p>
     * Description: 是不是邮箱 <br>
     * <a href=mailto:duanqp@inspur.com></a><br>
     * <p>
     * Date: 2016年4月21日 下午4:53:24<br/>
     * <p>
     *
     * @param email
     * @return
     * @see boolean
     */
    public static boolean isEmail(final String email) {
        boolean flag = false;
        try {

            Matcher matcher = emailRegex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * <br>
     * <p>
     * Description: 是不是手机 <br>
     * <a href=mailto:duanqp@inspur.com></a><br>
     * <p>
     * Date: 2016年4月21日 下午5:26:10<br/>
     * <p>
     *
     * @param phone
     * @return
     * @see boolean
     */
    public static boolean isPhone(final String phone) {
        boolean flag = false;
        try {
            Matcher matcher = phoneRegex.matcher(phone);
            flag = matcher.find();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean isCrypt(final String text) {
        boolean flag = false;
        try {
            Matcher matcher = cryptRegex.matcher(text);
            flag = matcher.find();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * <br>
     * <p>
     * Description: 是不是身份证号 <br>
     * <a href=mailto:duanqp@inspur.com></a><br>
     * <p>
     * Date: 2016年4月21日 下午5:26:45<br/>
     * <p>
     *
     * @param IDStr
     * @return
     * @see boolean
     */
    public static boolean isIDCard(final String IDStr) {
        //首先判空，否则会报空指针错误
        if (StringUtils.isBlank(IDStr)) {
            return false;
        }

        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {

            return false;
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            return false;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        try {
            Date d = new SimpleDateFormat("yyyyMMdd").parse(strYear + strMonth
                    + strDay);
            long p = System.currentTimeMillis() - d.getTime();
            if (p < 0) {
                return false;
            }
            // 100年
            if (p > 3153600000000L) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        // Hashtable h = GetAreaCode();
        // if (h.get(Ai.substring(0, 2)) == null) {
        // errorInfo = "身份证地区编码错误。";
        // return errorInfo;
        // }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (Ai.equalsIgnoreCase(IDStr) == false) {
                return false;
            }
        } else {
            return true;
        }
        // =====================(end)=====================
        return true;
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     * @SuppressWarnings("unchecked") private static Hashtable GetAreaCode() {
     *                                Hashtable hashtable = new Hashtable();
     *                                hashtable.put("11", "北京");
     *                                hashtable.put("12", "天津");
     *                                hashtable.put("13", "河北");
     *                                hashtable.put("14", "山西");
     *                                hashtable.put("15", "内蒙古");
     *                                hashtable.put("21", "辽宁");
     *                                hashtable.put("22", "吉林");
     *                                hashtable.put("23", "黑龙江");
     *                                hashtable.put("31", "上海");
     *                                hashtable.put("32", "江苏");
     *                                hashtable.put("33", "浙江");
     *                                hashtable.put("34", "安徽");
     *                                hashtable.put("35", "福建");
     *                                hashtable.put("36", "江西");
     *                                hashtable.put("37", "山东");
     *                                hashtable.put("41", "河南");
     *                                hashtable.put("42", "湖北");
     *                                hashtable.put("43", "湖南");
     *                                hashtable.put("44", "广东");
     *                                hashtable.put("45", "广西");
     *                                hashtable.put("46", "海南");
     *                                hashtable.put("50", "重庆");
     *                                hashtable.put("51", "四川");
     *                                hashtable.put("52", "贵州");
     *                                hashtable.put("53", "云南");
     *                                hashtable.put("54", "西藏");
     *                                hashtable.put("61", "陕西");
     *                                hashtable.put("62", "甘肃");
     *                                hashtable.put("63", "青海");
     *                                hashtable.put("64", "宁夏");
     *                                hashtable.put("65", "新疆");
     *                                hashtable.put("71", "台湾");
     *                                hashtable.put("81", "香港");
     *                                hashtable.put("82", "澳门");
     *                                hashtable.put("91", "国外"); return
     *                                hashtable; }
     */
    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(final String str) {
        Matcher isNum = numericRegex.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(final String[] args) {
        String s = "{fdfdfd";
        System.out.println(isCrypt(s));
    }

}
