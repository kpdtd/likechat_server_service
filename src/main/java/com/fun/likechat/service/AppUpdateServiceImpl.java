
package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.persistence.mapper.AppUpdateMapper;
import com.fun.likechat.persistence.po.AppUpdate;

@Service
public class AppUpdateServiceImpl implements AppUpdateService {

	@Autowired
	AppUpdateMapper appUpdateMapper;
	
	@Override
	public int insert(AppUpdate record) throws SQLException {
		return appUpdateMapper.insert(record);
	}

	@Override
	public int update(AppUpdate record) throws SQLException {
		return appUpdateMapper.update(record);
	}

	@Override
	public int deleteById(Integer id) throws SQLException {
		return appUpdateMapper.deleteById(id);
	}

	@Override
	public AppUpdate getById(Integer id) throws SQLException {
		return appUpdateMapper.getById(id);
	}

	@Override
	public List<AppUpdate> getListByMap(Map<String, Object> condition) throws SQLException {
		return appUpdateMapper.getListByMap(condition);
	}
	
	@Override
	public List<AppUpdate> getListByPo(AppUpdate record) throws SQLException {
		return appUpdateMapper.getListByPo(record);
	}

	@Override
	public int count(Map<String, Object> condition) throws SQLException {
		return appUpdateMapper.count(condition);
	}
	
}

