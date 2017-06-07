package com.fun.likechat.persistence.po;

import java.util.*;

public class YunxinAccid {
	private Integer id;
	private String openid;
	private String accid;
	private String token;
	private java.util.Date createTime;
	
	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setOpenid(String value) {
		this.openid = value;
	}
	
	public String getOpenid() {
		return this.openid;
	}
	public void setAccid(String value) {
		this.accid = value;
	}
	
	public String getAccid() {
		return this.accid;
	}
	public void setToken(String value) {
		this.token = value;
	}
	
	public String getToken() {
		return this.token;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
}

