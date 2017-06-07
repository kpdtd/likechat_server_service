package com.fun.likechat.persistence.po;

public class RactorTag {
	private Integer id;
	private Integer actorId;
	private Integer tagId;
	private String tagIdentifying;
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
	public void setTagId(Integer value) {
		this.tagId = value;
	}
	
	public Integer getTagId() {
		return this.tagId;
	}
	public void setTagIdentifying(String value) {
		this.tagIdentifying = value;
	}
	
	public String getTagIdentifying() {
		return this.tagIdentifying;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
}

