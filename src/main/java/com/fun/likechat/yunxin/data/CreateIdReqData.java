package com.fun.likechat.yunxin.data;

public class CreateIdReqData {
	private String accid;
	private String name;//云信ID昵称，最大长度64字符，用来PUSH推送时显示的昵称
	private String props;//json,用于扩展
	private String icon;
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
	public String getProps() {
		return props;
	}
	public void setProps(String props) {
		this.props = props;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
