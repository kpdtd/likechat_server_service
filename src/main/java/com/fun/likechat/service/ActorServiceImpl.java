
package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.persistence.mapper.ActorMapper;
import com.fun.likechat.persistence.po.Actor;

@Service
public class ActorServiceImpl implements ActorService {

	@Autowired
	ActorMapper actorMapper;
	
	@Override
	public int insert(Actor record) throws SQLException {
		return actorMapper.insert(record);
	}

	@Override
	public int update(Actor record) throws SQLException {
		return actorMapper.update(record);
	}

	@Override
	public int deleteById(Integer id) throws SQLException {
		return actorMapper.deleteById(id);
	}

	@Override
	public Actor getById(Integer id) throws SQLException {
		return actorMapper.getById(id);
	}

	@Override
	public List<Actor> getListByMap(Map<String, Object> condition) throws SQLException {
		return actorMapper.getListByMap(condition);
	}
	
	@Override
	public List<Actor> getListByPo(Actor record) throws SQLException {
		return actorMapper.getListByPo(record);
	}

	@Override
	public int count(Map<String, Object> condition) throws SQLException {
		return actorMapper.count(condition);
	}
	
	@Override
	public List<Actor> getRandomActors(int limit) throws SQLException {
		return actorMapper.getRandomActors(limit);
	}
	
}

