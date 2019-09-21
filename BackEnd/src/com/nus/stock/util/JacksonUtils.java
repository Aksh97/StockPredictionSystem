package com.nus.stock.util;

import org.codehaus.jackson.map.ObjectMapper;

public class JacksonUtils {

	public static final ObjectMapper objectMapper = new ObjectMapper();
	
	//issue
	public static String getJsonString(Object obj){
		if (obj == null){
			return null;
		}
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
