package com.fun.likechat.constant;

import java.util.HashMap;
import java.util.Map;

public enum ErrCodeEnum {
	
	success(0,"成功"),
	error(1,"系统异常"),
	//闪屏错误码
	delAttentionActor_error(3001,"删除关注主播参数错误"),
	addAttentionActor_error(3002,"关注主播userId或actorId参数错误"),
	getMyPageData_error(6001,"用户没有登录"),
	
	//支付
	pay_error(1001,"创建订单失败，请求参数错误"),
	//用户
	userInfo_error(8010,"登录失败,用户信息错误"),
	userNickNameExist(8002,"该昵称已经存在"),
	userNotExist(8001,"该用户不存在"),
	
	//云信
	yunxinCreateAccid_error(7001,"创建云信用户失败，请重新请求"),
	// 微信支付
	wechatpay_error(7010,"微信统一下单错误"),
	wechatpay_json_error(7011,"微信统一下单消息转JSON错误");
	//图片上传
//	picPath_error(8050,"没有配置图片保存根目录"),
//	pic_user_logout(8051,"没有登录用户信息，无法进行图片上传操作");
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
