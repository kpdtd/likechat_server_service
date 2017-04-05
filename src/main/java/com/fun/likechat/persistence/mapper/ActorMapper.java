package com.fun.likechat.persistence.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;

import com.fun.likechat.persistence.po.Actor;
/** 
 * 类名: Actor
 * 创建日期: 
 * 功能描述: 
 */
public interface ActorMapper extends BaseMapper<Actor> {
	public List<Actor> getRandomActors(@Param("limit") int limit) throws PersistenceException;
	public List<Actor> getRandomActorsByCondition(Map<String, Object> map) throws PersistenceException;
}