package com.fun.likechat.vo;

/**
 * 微信统一下单返回vo
 * @author 
 *
 */
public class WeChatUnifiedOrderReturnVo {
	/**
	 * 详细含义见：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
	 * 
	 * 有增加客户端要求返回的字段：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_12
	 */
	private String out_trade_no;//客户端生成的订单号
	private String return_code;//返回码“	SUCCESS/FAIL”
	private String return_msg;
	private String result_code;//SUCCESS/FAIL
	private String err_code;
	private String err_code_des;
	private String prepay_id;//预支付交易会话标识 用于后续接口调用中使用，该值有效期为2小时.在return_code 和result_code都为SUCCESS的时候有返回
	private String partnerid;//微信支付分配的商户号
	private String appid;//微信开放平台审核通过的应用APPID
	private String noncestr;//随机字符串，不长于32位。推荐随机数生成算法
	private String timestamp;//时间戳https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_2
	private String sign;
	
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
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
	public String getPrepay_id() {
		return prepay_id;
	}
	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	@Override
    public String toString() {
	    return "WeChatUnifiedOrderReturnVo [out_trade_no=" + out_trade_no + ", return_code=" + return_code + ", return_msg=" + return_msg + ", result_code=" + result_code + ", err_code=" + err_code + ", err_code_des=" + err_code_des + ", prepay_id=" + prepay_id + ", sign=" + sign + "]";
    }
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
