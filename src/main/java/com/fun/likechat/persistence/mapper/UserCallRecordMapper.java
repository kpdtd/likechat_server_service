package com.fun.likechat.persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;

import com.fun.likechat.persistence.po.UserCallRecord;
/** 
 * 类名: UserCallRecord
 * 创建日期: 
 * 功能描述: 
 */
public interface UserCallRecordMapper extends BaseMapper<UserCallRecord> {
	UserCallRecord getByUserIdActorId(@Param("userId") Integer user_id, @Param("actorId")  Integer actor_id) throws PersistenceException;
}