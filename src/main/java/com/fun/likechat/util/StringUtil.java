package com.fun.likechat.util;

import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	/**
	 * 
	 * @Author lianzhifei 2016年9月8日 strtoHex 方法描述: 字符串转换为16进制 逻辑描述:
	 * @param s
	 * @return
	 */
	public static String strtoHex(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return "0x" + str;// 0x表示十六进制
	}

	/**
	 * 
	 * @Author lianzhifei 2016年9月8日 deleteBOM 方法描述: 判断返回String中是否存在BOM头，若存在则去除
	 *         逻辑描述:
	 * @param value
	 * @return
	 */
	public static String deleteBOM(String value) {
		String tmpStr2 = new String(value.substring(0, 1));
		if (strtoHex(tmpStr2).equals("0xfeff")) {
			value = new String(value.getBytes(), 3, value.getBytes().length - 3, Charset.defaultCharset());
		}
		return value;
	}

	public static String listToString(List<?> list, String string) {
		return StringUtils.join(list.toArray(), string);
	}

	public static String splitString(String str, String pattern) {
		if (StringUtils.isNotBlank(pattern)) {
			try {
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(str);
				while (m.find()) {
					return m.group();
				}
			} catch (Exception e) {
			}
		} else {
			return str;
		}
		return null;
	}

	/**
	 * 
	 * replaceAccessTokenReg 方法描述: 替换url指定参数的值 逻辑描述:
	 * 
	 * @param url
	 * @param name
	 * @param accessToken
	 * @return
	 * @since Ver 1.00
	 */
	public static String replaceAccessTokenReg(String url, String name, String accessToken) {
		if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(accessToken)) {
			url = url.replaceAll("(" + name + "=[^&]*)", name + "=" + accessToken);
		}
		return url;
	}

	public static String matchResult(Pattern p, String str) {
		StringBuilder sb = new StringBuilder();
		Matcher m = p.matcher(str);
		while (m.find())
			for (int i = 0; i <= m.groupCount(); i++) {
				sb.append(m.group());
			}
		return sb.toString();
	}

	public static void main(String args[]) {
		String regEx1 = "[\\u4e00-\\u9fa5]|[a-z||A-Z]|[0-9]|[-]";  
//        String regEx2 = "[a-z||A-Z]";  
        String regEx3 = "[0-9]*[0-9]";  
        String str = "White55开：新AD复仇之矛 最飘逸的英雄";  
        String s1 = matchResult(Pattern.compile(regEx1),str);  
//        String s2 = matchResult(Pattern.compile(regEx2),str);  
//        String s3 = matchResult(Pattern.compile(regEx3),str);  
        System.out.println(splitString("：8238",regEx3));  
        System.out.println(str.replaceAll("[\\u4e00-\\u9fa5]|[a-z||A-Z]|[0-9]|[-]", ""));
        System.out.println("时间："+DateUtil.getTimeString(10001));
	}
}
