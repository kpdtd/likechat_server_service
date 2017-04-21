package com.fun.likechat.yunxin.data;

/**
 * 更新token返回的信息
 * @author yangyiqiang
 *
 */
public class RefreshTokenResData{
	private String accid;
	private String token;
	public String getAccid() {
		return accid;
	}
	public void setAccid(String accid) {
		this.accid = accid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
