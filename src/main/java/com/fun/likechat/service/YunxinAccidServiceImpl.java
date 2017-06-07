
package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.persistence.mapper.YunxinAccidMapper;
import com.fun.likechat.persistence.po.YunxinAccid;

@Service
public class YunxinAccidServiceImpl implements YunxinAccidService {

	@Autowired
	YunxinAccidMapper yunxinAccidMapper;
	
	@Override
	public int insert(YunxinAccid record) throws SQLException {
		return yunxinAccidMapper.insert(record);
	}

	@Override
	public int update(YunxinAccid record) throws SQLException {
		return yunxinAccidMapper.update(record);
	}

	@Override
	public int deleteById(Integer id) throws SQLException {
		return yunxinAccidMapper.deleteById(id);
	}

	@Override
	public YunxinAccid getById(Integer id) throws SQLException {
		return yunxinAccidMapper.getById(id);
	}

	@Override
	public List<YunxinAccid> getListByMap(Map<String, Object> condition) throws SQLException {
		return yunxinAccidMapper.getListByMap(condition);
	}
	
	@Override
	public List<YunxinAccid> getListByPo(YunxinAccid record) throws SQLException {
		return yunxinAccidMapper.getListByPo(record);
	}

	@Override
	public int count(Map<String, Object> condition) throws SQLException {
		return yunxinAccidMapper.count(condition);
	}

	@Override
    public YunxinAccid getByOpenId(String openid) throws SQLException {
	    return yunxinAccidMapper.getByOpenId(openid);
    }

	@Override
    public YunxinAccid getByAccid(String accid) throws SQLException {
		 return yunxinAccidMapper.getByAccid(accid);
    }
}

