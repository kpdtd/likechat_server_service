package com.fun.likechat.wechat;

public class WeChatConst {
	//积分超购
	/**
	 * AppID：微信开放平台审核通过的应用APPID 
	 */
	public final static String appid = "wx9af1549f05e98da1";
	
	//微信支付商户号  -- 商户平台
	public final static String mch_id = "1480109622";
	
	//body  商品描述  商品描述交易字段格式根据不同的应用场景按照以下格式： APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
	public final static String body = "积分超购";
	
	public final static String notify_url = "";//微信回调通知url
	
	public final static String key = "1234567";//微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
	
	public final static String unifiedorder = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
