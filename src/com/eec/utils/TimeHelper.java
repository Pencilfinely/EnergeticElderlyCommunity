package com.eec.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeHelper {

    public static int ageHelper(String birth) throws ParseException {
        Date today = new Date();
        SimpleDateFormat bDate = new SimpleDateFormat("yyyyMMdd");
        long msB = bDate.parse(birth).getTime();
        long msN = today.getTime();
        long msA = msN - msB;

        return (int)(msA/1000/60/60/24/365);
    }

    public static String weekHelper() {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(today);

        return week;
    }

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
