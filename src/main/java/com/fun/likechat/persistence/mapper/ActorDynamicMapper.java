package com.fun.likechat.persistence.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;

import com.fun.likechat.persistence.po.ActorDynamic;
/** 
 * 类名: ActorDynamic
 * 创建日期: 
 * 功能描述: 
 */
public interface ActorDynamicMapper extends BaseMapper<ActorDynamic> {
	
	List<Map<String, Object>> getNewestDynamic(Map<String, Object> condition) throws PersistenceException;
	List<Map<String, Object>> getHotDynamic(Map<String, Object> condition) throws PersistenceException;
	List<Map<String, Object>> getAttentionDynamic(Map<String, Object> condition) throws PersistenceException;
	int hotDynamicCount(Map<String, Object> dataMap) throws PersistenceException;
	int attentionDynamicCount(Map<String, Object> dataMap) throws PersistenceException;
	int newestDynamicCount(Map<String, Object> dataMap) throws PersistenceException;
	
	ActorDynamic newestOneDynamic(@Param("actorId") int actorId) throws PersistenceException;
	void addDynamicPageView(@Param("id") int id) throws PersistenceException;

}