package com.fun.likechat.persistence.po;

import java.util.*;

public class Goods {
	private Integer id;
	private String name;//商品名称  如：10金币
	private String subname;//商品子名称：预留
	private Integer value;//商品面值数字：（根据类型填入不同值 ，如果10金币 这里对应数字10  ；如果2-会员，这个值标识会员等级。
	private Integer giveValue;//赠送面值：买10金币送10金币 ，这里填入10，购买时后台自动填入10
	private String info;//商品信息
	private String pic;//商品图片
	private Integer price;//商品价格
	private Integer vipPrice;//vip价格
	private Integer type;//商品类型： 1-hi币  2-会员
	private Integer activeTime;//
	private java.util.Date createTime;//
	
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
	public void setValue(Integer value) {
		this.value = value;
	}
	
	public Integer getValue() {
		return this.value;
	}
	public void setGiveValue(Integer value) {
		this.giveValue = value;
	}
	
	public Integer getGiveValue() {
		return this.giveValue;
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
	public void setVipPrice(Integer value) {
		this.vipPrice = value;
	}
	
	public Integer getVipPrice() {
		return this.vipPrice;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public Integer getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Integer activeTime) {
		this.activeTime = activeTime;
	}

}

