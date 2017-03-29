//package com.fun.likechat.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSONObject;
//import com.fun.likechat.interceptor.ActionResult;
//import com.fun.likechat.util.JsonHelper;
//
///**
// * 发现栏目
// */
//@Controller
//@RequestMapping(value = "/message")
//public class MessagePageController extends BaseController {
//
//	/**
//	 * 获取动态列表：
//	 * @param
//	 * @return
//	 */
//	@RequestMapping(value = "getFindList", method = RequestMethod.POST)
//	public @ResponseBody ActionResult getFindList(@RequestBody String body) {
//		try {
//			JSONObject json = JsonHelper.toJsonObject(body);
//			int actorId = json.getIntValue("condition");
//			//根据条件获取动态列表
//
//			return new ActionResult();
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//		return ActionResult.fail();
//	}
//	
//	/**
//	 * 增加动态
//	 * @param
//	 * @return
//	 */
//	@RequestMapping(value = "addDynamic", method = RequestMethod.POST)
//	public @ResponseBody ActionResult addDynamic(@RequestBody String body) {
//		try {
//			JSONObject json = JsonHelper.toJsonObject(body);
//			int actorId = json.getIntValue("condition");
//			//根据条件获取动态列表
//
//			return new ActionResult();
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//		return ActionResult.fail();
//	}
//
//}
