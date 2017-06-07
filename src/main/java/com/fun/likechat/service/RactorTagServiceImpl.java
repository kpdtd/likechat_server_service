package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.persistence.mapper.RactorTagMapper;
import com.fun.likechat.persistence.po.RactorTag;

@Service
public class RactorTagServiceImpl implements RactorTagService {

	@Autowired
	RactorTagMapper ractorTagMapper;
	
	@Override
	public int insert(RactorTag record) throws SQLException {
		return ractorTagMapper.insert(record);
	}

	@Override
	public int update(RactorTag record) throws SQLException {
		return ractorTagMapper.update(record);
	}

	@Override
	public int deleteById(Integer id) throws SQLException {
		return ractorTagMapper.deleteById(id);
	}

	@Override
	public RactorTag getById(Integer id) throws SQLException {
		return ractorTagMapper.getById(id);
	}

	@Override
	public List<RactorTag> getListByMap(Map<String, Object> condition) throws SQLException {
		return ractorTagMapper.getListByMap(condition);
	}
	
	@Override
	public List<RactorTag> getListByPo(RactorTag record) throws SQLException {
		return ractorTagMapper.getListByPo(record);
	}

	@Override
	public int count(Map<String, Object> condition) throws SQLException {
		return ractorTagMapper.count(condition);
	}
	
}

