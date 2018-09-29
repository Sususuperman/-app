package com.cs.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by weifei on 17/5/15.
 */

public class CheckUtils {

    /**
     * 匹配电话号码
     * @param value
     * @return
     */
    public static boolean isTelPhoneNumber(String value) {
        if (value != null && value.length() == 11) {
            Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8][0-9]\\d{8}$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return false;
    }


    /**
     * 从字符串中找出电话
     * @param str
     * @return
     */
    public static String findPhoneNumber(String str){
        Pattern p=Pattern.compile("(1|861)(3|5|8)\\d{9}$*"); //使用正则表达式匹配
        Matcher m=p.matcher(str);
        while(m.find()){
            return m.group();
        }
        return "";
    }
    /**
     * 从字符串中找出邮箱
     * @param str
     * @return
     */
    public static String findEmailNumber(String str){
        Pattern p=Pattern.compile("\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}"); //使用正则表达式匹配
        Matcher m=p.matcher(str);
        while(m.find()){
            return m.group();
        }
        return "";
    }
}
