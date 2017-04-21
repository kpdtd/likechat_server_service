package com.fun.likechat.yunxin.data;

import java.util.List;

public class GetFriendsListResultInfo {
	private String code;
	private int size;
	private List<GetFriendsResData> friends;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<GetFriendsResData> getFriends() {
		return friends;
	}
	public void setFriends(List<GetFriendsResData> friends) {
		this.friends = friends;
	}
}
