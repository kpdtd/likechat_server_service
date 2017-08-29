package com.fun.likechat.wechat.util;

import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import com.fun.likechat.util.JsonHelper;

public class XmlUtil {

	public static String getXmlFromMap(Map<String, Object> params, String root) {
		JSON JSON = JSONObject.fromObject(params);
		XMLSerializer xmlSerializer = new XMLSerializer();
		xmlSerializer.setRootName(root);
		xmlSerializer.setTypeHintsEnabled(false);
		// xmlSerializer.setTypeHintsCompatibility(false);
		String xml = xmlSerializer.write(JSON);
		return xml;
	}

	public static String getJsonFromXml(String xml) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		JSONObject json = (JSONObject) xmlSerializer.read(xml);
		return json.toString();
	}

	public static <T> T fromXml(String xml, Class<T> clazz) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		JSONObject json = (JSONObject) xmlSerializer.read(xml);
		return (T) JSONObject.toBean(json, clazz);
	}

	public static void main(String[] args) {
//		String xml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[签名错误]]></return_msg></xml>";
//		System.out.println(XmlUtil.getJsonFromXml(xml));
//		String json = XmlUtil.getJsonFromXml(xml);
//		Map<String,Object> map = JsonHelper.toMap(json);
//        //遍历map获取key and value
//        for(Entry<String, Object> entry : map.entrySet()) {
//	        String key = entry.getKey();
//	        String value = entry.getValue().toString();
//	        System.out.println("kye=" + key + "| value=" + value);
//        }
		
		String xml = "<xml><appid><![CDATA[wx9af1549f05e98da1]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1480109622]]></mch_id><nonce_str><![CDATA[UKKQOHXBVNG0OIJ8]]></nonce_str><openid><![CDATA[ojZbvwbsf2XDaw_s5WH-WMrrMpbA]]></openid><out_trade_no><![CDATA[201707121706350549931954]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[C01AE365FCCD195618016B07730C4829]]></sign><time_end><![CDATA[20170712170650]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[APP]]></trade_type><transaction_id><![CDATA[4006832001201707120340859564]]></transaction_id></xml>";
		System.out.println(XmlUtil.getJsonFromXml(xml));

	}

}
