package com.nus.stock.util;


import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Cause {
    
    static public String getFailcode(int errorCode,
                              String failField,
                              String description){
        
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> errMap = new HashMap<String, Object>();
        
        errMap.put("code", errorCode);
        errMap.put("field", failField);
        errMap.put("description", description);
        
        map.put("ack", "failure");
        map.put("error", errMap);
        
        return JacksonUtils.getJsonString(map);
    }
    
    static public String getSuccess(Integer id){
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        
        map.put("ack", "success");
        
        if (null != id) {
            dataMap.put("id", id);
            map.put("data", dataMap);
        }
        
        return JacksonUtils.getJsonString(map);
    }
    
    static public String getData(List<Object> list){
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("ack", "success");
        map.put("data", list);
        map.put("size", list.size());
        
        return JacksonUtils.getJsonString(map);
    }
    
    @SuppressWarnings("unchecked")
    static public boolean isSuccess(String result){
        
        Map<String, Object> map;
        
        try {
            map = JacksonUtils.objectMapper.readValue(result, Map.class);
        } catch (Exception e) {
            return false;
        } 
        
        Object obj = map.get("ack");
        if (null != obj){
            if(obj.toString().equals("success")){
                return true;
            } 
        }
        return false;
    }

}
