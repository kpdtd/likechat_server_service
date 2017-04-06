package com.fun.likechat.persistence.po;

import java.util.*;

public class Actor {
	private Integer id;
	private Integer idcard;//'固定8位，不重复，无规则的数字。用于标识唯一用户（类似于房间号）',
	private String name;//'主播名称',
	private String nickname;// '昵称'
	private Integer recommend;//'编辑推荐：  1是 0否（预留）'
	private Integer fans;//'粉丝数（冗余字段，应该是实时计算）'
	private Integer attention;//'关注数（冗余字段，应该是实时计算）'
	private String icon;//'头像存放相对地址'
	private String signature;//个性签名
	private String introduction;//自我介绍
	private Integer price;//资费价格（整数）
	private String showTime;//播出时间，自己填入
	private String videoShow;
	private Integer state;//状态：1、账号生效   2、账号停用
	private Integer sex;//1男2女
	private java.util.Date birthday;//生日
	private String province;//省
	private String city;//城市
	private String profession;//职业
	private String hight;//身高
	private String weight;//体重
	private String blogurl;//微博
	private String phone;//手机号
	private String qq;
	private String wechat;
	private String creator;
	private java.util.Date createTime;
	private java.util.Date updateTime;
	
	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setIdcard(Integer value) {
		this.idcard = value;
	}
	
	public Integer getIdcard() {
		return this.idcard;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setNickname(String value) {
		this.nickname = value;
	}
	
	public String getNickname() {
		return this.nickname;
	}
	public void setRecommend(Integer value) {
		this.recommend = value;
	}
	
	public Integer getRecommend() {
		return this.recommend;
	}
	public void setFans(Integer value) {
		this.fans = value;
	}
	
	public Integer getFans() {
		return this.fans;
	}
	public void setAttention(Integer value) {
		this.attention = value;
	}
	
	public Integer getAttention() {
		return this.attention;
	}
	public void setIcon(String value) {
		this.icon = value;
	}
	
	public String getIcon() {
		return this.icon;
	}
	public void setSignature(String value) {
		this.signature = value;
	}
	
	public String getSignature() {
		return this.signature;
	}
	public void setIntroduction(String value) {
		this.introduction = value;
	}
	
	public String getIntroduction() {
		return this.introduction;
	}
	public void setPrice(Integer value) {
		this.price = value;
	}
	
	public Integer getPrice() {
		return this.price;
	}
	public void setShowTime(String value) {
		this.showTime = value;
	}
	
	public String getShowTime() {
		return this.showTime;
	}
	public void setState(Integer value) {
		this.state = value;
	}
	
	public Integer getState() {
		return this.state;
	}
	public void setSex(Integer value) {
		this.sex = value;
	}
	
	public Integer getSex() {
		return this.sex;
	}
	public void setBirthday(java.util.Date value) {
		this.birthday = value;
	}
	
	public java.util.Date getBirthday() {
		return this.birthday;
	}
	public void setprovince(String value) {
		this.province = value;
	}
	
	public String getprovince() {
		return this.province;
	}
	public void setCity(String value) {
		this.city = value;
	}
	
	public String getCity() {
		return this.city;
	}
	public void setProfession(String value) {
		this.profession = value;
	}
	
	public String getProfession() {
		return this.profession;
	}
	public void setHight(String value) {
		this.hight = value;
	}
	
	public String getHight() {
		return this.hight;
	}
	public void setWeight(String value) {
		this.weight = value;
	}
	
	public String getWeight() {
		return this.weight;
	}
	public void setBlogurl(String value) {
		this.blogurl = value;
	}
	
	public String getBlogurl() {
		return this.blogurl;
	}
	public void setPhone(String value) {
		this.phone = value;
	}
	
	public String getPhone() {
		return this.phone;
	}
	public void setQq(String value) {
		this.qq = value;
	}
	
	public String getQq() {
		return this.qq;
	}
	public void setWechat(String value) {
		this.wechat = value;
	}
	
	public String getWechat() {
		return this.wechat;
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
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public String getVideoShow() {
		return videoShow;
	}

	public void setVideoShow(String videoShow) {
		this.videoShow = videoShow;
	}
}

