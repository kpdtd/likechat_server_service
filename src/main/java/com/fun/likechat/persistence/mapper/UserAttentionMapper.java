package com.fun.likechat.persistence.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;

import com.fun.likechat.persistence.po.UserAttention;
/** 
 * 类名: UserAttention
 * 创建日期: 
 * 功能描述: 
 */
public interface UserAttentionMapper extends BaseMapper<UserAttention> {
	public void cancelAttention(@Param("userId") Integer userId, @Param("actorId") Integer actorId);
	List<Map<String, Object>> getUserFriends(Map<String, Object> condition) throws PersistenceException;
	List<Map<String, Object>> getMyFans(Map<String, Object> condition) throws PersistenceException;
	int userFriendsCount(Map<String, Object> dataMap) throws PersistenceException;
	int myFansCount(Map<String, Object> dataMap) throws PersistenceException;

}