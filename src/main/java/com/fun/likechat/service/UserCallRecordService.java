package com.fun.likechat.service;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;

import com.fun.likechat.persistence.po.UserCallRecord;
/** 
 * 类名: UserCallRecordService
 * 创建日期: 
 * 功能描述: 
 */
public interface UserCallRecordService extends CommonService<UserCallRecord> {
	UserCallRecord getByUserIdActorId(Integer userId, Integer actorId) throws PersistenceException;
}