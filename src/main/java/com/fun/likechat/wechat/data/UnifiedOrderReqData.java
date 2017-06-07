package com.fun.likechat.wechat.data;

public class UnifiedOrderReqData {
	private String appid;//ok  String(32)	wxd678efh567hg6787	微信开放平台审核通过的应用APPID
	private String mch_id;//ok	String(32)	1230000109	微信支付分配的商户号
	private String nonce_str;//ok 服务器产生 ，防止重复提交  String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，不长于32位。推荐随机数生成算法
	private String sign;//ok- (传送的sign参数不参与签名)	String(32)	C380BEC2BFD727A4B6845133519F3AD6	签名，详见签名生成算法
	private String body;//ok String(128)	 商品描述交易字段格式根据不同的应用场景按照以下格式： APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
	private String out_trade_no;//--注意   客户端产生--防止重复提交	String(32)	20150806125346	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。详见商户订单号
	private Integer total_fee;//应该有统一的商品库？？？ Int	888	订单总金额，单位为分，详见支付金额
	private String spbill_create_ip;//ok  String(16)	123.12.12.123	用户端实际ip
	private String notify_url;//ok  String(256)	http://www.weixin.qq.com/wxpay/pay.php	接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	private String trade_type;//--ok  String(16)	APP	支付类型
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public Integer getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	@Override
    public String toString() {
	    return "nifiedOrderReqData [appid=" + appid + ", mch_id=" + mch_id + ", nonce_str=" + nonce_str + ", sign=" + sign + ", body=" + body + ", out_trade_no=" + out_trade_no + ", total_fee=" + total_fee + ", spbill_create_ip=" + spbill_create_ip + ", notify_url=" + notify_url + ", trade_type=" + trade_type + "]";
    }
	
}
