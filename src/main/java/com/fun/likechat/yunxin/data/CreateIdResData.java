package com.fun.likechat.yunxin.data;

/**
 * 创建云信ID返回的信息
 * @author yangyiqiang
 *
 */
public class CreateIdResData{
	private String accid;
	private String name;//云信ID昵称，最大长度64字符，用来PUSH推送时显示的昵称
	private String token;
	public String getAccid() {
		return accid;
	}
	public void setAccid(String accid) {
		this.accid = accid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
