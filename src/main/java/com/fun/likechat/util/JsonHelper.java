package com.fun.likechat.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fun.likechat.vo.UserRegisterVo;

public class JsonHelper {
	
	private static final ObjectMapper objectMapper;
	
	static {
		objectMapper = new ObjectMapper();
        //去掉默认的时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置为中国上海时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //设置输入:禁止把POJO中值为null的字段映射到json字符串中
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        //空值不序列化
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        //反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //序列化时，日期的统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //单引号处理
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	}
	
	public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return null;
    }
	
	public static <T> String toJson(T entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> toMap(String json) {
		if(json == null || json.length() == 0) return new HashMap<K, V>();
		
		try {
			return objectMapper.readValue(json, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new HashMap<K, V>();
	}
	
	public static <K, V> String toJson(Map<K, V> obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@SuppressWarnings("rawtypes")
	public static <T> T toObject(Map map, Class<T> clazz) {
		return objectMapper.convertValue(map, clazz);
	}
	
	public static <T> List<T> toList(String jsonArrayStr, Class<T> clazz) {
		List<T> result = new ArrayList<T>();
		try {
			List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr,new TypeReference<List<T>>() {});
			
			for (Map<String, Object> map : list) {
	            result.add(toObject(map, clazz));
	        }
			
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 将字符串转换成FastJson对象
	 * @param json - String
	 * @return JSONObject
	 */
	public static JSONObject toJsonObject(String json) {
		return JSONObject.parseObject(json);
	}
	
	/**
	 * 将字符串转换成FastJson对象
	 * @param json - String
	 * @return JSONArray
	 */
	public static JSONArray toJsonArray(String json) {
		return JSONObject.parseArray(json);
	}
	
	/**
	 * st--实体对象转map
	 * @param object
	 * @return
	 */
	private Map<String, Object> object2Map(Object object){
        JSONObject jsonObject = (JSONObject) JSON.toJSON(object);
        Set<Entry<String,Object>> entrySet = jsonObject.entrySet();
        Map<String, Object> map=new HashMap<String,Object>();
        for (Entry<String, Object> entry : entrySet) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
	
	public static void main(String[] args) {
		UserRegisterVo vo = new UserRegisterVo();
		vo.setOpenId("1");
		vo.setNickname("name");
		String json =  JsonHelper.toJson(vo);
		System.out.println(json);
		UserRegisterVo s = JsonHelper.toObject(json, UserRegisterVo.class);
		System.out.println(s);
    }
}
