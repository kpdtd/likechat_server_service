package com.fun.likechat.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.constant.Constant;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.interceptor.BeatContext;
import com.fun.likechat.interceptor.RequestUtils;
import com.fun.likechat.persistence.po.Actor;
import com.fun.likechat.persistence.po.ActorDynamicPv;
import com.fun.likechat.service.ActorDynamicPvService;
import com.fun.likechat.service.ActorDynamicService;
import com.fun.likechat.service.ActorService;
import com.fun.likechat.service.UserAttentionService;
import com.fun.likechat.util.DateUtil;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.vo.ActorDynamicVo;
import com.fun.likechat.vo.ActorVo;

@Service
public class MinePageLogic {

	@Autowired
	ActorService actorService;
	@Autowired
	UserAttentionService userAttentionService;
	@Autowired
	ActorDynamicService actorDynamicService;
	@Autowired
	ActorDynamicPvService actorDynamicPvService;
	private static final Logger logger = LogFactory.getInstance().getLogger();

	/*
	 * 获取我的信息
	 */
	public ActionResult getMineInfo() throws Exception {
		// 获取用户信息
		BeatContext beatContext = RequestUtils.getCurrent();
		if (beatContext != null && beatContext.getUserid() > 0) {
			Actor actor = actorService.getById(beatContext.getUserid());
			if (actor != null) {
				// 转换成vo返回
				ActorVo vo = new ActorVo();
				vo.setAge(DateUtil.getPersonAgeByBirthDate(actor.getBirthday()));
				vo.setIcon(actor.getIcon());
				vo.setId(actor.getId());
				vo.setIdcard(actor.getIdcard());
				vo.setNickname(actor.getNickname());
				vo.setSex(actor.getSex());
				vo.setSignature(actor.getSignature());
				return ActionResult.success(vo);
			} else {
				logger.debug("无法根据用户ID获取到用户信息");
			}
		} else {
			logger.debug("没有用户信息");
		}

		return ActionResult.fail();
	}

	/*
	 * 账户余额
	 */
	public ActionResult getUserAccount() throws Exception {
		return ActionResult.fail();
	}

	/*
	 * 我的好友 返回关注数、粉丝数、关注列表（图片、昵称、性别、年龄、签名）
	 */
	public ActionResult getUserFriends(String stamp) throws Exception {
		// 获取用户信息
		BeatContext beatContext = RequestUtils.getCurrent();
		if (beatContext != null && beatContext.getUserid() > 0) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			int startPage = 0;
			int count = 0;
			if (StringUtils.isNotBlank(stamp)) {
				startPage = Integer.parseInt(stamp);
			}
			dataMap.put("startPage", startPage);
			dataMap.put("pageSize", Constant.PAGEZISE);
			dataMap.put("userId", beatContext.getUserid());
			dataMap.put("actorId", beatContext.getUserid());
			List<Map<String, Object>> actors = userAttentionService.getUserFriends(dataMap);
			count = userAttentionService.userFriendsCount(dataMap);
			int fansCount = userAttentionService.myFansCount(dataMap);
			// 封装返回的对象
			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("attentionCount", count);
			returnMap.put("fansCount", fansCount);
			returnMap.put("dataList", toActorVo(actors));
			// 返回
			ActionResult.success(returnMap, startPage + Constant.PAGEZISE,
					isNextPage(Constant.PAGEZISE, count, startPage));
		} else {
			logger.debug("没有用户信息");
		}

		return ActionResult.fail();
	}

	/*
	 * 我的好友 返回关注数、粉丝数、粉丝列表（图片、昵称、性别、年龄、签名）
	 */
	public ActionResult getFans(String stamp) throws Exception {
		// 获取用户信息
		BeatContext beatContext = RequestUtils.getCurrent();
		if (beatContext != null && beatContext.getUserid() > 0) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			int startPage = 0;
			int count = 0;
			if (StringUtils.isNotBlank(stamp)) {
				startPage = Integer.parseInt(stamp);
			}
			dataMap.put("startPage", startPage);
			dataMap.put("pageSize", Constant.PAGEZISE);
			dataMap.put("actorId", beatContext.getUserid());
			dataMap.put("userId", beatContext.getUserid());
			List<Map<String, Object>> actors = userAttentionService.getMyFans(dataMap);
			count = userAttentionService.myFansCount(dataMap);
			int attentionCount = userAttentionService.userFriendsCount(dataMap);
			// 封装返回的对象
			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("attentionCount", attentionCount);
			returnMap.put("fansCount", count);
			returnMap.put("dataList", toActorVo(actors));
			// 返回
			ActionResult.success(returnMap, startPage + Constant.PAGEZISE,
					isNextPage(Constant.PAGEZISE, count, startPage));
		} else {
			logger.debug("没有用户信息");
		}
		return ActionResult.fail();
	}

	/*
	 * 我的动态
	 */
	public ActionResult getMyDynamic(String stamp) throws Exception {
		// 获取用户信息
		BeatContext beatContext = RequestUtils.getCurrent();
		if (beatContext != null && beatContext.getUserid() > 0) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			int startPage = 0;
			int count = 0;
			if (StringUtils.isNotBlank(stamp)) {
				startPage = Integer.parseInt(stamp);
			}
			dataMap.put("startPage", startPage);
			dataMap.put("pageSize", Constant.PAGEZISE);
			dataMap.put("actorId", beatContext.getUserid());
			List<Map<String, Object>> actorDynamics = actorDynamicService.getNewestDynamic(dataMap);
			count = actorDynamicService.newestDynamicCount(dataMap);
			// 封装返回的对象
			return toActorDynamicVo(actorDynamics, count, startPage);
		} else {
			logger.debug("没有用户信息");
		}
		return ActionResult.fail();
	}

	private List<ActorVo> toActorVo(List<Map<String, Object>> actors) {
		List<ActorVo> list = new ArrayList<>();
		try {
			if (actors != null && actors.size() > 0) {
				for (Map<String, Object> actor : actors) {
					// 封装返回的vo
					ActorVo vo = new ActorVo();
					if (actor.get("birthday") != null) {
						vo.setAge(DateUtil.getPersonAgeByBirthDate((Date) actor.get("birthday")));
					}
					if (actor.get("icon") != null) {
						vo.setIcon(actor.get("icon").toString());
					}
					if (actor.get("nickname") != null) {
						vo.setNickname(actor.get("nickname").toString());
					}
					if (actor.get("signature") != null) {
						vo.setSignature(actor.get("signature").toString());
					}
					vo.setId((Integer) actor.get("id"));
					vo.setIdcard((Integer) actor.get("idcard"));
					vo.setSex((Integer) actor.get("sex"));
					list.add(vo);
				}
			} else {
				logger.debug("没有数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	private ActionResult toActorDynamicVo(List<Map<String, Object>> actorDynamics, int count, int startPage) {
		try {
			if (actorDynamics != null && actorDynamics.size() > 0) {
				for (Map<String, Object> actorDynamic : actorDynamics) {
					// 封装返回的vo
					ActorDynamicVo vo = new ActorDynamicVo();
					if (actorDynamic.get("content") != null) {
						vo.setContent(actorDynamic.get("content").toString());
					}
					if (actorDynamic.get("icon") != null) {
						vo.setImgUrl(actorDynamic.get("icon").toString());
					}
					if (actorDynamic.get("nickname") != null) {
						vo.setNickname(actorDynamic.get("nickname").toString());
					}
					if (actorDynamic.get("signature") != null) {
						vo.setSignature(actorDynamic.get("signature").toString());
					}
					vo.setUpdateTime(actorDynamic.get("update_time").toString());
					vo.setId((Integer)actorDynamic.get("id"));
					List<String> dynamicUrl = new ArrayList<String>();
					// 获取到主播动态资源，如图片、语音、视频
					ActorDynamicPv adpv = new ActorDynamicPv();
					adpv.setDynamicId(Integer.parseInt(actorDynamic.get("id").toString()));
					List<ActorDynamicPv> actorDynamicPvs = actorDynamicPvService.getListByPo(adpv);
					if (actorDynamicPvs != null && actorDynamicPvs.size() > 0) {
						for (ActorDynamicPv actorDynamicPv : actorDynamicPvs) {
							dynamicUrl.add(actorDynamicPv.getSavePath());
							vo.setDynamicType(actorDynamicPv.getType());
						}
					}
					vo.setDynamicUrl(dynamicUrl);
					return ActionResult.success(vo, startPage + Constant.PAGEZISE,
							isNextPage(Constant.PAGEZISE, count, startPage));
				}
			}else {
				logger.debug("没有动态数据");
				return ActionResult.success();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}
	public boolean isNextPage(int pageSize, int count, int start) throws Exception {
		// start,当前页的起始位
		if ((pageSize + start) < count) {
			return true;
		}
		return false;
	}
}
