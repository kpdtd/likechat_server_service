package com.fun.likechat.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fun.likechat.constant.ErrCodeEnum;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.logic.WeChatPayLogic;
import com.fun.likechat.persistence.po.YunxinAccid;
import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.vo.PayInfoVo;

/**
 * 微信相关接口，如微信支付等
 */
@Controller
@RequestMapping(value = "/wechat")
public class WeChatController extends BaseController {

	private static final Logger logger = LogFactory.getInstance().getLogger();
	@Autowired
	WeChatPayLogic weChatPayLogic;
	/**
	 * 微信统一下单接口
	 * @param
	 * @return
	 */
	@RequestMapping(value = "unifiedOrder", method = RequestMethod.POST)
	public @ResponseBody ActionResult unifiedOrder(@RequestBody String body) {
		try {
//			JSONObject json = JsonHelper.toJsonObject(body);
//			String out_trade_no = json.getString("out_trade_no");//商品订单号24位，随机不重复 规则：YYYYMMDDHHMMSS+10位随机数
//			Integer goods_no = json.getInteger("goods_no");//商品id
			PayInfoVo vo = JsonHelper.toObject(body, PayInfoVo.class);
			return weChatPayLogic.unifiedOrder(vo, getIpAddr(request));
		}
		catch(Exception e) {
			logger.error("统一下单接口json异常,消息:" + body, e);
			return ActionResult.fail(ErrCodeEnum.wechatpay_json_error.getCode(), ErrCodeEnum.wechatpay_json_error.getDesc());
		}
	}

	/**
	 * 获取用户真实ip
	 * @param request
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		
		if(ip != null && ip.length() > 15) {//多个代理可能会产生多个ip，取出第一个非unknown的ip作为真实ip
			String[] ips = ip.split(",");
			for(int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if(!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}
	
}
