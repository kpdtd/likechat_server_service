
package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

	@Override
	public List<Map<String, Object>> getNewestDynamic(Map<String, Object> condition) throws SQLException {
		return actorDynamicMapper.getNewestDynamic(condition);
	}

	@Override
	public List<Map<String, Object>> getHotDynamic(Map<String, Object> condition) throws SQLException {
		return actorDynamicMapper.getHotDynamic(condition);
	}

	@Override
	public List<Map<String, Object>> getAttentionDynamic(Map<String, Object> condition) throws SQLException {
		return actorDynamicMapper.getAttentionDynamic(condition);
	}

	@Override
	public int hotDynamicCount(Map<String, Object> dataMap) throws SQLException {
		return actorDynamicMapper.hotDynamicCount(dataMap);
	}

	@Override
	public int attentionDynamicCount(Map<String, Object> dataMap) throws SQLException {
		return actorDynamicMapper.attentionDynamicCount(dataMap);
	}

	@Override
	public int newestDynamicCount(Map<String, Object> dataMap) throws SQLException {
		return actorDynamicMapper.newestDynamicCount(dataMap);
	}
	
}

