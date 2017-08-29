package com.fun.likechat.persistence.po;

import java.util.*;

public class UserCallRecord {
	private Integer id;
	private Integer userId;
	private Integer actorId;
	private java.util.Date startTime;
	private java.util.Date endTime;
	private Integer callTime;
	private Integer payment;
	private Integer balance;
	private java.util.Date createTime;
	
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
	public void setActorId(Integer value) {
		this.actorId = value;
	}
	
	public Integer getActorId() {
		return this.actorId;
	}
	public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}
	
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	public void setCallTime(Integer value) {
		this.callTime = value;
	}
	
	public Integer getCallTime() {
		return this.callTime;
	}
	public void setPayment(Integer value) {
		this.payment = value;
	}
	
	public Integer getPayment() {
		return this.payment;
	}
	public void setBalance(Integer value) {
		this.balance = value;
	}
	
	public Integer getBalance() {
		return this.balance;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
}

