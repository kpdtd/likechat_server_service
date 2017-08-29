
package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.persistence.mapper.AccountMapper;
import com.fun.likechat.persistence.po.Account;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountMapper accountMapper;
	
	@Override
	public int insert(Account record) throws SQLException {
		return accountMapper.insert(record);
	}

	@Override
	public int update(Account record) throws SQLException {
		return accountMapper.update(record);
	}

	@Override
	public int deleteById(Integer id) throws SQLException {
		return accountMapper.deleteById(id);
	}

	@Override
	public Account getById(Integer id) throws SQLException {
		return accountMapper.getById(id);
	}

	@Override
	public List<Account> getListByMap(Map<String, Object> condition) throws SQLException {
		return accountMapper.getListByMap(condition);
	}
	
	@Override
	public List<Account> getListByPo(Account record) throws SQLException {
		return accountMapper.getListByPo(record);
	}

	@Override
	public int count(Map<String, Object> condition) throws SQLException {
		return accountMapper.count(condition);
	}
	
}

