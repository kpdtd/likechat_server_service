package com.fun.likechat.util;

/**
 * 这个类表示配置文件。
 * 
 * @version 1.0.0 2009-11-27
 *
 */
public class Configuration {

	public String smsUrl;
	public String smsUser;
	public String smsPass;
    public String getSmsUrl() {
        return smsUrl;
    }
    public void setSmsUrl(String smsUrl) {
        this.smsUrl = smsUrl;
    }
    public String getSmsUser() {
        return smsUser;
    }
    public void setSmsUser(String smsUser) {
        this.smsUser = smsUser;
    }
    public String getSmsPass() {
        return smsPass;
    }
    public void setSmsPass(String smsPass) {
        this.smsPass = smsPass;
    }
	
}