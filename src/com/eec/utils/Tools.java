package com.eec.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Tools{
    //path:json文件路径，class1：集合内元素类型
    //泛型方法：在修饰符和返回值之间添加泛型（<T>）
    public static <T> List<T> getJsonToList(String path, Class class1) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class,class1);

        List<T> o = (List<T>)objectMapper.readValue(new File(path), javaType);

        return o;
    }

    public static <T> void writeListToJSON(String path, List<T> list) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(new File(path),list);
    }

    public static <K,V> Map<K,V> getJsonToMap(String path,Class keyClass, Class valueClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class,keyClass,valueClass);

        Map<K,V> map = (Map<K,V>)objectMapper.readValue(new File(path), javaType);

        return map;
    }

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