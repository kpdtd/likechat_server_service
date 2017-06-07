package com.fun.likechat.service;

import java.sql.SQLException;
import java.util.List;

import com.fun.likechat.persistence.po.Actor;
import com.fun.likechat.persistence.po.YunxinAccid;
/** 
 * 类名: YunxinAccidService
 * 创建日期: 
 * 功能描述: 
 */
public interface YunxinAccidService extends CommonService<YunxinAccid> {
	YunxinAccid getByOpenId(String openid) throws SQLException;
	YunxinAccid getByAccid(String accid) throws SQLException;
}