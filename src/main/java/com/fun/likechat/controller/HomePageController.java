package com.fun.likechat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fun.likechat.constant.Constant;
import com.fun.likechat.constant.ErrCodeEnum;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.logic.HomePageLogic;
import com.fun.likechat.persistence.po.DataDictionary;
import com.fun.likechat.persistence.po.UserAttention;
import com.fun.likechat.service.DictionaryService;
import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.vo.ActorPageVo;
import com.fun.likechat.vo.ActorVo;
import com.fun.likechat.vo.BannerVo;
import com.fun.likechat.vo.TagVo;

/**
 * chat 首页栏目
 */
@Controller
@RequestMapping(value = "/home")
public class HomePageController extends BaseController {
	@Autowired
	HomePageLogic homePageLogic;
	@Autowired
	DictionaryService dictionaryService;

	/**
	 * 打开app时第一次获取 首页数据
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "getHomePageContent")
	public @ResponseBody ActionResult getHomePageContent() {
		try {
			// 1、获取tag列表
			List<TagVo> tagsVo = homePageLogic.getAllSystemTagVo();
			// 2、获取banner,banner是用频道来描述的
			List<BannerVo> bannerVo = homePageLogic.getBannerListVo("banner");
			// 3、获取随机（20个）主播列表，每次刷新都是随机20个
			List<ActorVo> actorsVo = homePageLogic.getRandomActorsVo(Constant.ACTOR_RANDOM_LIMIT);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tagsVo", tagsVo);
			map.put("bannerVo", bannerVo);
			map.put("actorsVo", actorsVo);
			return ActionResult.success(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}

	/**
	 * 根据tag条件随机获取20条主播列表
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "getActorListByTag", method = RequestMethod.POST)
	public @ResponseBody ActionResult getActorListByTag(@RequestBody String body) {
		try {
			JSONObject json = JsonHelper.toJsonObject(body);
			String identifying = json.getString("identifying");// 获取是否点击标签：温柔等
			int limit = Constant.ACTOR_RANDOM_LIMIT;
			List<ActorVo> actorsVo = null;

			if (StringUtils.isEmpty(identifying) || "QUANBU".equals(identifying)) {// 如果是"全部"(tag的identifying不填写或写成固定ALL)直接随机20条数据
				actorsVo = homePageLogic.getRandomActorsVo(limit);
			} else {// 别的，根据tag标签条件连表获取
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("limit", limit);//
				condition.put("tagIdentifying", identifying);
				actorsVo = homePageLogic.getRandomActorsByCondition(condition);
			}
			return ActionResult.success(actorsVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}

	/**
	 * 主播详情页信息
	 */
	@RequestMapping(value = "getActorPage", method = RequestMethod.POST)
	public @ResponseBody ActionResult getActorPage(@RequestBody String body) {
		try {
			JSONObject json = JsonHelper.toJsonObject(body);
			int identifying = json.getIntValue("id");// 根据id获取主播信息
			ActorPageVo vo = homePageLogic.getActorPage(identifying);
			return ActionResult.success(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}

	/**
	 * 取消对某主播的关注 param: 主播id
	 */
	@RequestMapping(value = "cancelAttention", method = RequestMethod.POST)
	public @ResponseBody ActionResult cancelAttention(@RequestBody String body) {
		try {
			JSONObject json = JsonHelper.toJsonObject(body);
			int userId = json.getIntValue("userId");
			int actorId = json.getIntValue("actorId");
			homePageLogic.cancelAttention(userId, actorId);
			return ActionResult.success();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}

	/**
	 * 主播关注：为每个用户增加关注列表
	 */
	@RequestMapping(value = "addAttention", method = RequestMethod.POST)
	public @ResponseBody ActionResult addAttention(@RequestBody String body) {
		try {
			JSONObject json = JsonHelper.toJsonObject(body);
			Integer userId = json.getIntValue("userId");
			Integer actorId = json.getIntValue("actorId");
			if (userId == null || actorId == null || userId < 1 || actorId < 1) {
				int code = ErrCodeEnum.addAttentionActor_error.getCode();
				return ActionResult.fail(code, ErrCodeEnum.getDescByCode(code));
			}
			UserAttention ua = new UserAttention();
			ua.setUserId(userId);
			ua.setActorId(actorId);
			homePageLogic.addAttention(ua);
			return ActionResult.success();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}

	/*
	 * 获取客服信息，直接取数据字段CUSTOMER_SERVICE的值
	 */
	@RequestMapping(value = "getCustomerService")
	public @ResponseBody ActionResult getCustomerService() {
		try{
			DataDictionary dataDictionary=dictionaryService.getDicByKey("CUSTOMER_SERVICE");
			Map<String, Object> dataMap=new HashMap<>();
			dataMap.put("info", dataDictionary.getValue());
			return ActionResult.success(dataMap);
		}catch (Exception e) {
		}
		return ActionResult.fail();
	}

}
