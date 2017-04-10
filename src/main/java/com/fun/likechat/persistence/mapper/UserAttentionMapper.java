package com.fun.likechat.persistence.mapper;

import org.apache.ibatis.annotations.Param;

import com.fun.likechat.persistence.po.UserAttention;
/** 
 * 类名: UserAttention
 * 创建日期: 
 * 功能描述: 
 */
public interface UserAttentionMapper extends BaseMapper<UserAttention> {
//	public void cancelAttention(@Param("userId") Integer userId, @Param("actorId") Integer actorId);
	
	/**
	测试下是否可以删,为什么集成的接口就不用@Param
	**/
	public void cancelAttention(Integer userId, Integer actorId);

}