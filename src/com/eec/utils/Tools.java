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

/*
 *工具类，用于json文件读写
 */
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

}