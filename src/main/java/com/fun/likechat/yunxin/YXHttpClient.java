package com.fun.likechat.yunxin;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fun.likechat.util.RedisFactory;

public class YXHttpClient {
	public static final String CHARACTER_ENCODING = "UTF-8";
	public static final String CHARACTER_GBK = "GBK";
	private static final String AppKey = "fa0f2219206b8a2e1be41fb9382cd0f4";
	public static final String AppSecret = "d15f9bba5ae5";// 去http://app.netease.im/index#/app/info/3263984
															// 刷新

	/**
	 * 云信http协议头定制；
	 */
	private static HttpPost setYXHead(HttpPost httpPost) {
		String curTime = String.valueOf((new Date()).getTime() / 1000L);
		String nonce = "startfly";
		httpPost.addHeader("AppKey", AppKey);
		httpPost.addHeader("Nonce", nonce);// 随机数，最大128
		httpPost.addHeader("CurTime", curTime);
		httpPost.addHeader("CheckSum", CheckSumBuilder.getCheckSum(AppSecret, nonce, curTime));
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		return httpPost;
	}

	/**
	 * post请求并返回数据包
	 * 
	 * @param url
	 *            请求url
	 * @param requestData
	 *            请求数据
	 * @param requestProperties
	 *            请求包体
	 * @return byte[] 数据包
	 * @throws IOException
	 */
	public static String  postRequest(String url, List<NameValuePair> nvps) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		setYXHead(httpPost);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
		HttpResponse response = httpClient.execute(httpPost);
		String result=EntityUtils.toString(response.getEntity(), CHARACTER_ENCODING);
		return result;
	}

	/**
	 * 判断字符是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str.matches("\\d*")) {
			return true;
		} else {
			return false;
		}
	}

	
	public static void main(String[] args) throws Exception {
		System.out.println(CheckSumBuilder.getCheckSum("go9dnk49bkd9jd9vmel1kglw0803mgq3", "4tgggergigwow323t23t",AppSecret));
	}

}