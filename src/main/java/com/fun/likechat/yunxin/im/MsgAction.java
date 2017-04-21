package com.fun.likechat.yunxin.im;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.yunxin.YXConstant;
import com.fun.likechat.yunxin.YXHttpClient;
import com.fun.likechat.yunxin.data.ResultInfo;
import com.fun.likechat.yunxin.data.SendMsgReqData;

/**
 * 云信消息功能模块
 * @author yangyiqiang
 *
 */
public class MsgAction {

	/**
	 * 发送普通消息
	 * @param data
	 * @return code  200 成功
	 * @throws Exception
	 */
	public String sendMsg(SendMsgReqData data)throws Exception{
		String result=null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("from", data.getFrom()));
		nvps.add(new BasicNameValuePair("ope", data.getOpe()+""));
		nvps.add(new BasicNameValuePair("to", data.getTo()));
		nvps.add(new BasicNameValuePair("type", data.getType() + ""));
		nvps.add(new BasicNameValuePair("body", data.getBody()));
		//--下面非必须---
		if(StringUtils.isNotBlank(data.getAntispam())){
			nvps.add(new BasicNameValuePair("antispam", data.getAntispam()));
		}
		if(StringUtils.isNotBlank(data.getAntispamCustom())){
			nvps.add(new BasicNameValuePair("antispamCustom", data.getAntispamCustom()));
		}
		if(StringUtils.isNotBlank(data.getOption())){
			nvps.add(new BasicNameValuePair("option", data.getOption()));
		}
		if(StringUtils.isNotBlank(data.getPushcontent())){
			nvps.add(new BasicNameValuePair("pushcontent", data.getPushcontent()));
		}
		if(StringUtils.isNotBlank(data.getPayload())){
			nvps.add(new BasicNameValuePair("payload", data.getPayload()));
		}
		if(StringUtils.isNotBlank(data.getExt())){
			nvps.add(new BasicNameValuePair("ext", data.getExt()));
		}
		if(StringUtils.isNotBlank(data.getForcepushlist())){
			nvps.add(new BasicNameValuePair("forcepushlist", data.getForcepushlist()));
		}
		if(StringUtils.isNotBlank(data.getForcepushcontent())){
			nvps.add(new BasicNameValuePair("forcepushcontent", data.getForcepushcontent()));
		}
		if(StringUtils.isNotBlank(data.getForcepushall())){
			nvps.add(new BasicNameValuePair("forcepushall", data.getForcepushall()));
		}
		try {
			String json = YXHttpClient.postRequest(YXConstant.SEND_MSG_URL, nvps);
			ResultInfo info = JsonHelper.toObject(json, ResultInfo.class);
			if ("200".equals(info.getCode())) {
				result = "200";
			} else {
				LogFactory.getInstance().getLogger().debug("请求发送消息接口错误：" + json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
