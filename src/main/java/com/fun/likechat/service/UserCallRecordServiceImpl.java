
package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.persistence.mapper.UserCallRecordMapper;
import com.fun.likechat.persistence.po.UserCallRecord;

@Service
public class UserCallRecordServiceImpl implements UserCallRecordService {

	@Autowired
	UserCallRecordMapper userCallRecordMapper;
	
	@Override
	public int insert(UserCallRecord record) throws SQLException {
		return userCallRecordMapper.insert(record);
	}

	@Override
	public int update(UserCallRecord record) throws SQLException {
		return userCallRecordMapper.update(record);
	}

	@Override
	public int deleteById(Integer id) throws SQLException {
		return userCallRecordMapper.deleteById(id);
	}

	@Override
	public UserCallRecord getById(Integer id) throws SQLException {
		return userCallRecordMapper.getById(id);
	}

	@Override
	public List<UserCallRecord> getListByMap(Map<String, Object> condition) throws SQLException {
		return userCallRecordMapper.getListByMap(condition);
	}
	
	@Override
	public List<UserCallRecord> getListByPo(UserCallRecord record) throws SQLException {
		return userCallRecordMapper.getListByPo(record);
	}

	@Override
	public int count(Map<String, Object> condition) throws SQLException {
		return userCallRecordMapper.count(condition);
	}

	@Override
    public UserCallRecord getByUserIdActorId(Integer userId, Integer actorId) throws PersistenceException {
	    return userCallRecordMapper.getByUserIdActorId(userId, actorId);
    }
	
}

