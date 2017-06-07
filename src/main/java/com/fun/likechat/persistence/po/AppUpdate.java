package com.fun.likechat.persistence.po;

import java.util.*;

public class AppUpdate {
	private Integer id;
	private String version;  //app版本
	private String newVersion; //要升级的版本号：不为空，则说明有要升级的版本
	private Integer isForce;//是否强制升级  1-强制升级   0或null 不强制升级
	private String url;//app下载地址
	private String desc;//升级描述
	private String timeLimit;//升级限制的时间段
	private java.util.Date createTime;//创建时间
	
	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setVersion(String value) {
		this.version = value;
	}
	
	public String getVersion() {
		return this.version;
	}
	public void setNewVersion(String value) {
		this.newVersion = value;
	}
	
	public String getNewVersion() {
		return this.newVersion;
	}
	public void setIsForce(Integer value) {
		this.isForce = value;
	}
	
	public Integer getIsForce() {
		return this.isForce;
	}
	public void setUrl(String value) {
		this.url = value;
	}
	
	public String getUrl() {
		return this.url;
	}
	public void setDesc(String value) {
		this.desc = value;
	}
	
	public String getDesc() {
		return this.desc;
	}
	public void setTimeLimit(String value) {
		this.timeLimit = value;
	}
	
	public String getTimeLimit() {
		return this.timeLimit;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
}

