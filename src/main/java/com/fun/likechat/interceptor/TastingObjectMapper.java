package com.fun.likechat.interceptor;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class TastingObjectMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TastingObjectMapper(){
		super();
        //去掉默认的时间戳格式
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置为中国上海时区
        this.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //设置输入:禁止把POJO中值为null的字段映射到json字符串中
        this.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        //空值不序列化
        this.setSerializationInclusion(Include.NON_NULL);
        //反序列化时，属性不存在的兼容处理
        this.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //序列化时，日期的统一格式
        this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //单引号处理
        this.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        
        //空值处理为空串 
        /*this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value,JsonGenerator jg,SerializerProvider sp) throws IOException, JsonProcessingException {
                jg.writeString("");
            }
        });*/
	}

}
