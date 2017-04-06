
package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.persistence.mapper.ActorDynamicMapper;
import com.fun.likechat.persistence.po.ActorDynamic;

@Service
public class ActorDynamicServiceImpl implements ActorDynamicService {

	@Autowired
	ActorDynamicMapper actorDynamicMapper;
	
	@Override
	public int insert(ActorDynamic record) throws SQLException {
		return actorDynamicMapper.insert(record);
	}

	@Override
	public int update(ActorDynamic record) throws SQLException {
		return actorDynamicMapper.update(record);
	}

	@Override
	public int deleteById(Integer id) throws SQLException {
		return actorDynamicMapper.deleteById(id);
	}

	@Override
	public ActorDynamic getById(Integer id) throws SQLException {
		return actorDynamicMapper.getById(id);
	}

	@Override
	public List<ActorDynamic> getListByMap(Map<String, Object> condition) throws SQLException {
		return actorDynamicMapper.getListByMap(condition);
	}
	
	@Override
	public List<ActorDynamic> getListByPo(ActorDynamic record) throws SQLException {
		return actorDynamicMapper.getListByPo(record);
	}

	@Override
	public int count(Map<String, Object> condition) throws SQLException {
		return actorDynamicMapper.count(condition);
	}
	
}

