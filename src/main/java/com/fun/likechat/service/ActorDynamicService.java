package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.fun.likechat.persistence.po.ActorDynamic;
/** 
 * 类名: ActorDynamicService
 * 创建日期: 
 * 功能描述: 
 */
public interface ActorDynamicService extends CommonService<ActorDynamic> {

	List<Map<String, Object>> getNewestDynamic(Map<String, Object> condition) throws SQLException;
	List<Map<String, Object>> getHotDynamic(Map<String, Object> condition) throws SQLException;
	List<Map<String, Object>> getAttentionDynamic(Map<String, Object> condition) throws SQLException;
	int hotDynamicCount(Map<String, Object> dataMap) throws SQLException;
	int attentionDynamicCount(Map<String, Object> dataMap) throws SQLException;
	int newestDynamicCount(Map<String, Object> dataMap) throws SQLException;
}