package com.fun.likechat.persistence.po;

import java.util.*;

public class Account {
	private Integer id;
	private Integer userId;
	private Integer isvip;//是否是vip会员：0-否  1-是',
	private Integer grade;
	private java.util.Date vipActiveTime;
	private Integer money;
	private java.util.Date createTime;
	private java.util.Date updateTime;
	
	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setUserId(Integer value) {
		this.userId = value;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	public void setIsvip(Integer value) {
		this.isvip = value;
	}
	
	public Integer getIsvip() {
		return this.isvip;
	}
	public void setGrade(Integer value) {
		this.grade = value;
	}
	
	public Integer getGrade() {
		return this.grade;
	}
	public void setVipActiveTime(java.util.Date value) {
		this.vipActiveTime = value;
	}
	
	public java.util.Date getVipActiveTime() {
		return this.vipActiveTime;
	}
	public void setMoney(Integer value) {
		this.money = value;
	}
	
	public Integer getMoney() {
		return this.money;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
}

