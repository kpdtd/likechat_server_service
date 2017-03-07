package com.fun.likechat.util;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * <p>
 * 请添加类描述 json工具类
 * </p>
 * 修改记录:
 * (从这里添加，没有则删除此项)
 * 作者 yq
 * 日期 2016年2月24日
 */
public class JacksonUtil {

	private static final ObjectMapper objectMapper;
	static {
		objectMapper = new ObjectMapper();
		// 空值不序列化
		objectMapper.setSerializationInclusion(Include.NON_NULL);
	}

	/**
	 * 
	 * toObject
	 * 方法描述: json串转换对java对象
	 * 逻辑描述:
	 * @param json
	 * @param clazz
	 * @return
	 * @since Ver 1.00
	 */
	public static <T> T toObject(String json, Class<T> clazz) throws Exception {
		return objectMapper.readValue(json, clazz);
	}

	public static <T> String toJson(T entity) throws Exception {
		return objectMapper.writeValueAsString(entity);
	}

	@SuppressWarnings("unchecked")
	public static <T> Map<String, Object> json2map(String jsonStr) throws Exception {
		return objectMapper.readValue(jsonStr, Map.class);
	}

	public static <T> T map2pojo(Map<T, T> map, Class<T> clazz) {
		return objectMapper.convertValue(map, clazz);
	}
	public static <T> T toObject(String json, TypeReference<T> typeOfT) throws Exception {
        return objectMapper.readValue(json, typeOfT);
    }
}
