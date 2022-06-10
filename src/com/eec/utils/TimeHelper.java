package com.eec.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 *工具类，用于时间格式化及换算、比较
 */
public class TimeHelper {

    /*
     *利用SimpleDateFormat，将yyyyMMdd格式的出生日期转化为当前年龄
     */
    public static int ageHelper(String birth) throws ParseException {
        Date today = new Date();
        SimpleDateFormat bDate = new SimpleDateFormat("yyyyMMdd");
        long msB = bDate.parse(birth).getTime();
        long msN = today.getTime();
        long msA = msN - msB;

        return (int)(msA/1000/60/60/24/365);
    }

    /*
     *利用SimpleDateFormat，获取当前为星期几
     */
    public static String weekHelper() {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(today);

        return week;
    }

    /*
     *end:截止时间
     *利用SimpleDateFormat，判断当前时间是否早于截止时间
     */
    public static boolean timeEnough(String end) throws ParseException {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String now = sdf.format(today);
        long msN = sdf.parse(now).getTime();
        long msE = sdf.parse(end).getTime();
//        System.out.println(now);
//        System.out.println(msN + " " + msE);
        return msN <= msE;
    }

}
