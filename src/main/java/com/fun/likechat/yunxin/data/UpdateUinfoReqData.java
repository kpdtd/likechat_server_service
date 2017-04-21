package com.fun.likechat.yunxin.data;

public class UpdateUinfoReqData {

	private String accid;// String 是 用户帐号，最大长度32字符，必须保证一个APP内唯一
	private String name;// String 否 用户昵称，最大长度64字符
	private String icon;// String 否 用户icon，最大长度1024字符
	private String sign;// String 否 用户签名，最大长度256字符
	private String email;// String 否 用户email，最大长度64字符
	private String birth;// String 否 用户生日，最大长度16字符
	private String mobile;// String 否 用户mobile，最大长度32字符，只支持国内号码
	private int gender;// int 否 用户性别，0表示未知，1表示男，2女表示女，其它会报参数错误
	private String ex;// String 否 用户名片扩展字段，最大长度1024字符，用户可自行扩展，建议封装成JSON字符串

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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getEx() {
		return ex;
	}

	public void setEx(String ex) {
		this.ex = ex;
	}

}
