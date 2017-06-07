package com.fun.likechat.persistence.po;

import java.util.*;

public class AccountDetail {
	private Integer id;
	private Integer actorId;
	private String openId;
	private String payType;
	private String goodsType;
	private Integer goodsId;
	private String goodsCode;
	private Integer money;
	private String outTradeNo;
	private String tradeNo;
	private String payer;
	private String payee;
	private Integer state;
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
	public void setOpenId(String value) {
		this.openId = value;
	}
	
	public String getOpenId() {
		return this.openId;
	}
	public void setPayType(String value) {
		this.payType = value;
	}
	
	public String getPayType() {
		return this.payType;
	}
	public void setGoodsType(String value) {
		this.goodsType = value;
	}
	
	public String getGoodsType() {
		return this.goodsType;
	}
	public void setGoodsId(Integer value) {
		this.goodsId = value;
	}
	
	public Integer getGoodsId() {
		return this.goodsId;
	}
	public void setGoodsCode(String value) {
		this.goodsCode = value;
	}
	
	public String getGoodsCode() {
		return this.goodsCode;
	}
	public void setMoney(Integer value) {
		this.money = value;
	}
	
	public Integer getMoney() {
		return this.money;
	}
	public void setOutTradeNo(String value) {
		this.outTradeNo = value;
	}
	
	public String getOutTradeNo() {
		return this.outTradeNo;
	}
	public void setTradeNo(String value) {
		this.tradeNo = value;
	}
	
	public String getTradeNo() {
		return this.tradeNo;
	}
	public void setPayer(String value) {
		this.payer = value;
	}
	
	public String getPayer() {
		return this.payer;
	}
	public void setPayee(String value) {
		this.payee = value;
	}
	
	public String getPayee() {
		return this.payee;
	}
	public void setState(Integer value) {
		this.state = value;
	}
	
	public Integer getState() {
		return this.state;
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

