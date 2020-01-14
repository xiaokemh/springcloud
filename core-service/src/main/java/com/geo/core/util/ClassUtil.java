package com.geo.core.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;

/**
 * 对象属性拷贝
 * 
 * @author zwj
 *
 */
public class ClassUtil {
	public static <T> T copyProperties(Object source, Class<T> targetClass, String... ignoreProperties)
			throws Exception {
		T targetObj = targetClass.newInstance();
		BeanUtils.copyProperties(source, targetObj, ignoreProperties);
		return targetObj;
	}

	public static Object copyProperties(Object source, Object target, String... ignoreProperties) throws Exception {
		BeanUtils.copyProperties(source, target, ignoreProperties);
		return target;
	}
	
	public static <T> T copyNestProperties(Object source, Class<T> targetClass) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String smsJsonstr = mapper.writeValueAsString(source);
		T targetObj = mapper.readValue(smsJsonstr, targetClass);
		return targetObj;
	}
}
