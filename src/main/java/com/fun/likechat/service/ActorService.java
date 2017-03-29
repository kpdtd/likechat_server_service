package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;

import com.fun.likechat.persistence.po.Actor;
/** 
 * 类名: ActorService
 * 创建日期: 
 * 功能描述: 
 */
public interface ActorService extends CommonService<Actor> {

	List<Actor> getRandomActors(int limit) throws SQLException;

}