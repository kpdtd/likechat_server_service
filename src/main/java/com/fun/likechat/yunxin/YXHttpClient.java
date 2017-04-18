package com.fun.likechat.yunxin;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fun.likechat.util.RedisFactory;

public class YXHttpClient {
	public static final String CHARACTER_ENCODING = "UTF-8";
	public static final String CHARACTER_GBK = "GBK";
	private static final String AppKey = "fa0f2219206b8a2e1be41fb9382cd0f4";
	private static final String AppSecret = "d15f9bba5ae5";// 去http://app.netease.im/index#/app/info/3263984 刷新

	/**
	 * post 请求，返回ResultInfo的数据包
	 * 
	 */
	public static ReturnInfo postRequestGBK(String url, Map<String, Object> map) throws IOException {
		return postFormRequest(url, map, CHARACTER_GBK);
	}

	public static ReturnInfo postRequestUTF8(String url, Map<String, Object> map) throws IOException {
		return postFormRequest(url, map, CHARACTER_ENCODING);
	}

	public static ReturnInfo getRequest(String url, String requestData) throws IOException {
		return getRequest(url);
	}

	// public static ReturnInfo getRequestUTF8(String url, String requestData) throws IOException {
	// return postRequest(url, requestData.getBytes(CHARACTER_ENCODING), CHARACTER_ENCODING);
	// }

	/**
	 * 云信http协议头定制；
	 */
	private static HttpURLConnection setYXHead(HttpURLConnection httpConn) {
		String curTime = String.valueOf((new Date()).getTime() / 1000L);
		String nonce = "startfly";
		httpConn.setRequestProperty("AppKey", AppKey);
		httpConn.setRequestProperty("Nonce", nonce);// 随机数，最大128
		httpConn.setRequestProperty("CurTime", curTime);
		httpConn.setRequestProperty("CheckSum", CheckSumBuilder.getCheckSum(AppKey, nonce, curTime));
		return httpConn;
	}

	/**
	 * HTTPS 请求，返回String的数据包
	 * 
	 */
	public static ReturnInfo httpsPostRequest(String url, String requestData) throws Exception {
		return httpsPostRequest(url, requestData.getBytes(CHARACTER_ENCODING), CHARACTER_ENCODING);
	}

	/**
	 * 从网络获取json数据,(String byte[})
	 * @param path
	 * @return
	 */
	public static ReturnInfo getRequest(String uri) {
		ReturnInfo result = new ReturnInfo();
		try {
			URL url = new URL(uri.trim());
			// 打开连接
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			int code = httpConn.getResponseCode();
			result.setCode(code);
			if(200 == code) {
				// 得到输入流
				InputStream is = httpConn.getInputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while(-1 != (len = is.read(buffer))) {
					baos.write(buffer, 0, len);
					baos.flush();
				}
				result.setContent(baos.toString("utf-8"));
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * post请求并返回数据包
	 * @param url
	 * 请求url
	 * @param requestData
	 * 请求数据
	 * @param requestProperties
	 * 请求包体
	 * @return byte[] 数据包
	 * @throws IOException
	 */
	public static ReturnInfo postFormRequest(String url, Map<String, Object> map, String charachter) throws IOException {
		HttpURLConnection httpConn = null;
		StringBuffer sBuffer = new StringBuffer("");
		ReturnInfo result = new ReturnInfo();
		try {
			httpConn = (HttpURLConnection) new URL(url).openConnection();
			int code = httpConn.getResponseCode();
			result.setCode(code);
			if(200 == code) {
				StringBuffer params = new StringBuffer();
				int length = 0;
				Iterator it = map.entrySet().iterator();
				while(it.hasNext()) {
					Map.Entry element = (Map.Entry) it.next();
					params.append(element.getKey());
					params.append("=");
					params.append(element.getValue());
					params.append("&");
				}
				if(params.length() > 0) {
					params.deleteCharAt(params.length() - 1);//去掉最后一个无用的&
					length = params.length();
				}
				httpConn = setYXHead(httpConn);// 增加网易云信head
				httpConn.setConnectTimeout(15000);// 连接超时 单位毫秒
				// conn.setReadTimeout(2000);//读取超时 单位毫秒
				httpConn.setRequestMethod("POST");
				httpConn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=" + charachter);
				// httpConn.setRequestProperty("Content-type",
				// "multipart/form-data;charset=UTF-8");
				httpConn.setRequestProperty("Connection", "close");
				httpConn.setRequestProperty("Content-Length", String.valueOf(length));
				// 发送POST请求必须设置如下两行
				httpConn.setDoInput(true);
				httpConn.setDoOutput(true);
				OutputStream outStream = httpConn.getOutputStream();
				outStream.write(params.toString().getBytes(charachter));
				outStream.flush();
				outStream.close();
				BufferedReader in = null;
				String inputLine = null;
				in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), charachter));
				while((inputLine = in.readLine()) != null) {
					sBuffer.append(inputLine);
				}
				in.close();
			}

		}
		catch(IOException e) {
			throw e;
		}
		finally {
			if(httpConn != null) {
				httpConn.disconnect();
				httpConn = null;
			}
		}
		result.setContent(sBuffer.toString());
		return result;
	}

	/**
	 * post请求并返回数据包
	 * @param url
	 * 请求url
	 * @param requestData
	 * 请求数据
	 * @param requestProperties
	 * 请求包体
	 * @return byte[] 数据包
	 * @throws IOException
	 */
	public static ReturnInfo postJsonRequest(String url, byte[] requestData, String charachter) throws IOException {
		HttpURLConnection httpConn = null;
		StringBuffer sBuffer = new StringBuffer("");
		ReturnInfo result = new ReturnInfo();
		try {
			httpConn = (HttpURLConnection) new URL(url).openConnection();
			int code = httpConn.getResponseCode();
			result.setCode(code);
			if(200 == code) {
				String length = "0";
				if(requestData != null) {
					length = Integer.toString(requestData.length);
				}
				httpConn = setYXHead(httpConn);// 增加网易云信head
				httpConn.setConnectTimeout(15000);// 连接超时 单位毫秒
				// conn.setReadTimeout(2000);//读取超时 单位毫秒
				httpConn.setRequestMethod("POST");
				httpConn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=" + charachter);
				// httpConn.setRequestProperty("Content-type",
				// "multipart/form-data;charset=UTF-8");
				httpConn.setRequestProperty("Connection", "close");
				httpConn.setRequestProperty("Content-Length", length);
				// 发送POST请求必须设置如下两行
				httpConn.setDoInput(true);
				httpConn.setDoOutput(true);
				OutputStream outStream = httpConn.getOutputStream();
				outStream.write(requestData);
				outStream.flush();
				outStream.close();
				BufferedReader in = null;
				String inputLine = null;
				in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), charachter));
				while((inputLine = in.readLine()) != null) {
					sBuffer.append(inputLine);
				}
				in.close();
			}

		}
		catch(IOException e) {
			throw e;
		}
		finally {
			if(httpConn != null) {
				httpConn.disconnect();
				httpConn = null;
			}
		}
		result.setContent(sBuffer.toString());
		return result;
	}

	/**
	 * https发送消息
	 * @param url
	 * @param requestData
	 * @param requestProperties
	 * @return
	 * @throws Exception
	 */
	public static ReturnInfo httpsPostRequest(String url, byte[] requestData, String character) throws Exception {
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
		HttpsURLConnection httpsConn = null;
		StringBuffer sBuffer = new StringBuffer("");
		ReturnInfo result = new ReturnInfo();

		try {
			int code = httpsConn.getResponseCode();
			result.setCode(code);
			if(200 == code) {
				httpsConn = (HttpsURLConnection) new URL(url).openConnection();
				httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
				httpsConn.setSSLSocketFactory(sc.getSocketFactory());
				httpsConn.setConnectTimeout(15000);// 连接超时 单位毫秒
				// conn.setReadTimeout(2000);//读取超时 单位毫秒
				httpsConn.setRequestMethod("POST");
				httpsConn.setDoInput(true);
				httpsConn.setDoOutput(true);
				BufferedOutputStream hurlBufOus = new BufferedOutputStream(httpsConn.getOutputStream());
				hurlBufOus.write(requestData);
				hurlBufOus.flush();
				hurlBufOus.close();
				httpsConn.connect();
				BufferedReader in = null;
				String inputLine = null;
				in = new BufferedReader(new InputStreamReader(httpsConn.getInputStream(), character));
				while((inputLine = in.readLine()) != null) {
					sBuffer.append(inputLine);
				}
				in.close();
			}
		}
		finally {
			if(httpsConn != null) {
				httpsConn.disconnect();
				httpsConn = null;
			}
		}
		result.setContent(sBuffer.toString());
		return result;
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

	/**
	 * <p>
	 * 封装一个返回结果信息的对象
	 * </p>
	 */
	private static class ReturnInfo {
		private int code;
		private String content;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}

	/**
	 * https免证书登录，复写达到不校验本地X509证书效果；
	 * 修改记录:
	 */
	private static class TrustAnyTrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	public static void main(String[] args) throws Exception {
		// HttpClient.postRequest("http://192.168.20.27:8080/tasting_server/api/c_special_area", "");
		// HttpClient.postRequest("http://192.168.11.165:8080/tasting_server/api/c_special_area", "");
		// HttpClient.postRequest("http://localhost:8080/tasting_server/api/user/info/detail", "111");
		String b = URLEncoder.encode("验证码123456", "GBK");
		// System.out.println(HttpClient.postRequest("http://3g.3gxcm.com/sms/push_mt.jsp?cpid=lanmifeng&cppwd=lanmifeng&phone=18910266103&spnumber=&msgcont="+b,
		// ""));
		String aaString = "aaaaa//////";

		System.out.println(URLEncoder.encode(aaString, "UTF-8"));
		System.out.println(URLDecoder.decode(b, "UTF-8"));
		ApplicationContext app = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		RedisFactory redisFactory = (RedisFactory) app.getBean("redisFactory");
		String[][] aa = new String[][] { new String[] { "1", "aa" }, new String[] { "17", "bb" }, new String[] { "20", "cc" } };
		redisFactory.set("11", "11");
	}

}