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
		String xml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[签名错误]]></return_msg></xml>";
		System.out.println(XmlUtil.getJsonFromXml(xml));
		String json = XmlUtil.getJsonFromXml(xml);
		Map<String,Object> map = JsonHelper.toMap(json);
        //遍历map获取key and value
        for(Entry<String, Object> entry : map.entrySet()) {
	        String key = entry.getKey();
	        String value = entry.getValue().toString();
	        System.out.println("kye=" + key + "| value=" + value);
        }
		System.out.println();

	}

}
