package com.fun.likechat.yunxin.data;

/**
 * 发送普通消息请求数据
 * @author yangyiqiang
 *
 */
public class SendMsgReqData {
	private String from;//String	是	发送者accid，用户帐号，最大32字符，必须保证一个APP内唯一
	private int ope;//	int	是	0：点对点个人消息，1：群消息（高级群），其他返回414
	private String to;//	String	是	ope==0是表示accid即用户id，ope==1表示tid即群id
	private int type;//	int	是	0 表示文本消息,1 表示图片2 表示语音，3 表示视频，4 表示地理位置信息，6 表示文件，100 自定义消息类型
	private String body;//	String	是	请参考下方消息示例说明中对应消息的body字段，最大长度5000字符，为一个JSON串
	private String antispam;//	String	否	本消息是否需要过自定义反垃圾系统，true或false, 默认false
	private String antispamCustom;//String	否	自定义的反垃圾内容, JSON格式，长度限制同body字段，不能超过5000字符，antispamCustom示例：{"type":1,"data":"custom content"}
	//字段说明：1. type: 1:文本，2：图片，3视频;2. data: 文本内容or图片地址or视频地址。
	private String option;//	String	否	发消息时特殊指定的行为选项,JSON格式，可用于指定消息的漫游，存云端历史，发送方多端同步，推送，消息抄送等特殊行为;option中字段不填时表示默认值 ，option示例:
	//{"push":false,"roam":true,"history":false,"sendersync":true,"route":false,"badge":false,"needPushNick":true}
	private  String pushcontent;//否 ios推送内容，不超过150字符，option选项中允许推送（push=true），此字段可以指定推送内容
	private  String payload;// 否 os 推送对应的payload,必须是JSON,不能超过2k字符
	private String ext;//否 开发者扩展字段，长度限制1024字符
	private String forcepushlist;//	String	否	发送群消息时的强推（@操作）用户列表，格式为JSONArray，如["accid1","accid2"]。若forcepushall为true，则forcepushlist为除发送者外的所有有效群成员
	private String forcepushcontent;//	String	否	发送群消息时，针对强推（@操作）列表forcepushlist中的用户，强制推送的内容
	private String forcepushall;//	String	否	发送群消息时，强推（@操作）列表是否为群里除发送者外的所有有效成员，true或false，默认为false
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public int getOpe() {
		return ope;
	}
	public void setOpe(int ope) {
		this.ope = ope;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAntispam() {
		return antispam;
	}
	public void setAntispam(String antispam) {
		this.antispam = antispam;
	}
	public String getAntispamCustom() {
		return antispamCustom;
	}
	public void setAntispamCustom(String antispamCustom) {
		this.antispamCustom = antispamCustom;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public String getPushcontent() {
		return pushcontent;
	}
	public void setPushcontent(String pushcontent) {
		this.pushcontent = pushcontent;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getForcepushlist() {
		return forcepushlist;
	}
	public void setForcepushlist(String forcepushlist) {
		this.forcepushlist = forcepushlist;
	}
	public String getForcepushcontent() {
		return forcepushcontent;
	}
	public void setForcepushcontent(String forcepushcontent) {
		this.forcepushcontent = forcepushcontent;
	}
	public String getForcepushall() {
		return forcepushall;
	}
	public void setForcepushall(String forcepushall) {
		this.forcepushall = forcepushall;
	}
}
