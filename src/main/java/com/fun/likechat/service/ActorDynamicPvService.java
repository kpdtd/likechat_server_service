package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.fun.likechat.persistence.po.ActorDynamicPv;
/** 
 * 类名: ActorDynamicPvService
 * 创建日期: 
 * 功能描述: 
 */
public interface ActorDynamicPvService extends CommonService<ActorDynamicPv> {
	List<ActorDynamicPv> getLimitListByMap(Map<String, Object> dataMap) throws SQLException;
}