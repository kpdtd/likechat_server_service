package com.fun.likechat.constant;

import java.util.HashMap;
import java.util.Map;

public enum ErrCodeEnum {
	
	success(0,"成功"),
	error(1,"系统异常"),
	//闪屏错误码
	adBanner_error(2001,"没有可用的闪屏图片"),
	//主播收藏
	delCollectionActor_error(3001,"删除关注主播参数错误"),
	addCollectionActor_error(3002,"关注主播参数错误"),
	getCollectionVideos_error(4001,"获取收藏视频列表用户错误"),
	feedback_error(5001,"用户没有登录不能反馈建议"),
	feedbackList_error(5002,"获取反馈建议列表参数错误"),
	getMyPageData_error(6001,"用户没有登录"),
	getAllAnchorRankingList_error(7001,"无法获取榜单频道信息"),
	
	addVideoComments_error(9001,"增加视频评论错误，用户和视频ID不能为空"),
	collectionComments_error(9002,"视频评论点赞错误，用户或评论ID不能为空"),
	
	//用户
	sendValidationCode_error2(8009,"短信验证码下发失败,该用户已停用"),
	sendValidationCode_error(8008,"短信验证码下发失败"),
	getValidationCode_error(8007,"产生验证码异常"),
	userMobileNull(8006,"登录失败,手机号不能为空"),
	userMobileOrCode_error(8005,"登录失败,手机号或验证码错误"),
	userState_error(8004,"登录失败,该用户已停用"),
	validationCode_error(8003,"验证码超过有效期"),
	userNickNameExist(8002,"该昵称已经存在"),
	userNotExist(8001,"该用户不存在"),
	//图片上传
	picPath_error(8050,"没有配置图片保存根目录"),
	pic_user_logout(8051,"没有登录用户信息，无法进行图片上传操作");
	private int code;
	private String desc;
	
	private static final Map<Integer , String> err_desc = new HashMap<Integer , String>();
	
	static {
		for(ErrCodeEnum refer : ErrCodeEnum.values()) {
			err_desc.put(refer.getCode(), refer.getDesc());
		}
	}
	
	private ErrCodeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static String getDescByCode(int code) {
		return err_desc.get(code);
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
