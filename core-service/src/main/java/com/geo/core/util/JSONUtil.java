package com.geo.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;


/**
 * @Description JSON工具类
 * @author chenjh
 * @date 2019年4月8日 上午11:43:00
 */
public class JSONUtil {
	
	/**
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		return JSON.toJSONString(obj);
	}

	public static JSONObject json2obj(String json){
		return JSON.parseObject(json);
	}
	
	/**
	 * 
	 * @param json
	 * @param clz
	 * @return 
	 * @desc JSONתBean
	 */
	public static <T> T toBean(String json, Class<T> clz) {
		return JSON.parseObject(json, clz);
	}

	/**
	 * 
	 * @desc
	 * @param json
	 * @param clz
	 * @return 
	 */
	public static <T> Map<String, T> toMap(String json, Class<T> clz) {
		Map<String, T> map = JSON.parseObject(json,new TypeReference<Map<String, T>>() {});
		return map;
	}

	/**
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, String> toMap(String json) {
		Map<String, String> map = JSON.parseObject(json,new TypeReference<Map<String, String>>() {});
		return map;
	}

	/**
	 * 
	 * @param json
	 * @param clz
	 * @return 
	 */
	public static <T> List<T> toList(String json, Class<T> clz) {
		return JSON.parseArray(json, clz);
	}
	
}
