
package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.persistence.mapper.TagMapper;
import com.fun.likechat.persistence.po.Tag;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	TagMapper tagMapper;
	
	@Override
	public int insert(Tag record) throws SQLException {
		return tagMapper.insert(record);
	}

	@Override
	public int update(Tag record) throws SQLException {
		return tagMapper.update(record);
	}

	@Override
	public int deleteById(Integer id) throws SQLException {
		return tagMapper.deleteById(id);
	}

	@Override
	public Tag getById(Integer id) throws SQLException {
		return tagMapper.getById(id);
	}

	@Override
	public List<Tag> getListByMap(Map<String, Object> condition) throws SQLException {
		return tagMapper.getListByMap(condition);
	}
	
	@Override
	public List<Tag> getListByPo(Tag record) throws SQLException {
		return tagMapper.getListByPo(record);
	}

	@Override
	public int count(Map<String, Object> condition) throws SQLException {
		return tagMapper.count(condition);
	}
	
}

