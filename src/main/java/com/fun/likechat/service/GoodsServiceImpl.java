
package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.persistence.mapper.GoodsMapper;
import com.fun.likechat.persistence.po.Goods;

@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	GoodsMapper goodsMapper;
	
	@Override
	public int insert(Goods record) throws SQLException {
		return goodsMapper.insert(record);
	}

	@Override
	public int update(Goods record) throws SQLException {
		return goodsMapper.update(record);
	}

	@Override
	public int deleteById(Integer id) throws SQLException {
		return goodsMapper.deleteById(id);
	}

	@Override
	public Goods getById(Integer id) throws SQLException {
		return goodsMapper.getById(id);
	}

	@Override
	public List<Goods> getListByMap(Map<String, Object> condition) throws SQLException {
		return goodsMapper.getListByMap(condition);
	}
	
	@Override
	public List<Goods> getListByPo(Goods record) throws SQLException {
		return goodsMapper.getListByPo(record);
	}

	@Override
	public int count(Map<String, Object> condition) throws SQLException {
		return goodsMapper.count(condition);
	}
	
}

