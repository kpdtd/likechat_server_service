package com.fun.likechat.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fun.likechat.constant.ErrCodeEnum;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.logic.WeChatPayLogic;
import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.vo.PayInfoVo;
import com.fun.likechat.vo.WeChatUnifiedOrderReqVo;
import com.fun.likechat.wechat.WeChatConst;
import com.fun.likechat.wechat.data.WechatOrderNotificationData;
import com.fun.likechat.wechat.util.WeChatUtil;
import com.fun.likechat.wechat.util.XmlUtil;

/**
 * 微信相关接口，如微信支付等
 * 流程图和统一下单逻辑
 * https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_3#
 * https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1 *
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
	public @ResponseBody ActionResult unifiedOrder(@RequestBody String body, HttpServletRequest request) {
		try {
			// JSONObject json = JsonHelper.toJsonObject(body);
			// String out_trade_no = json.getString("out_trade_no");//商品订单号24位，随机不重复 规则：YYYYMMDDHHMMSS+10位随机数
			// Integer goods_no = json.getInteger("goods_no");//商品id
			WeChatUnifiedOrderReqVo vo = JsonHelper.toObject(body, WeChatUnifiedOrderReqVo.class);
			return weChatPayLogic.unifiedOrder(vo, getIpAddr(request));
		}
		catch(Exception e) {
			logger.error("统一下单接口json异常,消息:" + body, e);
			return ActionResult.fail(ErrCodeEnum.wechatpay_json_error.getCode(), ErrCodeEnum.wechatpay_json_error.getDesc());
		}
	}

	/**
	 * 微信订单结果通知接口，只接收xml或json
	 * @param
	 * @return
	 */
	@RequestMapping(value = "orderNotification")// 其实没用。我最后通过request来获取xml的
//	public String orderNotification(@RequestBody String body, HttpServletRequest request) {
	public String orderNotification(HttpServletRequest request) {
		try {
			// 因为直接用body，XmlUtil.getJsonFromXml(body);会报[ Content is not allowed in prolog]错，网上说是utf-8的pom引起的。需要处理掉--》：
				//问题：http://cuisuqiang.iteye.com/blog/2062164
			// 示例代码参考：http://blog.csdn.net/qq_29739821/article/details/53466614
			System.out.println("微信最终支付结果开始：********************************");
			request.setCharacterEncoding("UTF-8");
			String inputLine;
			String notityXml = "";
			// 微信给返回的东西
			while((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			String resutlJson = XmlUtil.getJsonFromXml(notityXml);
			System.out.println("微信返回的订单通知xml：" + resutlJson);
			WechatOrderNotificationData data = JsonHelper.toObject(resutlJson, WechatOrderNotificationData.class);
			boolean isSuccess = weChatPayLogic.orderNotification(data);
			if(isSuccess) {//
				return WeChatConst.orderNotificationReturnXml;
			}
		}
		catch(Exception e) {
			logger.error("微信订单通知xml转json异常,消息:", e);
		}
		return WeChatConst.orderNotificationErrReturnXml;
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

		if(ip != null && ip.length() > 15) {// 多个代理可能会产生多个ip，取出第一个非unknown的ip作为真实ip
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
