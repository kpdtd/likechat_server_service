package com.fun.likechat.logic;

import java.util.Date;
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
import com.fun.likechat.persistence.po.Actor;
import com.fun.likechat.persistence.po.DataDictionary;
import com.fun.likechat.persistence.po.YunxinAccid;
import com.fun.likechat.service.ActorDynamicPvService;
import com.fun.likechat.service.ActorDynamicService;
import com.fun.likechat.service.ActorService;
import com.fun.likechat.service.DictionaryService;
import com.fun.likechat.service.UserAttentionService;
import com.fun.likechat.service.YunxinAccidService;
import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.yunxin.YXHttpClient;

@Service
public class YunXinLogic {

	@Autowired
	ActorService actorService;
	@Autowired
	YunxinAccidService yunxinAccidService;
	@Autowired
	DictionaryService dictionaryService;

	private static final Logger logger = LogFactory.getInstance().getLogger();

	/*
	 * 获取我的信息
	 */
	public ActionResult createAccid(String openId) throws Exception {
		//等于null，调用云信接口创建用户并生成token
		BeatContext beatContext = RequestUtils.getCurrent();
		if(beatContext != null && beatContext.getUserid() > 0) {
			Actor actor = actorService.getById(beatContext.getUserid());
			if(actor != null) {
				DataDictionary dictionary = dictionaryService.getDicByKey(Constant.D_IMG_SAVE_PATH_HTTP);
				String httpPath = dictionary.getValue();
				String icon = StringUtils.isNotBlank(actor.getIcon()) ? httpPath + actor.getIcon() : null; 
				try {
					//"Content-Type": "application/json; charset=utf-8" 云信返回：
					//{ "code":200, "info":{"token":"xx","accid":"xx","name":"xx"}  }注意：云信返回的accid可能和传过去的参数不一样 ，估计是因为云信解决重复的原因
					String yxResult = YXHttpClient.createAccid(openId, actor.getNickname(), icon, openId);
					Map<String , Object> map = JsonHelper.toMap(yxResult);
					if(map != null && map.get("code") != null && Integer.parseInt(map.get("code").toString()) == 200) {
						YunxinAccid po = new YunxinAccid();
						Map<String,String> yx =  (Map)map.get("info");
						po.setAccid(yx.get("accid"));
						po.setToken(yx.get("token"));
						po.setCreateTime(new Date());
						yunxinAccidService.insert(po);//成功则入库
						return ActionResult.success(po);
					}
                }
                catch(Exception e) {
                	e.printStackTrace();
                	return ActionResult.fail(ErrCodeEnum.yunxinCreateAccid_error.getCode(), ErrCodeEnum.yunxinCreateAccid_error.getDesc());
                }
			}
		}
		logger.debug("无法根据用户ID获取到用户信息");
		return ActionResult.fail(ErrCodeEnum.userNotExist.getCode(), ErrCodeEnum.userNotExist.getDesc());
	}
}
