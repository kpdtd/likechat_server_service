package com.fun.likechat.yunxin.im;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.yunxin.CheckSumBuilder;
import com.fun.likechat.yunxin.YXHttpClient;
/**
 * 消息抄送，供云信调用
 * @author yangyiqiang
 *
 */
@Controller
@RequestMapping(value = { "/route" })
public class MsgRouteController {

	@RequestMapping(value = { "/mockClient.action" }, method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject mockClient(HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		try {
			// 获取请求体
			byte[] body = readBody(request);
			if (body == null) {
				LogFactory.getInstance().getLogger().warn("request wrong, empty body!");
				result.put("code", 414);
				return result;
			}
			// 获取部分request header，并打印
			String ContentType = request.getContentType();
			String AppKey = request.getHeader("AppKey");
			String CurTime = request.getHeader("CurTime");
			String MD5 = request.getHeader("MD5");
			String CheckSum = request.getHeader("CheckSum");
			LogFactory.getInstance().getLogger().info(
					"request headers: ContentType = {}, AppKey = {}, CurTime = {}, " + "MD5 = {}, CheckSum = {}",
					ContentType, AppKey, CurTime, MD5, CheckSum);
			// 将请求体转成String格式，并打印
			String requestBody = new String(body, "utf-8");
			LogFactory.getInstance().getLogger().info("request body = {}", requestBody);
			// 获取计算过的md5及checkSum
			String verifyMD5 = CheckSumBuilder.getMD5(requestBody);
			String verifyChecksum = CheckSumBuilder.getCheckSum(YXHttpClient.AppSecret, verifyMD5, CurTime);
			LogFactory.getInstance().getLogger().debug("verifyMD5 = {}, verifyChecksum = {}", verifyMD5,
					verifyChecksum);
			// TODO: 比较md5、checkSum是否一致，以及后续业务处理
			result.put("code", 200);
			return result;
		} catch (Exception ex) {
			LogFactory.getInstance().getLogger().error(ex.getMessage(), ex);
			result.put("code", 414);
			return result;
		}
	}

	private byte[] readBody(HttpServletRequest request) throws IOException {
		if (request.getContentLength() > 0) {
			byte[] body = new byte[request.getContentLength()];
			IOUtils.readFully(request.getInputStream(), body);
			return body;
		} else
			return null;
	}

}
