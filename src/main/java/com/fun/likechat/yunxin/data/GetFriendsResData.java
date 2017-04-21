package com.fun.likechat.yunxin.data;

public class GetFriendsResData {

	private String createtime;//加好友时间
	private String faccid;//好友的accid
	private String alias;//备注名称
	private String ex;//扩展
	private boolean bidirection;//相互都是好友，true
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getFaccid() {
		return faccid;
	}
	public void setFaccid(String faccid) {
		this.faccid = faccid;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getEx() {
		return ex;
	}
	public void setEx(String ex) {
		this.ex = ex;
	}
	public boolean isBidirection() {
		return bidirection;
	}
	public void setBidirection(boolean bidirection) {
		this.bidirection = bidirection;
	}
	
}
