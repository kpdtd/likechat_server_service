package com.fun.likechat.vo;

import java.util.*;
/**
 * <p>
 * antor的主页vo
 * </p>
 * 修改记录:
 * 		(从这里添加，没有则删除此项)
 */
public class ActorVo {
	private Integer id;
	private Integer idcard;//'固定8位，不重复，无规则的数字。用于标识唯一用户（类似于房间号）'
	private String nickname;// '昵称'
	private String icon;//'头像存放相对地址'
	private String signature;//个性签名
	private String age;//年龄，直接填数  db is int（birthday）
	private Integer sex;//1男2女
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdcard() {
		return idcard;
	}
	public void setIdcard(Integer idcard) {
		this.idcard = idcard;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	@Override
    public String toString() {
	    return "ActorVo [id=" + id + ", idcard=" + idcard + ", nickname=" + nickname + ", icon=" + icon + ", signature=" + signature + ", age=" + age + ", sex=" + sex + "]";
    }
}

