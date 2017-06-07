package com.fun.likechat.persistence.po;

import java.util.*;

public class ActorDynamic {
	private Integer id;
	private Integer actorId;
	private Integer type;
	private String content;
	private Integer price;
	private Integer pageView;
	private java.util.Date createTime;
	private java.util.Date updateTime;
	
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
	public void setContent(String value) {
		this.content = value;
	}
	
	public String getContent() {
		return this.content;
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
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public Integer getPageView() {
		return pageView;
	}

	public void setPageView(Integer pageView) {
		this.pageView = pageView;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}

