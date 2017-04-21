package com.fun.likechat.yunxin.data;

import java.util.List;

/**
 * 查看用户的黑名单和静音列表
 * @author yangyiqiang
 *
 */
public class ListBlackAndMuteListResultInfo {
	private String code;
	private List<String> mutelist;//被静音的帐号列表
	private List<String> blacklist;//加黑的帐号列表
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<String> getMutelist() {
		return mutelist;
	}
	public void setMutelist(List<String> mutelist) {
		this.mutelist = mutelist;
	}
	public List<String> getBlacklist() {
		return blacklist;
	}
	public void setBlacklist(List<String> blacklist) {
		this.blacklist = blacklist;
	}
	
}
