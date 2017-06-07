package com.fun.likechat.persistence.mapper;

import java.util.Map;

import com.fun.likechat.persistence.po.DataDictionary;


public interface DictionaryMapper extends BaseMapper<DataDictionary> {

	int countKey(Map<String, Object> conditionMap);

}
