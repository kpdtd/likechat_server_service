package com.fun.likechat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.persistence.mapper.ColumnMapper;
import com.fun.likechat.persistence.po.Column;

@Service
public class ColumnServiceImpl implements ColumnService {

    @Autowired
    ColumnMapper ColumnMapper;
    
//    @Autowired
//    DictionaryService dictionaryService;

    @Override
    public List<Column> getSonColumnByIdentifying(String identifying) throws Exception {
        return ColumnMapper.getSonColumnByIdentifying(identifying);
    }

    @Override
    public Column getColumnByIdentifying(String identifying) throws Exception {
        return ColumnMapper.getColumnByIdentifying(identifying);
    }

	@Override
    public Column getColumnById(Integer id) throws Exception {
	    return ColumnMapper.getById(id);
    }
}
