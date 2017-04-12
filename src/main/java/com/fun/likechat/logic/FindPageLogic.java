package com.fun.likechat.logic;

import java.util.ArrayList;
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
import com.fun.likechat.persistence.po.ActorDynamicPv;
import com.fun.likechat.service.ActorDynamicPvService;
import com.fun.likechat.service.ActorDynamicService;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.vo.ActorDynamicVo;

@Service
public class FindPageLogic {

	@Autowired
	ActorDynamicService actorDynamicService;
	ActorDynamicPvService actorDynamicPvService;
	private static final Logger logger = LogFactory.getInstance().getLogger();

	public ActionResult getFindList(int tag, String stamp) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		int startPage = 0;
		int count = 0;
		if (StringUtils.isNotBlank(stamp)) {
			startPage = Integer.parseInt(stamp);
		}
		dataMap.put("startPage", startPage);
		dataMap.put("pageSize", Constant.PAGEZISE);
		List<Map<String, Object>> actorDynamics = null;
		switch (tag) {
		/*
		 * 最新动态,按照时间排序
		 */
		case 1:
			actorDynamics = actorDynamicService.getNewestDynamic(dataMap);
			count = actorDynamicService.newestDynamicCount(dataMap);
			return toVo(actorDynamics, count, startPage);

			/*
			 * 热门
			 */
		case 2:
			actorDynamics = actorDynamicService.getHotDynamic(dataMap);
			count = actorDynamicService.hotDynamicCount(dataMap);
			return toVo(actorDynamics, count, startPage);
			
			/*
			 * 关注
			 */
			
		case 3:
			// 获取用户信息
			BeatContext beatContext = RequestUtils.getCurrent();
			if (beatContext != null && beatContext.getUserid() > 0) {
				dataMap.put("userId", beatContext.getUserid());
			} else {
				logger.debug("没有用户信息");
			}
			actorDynamics = actorDynamicService.getAttentionDynamic(dataMap);
			count = actorDynamicService.attentionDynamicCount(dataMap);
			return toVo(actorDynamics, count, startPage);
		}
		return ActionResult.fail();
	}
	private ActionResult toVo(List<Map<String, Object>> actorDynamics, int count, int startPage) {
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
