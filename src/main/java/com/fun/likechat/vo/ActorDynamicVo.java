package com.fun.likechat.vo;

import java.util.List;

public class ActorDynamicVo {

	private Integer id;//动态ID
    private String nickname; // 主播昵称
    private String imgUrl;// 主播图片URL
    private String signature;// 主播个性签名
    private String createTime; // 动态的更新时间
    private String content; // 动态文字说明
    private int dynamicType; // 动态类型（1、视频2、照片3、语音）
    private int price;
    private int pageView; // 动态浏览量
    private List<String> dynamicUrl; // 动态URL，字符串的列表（视频、照片、语音的下载播放地址）
    
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getDynamicType() {
        return dynamicType;
    }
    public void setDynamicType(int dynamicType) {
        this.dynamicType = dynamicType;
    }
    public List<String> getDynamicUrl() {
        return dynamicUrl;
    }
    public void setDynamicUrl(List<String> dynamicUrl) {
        this.dynamicUrl = dynamicUrl;
    }
	public int getPageView() {
		return pageView;
	}
	public void setPageView(int pageView) {
		this.pageView = pageView;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	} 

}
