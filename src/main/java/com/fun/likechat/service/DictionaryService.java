package com.fun.likechat.service;

import java.util.List;
import java.util.Map;

import com.fun.likechat.persistence.po.DataDictionary;


public interface DictionaryService extends CommonService<DataDictionary> {

	public int countKey(Map<String, Object> conditionMap);

	public DataDictionary getDicByKey(String key);

	public Map<String, String> getDicValueByKey(String key) throws Exception;
	
}
