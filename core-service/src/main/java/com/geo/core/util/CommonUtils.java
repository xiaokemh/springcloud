package com.geo.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工具类
 *
 * @author yyq
 */
public final class CommonUtils {

    /**
     * 日志
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

    public static String objToString(Object o) {
        return null == o ? "" : o.toString();
    }

    public static int objToInt(Object o, int iDefault) {
        int result = iDefault;
        String str = objToString(o);
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {

        }
        return result;
    }

    /**
     * 对象转Map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj) {
        if (obj == null) {
            return Collections.EMPTY_MAP;
        }
        Map<String, Object> map = new HashMap<>();

        try {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 集合转JSON字符串
     *
     * @param map
     * @return
     */
    public static String mapToJsonStr(Map<String, Object> map) {
        if (CollectionUtils.isEmpty(map)) {
            map = Collections.EMPTY_MAP;
        }
        return JSONObject.toJSONString(map);
    }

    /**
     * JSON字符串转集合
     *
     * @param jsonStr
     * @return
     */
    public static Map jsonStrToMap(String jsonStr) {
        return JSONObject.parseObject(jsonStr, Map.class);
    }


    /**
     * json转换为Object
     */
    public static <T> T json2Object(String json, Class<T> clazz) {
        try {
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            LOGGER.error("{} 转 JSON 失败", json);
        }
        return null;
    }

    /**
     * 对象转json
     */
    public static <T> String object2Json(T object) {
        String json = JSON.toJSONString(object);
        return json;
    }

    /**
     * list转json
     */
    public static <T> String list2Json(List<T> list) {
        String jsons = JSON.toJSONString(list);
        return jsons;
    }

    /**
     * 获取key对应的List集合
     */
    public static <T> List<T> json2List(String json, Class<T> clazz) {
        List<T> list = JSON.parseArray(json, clazz);
        return list;
    }

    /**
     * Clob 转 String
     *
     * @param sc
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static String ClobToString(Clob sc) throws SQLException, IOException {
        String reString = "";
        // 得到流
        Reader is = sc.getCharacterStream();
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        // 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
        while (s != null) {
            sb.append(s);
            s = br.readLine();
        }
        reString = sb.toString();
        return reString;
    }
}
