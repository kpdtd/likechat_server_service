
package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.persistence.mapper.AccountDetailMapper;
import com.fun.likechat.persistence.po.AccountDetail;

@Service
public class AccountDetailServiceImpl implements AccountDetailService {

	@Autowired
	AccountDetailMapper accountDetailMapper;
	
	@Override
	public int insert(AccountDetail record) throws SQLException {
		return accountDetailMapper.insert(record);
	}

	@Override
	public int update(AccountDetail record) throws SQLException {
		return accountDetailMapper.update(record);
	}

	@Override
	public int deleteById(Integer id) throws SQLException {
		return accountDetailMapper.deleteById(id);
	}

	@Override
	public AccountDetail getById(Integer id) throws SQLException {
		return accountDetailMapper.getById(id);
	}

	@Override
	public List<AccountDetail> getListByMap(Map<String, Object> condition) throws SQLException {
		return accountDetailMapper.getListByMap(condition);
	}
	
	@Override
	public List<AccountDetail> getListByPo(AccountDetail record) throws SQLException {
		return accountDetailMapper.getListByPo(record);
	}

	@Override
	public int count(Map<String, Object> condition) throws SQLException {
		return accountDetailMapper.count(condition);
	}
	
}

