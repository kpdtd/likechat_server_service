package com.fun.likechat.logic;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.constant.ErrCodeEnum;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.interceptor.BeatContext;
import com.fun.likechat.interceptor.RequestUtils;
import com.fun.likechat.persistence.po.AccountDetail;
import com.fun.likechat.persistence.po.Actor;
import com.fun.likechat.service.AccountDetailService;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.vo.PayInfoVo;

@Service
public class AccountingLogic {

	@Autowired
	AccountDetailService accountDetailService;

	private static final Logger logger = LogFactory.getInstance().getLogger();

	/*
	 * 获取我的信息
	 */
	public ActionResult createOder(PayInfoVo vo) throws Exception {
		// 获取用户信息
		if(vo == null || StringUtils.isEmpty(vo.getOpenId())) {
			return ActionResult.fail(ErrCodeEnum.pay_error.getCode(), ErrCodeEnum.pay_error.getDesc());
		}
		BeatContext beatContext = RequestUtils.getCurrent();
		if(beatContext != null && beatContext.getUserid() > 0) {
			AccountDetail po = new AccountDetail();
			po.setActorId(vo.getActorId());
			po.setOpenId(vo.getOpenId());
			po.setPayType(vo.getPayType());
			po.setGoodsType(vo.getGoodsType());
			po.setGoodsId(vo.getGoodsId());
			po.setMoney(vo.getMoney());
			po.setOutTradeNo(vo.getOutTradeNo());
			po.setTradeNo(vo.getTradeNo());
			po.setPayer(vo.getPayer());
			po.setPayee(vo.getPayee());
			po.setState(0);
			po.setCreateTime(new Date());
			po.setUpdateTime(new Date());
			accountDetailService.insert(po);//sql中加入IGNORE 防止重复插入（可以根据实际情况选择：Replace  或者 ON DUPLICATE KEY UPDAT）
			return ActionResult.success();
		}
		else {
			logger.debug("没有用户信息");
			return ActionResult.fail(ErrCodeEnum.getMyPageData_error.getCode(), ErrCodeEnum.getMyPageData_error.getDesc());
		}
	}
}
