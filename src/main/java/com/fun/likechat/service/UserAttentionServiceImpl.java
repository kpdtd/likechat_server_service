
package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.persistence.mapper.UserAttentionMapper;
import com.fun.likechat.persistence.po.UserAttention;

@Service
public class UserAttentionServiceImpl implements UserAttentionService {

	@Autowired
	UserAttentionMapper userAttentionMapper;
	
	@Override
	public int insert(UserAttention record) throws SQLException {
		return userAttentionMapper.insert(record);
	}

	@Override
	public int update(UserAttention record) throws SQLException {
		return userAttentionMapper.update(record);
	}

	@Override
	public int deleteById(Integer id) throws SQLException {
		return userAttentionMapper.deleteById(id);
	}

	@Override
	public UserAttention getById(Integer id) throws SQLException {
		return userAttentionMapper.getById(id);
	}

	@Override
	public List<UserAttention> getListByMap(Map<String, Object> condition) throws SQLException {
		return userAttentionMapper.getListByMap(condition);
	}
	
	@Override
	public List<UserAttention> getListByPo(UserAttention record) throws SQLException {
		return userAttentionMapper.getListByPo(record);
	}

	@Override
	public int count(Map<String, Object> condition) throws SQLException {
		return userAttentionMapper.count(condition);
	}

	@Override
    public void cancelAttention(int userId, int actorId) throws SQLException {
		userAttentionMapper.cancelAttention(userId, actorId);	    
    }

	@Override
	public List<Map<String, Object>> getUserFriends(Map<String, Object> condition) throws SQLException {
		return userAttentionMapper.getUserFriends(condition);
	}

	@Override
	public List<Map<String, Object>> getMyFans(Map<String, Object> condition) throws SQLException {
		return userAttentionMapper.getMyFans(condition);
	}

	@Override
	public int userFriendsCount(Map<String, Object> dataMap) throws SQLException {
		return userAttentionMapper.userFriendsCount(dataMap);
	}

	@Override
	public int myFansCount(Map<String, Object> dataMap) throws SQLException {
		return userAttentionMapper.myFansCount(dataMap);
	}
	
}

