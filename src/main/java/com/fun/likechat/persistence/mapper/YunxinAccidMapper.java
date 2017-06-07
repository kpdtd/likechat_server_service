package com.fun.likechat.persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;

import com.fun.likechat.persistence.po.YunxinAccid;
/** 
 * 类名: YunxinAccid
 * 创建日期: 
 * 功能描述: 
 */
public interface YunxinAccidMapper extends BaseMapper<YunxinAccid> {
	YunxinAccid getByOpenId(@Param("openid") String openid) throws PersistenceException;
	YunxinAccid getByAccid(@Param("accid") String accid) throws PersistenceException;
}