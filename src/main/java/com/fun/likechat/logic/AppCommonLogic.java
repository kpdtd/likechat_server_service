package com.fun.likechat.logic;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.constant.ErrCodeEnum;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.interceptor.BeatContext;
import com.fun.likechat.interceptor.RequestUtils;
import com.fun.likechat.persistence.po.Actor;
import com.fun.likechat.persistence.po.AppUpdate;
import com.fun.likechat.service.AppUpdateService;
import com.fun.likechat.util.DateUtil;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.vo.ActorVo;
import com.fun.likechat.vo.AppUpdateVo;

@Service
public class AppCommonLogic {

	@Autowired
	AppUpdateService appUpdateService;
	
	private static final Logger logger = LogFactory.getInstance().getLogger();

	/*
	 * 软件升级信息
	 */
	public ActionResult appUpdate(String version) throws Exception {
		if(StringUtils.isNotEmpty(version)) {
			AppUpdate app = new AppUpdate();
			app.setVersion(version);
			List<AppUpdate> list = appUpdateService.getListByPo(app);
			if(list != null && !list.isEmpty()) {
				AppUpdateVo vo = po2vo(list.get(0));
				return ActionResult.success(vo);
			}
		}
		return ActionResult.success();
	}

	private AppUpdateVo po2vo(AppUpdate po) {
		AppUpdateVo vo = new  AppUpdateVo();
		vo.setNewVersion(po.getNewVersion());
		vo.setIsForce(false);
		if(po.getIsForce() != null && po.getIsForce() == 1) {
			vo.setIsForce(true);
		}
		vo.setUrl(po.getUrl());
		vo.setDesc(po.getDesc());
	    return vo;
    }
}
