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
import com.fun.likechat.constant.ErrCodeEnum;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.interceptor.BeatContext;
import com.fun.likechat.interceptor.RequestUtils;
import com.fun.likechat.persistence.po.ActorDynamic;
import com.fun.likechat.persistence.po.ActorDynamicPv;
import com.fun.likechat.persistence.po.DataDictionary;
import com.fun.likechat.service.ActorDynamicPvService;
import com.fun.likechat.service.ActorDynamicService;
import com.fun.likechat.service.DictionaryService;
import com.fun.likechat.service.UserAttentionService;
import com.fun.likechat.util.DateUtil;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.vo.ActorDynamicVo;

@Service
public class FindPageLogic {

	@Autowired
	ActorDynamicService actorDynamicService;
	@Autowired
	ActorDynamicPvService actorDynamicPvService;
	@Autowired
	DictionaryService dictionaryService;
	@Autowired
	UserAttentionService userAttentionService;

	private static final Logger logger = LogFactory.getInstance().getLogger();

	public ActionResult getActorDynamicList(int actorId, String stamp) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		int startPage = 0;
		int count = 0;
		if (StringUtils.isNotBlank(stamp)) {
			startPage = Integer.parseInt(stamp);
		}
		dataMap.put("actorId", actorId);
		dataMap.put("startPage", startPage);
		dataMap.put("pageSize", Constant.FIND_PAGEZISE);
		List<Map<String, Object>> actorDynamics = null;
		count = actorDynamicService.newestDynamicCount(dataMap);
		actorDynamics = actorDynamicService.getNewestDynamic(dataMap);
		return toVo(actorDynamics, count, startPage);
		
	}
	public ActionResult getFindList(int tag, String stamp) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		int startPage = 0;
		int count = 0;
		if(StringUtils.isNotBlank(stamp)) {
			startPage = Integer.parseInt(stamp);
		}
		dataMap.put("startPage", startPage);
		dataMap.put("pageSize", Constant.FIND_PAGEZISE);
		List<Map<String, Object>> actorDynamics = null;
		switch(tag) {
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
			 * 关注:按照用户关注的主播发的动态最新的放在最上面
			 */
		case 3:
			// 获取用户信息
			BeatContext beatContext = RequestUtils.getCurrent();
			if(beatContext != null && beatContext.getUserid() > 0) {
				dataMap.put("userId", beatContext.getUserid());//
				// 我的实现：
				// 1：获取关注的主播列表
				List<Map<String, Object>> actorList = userAttentionService.getUserFriends(dataMap);
				List<Map<String, Object>> actorListtemp = userAttentionService.getUserFriends(dataMap);
				count = userAttentionService.userFriendsCount(dataMap);
				// 2：循环获取主播的最新关注数据-- order by id desc limit 1
				for(Map<String, Object> mapPo : actorList) {
					ActorDynamic ad = actorDynamicService.getNewestOneDynamic((Integer) mapPo.get("id"));
					if(ad != null) {
						mapPo.put("type", ad.getType());
						mapPo.put("price", ad.getPrice());
						mapPo.put("page_view", ad.getPageView());
						mapPo.put("create_time", ad.getCreateTime());
						mapPo.put("content", ad.getContent());
						mapPo.put("id", ad.getId());
						actorListtemp.add(mapPo);
					}
				}
				return toVo(actorListtemp, actorListtemp.size(), 0);// 这里有个隐形bug，主播超过10个，则不会显示，永远只显示10条。并且会有下一页
			}
			else {
				logger.debug("没有用户信息");
				return ActionResult.fail(ErrCodeEnum.getMyPageData_error.getCode(), ErrCodeEnum.getMyPageData_error.getDesc());
			}
		}
		return ActionResult.fail();
	}

	private ActionResult toVo(List<Map<String, Object>> actorDynamics, int count, int startPage) {
		try {
			if(actorDynamics != null && actorDynamics.size() > 0) {
				DataDictionary dictionary = dictionaryService.getDicByKey(Constant.D_IMG_SAVE_PATH_HTTP);
				String httpPath = dictionary.getValue();
				List<ActorDynamicVo> voList = new ArrayList<ActorDynamicVo>();
				for(Map<String, Object> actorDynamic : actorDynamics) {
					// 封装返回的vo
					ActorDynamicVo vo = new ActorDynamicVo();
					if(actorDynamic.get("content") != null) {
						vo.setContent(actorDynamic.get("content").toString());
					}
					if(actorDynamic.get("icon") != null) {
						vo.setImgUrl(httpPath + actorDynamic.get("icon").toString());
					}
					if(actorDynamic.get("nickname") != null) {
						vo.setNickname(actorDynamic.get("nickname").toString());
					}
					if(actorDynamic.get("signature") != null) {
						vo.setSignature(actorDynamic.get("signature").toString());
					}
					if(actorDynamic.get("price") != null) {//
						vo.setPrice((Integer) actorDynamic.get("price"));//
					}
					if(actorDynamic.get("page_view") != null) {//
						vo.setPageView((Integer) actorDynamic.get("page_view"));//
					}
					if(actorDynamic.get("type") != null) {// 如果报错，采用这种方式：Integer.parseInt(actorDynamic.get("id").toString())
						vo.setDynamicType((Integer) actorDynamic.get("type"));// 1、视频 2、照片 3、语音'
					}
					// 做日期格式转换
					if(actorDynamic.get("create_time") != null && actorDynamic.get("create_time") instanceof Date) {
						Date d = (Date) actorDynamic.get("create_time");
						String sd = DateUtil.parseDateToString(d, DateUtil.DATE_FORMAT_MONTH_DAY_HOUR);
						if(DateUtil.isNow(d) && sd.length() > 5) {
							sd = "今天" + sd.substring(5);
						}
						vo.setCreateTime(sd);
					}

					vo.setId(Integer.parseInt(actorDynamic.get("id").toString()));
					List<String> dynamicUrlList = new ArrayList<String>();
					// 获取到主播动态资源，如图片、语音、视频
					ActorDynamicPv adpv = new ActorDynamicPv();
					adpv.setDynamicId(Integer.parseInt(actorDynamic.get("id").toString()));
					List<ActorDynamicPv> actorDynamicPvs = actorDynamicPvService.getListByPo(adpv);
					if(actorDynamicPvs != null && actorDynamicPvs.size() > 0) {
						for(ActorDynamicPv actorDynamicPv : actorDynamicPvs) {
							dynamicUrlList.add(httpPath + actorDynamicPv.getSavePath());
						}

						// 单独处理音视频的时长和 视频的封面字段，如果是音视频，每个动态下只有一条记录
						if(actorDynamicPvs.size() == 1 && (actorDynamicPvs.get(0).getType() == 1 || actorDynamicPvs.get(0).getType() == 3)) {// 类型为1-视频、3-语音才可能有值
							vo.setVoiceSec(actorDynamicPvs.get(0).getSecond());
							if(StringUtils.isNotEmpty(actorDynamicPvs.get(0).getVideoCover())) {
								vo.setVideoFaceUrl(httpPath + actorDynamicPvs.get(0).getVideoCover());
							}
						}
					}
					vo.setDynamicUrl(dynamicUrlList);
					voList.add(vo);
				}
				return ActionResult.success(voList, startPage + Constant.FIND_PAGEZISE, isNextPage(Constant.FIND_PAGEZISE, count, startPage));
			}
			else {
				logger.debug("没有动态数据");
				return ActionResult.success();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}

	public boolean isNextPage(int pageSize, int count, int start) throws Exception {
		// start,当前页的起始位
		if((pageSize + start) < count) {
			return true;
		}
		return false;
	}

	public ActionResult addDynamicPageView(int id) throws Exception {
		actorDynamicService.addDynamicPageView(id);
		return ActionResult.success();
	}
}
