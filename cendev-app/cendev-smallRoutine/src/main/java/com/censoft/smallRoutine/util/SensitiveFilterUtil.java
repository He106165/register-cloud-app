package com.censoft.smallRoutine.util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;

/**
 * 敏感词过滤工具类
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class SensitiveFilterUtil {

    //敏感词集合
    public static HashMap sensitiveWordMap;
    //初始化敏感词库，构建DFA算法模型
    public static  void initContext(){
        HashSet<String> set = new HashSet<String>();
        Row row =null;
        try{
            //获取敏感词文件
            Workbook workbook = new XSSFWorkbook(new FileInputStream("D:\\IdeaPorjectPace\\register-cloud-app\\cendev-app\\cendev-smallRoutine\\src\\main\\resources\\敏感词库表统计.xlsx"));
             //获取第一张表
            Sheet sheet = workbook.getSheetAt(0);
            //获取每行中的字段
            for (int j = 1; j<= sheet.getLastRowNum(); j++){
                  //获取行
                row = sheet.getRow(j);
                if(row == null){
                    //略过空行
                    continue;
                }else {
                    if (row.getCell(3) != null)
                        set.add(row.getCell(3).getStringCellValue());
                 }
            }
            initSensitiveWordMap(set);
        }catch (Exception e){
            System.out.println("<<<<<<解析敏感文件报错！" +row.toString());
            e.printStackTrace();
        }
    }

    /**
     * 初始化敏感词库，侯建DFA算法模型
     *  @param sensitiveWordSet 敏感词库
     */
    private  static  void  initSensitiveWordMap(Set<String> sensitiveWordSet){
        //初始化敏感词容器，减少扩容操作
        sensitiveWordMap = new HashMap<String,String>(sensitiveWordSet.size());
        Map<Object,Object>temp;
        Map<Object,Object>newWordMap;
        //遍历sensitiveWordSet
        for (String key:sensitiveWordSet){
            temp = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++){
                //转换成char类型
                char keyChar = key.charAt(i);
                //库或只能怪获取关键字
                Object wordMap = temp.get(keyChar);
                //如果UC你在这个key,直接赋值，用于下一个循环获取
                if (wordMap != null){
                    temp =(Map) wordMap;
                }else {
                    //不存在则构建一个map,同时将isEnd设置为0，因为它不是字最后一个
                    newWordMap = new HashMap<>();
                    //不是最后一个
                    newWordMap.put("isEnd","0");
                    temp.put(keyChar,newWordMap);
                    temp = newWordMap;
                }
                //最后一个
                if (i == key.length() -1) temp.put("isEnd","1");
            }
        }
    }

    /**
     * 判断文字是否包含敏感字符
     * 文本
     * 如若包含返回true,否则返回false
     */
    public  static  boolean contains(String txt){
        boolean flag = false;
        for (int i =0; i < txt.length(); i++){
            //判断是否包含敏感字符
            int matchFlag = checkSensitiveWord(txt, i);
            if (matchFlag > 0){
                //大于0存在，返回true
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检查文字中是否包含敏感字符，检测规则如下
     *@param txt
     * @param beginIndex
     * @return 如果存在,则返回敏感词字符的长度,不存在返回0
     */
    private  static  int checkSensitiveWord(String txt,int beginIndex){
        //敏感词结束标识位,用于敏感词只有1位的情况
        boolean flag = false;
        //匹配标识数默认为0；
        int matchFlag = 0;
        char word;
        Map nowMap = sensitiveWordMap;
        for (int i = beginIndex; i < txt.length(); i++){
            word = txt.charAt(i);
            //获取指定key
          nowMap = nowMap !=null && nowMap.get(word) != null ? (Map) nowMap.get(word) : null;
            if (nowMap != null){
                //存在则判断是否为最后一个
                //找到相应key，匹配标识+1
                matchFlag++;
                //如果为最后一个匹配规则，结束循环，返回匹配标识数
                if ("1".equals(nowMap.get("isEnd"))){
                    //结束标志位为true
                    flag = true;
                }else { //不存在，直接返回
                    break;
                }
              }
        }
        //长度必须大于等于1，为词
        if (matchFlag <2 && !flag){
            matchFlag = 0;
        }
        return matchFlag;
    }
    /**
     * 获取文字中的敏感词
     * txt 文字
     */
    public static List getSensitiveWord(String txt) {
        List sensitiveWordList = new ArrayList();
        for (int i= 0; i < txt.length();i++){
            //判断是否包含敏感字符
            int length = checkSensitiveWord(txt,i);
            if (length > 0){ //存在，加入list中
                sensitiveWordList.add(txt.substring(i,i + length));
                i = i +length -1;//减1的原因，是因为for会自增
            }
        }
        return sensitiveWordList;
    }
    /**
     * context是要校验的内容，返回结果为list,为空说明没有敏感词
      * @param context
      * @return
     */
    public  static  List checkText(String context){
        initContext();
        //包含敏感词返回所有的敏感词数据
        return  getSensitiveWord(context);
    }

    public  static void main(String[] args){
        System.out.println(checkText("卖淫嫖娼,杀人犯法,共产党国民党法轮大法,"));

    }





}
