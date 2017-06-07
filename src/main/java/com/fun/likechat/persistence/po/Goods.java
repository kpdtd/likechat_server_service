package com.fun.likechat.persistence.po;

import java.util.*;

public class Goods {
	private Integer id;
	private String name;
	private String subname;
	private String info;
	private String pic;
	private Integer price;
	private String displayPrice;
	private Integer type;
	private Integer discountPirce;
	private String displayDiscountPrice;
	private java.util.Date createTime;
	
	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setSubname(String value) {
		this.subname = value;
	}
	
	public String getSubname() {
		return this.subname;
	}
	public void setInfo(String value) {
		this.info = value;
	}
	
	public String getInfo() {
		return this.info;
	}
	public void setPic(String value) {
		this.pic = value;
	}
	
	public String getPic() {
		return this.pic;
	}
	public void setPrice(Integer value) {
		this.price = value;
	}
	
	public Integer getPrice() {
		return this.price;
	}
	public void setDisplayPrice(String value) {
		this.displayPrice = value;
	}
	
	public String getDisplayPrice() {
		return this.displayPrice;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	public void setDiscountPirce(Integer value) {
		this.discountPirce = value;
	}
	
	public Integer getDiscountPirce() {
		return this.discountPirce;
	}
	public void setDisplayDiscountPrice(String value) {
		this.displayDiscountPrice = value;
	}
	
	public String getDisplayDiscountPrice() {
		return this.displayDiscountPrice;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
}

