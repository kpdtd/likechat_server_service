package com.fun.likechat.persistence.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;

import com.fun.likechat.persistence.po.ActorDynamicPv;
/** 
 * 类名: ActorDynamicPv
 * 创建日期: 
 * 功能描述: 
 */
public interface ActorDynamicPvMapper extends BaseMapper<ActorDynamicPv> {
	List<ActorDynamicPv> getLimitListByMap(Map<String, Object> dataMap) throws PersistenceException;
}