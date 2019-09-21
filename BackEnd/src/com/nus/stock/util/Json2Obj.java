package com.nus.stock.util;


import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class Json2Obj {
    
    private static final Logger log = Logger4j.getLogger(Json2Obj.class);
    
    public static Object getObj(String jsonString, Class<?> clazz){
        try {
            Class<?> cls = Class.forName(clazz.getName());
            Object tempObject = cls.newInstance();

            @SuppressWarnings("unchecked")
            Map<String, String> data = JacksonUtils.objectMapper.readValue(jsonString, Map.class);
            
            Iterator<Entry<String, String>>  iter = data.entrySet().iterator();
            while(iter.hasNext()){
                Entry<String, String> entry = iter.next();
                String key = entry.getKey();
                Object value = entry.getValue();
                
                Field field = null;
                try {
                    field = cls.getDeclaredField(key);
                } catch (Exception e) {
                    /* if not find field skip */
                    continue;
                }
                
                String type = field.getType().toString();
                field.setAccessible(true);
                
                if("int".equals(type) || "class java.lang.Integer".equals(type)){
                    field.set(tempObject, Integer.parseInt(value.toString()));
                }else if("float".equals(type) || "class java.lang.Float".equals(type)){
                    field.set(tempObject, Float.parseFloat(value.toString()));
                }else if("double".equals(type) || "class java.lang.Double".equals(type)){
                    field.set(tempObject, Double.parseDouble(value.toString()));
                }else if("long".equals(type) || "class java.lang.Long".equals(type)){
                    field.set(tempObject, Long.parseLong(value.toString()));
                }else if("class java.util.Date".equals(type)){
                    SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
                    Date date = format.parse(value.toString());
                    field.set(tempObject, date);
                }else{
                    field.set(tempObject, value.toString());
                }
            }
            
            return tempObject;
        } catch (Exception e) {
            log.error(e);
        } 
        
        return null;
    }
    
    /**
     * 
     * @param obj1  The destination object
     * @param obj2  The source object
     * @param clazz  To be sure obj1 & obj2 are same class
     */
    public static void updateObject(Object obj1, Object obj2){
        if (obj1.getClass() != obj2.getClass())
            return;
        try {
            Class<?> clazz = obj1.getClass();
            Field[] f = clazz.getDeclaredFields();
            for (Field field : f){
                field.setAccessible(true);
                Object objtemp = field.get(obj1);
                if (null != objtemp) {
                    field.set(obj2, objtemp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
}

