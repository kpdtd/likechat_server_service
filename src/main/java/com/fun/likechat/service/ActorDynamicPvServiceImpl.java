
package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.persistence.mapper.ActorDynamicPvMapper;
import com.fun.likechat.persistence.po.ActorDynamicPv;

@Service
public class ActorDynamicPvServiceImpl implements ActorDynamicPvService {

	@Autowired
	ActorDynamicPvMapper actorDynamicPvMapper;
	
	@Override
	public int insert(ActorDynamicPv record) throws SQLException {
		return actorDynamicPvMapper.insert(record);
	}

	@Override
	public int update(ActorDynamicPv record) throws SQLException {
		return actorDynamicPvMapper.update(record);
	}

	@Override
	public int deleteById(Integer id) throws SQLException {
		return actorDynamicPvMapper.deleteById(id);
	}

	@Override
	public ActorDynamicPv getById(Integer id) throws SQLException {
		return actorDynamicPvMapper.getById(id);
	}

	@Override
	public List<ActorDynamicPv> getListByMap(Map<String, Object> condition) throws SQLException {
		return actorDynamicPvMapper.getListByMap(condition);
	}
	
	@Override
	public List<ActorDynamicPv> getListByPo(ActorDynamicPv record) throws SQLException {
		return actorDynamicPvMapper.getListByPo(record);
	}

	@Override
	public int count(Map<String, Object> condition) throws SQLException {
		return actorDynamicPvMapper.count(condition);
	}
	
	@Override
	public List<ActorDynamicPv> getLimitListByMap(Map<String, Object> condition) throws SQLException {
		return actorDynamicPvMapper.getLimitListByMap(condition);
	}
	
}

