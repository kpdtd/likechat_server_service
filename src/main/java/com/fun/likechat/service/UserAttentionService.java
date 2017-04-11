package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.fun.likechat.persistence.po.UserAttention;
/** 
 * 类名: UserAttentionService
 * 创建日期: 
 * 功能描述: 
 */
public interface UserAttentionService extends CommonService<UserAttention> {
	 public void cancelAttention(int userId, int actorId) throws SQLException;
	 List<Map<String, Object>> getUserFriends(Map<String, Object> condition) throws SQLException;
		List<Map<String, Object>> getMyFans(Map<String, Object> condition) throws SQLException;
		int userFriendsCount(Map<String, Object> dataMap) throws SQLException;
		int myFansCount(Map<String, Object> dataMap) throws SQLException;
}