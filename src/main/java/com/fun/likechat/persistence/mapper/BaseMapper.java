package com.fun.likechat.persistence.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;

public interface BaseMapper<T> {

	int count(Map<String, Object> dataMap) throws PersistenceException;

	int deleteById(Integer id) throws PersistenceException;

	T getById(Integer id) throws PersistenceException;

	List<T> getListByPo(T record) throws PersistenceException;
	
	List<T> getListByMap(Map<String, Object> dataMap) throws PersistenceException;

	int insert(T record) throws PersistenceException;

	int update(T record) throws PersistenceException;
}
