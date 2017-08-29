package com.fun.likechat.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.logic.AccountingLogic;
import com.fun.likechat.logic.YunXinLogic;
import com.fun.likechat.persistence.po.YunxinAccid;
import com.fun.likechat.service.AccountService;
import com.fun.likechat.service.ActorService;
import com.fun.likechat.service.YunxinAccidService;
import com.fun.likechat.util.JsonHelper;

/**
 * 网易云信相关接口
 */
@Controller
@RequestMapping(value = "/yunxin")
public class YunXinController extends BaseController {

	@Autowired
	YunxinAccidService yunxinAccidService;
	
	@Autowired
	ActorService actorService;
	
	@Autowired
	YunXinLogic yunXinLogic;
	
	@Autowired
	AccountingLogic accountingLogic;
	
	/**
	 * 获取云信token：
	 * @param
	 * @return
	 */
	@RequestMapping(value = "getYXToken", method = RequestMethod.POST)
	public @ResponseBody ActionResult getYXToken(@RequestBody String body) {
		try {
			JSONObject json = JsonHelper.toJsonObject(body);
			String openId = json.getString("openId");
			String token = null;
			if(StringUtils.isNotEmpty(openId)) {
				//从db中找openid对应的token，找不到
				YunxinAccid po = yunxinAccidService.getByOpenId(openId);
				if(po != null) {
					return ActionResult.success(po);//等同于YunxinTokenVo
				}else {
					return yunXinLogic.createAccid(openId);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}
	
	/**
	 * 作用：用户连线主播后发起扣费通知，服务器对用户进行扣款（余额），并生成一条通话记录。
	 * 			并且在连线状态下每隔60秒想服务器发起一次计费请求；
	 *  扣费周期：每分钟
	 * 场景：
	 * 1、用户接通主播后立即调用此接口
	 * 2、通话每60秒调用此接口
	 */
	@RequestMapping(value = "chargeNotify", method = RequestMethod.POST)
	public @ResponseBody ActionResult chargeNotify(@RequestBody String body) {
		try {
			JSONObject json = JsonHelper.toJsonObject(body);
			int actorId = json.getIntValue("actorId");
//			boolean isFirstNotify = json.getBooleanValue("isFirstNotify");
			int recordId = json.getIntValue("recordId");
			return accountingLogic.callPay(actorId, recordId);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}
	
	/**
	 * 作用：挂断通知，主要用于修正通话记录的结束时间 和 本次通话时长call_time
	 */
	@RequestMapping(value = "hangUpNotify", method = RequestMethod.POST)
	public @ResponseBody ActionResult hangUpNotify(@RequestBody String body) {
		try {
			JSONObject json = JsonHelper.toJsonObject(body);
			int recordId = json.getIntValue("recordId");
			return accountingLogic.hangUpNotify(recordId);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}
	
	/**
	 * 通话记录--这个表要和sdkdemo结合/ 没有了，用上面的接通通知和挂断通知接口
	 * @param
	 * @return
	 */
//	@RequestMapping(value = "callRecord", method = RequestMethod.POST)
//	public @ResponseBody ActionResult callRecord(@RequestBody String body) {
//		try {
//			JSONObject json = JsonHelper.toJsonObject(body);
//			int actorId = json.getIntValue("actorId");
//
//			return new ActionResult();
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//		return ActionResult.fail();
//	}

}
