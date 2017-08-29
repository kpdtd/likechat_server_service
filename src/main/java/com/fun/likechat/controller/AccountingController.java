package com.fun.likechat.controller;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.logic.AccountingLogic;
import com.fun.likechat.logic.WeChatPayLogic;
import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.vo.PayInfoVo;

/**
 */
@Controller
@RequestMapping(value = "/accounting")
public class AccountingController extends BaseController {
	private static final Logger logger = LogFactory.getInstance().getLogger();

	@Autowired
	AccountingLogic accountingLogic;
	@Autowired
	WeChatPayLogic weChatPayLogic;

	/*
	 * 获取账号余额及相关信息
	 */
	@RequestMapping(value = "getAccountInfo")
	public @ResponseBody ActionResult getAccountInfo() {
		try {
			return accountingLogic.getAccountInfo();
		}
		catch(Exception e) {
			logger.error("获取用户账户余额失败", e);
		}
		return ActionResult.fail();
	}

	/*
	 * 用于在“我的”页面中 点击“账号余额”后 的显示信息（返回账号余额，产品列表信息等）
	 * 返回内容：
	 * 1、余额 ： 数字
	 * 2、产品列表：GoodsVo 的list
	 */
	@RequestMapping(value = "accountBalance")
	public @ResponseBody ActionResult accountBalance() {
		try {
			return accountingLogic.accountBalance();
		}
		catch(Exception e) {
			logger.error("获取用户账户余额信息失败", e);
		}
		return ActionResult.fail();
	}

	/*
	 * vip会员相关数据
	 */
	@RequestMapping(value = "vipMember")
	public @ResponseBody ActionResult vipMember() {
		try {
			return accountingLogic.vipMember();
		}
		catch(Exception e) {
			logger.error("获取用户账户余额失败", e);
		}
		return ActionResult.fail();
	}

	/*
	 * 我的付款记录--订单生成
	 * 1：客户端在用户调用支付接口前发起支付记录，创建订单。主要同步一下openid 和 订单号
	 */
	@RequestMapping(value = "createOder", method = RequestMethod.POST)
	public @ResponseBody ActionResult createOder(@RequestBody String body) {
		try {
			PayInfoVo vo = JsonHelper.toObject(body, PayInfoVo.class);
			return accountingLogic.createOder(vo);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}

	/*
	 * 支付宝回传结果充值处理--不是JSON。
	 * https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.OoQbGf&treeId=59&articleId=103666&docType=1
	 * 1：支付宝回传后，如果订单成功，则为用户充值；
	 * 2：支付宝强烈建议通过另外一个接口验证是否回传是支付宝传递的。https://doc.open.alipay.com/doc2/detail?treeId=58&articleId=103597&docType=1
	 */
	@RequestMapping(value = "alipayOderNotify")//, method = RequestMethod.POST
	public void alipayOderNotify(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map map=request.getParameterMap();  
		    Set keSet=map.entrySet();  
		    System.out.println(keSet+"长度="+keSet.size());  
		    for(Iterator itr=keSet.iterator();itr.hasNext();){  
		        Map.Entry me=(Map.Entry)itr.next();  
		        Object ok=me.getKey();  
		        Object ov=me.getValue();  
		        String[] value=new String[1];  
		        if(ov instanceof String[]){  
		            value=(String[])ov;  
		        }else{  
		            value[0]=ov.toString();  
		        }  
		  
		        for(int k=0;k<value.length;k++){  
		            System.out.println(ok+"="+value[k]);  
		        }  
		      }
			String notify_time = request.getParameter("notify_time");// 通知时间 (Date) 通知的发送时间。格式为yyyy-MM-dd HH:mm:ss。 不可空
																	 // 2013-08-22 14:45:24
			String notify_type = request.getParameter("notify_type");// 通知类型 String 通知的类型。 不可空 trade_status_sync
			String notify_id = request.getParameter("notify_id");// 通知校验ID String 通知校验ID。 不可空
																 // 64ce1b6ab92d00ede0ee56ade98fdf2f4c
			String sign_type = request.getParameter("sign_type");// 签名方式 String 固定取值为RSA。 不可空 RSA
			String sign = request.getParameter("sign");// 签名 String 请参见签名机制。 不可空
													   // lBBK%2F0w5LOajrMrji7DUgEqNjIhQbidR13GovA5r3TgIbNqv231yC1NksLdw%2Ba3JnfHXoXuet6XNNHtn7VE%2BeCoRO1O%2BR1KugLrQEZMtG5jmJI
			String out_trade_no = request.getParameter("out_trade_no");// 商户网站唯一订单号 String(64)
																	   // 对应商户网站的订单系统中的唯一订单号，非支付宝交易号。需保证在商户网站中的唯一性。是请求时对应的参数，原样返回。
																	   // 可空 082215222612710
			String subject = request.getParameter("subject");// 商品名称 String(128)
			/**
			 * WAIT_BUYER_PAY 交易创建，等待买家付款。
			 * TRADE_CLOSED 在指定时间段内未支付时关闭的交易 在交易完成全额退款成功时关闭的交易。
			 * TRADE_SUCCESS 交易成功，且可对该交易做操作，如：多级分润、退款等。
			 * TRADE_FINISHED 交易成功且结束，即不可再做任何操作。
			 */
			String trade_status = request.getParameter("trade_status");// 交易状态 String 交易状态，取值范围请参见“交易状态”。 不可空
																	   // TRADE_SUCCESS
			String seller_email = request.getParameter("seller_email");// 卖家支付宝账号 String(100) 卖家支付宝账号，可以是email和手机号码。 不可空
																	   // xxx@alipay.com
			String buyer_email = request.getParameter("buyer_email");// 买家支付宝账号 String(100) 买家支付宝账号，可以是Email或手机号码。 不可空
																	 // dlw***@gmail.com
			String total_fee = request.getParameter("total_fee");// 交易金额 Number 该笔订单的总金额。请求时对应的参数，原样通知回来。 不可空 1.00
			boolean isSuccess = weChatPayLogic.alipayOderNotifyUpdateUserAccount(out_trade_no, trade_status);
			if(isSuccess) {
				writerToClient("success", request, response);// 程序执行完后必须打印输出“success”（不包含引号）。如果商户反馈给支付宝的字符不是success这7个字符，支付宝服务器会不断重发通知，直到超过24小时22分钟。
				return;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		writerToClient("error", request, response);
	}

}
