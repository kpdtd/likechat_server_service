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

import com.alibaba.fastjson.JSONObject;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.logic.HomePageLogic;
import com.fun.likechat.persistence.po.Actor;
import com.fun.likechat.persistence.po.Column;
import com.fun.likechat.persistence.po.Tag;
import com.fun.likechat.util.JsonHelper;

/**
 * chat 首页栏目
 */
@Controller
@RequestMapping(value = "/home")
public class HomePageController extends BaseController {
	@Autowired
	HomePageLogic homePageLogic;

	/**
	 * 获取chat 首页数据
	 * @param
	 * @return
	 */
	@RequestMapping(value = "getHomePageContent", method = RequestMethod.POST)
	public @ResponseBody ActionResult getHomePageContent() {
		try {
			// 1、获取tag列表
			List<Tag> tags = homePageLogic.getAllSystemTag();
			// 2、获取banner,banner是用频道来描述的；
			List<Column> banner = homePageLogic.getSubColumnsById("banner");
			// 3、获取全部主播列表
			List<Actor> actors = homePageLogic.getRandomActors();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tags", tags);
			map.put("banner", banner);
			map.put("actors", actors);
			return ActionResult.success(map);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}
	

	/**
	 * 获取chat 首页数据
	 * @param
	 * @return
	 */
	@RequestMapping(value = "getActorList", method = RequestMethod.POST)
	public @ResponseBody ActionResult getActorList() {
		try {
			// 1、获取tag列表
			List<Tag> tags = homePageLogic.getAllSystemTag();
			// 2、获取banner,banner是用频道来描述的；
			List<Column> banner = homePageLogic.getSubColumnsById("banner");
			// 3、获取全部主播列表
			List<Actor> actors = homePageLogic.getRandomActors();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tags", tags);
			map.put("banner", banner);
			map.put("actors", actors);
			return ActionResult.success(map);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}	

	/**
	 * 用户详情页信息
	 */
	@RequestMapping(value = "getActorDetail", method = RequestMethod.POST)
	public @ResponseBody ActionResult getAllAnchorRankingList() {
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
