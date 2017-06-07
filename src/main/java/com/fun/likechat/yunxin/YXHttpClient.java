package com.fun.likechat.yunxin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fun.likechat.util.JsonHelper;


public class YXHttpClient {
	public static final String CHARACTER_ENCODING = "UTF-8";
	public static final String CHARACTER_GBK = "GBK";
	private static final String AppKey = "fa0f2219206b8a2e1be41fb9382cd0f4";
	public static final String AppSecret = "d15f9bba5ae5";// 去http://app.netease.im/index#/app/info/3263984
	/**
	 * 加入频道支持安全模式和非安全模式两种：
	 * 非安全模式：客户端只需要AppKey即可完成认证进行实时通话，这种情况下用户需要保管好AppKey，防止泄露，默认情况下非安全模式处于关闭状态, 如需开启请联系我们的客服；
	 * 安全模式：客户端需要AppKey和一个Token来完成认证进行实时通话。 其中Token需要第三方服务器向网易云通信服务器获取后传回客户端。
	 */
	private static final String tokenUrl = "https://api.netease.im/nimserver/user/getToken.action";// 获取token的url
	private static final String createAccidUrl = "https://api.netease.im/nimserver/user/create.action";


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
	 * post请求并返回数据包--网易云信的例子，可以测试，未必生产环境合适
	 * 
	 * @param url
	 * 请求url
	 * @param requestData
	 * 请求数据
	 * @param requestProperties
	 * 请求包体
	 * @return byte[] 数据包
	 * @throws IOException
	 */
	public static String postRequest(String url, List<NameValuePair> nvps) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		setYXHead(httpPost);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
		HttpResponse response = httpClient.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity(), CHARACTER_ENCODING);// entity所得到的流是不可重复读取，读取完会关闭
		return result;
	}

	/**
	 * 网易云信的id就是要同步我方账号到云信账户。参考文档：http://dev.netease.im/docs/product/IM%E5%8D%B3%E6%97%B6%E9%80%9A%E8%AE%AF/%E6%9C%8D%E5%8A%A1%E7%AB%AFAPI%E6%96%87%E6%A1%A3#API调用说明
	 * 实现思路建议如下：
	 * 1、每次音视频通话需要获取token，先查看token是否已经在db存在；
	 * 2、存在说明用户已经同步到网易云信上。不存在则调用接口创建网易云信id
	 *
	 */
//	public static String getToken() {
//		List<NameValuePair> nvps = new List<NameValuePair>();
//		
//		postRequest(createAccidUrl, );
//	}
	
	/**
	 * http://dev.netease.im/docs/product/IM%E5%8D%B3%E6%97%B6%E9%80%9A%E8%AE%AF/%E6%9C%8D%E5%8A%A1%E7%AB%AFAPI%E6%96%87%E6%A1%A3#API调用说明
	 * @param accid：必填 网易云通信ID，最大长度32字符，必须保证一个APP内唯一（只允许字母、数字、半角下划线_、@、半角点以及半角-组成，不区分大小写，会统一小写处理，请注意以此接口返回结果中的accid为准
	 * @param name：可空  网易云通信ID昵称，最大长度64字符，用来PUSH推送时显示的昵称
	 * @param icon: 可空 网易云通信ID头像URL，第三方可选填，最大长度1024
	 * @param token 可空 网易云通信ID可以指定登录token值，最大长度128字符，并更新，如果未指定，会自动生成token，并在创建成功后返回
	 * @return
	 * @throws Exception 
	 */
	//注意：我们默认的token就是 openId
	public static String createAccid(String accid, String name, String icon, String token) throws Exception {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("accid", accid));
		if(StringUtils.isNotEmpty(name)) {
			nvps.add(new BasicNameValuePair("name", name));
		}
		if(StringUtils.isNotEmpty(icon) && icon.startsWith("http")) {
			nvps.add(new BasicNameValuePair("icon", icon));
		}
		if(StringUtils.isNotEmpty(token)) {
			nvps.add(new BasicNameValuePair("token", token));
		}
		return postRequest(createAccidUrl, nvps);
	}

	/**
	 * 判断字符是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if(str.matches("\\d*")) {
			return true;
		}
		else {
			return false;
		}
	}

	public static void main(String[] args) throws Exception {
//		System.out.println(CheckSumBuilder.getCheckSum("go9dnk49bkd9jd9vmel1kglw0803mgq3", "4tgggergigwow323t23t", AppSecret));
		
		/**
		 * 测试云信添加用户接口
		 */
		Map<String,Object>  map =  JsonHelper.toMap(YXHttpClient.createAccid("369689","云信测试4", null, "369689"));
        //遍历map获取key and value
        for(Entry<String, Object> entry : map.entrySet()) {
	        String key = entry.getKey();
	        System.out.println("@"+key);
	        Object value = entry.getValue();
	        System.out.println("@"+value);
	        System.out.println("================");
	        if(value instanceof Map  ) {
//	        	Map m = (Map)value;
	        	for(Entry<String, String> entryss : ((Map<String, String>) value).entrySet()) {
	        		String keys = entryss.getKey();
	    	        System.out.println("#"+keys);
	    	        String values = entryss.getValue();
	    	        System.out.println("#"+values);
	        	}
	        }
        }
		
		
	}

}