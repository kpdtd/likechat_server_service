package com.fun.likechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.util.JsonHelper;

/**
 * 发现栏目
 */
@Controller
@RequestMapping(value = "/find")
public class FindPageController extends BaseController {

	/**
	 * 获取动态列表：
	 * 最新：按照时间排序，最新的放在最上面；
	 * 热门：按照观看的人数排序，最多的放在最上面；
	 * 关注：按照用户关注的主播发的动态最新的放在最上面；
	 * 图片可以一次上传多张，视频一次只能上传一个，声音现录制
	 * @param
	 * @return
	 */
	@RequestMapping(value = "getFindList", method = RequestMethod.POST)
	public @ResponseBody ActionResult getFindList(@RequestBody String body) {
		try {
			JSONObject json = JsonHelper.toJsonObject(body);
			int actorId = json.getIntValue("condition");
			//根据条件获取动态列表

			return new ActionResult();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}
	
	/**
	 * 增加动态
	 * @param
	 * @return
	 */
	@RequestMapping(value = "addDynamic", method = RequestMethod.POST)
	public @ResponseBody ActionResult addDynamic(@RequestBody String body) {
		try {
			JSONObject json = JsonHelper.toJsonObject(body);
			int actorId = json.getIntValue("condition");
			//根据条件获取动态列表

			return new ActionResult();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}

}