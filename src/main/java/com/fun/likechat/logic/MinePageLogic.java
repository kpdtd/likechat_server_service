package com.fun.likechat.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.Base64;
import com.fun.likechat.constant.Constant;
import com.fun.likechat.constant.ErrCodeEnum;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.interceptor.BeatContext;
import com.fun.likechat.interceptor.RequestUtils;
import com.fun.likechat.persistence.po.Actor;
import com.fun.likechat.persistence.po.ActorDynamicPv;
import com.fun.likechat.persistence.po.DataDictionary;
import com.fun.likechat.persistence.po.YunxinAccid;
import com.fun.likechat.service.ActorDynamicPvService;
import com.fun.likechat.service.ActorDynamicService;
import com.fun.likechat.service.ActorService;
import com.fun.likechat.service.DictionaryService;
import com.fun.likechat.service.UserAttentionService;
import com.fun.likechat.service.YunxinAccidService;
import com.fun.likechat.util.DateUtil;
import com.fun.likechat.util.FileUtil;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.util.SeqIdGenerator;
import com.fun.likechat.vo.ActorDynamicVo;
import com.fun.likechat.vo.ActorVo;
import com.fun.likechat.vo.UserRegisterVo;

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
	@Autowired
	YunXinLogic YunXinLogic;
	@Autowired
	YunxinAccidService yunxinAccidService;
	@Autowired
	DictionaryService dictionaryService;

	private static final Logger logger = LogFactory.getInstance().getLogger();

	/*
	 * 获取我的信息
	 */
	public ActionResult getMineInfo() throws Exception {
		// 获取用户信息
		BeatContext beatContext = RequestUtils.getCurrent();
		if(beatContext != null && beatContext.getUserid() > 0) {
			Actor actor = actorService.getById(beatContext.getUserid());
			if(actor != null) {
				// 转换成vo返回
				ActorVo vo = toActorVo(actor);
				return ActionResult.success(vo);
			}
			else {
				logger.debug("无法根据用户ID获取到用户信息");
				return ActionResult.fail(ErrCodeEnum.userNotExist.getCode(), ErrCodeEnum.userNotExist.getDesc());
			}
		}
		else {
			logger.debug("没有用户信息");
			return ActionResult.fail(ErrCodeEnum.getMyPageData_error.getCode(), ErrCodeEnum.getMyPageData_error.getDesc());
		}
	}

	/*
	 * 更新我的信息
	 */
	public ActionResult updateMineInfo(ActorVo vo) throws Exception {
		// 获取用户信息
		BeatContext beatContext = RequestUtils.getCurrent();
		if(beatContext != null && beatContext.getUserid() > 0) {
			Actor actor = actorService.getById(beatContext.getUserid());
			if(actor != null && vo != null) {
				//判断昵称是否唯一，唯一则可以更新
				if(StringUtils.isNotEmpty(vo.getNickname()) ) {
					if(!vo.getNickname().equals(actor.getNickname())) {//不相等才去数据库判断是否重复
						Actor ta = new Actor();
						ta.setNickname(vo.getNickname());
						List<Actor> alist = actorService.getListByPo(ta);
						if(alist != null && !alist.isEmpty()) {
							return ActionResult.fail(ErrCodeEnum.userNickNameExist.getCode(), ErrCodeEnum.userNickNameExist.getDesc()); 
						}
						actor.setNickname(vo.getNickname());
					}
				}

				// 如果头像不为空，则更新头像
				if(StringUtils.isNotEmpty(vo.getIcon()) && StringUtils.isNotEmpty(vo.getIconName())) {
					FileOutputStream out = null;
					try {
						String fileName = System.currentTimeMillis() + "." + vo.getIconName().substring(vo.getIconName().lastIndexOf(".") + 1);
						DataDictionary dictionary = dictionaryService.getDicByKey(Constant.D_IMG_SAVE_PATH);
						String rootPath = FileUtil.createFilePath(dictionary.getValue());//返回一个日期文件夹，不含数据字典前缀
						String imgPath = dictionary.getValue() + File.separator + rootPath + File.separator + fileName;
						out = new FileOutputStream(imgPath);
						byte[] buffer = Base64.base64ToByteArray(vo.getIcon());
						out.write(buffer);
						actor.setIcon(rootPath + File.separator + fileName);
					}
					catch(FileNotFoundException e) {
						e.printStackTrace();
					}
					finally {
						if(out != null) {
							out.close();
						}
					}
				}
				//更新性别(不用判断)
				if(vo.getSex() != null && (vo.getSex() == 1 || vo.getSex() ==2)) {
					actor.setSex(vo.getSex());
				}
				
				//年龄,传过来的是YYYY-MM-DD
				if(StringUtils.isNotEmpty(vo.getAge())) {
					actor.setBirthday(DateUtil.parseStringToDate(vo.getAge(), DateUtil.DATE_FORMAT_SHORT));
				}
				//签名-signature  
				if(StringUtils.isNotEmpty(vo.getSignature())) {
					actor.setSignature(vo.getSignature());
				}
				// 城市 
				if(StringUtils.isNotEmpty(vo.getCity())) {
					actor.setCity(vo.getCity());
				}
				actorService.update(actor);
				return ActionResult.success();
			}
			else {
				logger.debug("无法根据用户ID获取到用户信息");
				return ActionResult.fail(ErrCodeEnum.userNotExist.getCode(), ErrCodeEnum.userNotExist.getDesc());
			}
		}
		else {
			logger.debug("没有用户信息");
			return ActionResult.fail(ErrCodeEnum.getMyPageData_error.getCode(), ErrCodeEnum.getMyPageData_error.getDesc());
		}
	}

	/*
	 * 微信和qq登录注册接口。
	 */
	public ActionResult registerAndLogin(UserRegisterVo vo) throws Exception {
		Actor po = new Actor();
		String openId = vo.getOpenId();
		po.setOpenId(openId);
		List<Actor> actors = actorService.getListByPo(po);// 先判断openid是否已经注册过。注册过则直接返回
		if(actors != null && !actors.isEmpty()) {
			ActorVo vos = toActorVo(actors.get(0));
			
			/**
			 * 增加检查，没有云信accid也应该同步下
			 */
			YunxinAccid yx = yunxinAccidService.getByOpenId(openId);
			if(yx == null) {
				YunXinLogic.createAccid(po);
			}
			return ActionResult.success(vos);
		}

		// 判断nickname是否已经存在，冲突需要提用户变更nickname
		String nickname = vo.getNickname();
		po = new Actor();
		po.setNickname(nickname);
		actors = actorService.getListByPo(po);// 判断nickname是否已经存在
		if(actors != null && !actors.isEmpty()) {
			nickname = nickname + openId;
		}

		// 判断idcard是否冲突，冲突重新生成
		String idcard = null;
		for(int i = 0; i < 5; i++) {// 最多重试5次，防止以后用户增多冲撞几率增大
			idcard = SeqIdGenerator.getFixLenthString(8);
			po = new Actor();
			po.setIdcard(idcard);
			actors = actorService.getListByPo(po);// 判断idcard是否已经存在
			if(actors == null || actors.isEmpty()) {
				po.setOpenId(openId);
				po.setNickname(nickname);
				po.setLoginType(vo.getLoginType());
				po.setSignature(vo.getSignature());
				po.setProvince(vo.getProvince());
				po.setCity(vo.getCity());
				po.setSex(vo.getSex() == null || "男".equals(vo.getSex()) ? 1 : 2);
				po.setState(1);// 默认账号生效
				po.setIdentity(0);// 身份标识:0 - 普通用户 1 - 主播（平台主播） 2 - 用户申请的主播', 如果是主播需要在后台进行修改
				po.setCreateTime(new Date());
				actorService.insert(po);
				break;
			}
		}
		/**
		 * 06-09 新增：创建云信id。原来是打算用一个取一个。
		 */
		YunxinAccid yx = yunxinAccidService.getByOpenId(openId);
		if(yx == null) {
			YunXinLogic.createAccid(po);
		}
		return ActionResult.success(toActorVo(po));
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
		if(beatContext != null && beatContext.getUserid() > 0) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			int startPage = 0;
			int count = 0;
			if(StringUtils.isNotBlank(stamp)) {
				startPage = Integer.parseInt(stamp);
			}
			dataMap.put("startPage", startPage);
			dataMap.put("pageSize", Constant.PAGEZISE);
			dataMap.put("userId", beatContext.getUserid());

			List<Map<String, Object>> actors = userAttentionService.getUserFriends(dataMap);
			count = userAttentionService.userFriendsCount(dataMap);
			dataMap.remove("userId");// 获取我的粉丝数时，需要将userId清理掉。同时将userId当做actorId使用
			dataMap.put("actorId", beatContext.getUserid());// 获取自己的粉丝数，需要用userid当actorid用
			int fansCount = userAttentionService.myFansCount(dataMap);
			// 封装返回的对象
			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("attentionCount", count);
			returnMap.put("fansCount", fansCount);
			returnMap.put("dataList", toActorVo(actors));
			// 返回
			return ActionResult.success(returnMap, startPage + Constant.PAGEZISE, isNextPage(Constant.PAGEZISE, count, startPage));
		}
		else {
			logger.debug("没有用户信息");
			return ActionResult.fail(ErrCodeEnum.userNotExist.getCode(), ErrCodeEnum.userNotExist.getDesc());
		}
	}

	/*
	 * 我的好友 返回关注数、粉丝数、粉丝列表（图片、昵称、性别、年龄、签名）
	 */
	public ActionResult getFans(String stamp) throws Exception {
		// 获取用户信息
		BeatContext beatContext = RequestUtils.getCurrent();
		if(beatContext != null && beatContext.getUserid() > 0) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			int startPage = 0;
			int count = 0;
			if(StringUtils.isNotBlank(stamp)) {
				startPage = Integer.parseInt(stamp);
			}
			dataMap.put("startPage", startPage);
			dataMap.put("pageSize", Constant.PAGEZISE);
			dataMap.put("actorId", beatContext.getUserid());
			List<Map<String, Object>> actors = userAttentionService.getMyFans(dataMap);
			count = userAttentionService.myFansCount(dataMap);

			dataMap.remove("actorId");
			dataMap.put("userId", beatContext.getUserid());
			int attentionCount = userAttentionService.userFriendsCount(dataMap);
			// 封装返回的对象
			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("attentionCount", attentionCount);
			returnMap.put("fansCount", count);
			returnMap.put("dataList", toActorVo(actors));
			// 返回
			return ActionResult.success(returnMap, startPage + Constant.PAGEZISE, isNextPage(Constant.PAGEZISE, count, startPage));
		}
		else {
			logger.debug("没有用户信息");
			return ActionResult.fail(ErrCodeEnum.userNotExist.getCode(), ErrCodeEnum.userNotExist.getDesc());
		}
	}

	/*
	 * 我的动态
	 */
	public ActionResult getMyDynamic(String stamp) throws Exception {
		// 获取用户信息
		BeatContext beatContext = RequestUtils.getCurrent();
		if(beatContext != null && beatContext.getUserid() > 0) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			int startPage = 0;
			int count = 0;
			if(StringUtils.isNotBlank(stamp)) {
				startPage = Integer.parseInt(stamp);
			}
			dataMap.put("startPage", startPage);
			dataMap.put("pageSize", Constant.PAGEZISE);
			dataMap.put("actorId", beatContext.getUserid());
			List<Map<String, Object>> actorDynamics = actorDynamicService.getNewestDynamic(dataMap);
			count = actorDynamicService.newestDynamicCount(dataMap);
			// 封装返回的对象
			return toActorDynamicVo(actorDynamics, count, startPage);
		}
		else {
			logger.debug("没有用户信息");
		}
		return ActionResult.fail();
	}

	private List<ActorVo> toActorVo(List<Map<String, Object>> actors) {
		List<ActorVo> list = new ArrayList<>();
		try {
			if(actors != null && actors.size() > 0) {
				DataDictionary dictionary = dictionaryService.getDicByKey(Constant.D_IMG_SAVE_PATH_HTTP);
				String httpPath = dictionary.getValue();
				for(Map<String, Object> actor : actors) {
					// 封装返回的vo
					ActorVo vo = new ActorVo();
					if(actor.get("birthday") != null) {
						vo.setAge(DateUtil.getPersonAgeByBirthDate((Date) actor.get("birthday")));
					}
					if(actor.get("icon") != null) {
						vo.setIcon(httpPath + actor.get("icon").toString());
					}
					if(actor.get("nickname") != null) {
						vo.setNickname(actor.get("nickname").toString());
					}
					if(actor.get("signature") != null) {
						vo.setSignature(actor.get("signature").toString());
					}
					vo.setId((Integer) actor.get("id"));
					vo.setIdcard((String) actor.get("idcard"));
					vo.setSex(actor.get("sex") != null ? (Integer) actor.get("sex") : null);
					vo.setToken(actor.get("open_id").toString());
					list.add(vo);
				}
			}
			else {
				logger.debug("没有数据");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private ActorVo toActorVo(Actor actor) {
		try {
			DataDictionary dictionary = dictionaryService.getDicByKey(Constant.D_IMG_SAVE_PATH_HTTP);
			String httpPath = dictionary.getValue();
			ActorVo vo = new ActorVo();
			if(actor.getBirthday() != null) {
				vo.setAge(DateUtil.getPersonAgeByBirthDate((Date) actor.getBirthday()));
			}
			if(actor.getIcon() != null) {
				vo.setIcon(httpPath + actor.getIcon());
			}
			if(actor.getNickname() != null) {
				vo.setNickname(actor.getNickname());
			}
			if(actor.getSignature() != null) {
				vo.setSignature(actor.getSignature());
			}
			vo.setId(actor.getId());
			vo.setIdcard(actor.getIdcard());
			vo.setSex(actor.getSex());
			vo.setToken(actor.getOpenId());
			return vo;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private ActionResult toActorDynamicVo(List<Map<String, Object>> actorDynamics, int count, int startPage) {
		try {
			if(actorDynamics != null && actorDynamics.size() > 0) {
				DataDictionary dictionary = dictionaryService.getDicByKey(Constant.D_IMG_SAVE_PATH_HTTP);
				String httpPath = dictionary.getValue();
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
					vo.setCreateTime(actorDynamic.get("create_time").toString());
					vo.setId((Integer) actorDynamic.get("id"));
					List<String> dynamicUrl = new ArrayList<String>();
					// 获取到主播动态资源，如图片、语音、视频
					ActorDynamicPv adpv = new ActorDynamicPv();
					adpv.setDynamicId(Integer.parseInt(actorDynamic.get("id").toString()));
					List<ActorDynamicPv> actorDynamicPvs = actorDynamicPvService.getListByPo(adpv);
					if(actorDynamicPvs != null && actorDynamicPvs.size() > 0) {
						for(ActorDynamicPv actorDynamicPv : actorDynamicPvs) {
							dynamicUrl.add(httpPath + actorDynamicPv.getSavePath());
							vo.setDynamicType(actorDynamicPv.getType());
						}

						// 单独处理音视频的时长和 视频的封面字段，如果是音视频，每个动态下只有一条记录
						if(actorDynamicPvs.size() == 1 && (actorDynamicPvs.get(0).getType() == 1 || actorDynamicPvs.get(0).getType() == 3)) {// 类型为1-视频、3-语音才可能有值
							vo.setVoiceSec(actorDynamicPvs.get(0).getSecond());
							if(StringUtils.isNotEmpty(actorDynamicPvs.get(0).getVideoCover())) {
								vo.setVideoFaceUrl(httpPath + actorDynamicPvs.get(0).getVideoCover());
							}
						}
					}
					vo.setDynamicUrl(dynamicUrl);
					return ActionResult.success(vo, startPage + Constant.PAGEZISE, isNextPage(Constant.PAGEZISE, count, startPage));
				}
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
}
