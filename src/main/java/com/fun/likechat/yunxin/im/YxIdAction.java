package com.fun.likechat.yunxin.im;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.yunxin.YXConstant;
import com.fun.likechat.yunxin.YXHttpClient;
import com.fun.likechat.yunxin.data.CreateIdReqData;
import com.fun.likechat.yunxin.data.CreateIdResData;
import com.fun.likechat.yunxin.data.RefreshTokenResData;
import com.fun.likechat.yunxin.data.ResultInfo;

@Service
public class YxIdAction {

	/**
	 * 创建云信ID: 说明页：http://dev.netease.im/docs?doc=server&#消息抄送 POST
	 * https://api.netease.im/nimserver/user/create.action HTTP/1.1
	 * Content-Type:application/x-www-form-urlencoded;charset=utf-8
	 * 
	 * @param args
	 */
	public CreateIdResData createYXId(CreateIdReqData data) throws Exception {
		CreateIdResData result = null;
		if (data != null) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("accid", data.getAccid()));
			nvps.add(new BasicNameValuePair("name", data.getName()));
			nvps.add(new BasicNameValuePair("icon", data.getIcon()));
			try {
				String json = YXHttpClient.postRequest(YXConstant.CREATE_URL, nvps);
				ResultInfo info = JsonHelper.toObject(json, ResultInfo.class);
				if ("200".equals(info.getCode())) {
					result = new CreateIdResData();
					Map<String, String> infoMap=info.getInfo();
					result.setAccid(infoMap.get("accid"));
					result.setName(infoMap.get("name"));
					result.setToken(infoMap.get("token"));
				} else {
					LogFactory.getInstance().getLogger().debug("请求创建云信ID错误：" + json);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 云信ID更新
	 * 
	 * @param accid
	 *            yes
	 * @param props
	 *            no
	 * @param token
	 *            no
	 * @return
	 * @throws Exception
	 */
	public String updateYXId(String accid, String props, String token) throws Exception {
		String result = "-1";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("accid", accid));
		nvps.add(new BasicNameValuePair("props", props));
		nvps.add(new BasicNameValuePair("token", token));
		try {
			String json = YXHttpClient.postRequest(YXConstant.UPDATE_URL, nvps);
			ResultInfo info = JsonHelper.toObject(json, ResultInfo.class);
			if ("200".equals(info.getCode())) {
				result = "200";
			} else {
				LogFactory.getInstance().getLogger().debug("请求更新云信ID错误：" + json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 更新token
	 * 
	 * @param accid
	 * @return 更新后的token
	 * @throws Exception
	 */
	public RefreshTokenResData refreshToken(String accid) throws Exception {
		RefreshTokenResData result = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("accid", accid));
		try {
			String json = YXHttpClient.postRequest(YXConstant.REFRESHTOKEN_URL, nvps);
			ResultInfo info = JsonHelper.toObject(json, ResultInfo.class);
			if ("200".equals(info.getCode())) {
				result=new RefreshTokenResData();
				Map<String, String> infoMap=info.getInfo();
				result.setAccid(infoMap.get("accid"));
				result.setToken(infoMap.get("token"));
			} else {
				LogFactory.getInstance().getLogger().debug("请求更新token错误：" + json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
