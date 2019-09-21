package com.nus.stock.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Obj2Map {
	
	public  static Map  toMap(Object temp, Class<?> obj){

		try {
			
			Map map = new HashMap();
			
			Field[] f = obj.getDeclaredFields();
			for(Field field : f){
				field.setAccessible(true);
				Object objtemp = field.get(temp);
				
				map.put(field.getName(), objtemp);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
