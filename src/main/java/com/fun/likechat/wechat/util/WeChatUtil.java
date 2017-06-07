package com.fun.likechat.wechat.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

/**
 * 参考：http://blog.csdn.net/gbguanbo/article/details/50915333
 * @author
 *
 */
public class WeChatUtil {
	/**
	 * 辅助算法 生成指定位数的随机秘钥
	 * @param KeyLength
	 * @return Keysb
	 */
	public static String KeyCreate(int keyLength) {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer Keysb = new StringBuffer();
		for(int i = 0; i < keyLength; i++) {
			int number = random.nextInt(base.length());
			Keysb.append(base.charAt(number));
		}
		return Keysb.toString();
	}

	// 请求xml组装
	public static String getRequestXml(SortedMap<String, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			String value =  entry.getValue().toString();
			//if("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key)) {
			if("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key)) {
				sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
			}
			else {
				sb.append("<" + key + ">" + value + "</" + key + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 
	 * @param characterEncoding  UTF-8
	 * @param parameters  
	 * @param API_KEY   String key = "";//微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
	 * @return
	 */
	public static String createSign(String characterEncoding, SortedMap<String, Object> parameters, String API_KEY) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + API_KEY);
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}

	// 请求方法
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			// 当outputStr不为null时向输出流写数据
			if(null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			return buffer.toString();
		}
		catch(ConnectException ce) {
			System.out.println("连接超时：{}" + ce);
		}
		catch(Exception e) {
			System.out.println("https请求异常：{}" + e);
		}
		return null;
	}

	// xml解析 -- 好像不能用，有无法解析的包。可能不是java原省api
	// public static Map doXMLParse(String strxml) throws JDOMException, IOException {
	// strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
	//
	// if(null == strxml || "".equals(strxml)) {
	// return null;
	// }
	//
	// Map m = new HashMap();
	//
	// InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
	// SAXBuilder builder = new SAXBuilder();
	// Document doc = builder.build(in);
	// Element root = doc.getRootElement();
	// List list = root.getChildren();
	// Iterator it = list.iterator();
	// while(it.hasNext()) {
	// Element e = (Element) it.next();
	// String k = e.getName();
	// String v = "";
	// List children = e.getChildren();
	// if(children.isEmpty()) {
	// v = e.getTextNormalize();
	// } else {
	// v = getChildrenText(children);
	// }
	//
	// m.put(k, v);
	// }
	//
	// //关闭流
	// in.close();
	//
	// return m;
	// }

	/**
	 * //退款的请求方法 见http://blog.csdn.net/gbguanbo/article/details/50915333
	 * @param args
	 */

	public static void main(String[] args) {
		
	}

}
