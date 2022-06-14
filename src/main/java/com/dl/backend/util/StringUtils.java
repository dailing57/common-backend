package com.dl.backend.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        return m.matches();
    }

    //座机
    public static boolean isFixedPhone(String string) {
        if (string == null)
            return false;
        //将点号全部替换为横线
        String targetStr = string.replaceAll("\\-", "");
        String regEx1 = "^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(targetStr);
        if (m.matches())
            return true;
        else
            return false;
    }

    public static boolean isMobilePhone(String string) {
        if (string == null)
            return false;
        String regEx1 = "^1[34578][0-9]{9}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }

    /**
     * 格式化为两位小数字符串
     *
     * @param d
     * @return
     */
    public static String format(Double d) {
        String s = String.format("%.2f", d);
        return s;
    }
}


