package com.fun.likechat.wechat.data;

/**
 * https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_7
 * 订单通知vo
 */
public class WechatOrderNotificationData {

	private String return_code;//SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
	private String return_msg;//返回信息，如非空，为错误原因
	private String appid;//微信开放平台审核通过的应用APPID
	private String mch_id;//微信支付分配的商户号
	private String nonce_str;//随机字符串，不长于32位
	private String result_code;//SUCCESS/FAIL
	private String err_code;//错误返回的信息描述
	private String err_code_des;//错误返回的信息描述
	private String openid;//用户在商户appid下的唯一标识
	private String trade_type;//APP
	private String bank_type;//银行类型，采用字符串类型的银行标识，银行类型见银行列表
	private String total_fee;//订单总金额，单位为分
	private String cash_fee;//现金支付金额订单现金支付金额，详见支付金额
	private String transaction_id;//微信支付订单号
	private String out_trade_no;//商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
	private String attach;//商家数据包，原样返回
	private String time_end;//支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	private String sign;//签名
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
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
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getCash_fee() {
		return cash_fee;
	}
	public void setCash_fee(String cash_fee) {
		this.cash_fee = cash_fee;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	@Override
    public String toString() {
	    return "WechatOrderNotification [return_code=" + return_code + ", return_msg=" + return_msg + ", appid=" + appid + ", mch_id=" + mch_id + ", nonce_str=" + nonce_str + ", result_code=" + result_code + ", err_code=" + err_code + ", err_code_des=" + err_code_des + ", openid=" + openid + ", trade_type=" + trade_type + ", bank_type=" + bank_type + ", total_fee=" + total_fee + ", cash_fee=" + cash_fee + ", transaction_id=" + transaction_id + ", out_trade_no=" + out_trade_no + ", attach=" + attach + ", time_end=" + time_end + ", sign=" + sign + "]";
    }
}
