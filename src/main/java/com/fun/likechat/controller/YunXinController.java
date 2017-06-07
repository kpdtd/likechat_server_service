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
import com.fun.likechat.logic.YunXinLogic;
import com.fun.likechat.persistence.po.YunxinAccid;
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
	
	/**
	 * 获取动态列表：
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
					yunXinLogic.createAccid(openId);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}
	
	/**
	 * 通话记录--这个表要和sdkdemo结合
	 * @param
	 * @return
	 */
	@RequestMapping(value = "callRecord", method = RequestMethod.POST)
	public @ResponseBody ActionResult callRecord(@RequestBody String body) {
		try {
//			JSONObject json = JsonHelper.toJsonObject(body);
//			int actorId = json.getIntValue("condition");
//			//根据条件获取动态列表

			return new ActionResult();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}

}
