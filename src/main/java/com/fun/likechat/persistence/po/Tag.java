package com.fun.likechat.persistence.po;

public class Tag {
	private Integer id;
	private String tagName;
	private String pic;
	private Integer property;
	private Integer state;
	private String creator;
	private java.util.Date createTime;
	
	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setTagName(String value) {
		this.tagName = value;
	}
	
	public String getTagName() {
		return this.tagName;
	}
	public void setPic(String value) {
		this.pic = value;
	}
	
	public String getPic() {
		return this.pic;
	}
	public void setProperty(Integer value) {
		this.property = value;
	}
	
	public Integer getProperty() {
		return this.property;
	}
	public void setState(Integer value) {
		this.state = value;
	}
	
	public Integer getState() {
		return this.state;
	}
	public void setCreator(String value) {
		this.creator = value;
	}
	
	public String getCreator() {
		return this.creator;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
}

