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
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.logic.HomePageLogic;
import com.fun.likechat.util.JsonHelper;
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

	/**
	 * 打开app时第一次获取 首页数据
	 * @param
	 * @return
	 */
	@RequestMapping(value = "getHomePageContent", method = RequestMethod.POST)
	public @ResponseBody ActionResult getHomePageContent() {
		try {
			// 1、获取tag列表
			List<TagVo> tagsVo = homePageLogic.getAllSystemTagVo();
			// 2、获取banner,banner是用频道来描述的；
			List<BannerVo> bannerVo = homePageLogic.getBannerListVo("banner");
			// 3、获取随机（20个）主播列表，每次刷新都是随机20个
			List<ActorVo> actorsVo = homePageLogic.getRandomActorsVo(Constant.ACTOR_RANDOM_LIMIT);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tagsVo", tagsVo);
			map.put("bannerVo", bannerVo);
			map.put("actorsVo", actorsVo);
			return ActionResult.success(map);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}
	

	/**
	 * 根据tag条件随机获取20条主播列表
	 * @param
	 * @return
	 */
	@RequestMapping(value = "getActorListByTag", method = RequestMethod.POST)
	public @ResponseBody ActionResult getActorListByTag(@RequestBody String body) {
		try {
			JSONObject json = JsonHelper.toJsonObject(body);
			String identifying = json.getString("identifying");//获取是否点击标签：温柔等
			int limit = Constant.ACTOR_RANDOM_LIMIT;
			List<ActorVo> actorsVo = null;
			
			if(StringUtils.isEmpty(identifying) || "ALL".equals(identifying)) {//如果是"全部"(tag的identifying不填写或写成固定ALL)直接随机20条数据
				actorsVo = homePageLogic.getRandomActorsVo(limit);
			}else {//别的，根据tag标签条件连表获取
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("limit", limit);//
				condition.put("identifying", identifying);
				actorsVo = homePageLogic.getRandomActorsByCondition(condition);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("actorsVo", actorsVo);
			return ActionResult.success(map);
		}
		catch(Exception e) {
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
			// 1、获取主播详细信息：手机号、QQ号、微信号只有VIP用户可以看见，点击后直接跳转到会员中心进行充值VIP，并提示“只有VIP会员可见”，如果是未登录用户则跳转登录页面。

			// 2、获取主播照片：每行4张。最多两行，大于四张显示第二行

			// 3、获取最新动态页面信息或跳转信息？？仅显示一个主播的动态，可以下拉

			// 4、随机推荐主播列表：8个

			return new ActionResult();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}

	/**
	 * 主播关注：为每个用户增加和删除关注列表
	 */
	@RequestMapping(value = "attention", method = RequestMethod.POST)
	public @ResponseBody ActionResult attention(@RequestBody String body) {
		try {
			JSONObject json = JsonHelper.toJsonObject(body);
			int actorId = json.getIntValue("actorId");
			return new ActionResult();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}

	//
	// /**
	// * 榜单，榜单列表更多页（主播排名列表）对应频道：
	// */
	// @RequestMapping(value = "/c_ranking_list_more", method = RequestMethod.POST)
	// public @ResponseBody ActionResult getAnchorRankingListMore(@RequestBody String body){
	// try {
	// JSONObject json = JsonHelper.toJsonObject(body);
	// int rankingId = json.getIntValue("rankingId");
	// int pageSize=json.getIntValue("pageSize");
	// String stamp=json.getString("stamp");
	// return rankingListLogic.getAnchorRankingListMore(rankingId, pageSize, stamp);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return ActionResult.fail();
	// }
	//
	// /**
	// * 3.首页播单列表-获取播单列表
	// */
	// @RequestMapping(value = "/live/getBodanList", method = RequestMethod.POST)
	// public @ResponseBody ActionResult getBodanList(@RequestBody String data) {
	// try {
	// JSONObject json = JsonHelper.toJsonObject(data);
	// int pageSize = json.getIntValue("pageSize");
	// String stamp = json.getString("stamp");
	// return bodanListLogic.getBodanList(pageSize, stamp);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return ActionResult.fail();
	// }
	//
	// /**
	// * 我的页
	// */
	// @RequestMapping(value = "/myPage", method = RequestMethod.POST)
	// public @ResponseBody ActionResult getBodanList() {
	// try {
	// return myPageLogic.getMyPageData();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return ActionResult.fail();
	// }

}
