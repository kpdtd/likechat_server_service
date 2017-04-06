package com.fun.likechat.persistence.po;

import java.util.*;

public class ActorDynamicPv {
	private Integer id;
	private Integer actorId;
	private Integer dynamicId;
	private Integer type;
	private String savePath;
	private Integer price;
	private java.util.Date createTime;
	
	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setActorId(Integer value) {
		this.actorId = value;
	}
	
	public Integer getActorId() {
		return this.actorId;
	}
	public void setDynamicId(Integer value) {
		this.dynamicId = value;
	}
	
	public Integer getDynamicId() {
		return this.dynamicId;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	public void setSavePath(String value) {
		this.savePath = value;
	}
	
	public String getSavePath() {
		return this.savePath;
	}
	public void setPrice(Integer value) {
		this.price = value;
	}
	
	public Integer getPrice() {
		return this.price;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
}

