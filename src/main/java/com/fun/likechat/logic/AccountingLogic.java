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

import com.fun.likechat.constant.ErrCodeEnum;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.interceptor.BeatContext;
import com.fun.likechat.interceptor.RequestUtils;
import com.fun.likechat.persistence.po.Account;
import com.fun.likechat.persistence.po.AccountDetail;
import com.fun.likechat.persistence.po.Actor;
import com.fun.likechat.persistence.po.DataDictionary;
import com.fun.likechat.persistence.po.Goods;
import com.fun.likechat.persistence.po.UserCallRecord;
import com.fun.likechat.service.AccountDetailService;
import com.fun.likechat.service.AccountService;
import com.fun.likechat.service.ActorService;
import com.fun.likechat.service.DictionaryService;
import com.fun.likechat.service.GoodsService;
import com.fun.likechat.service.UserCallRecordService;
import com.fun.likechat.util.DateUtil;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.vo.AccountBalanceVo;
import com.fun.likechat.vo.AccountVo;
import com.fun.likechat.vo.GoodsVo;
import com.fun.likechat.vo.PayInfoVo;
import com.fun.likechat.vo.VipMemberVo;

@Service
public class AccountingLogic {

	@Autowired
	AccountService accountService;
	@Autowired
	AccountDetailService accountDetailService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	ActorService actorService;
	@Autowired
    DictionaryService dictionaryService;
	@Autowired
	UserCallRecordService 	userCallRecordService;

	private static final Logger logger = LogFactory.getInstance().getLogger();

	/*
	 * 获取账号余额及相关信息
	 */
	public ActionResult getAccountInfo() throws Exception {
		BeatContext beatContext = RequestUtils.getCurrent();
		if(beatContext != null && beatContext.getUserid() > 0) {
			Account po = new Account();
			po.setUserId(beatContext.getUserid());
			List<Account> list = accountService.getListByPo(po);
			AccountVo vo = new AccountVo();
			if(list != null && !list.isEmpty()) {
				vo.setUserId(list.get(0).getUserId());
				vo.setIsvip(list.get(0).getIsvip() == null ? 0 : list.get(0).getIsvip());
				vo.setGrade(list.get(0).getGrade() == null ? 0 : list.get(0).getGrade());
				vo.setMoney(list.get(0).getMoney() == null ? 0 : list.get(0).getMoney());
				vo.setVipActiveTime(list.get(0).getVipActiveTime());
			}
			return ActionResult.success(vo);
		}
		else {
			logger.debug("没有用户信息");
			return ActionResult.fail(ErrCodeEnum.getMyPageData_error.getCode(), ErrCodeEnum.getMyPageData_error.getDesc());
		}
	}

	/*
	 * 用于在“我的”页面中 点击“账号余额”后 的显示信息（返回账号余额，产品列表信息等）
	 */
	public ActionResult accountBalance() throws Exception {
		BeatContext beatContext = RequestUtils.getCurrent();
		if(beatContext != null && beatContext.getUserid() > 0) {
			AccountBalanceVo resultVo = new AccountBalanceVo();
			Account po = new Account();
			po.setUserId(beatContext.getUserid());
			// 获取账户余额（t_account）
			List<Account> list = accountService.getListByPo(po);
			if(list != null && !list.isEmpty()) {
				resultVo.setMoney(list.get(0).getMoney());
			}

			// 获取商品信息 type-1 hi币兑换
			Goods goods = new Goods();
			goods.setType(1);// 1-hi币 2-会员
			List<Goods> goodlist = goodsService.getListByPo(goods);
			List<GoodsVo> goodsVoList = poTovo(goodlist);
			resultVo.setGoods(goodsVoList);
			return ActionResult.success(resultVo);
		}
		else {
			logger.debug("没有用户信息");
			return ActionResult.fail(ErrCodeEnum.getMyPageData_error.getCode(), ErrCodeEnum.getMyPageData_error.getDesc());
		}
	}

	/*
	 * vip会员相关数据
	 */
	public ActionResult vipMember() throws Exception {
		BeatContext beatContext = RequestUtils.getCurrent();
		if(beatContext != null && beatContext.getUserid() > 0) {
			VipMemberVo resultVo = new VipMemberVo();
			Account po = new Account();
			po.setUserId(beatContext.getUserid());
			// 获取账户余额（t_account）
			List<Account> list = accountService.getListByPo(po);
			if(list != null && !list.isEmpty()) {
				resultVo.setIsvip(list.get(0).getIsvip());// '是否是vip会员：0-否 1-是',
				int grade = list.get(0).getGrade();
				if(grade == 1) {
					resultVo.setGrade("白银会员");
				}
				else if(grade == 2) {
					resultVo.setGrade("黄金会员");
				}
				else if(grade == 3) {
					resultVo.setGrade("钻石会员");
				}
				else {
					resultVo.setGrade("您还不是会员");
				}
				// 计算剩余天数
				if((grade == 1 || grade == 2 || grade == 3) && list.get(0).getVipActiveTime() != null) {
					int day = DateUtil.differentDaysByMillisecond(new Date(), list.get(0).getVipActiveTime());
					resultVo.setVipActiveTime(day + "天");
				}
			}

			// 获取商品信息 type-1 hi币兑换
			Goods goods = new Goods();
			goods.setType(2);// 1-hi币 2-会员
			List<Goods> goodlist = goodsService.getListByPo(goods);
			List<GoodsVo> goodsVoList = poTovo(goodlist);
			resultVo.setGoods(goodsVoList);
			return ActionResult.success(resultVo);
		}
		else {
			logger.debug("没有用户信息");
			return ActionResult.fail(ErrCodeEnum.getMyPageData_error.getCode(), ErrCodeEnum.getMyPageData_error.getDesc());
		}
	}

	/*
	 * 创建支付宝订单（微信的已经挪到Wechart里面了）
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
			accountDetailService.insert(po);// sql中加入IGNORE 防止重复插入（可以根据实际情况选择：Replace 或者 ON DUPLICATE KEY UPDAT
			
			DataDictionary dictionary = dictionaryService.getDicByKey("ALIPAY_NOTIFY_URL");
			String notifyUrl = dictionary.getValue();
			Map<String,String> map = new HashMap<String,String>();
			if(StringUtils.isNotEmpty(notifyUrl)) {
				map.put("notifyUrl", notifyUrl);
			}else{
				map.put("notifyUrl", "http://114.215.221.15:8080/likechat_server_service/accounting/alipayOderNotify");
				System.out.println("注意：支付宝异步通知地址在数据字典没有找到，返回一个写死的值");
			}
			return ActionResult.success(map);
		}
		else {
			logger.debug("没有用户信息");
			return ActionResult.fail(ErrCodeEnum.getMyPageData_error.getCode(), ErrCodeEnum.getMyPageData_error.getDesc());
		}
	}

	/**
	 *  用户和主播通话时的计费逻辑
	 */
	public ActionResult callPay(Integer actorId, Integer recordId)  throws Exception {
		BeatContext beatContext = RequestUtils.getCurrent();
		
		if(beatContext != null && beatContext.getUserid() > 0) {
			//1、获取主播没分钟价格 及 平台价格
			Actor actor = actorService.getById(actorId);
	    	if(actor == null || actor.getState() != 1) {
	    		return ActionResult.fail(ErrCodeEnum.userNotExist.getCode(), ErrCodeEnum.userNotExist.getDesc());//主播不存在或状态为不可用；
	    	}
	    	Integer actorPrice = actor.getPrice();
	    	//获取数据字典里的平台价格：
	    	DataDictionary dictionary = dictionaryService.getDicByKey("PLATFORM_PRICE");
			String platformPrice = dictionary.getValue();
			Integer  deductMoney = actorPrice + Integer.parseInt(platformPrice);//应扣款（hi币）金额
	    	
			//2、获取用户余额
			Integer userId = beatContext.getUserid();
			Account account = new Account();
			account.setUserId(userId);
			List<Account> accountList = accountService.getListByPo(account);
			if(accountList == null || accountList.isEmpty() || (accountList.get(0).getMoney() - deductMoney) < 0) {
				return ActionResult.fail(ErrCodeEnum.userInsufficientBalance_error.getCode(), ErrCodeEnum.userInsufficientBalance_error.getDesc());//主播不存在或状态为不可用；
			}
			
			//3、余额操作
			account = accountList.get(0);
			Integer balance = account.getMoney() - deductMoney;
			account.setMoney(balance);
			accountService.update(account);//更新余额
			
			//4、产生一条通话记录
			UserCallRecord ucr = new UserCallRecord();
			if(recordId == null || recordId < 1) {
				ucr.setUserId(userId);
				ucr.setActorId(actorId);
				ucr.setStartTime(new Date());
				ucr.setPayment(deductMoney);//每次叠加
				ucr.setBalance(balance);
				ucr.setCreateTime(new Date());
				userCallRecordService.insert(ucr);
			}
			else{
				ucr = userCallRecordService.getById(recordId);
//				UserCallRecord ucr =  userCallRecordService.getByUserIdActorId(userId, actorId);
				if(ucr == null) {
					return ActionResult.fail(ErrCodeEnum.userNotExistCallRecord_error.getCode(), ErrCodeEnum.userNotExistCallRecord_error.getDesc());
				}
				ucr.setPayment(ucr.getPayment() + deductMoney);//每次叠加，方便统计用。可得知用户为当前通话已经支付的hi币
				ucr.setBalance(balance);
				userCallRecordService.update(ucr);
			}
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("balance", balance);//余额
			map.put("recordId", ucr.getId());//余额
			return ActionResult.success(map);//仅返回余额，可根据情况增加其他信息
		}
		else {
			logger.debug("没有用户信息");
			return ActionResult.fail(ErrCodeEnum.getMyPageData_error.getCode(), ErrCodeEnum.getMyPageData_error.getDesc());
		}
	}
	
	/**
	 *  用户挂断通知
	 *  	仅仅是修改用户通话记录表，更新通话时长和结束时间
	 */
	public ActionResult hangUpNotify(Integer recordId)  throws Exception {
		BeatContext beatContext = RequestUtils.getCurrent();
		if(beatContext != null && beatContext.getUserid() > 0) {
//			UserCallRecord ucr =  userCallRecordService.getByUserIdActorId(beatContext.getUserid(), actorId);
			if(recordId == null || recordId < 1)
				return ActionResult.fail(ErrCodeEnum.userNotExistCallRecord_error.getCode(), ErrCodeEnum.userNotExistCallRecord_error.getDesc()); 
			UserCallRecord ucr = userCallRecordService.getById(recordId);
			if(ucr == null) {
				return ActionResult.fail(ErrCodeEnum.userNotExistCallRecord_error.getCode(), ErrCodeEnum.userNotExistCallRecord_error.getDesc());
			}
			ucr.setEndTime(new Date());
			ucr.setCallTime((int)(ucr.getEndTime().getTime() - ucr.getStartTime().getTime())/1000);
			userCallRecordService.update(ucr);
			return ActionResult.success();//仅返回余额，可根据情况增加其他信息
		}
		else {
			logger.debug("没有用户信息");
			return ActionResult.fail(ErrCodeEnum.getMyPageData_error.getCode(), ErrCodeEnum.getMyPageData_error.getDesc());
		}
	}
	
	
	/**
	 * 列表po 转 vo
	 * @param goodlist
	 * @return
	 */
	private List<GoodsVo> poTovo(List<Goods> goodlist) {
		List<GoodsVo> list = new ArrayList<GoodsVo>();
		if(goodlist != null && !goodlist.isEmpty()) {
			for(Goods po : goodlist) {
				GoodsVo vo = new GoodsVo();
				vo.setId(po.getId());
				vo.setName(po.getName());
				vo.setSubname(po.getSubname());
				vo.setPic(po.getPic());
				vo.setDisplayPrice(po.getPrice() / 100F + "元");
				vo.setRealPrice(po.getPrice());
				list.add(vo);
			}
		}
		return list;
	}



	public static void main(String[] args) {
		System.out.println(1 / 100F + "元");
	}

}
