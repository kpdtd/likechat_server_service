package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CommonService<T> {

	int count(Map<String,Object> condition) throws SQLException;
	
	int deleteById(Integer id) throws SQLException;
	
	T getById(Integer id) throws SQLException;

	List<T> getListByPo(T record) throws SQLException;
	
	List<T> getListByMap(Map<String, Object> dataMap) throws SQLException;
	
	int insert(T record) throws SQLException;

	int update(T record) throws SQLException;
}
