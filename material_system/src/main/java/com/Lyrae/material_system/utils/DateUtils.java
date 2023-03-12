package com.Lyrae.material_system.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * 日期转换为字符串
     * @param date
     * @param patt
     * @return
     */
    public static String date2String(Date date,String patt){
        SimpleDateFormat sdf = new SimpleDateFormat(patt);
        String format = sdf.format(date);
        return format;
    }

    /**
     * 字符串转换为日期
     * @param str
     * @param patt
     * @return
     * @throws ParseException
     */
    public static Date string2Date(String str,String patt) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat(patt);
        Date parse = sdf.parse(str);
        return parse;
    }
}
