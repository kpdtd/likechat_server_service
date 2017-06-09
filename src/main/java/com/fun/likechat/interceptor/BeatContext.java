package com.fun.likechat.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BeatContext {
	
	/**
	 * HttpServletRequest
	 */
	private HttpServletRequest request;
	
	/**
	 * HttpServletResponse
	 */
	private HttpServletResponse response;
	
	/**
	 * header头信息
	 */
	//private HeaderData header;
	
	/**
	 * 头加密信息
	 */
	private String headerEncrypt;
	
	/**
	 * 是否是加密的
	 */
	private boolean isEncode = false;
	
	/**
	 * request body
	 */
	private String requestBody;
	
	/**
	 * 用户ID
	 */
	private int userid;
	
	/**
	 * 用户ID
	 */
	private String  openId;
	
	/**
	 * 用户对象
	 */
	//private UserInfoData user;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 当前定位的城市
	 */
	private long cityid;
	
	/**
	 * 当前定位的城市名称，可能为空
	 */
	private String cityname;
	
	/**
	 * 设备号
	 */
	private String imei;
	
	/**
	 * 0未知，1android,2 IOS
	 */
	private int os_type;
	
	/**
	 * APP版本
	 */
	private String appversion;
	
	/**
	 * 渠道
	 */
	private String channel;
	
	/**
	 * 客户端唯一标识
	 */
	private String clientId;
	
	/**
	 * 是否是用户点击的
	 */
	private boolean userClick = false;
	
	/**
	 * 点击的分组
	 */
	private String userClickGroup;
	
	/**
	 * 点击的来源
	 */
	private String userClickTarget;
	
	/**
	 * 是否记录日志
	 */
	private boolean islog = true;
	
	/**
	 * 对应的mapping的方法
	 */
	private Method method;
	
	/**
	 * 对应的mapping的class
	 */
	private Class<?> clazz;

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
	public long getCityid() {
		return cityid;
	}

	public void setCityid(long cityid) {
		this.cityid = cityid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public int getOs_type() {
		return os_type;
	}

	public void setOs_type(int os_type) {
		this.os_type = os_type;
	}

	public String getAppversion() {
		return appversion;
	}

	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public boolean isEncode() {
		return isEncode;
	}

	public void setEncode(boolean isEncode) {
		this.isEncode = isEncode;
	}

	public String getHeaderEncrypt() {
		return headerEncrypt;
	}

	public void setHeaderEncrypt(String headerEncrypt) {
		this.headerEncrypt = headerEncrypt;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public boolean isUserClick() {
		return userClick;
	}

	public void setUserClick(boolean userClick) {
		this.userClick = userClick;
	}

	public String getUserClickGroup() {
		return userClickGroup;
	}

	public void setUserClickGroup(String userClickGroup) {
		this.userClickGroup = userClickGroup;
	}

	public String getUserClickTarget() {
		return userClickTarget;
	}

	public void setUserClickTarget(String userClickTarget) {
		this.userClickTarget = userClickTarget;
	}

	public boolean isIslog() {
		return islog;
	}

	public void setIslog(boolean islog) {
		this.islog = islog;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
//    public UserInfoData getUser() {
//        return user;
//    }
//
//    public void setUser(UserInfoData user) {
//        this.user = user;
//    }
//
//    public HeaderData getHeader() {
//        return header;
//    }
//
//    public void setHeader(HeaderData header) {
//        this.header = header;
//    }

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
