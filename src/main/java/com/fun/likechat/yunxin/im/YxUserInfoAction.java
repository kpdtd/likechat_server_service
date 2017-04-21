package com.fun.likechat.yunxin.im;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.yunxin.YXConstant;
import com.fun.likechat.yunxin.YXHttpClient;
import com.fun.likechat.yunxin.data.ResultInfo;
import com.fun.likechat.yunxin.data.UpdateUinfoReqData;

@Service
public class YxUserInfoAction {

	/**
	 * 更新用户名片
	 * @param data
	 * @return code 
	 * @throws Exception
	 */
	public String updateUinfo(UpdateUinfoReqData data) throws Exception {
		String result = null;
		if (data != null) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("accid", data.getAccid()));
			nvps.add(new BasicNameValuePair("name", data.getName()));
			nvps.add(new BasicNameValuePair("icon", data.getIcon()));
			nvps.add(new BasicNameValuePair("sign", data.getSign()));
			nvps.add(new BasicNameValuePair("email", data.getEmail()));
			nvps.add(new BasicNameValuePair("birth", data.getBirth()));
			nvps.add(new BasicNameValuePair("mobile", data.getMobile()));
			nvps.add(new BasicNameValuePair("gender", data.getGender()+""));
			nvps.add(new BasicNameValuePair("ex", data.getEx()));
			try {
				String json = YXHttpClient.postRequest(YXConstant.UPDATE_UINFO_URL, nvps);
				ResultInfo info = JsonHelper.toObject(json, ResultInfo.class);
				if ("200".equals(info.getCode())) {
					result ="200";
				} else {
					LogFactory.getInstance().getLogger().debug("请求更新用户名片错误：" + json);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * "用户名片"接口好像用不上，直接从数据库取就好，不用去云信
	 */
}
