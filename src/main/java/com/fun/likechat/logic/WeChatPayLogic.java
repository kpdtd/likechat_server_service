package com.fun.likechat.logic;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
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
import com.fun.likechat.service.AccountDetailService;
import com.fun.likechat.service.AccountService;
import com.fun.likechat.service.ActorService;
import com.fun.likechat.service.DictionaryService;
import com.fun.likechat.service.GoodsService;
import com.fun.likechat.util.DateUtil;
import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.vo.WeChatUnifiedOrderReqVo;
import com.fun.likechat.vo.WeChatUnifiedOrderReturnVo;
import com.fun.likechat.wechat.WeChatConst;
import com.fun.likechat.wechat.data.UnifiedOrderReqData;
import com.fun.likechat.wechat.data.WechatOrderNotificationData;
import com.fun.likechat.wechat.util.WeChatUtil;
import com.fun.likechat.wechat.util.XmlUtil;

/**
 * 阿里和微信的回传通知逻辑均在此类。
 * 主要还是微信订购相关的逻辑。阿里是为了复用代码移到这里。
 * 
 * 流程图和统一下单逻辑
 * https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_3#
 * https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
 */
@Service
public class WeChatPayLogic {

	@Autowired
	ActorService actorService;

	@Autowired
	GoodsService goodsService;

	@Autowired
	AccountDetailService accountDetailService;

	@Autowired
	AccountService accountService;

	@Autowired
	DictionaryService dictionaryService;

	private static final Logger logger = LogFactory.getInstance().getLogger();

	/*
	 * 统一下单主逻辑
	 * 参数：
	 * out_trade_no： 订单号
	 * goods_no：产品id
	 */
	public ActionResult unifiedOrder(WeChatUnifiedOrderReqVo wcuovo, String spbill_create_ip) {
		try {
			BeatContext beatContext = RequestUtils.getCurrent();
			if(beatContext != null && beatContext.getUserid() > 0) {
				Actor actor = actorService.getById(beatContext.getUserid());
				if(actor != null) {
					/**
					 * 缺少判断是否重复的逻辑- 好像也不用，虽然去微信订购，返回：OUT_TRADE_NO_USED 商户订单号重复 同一笔交易不能多次提交 请核实商户订单号是否重复提交
					 */
					/**
					 * 获取产品信息的逻辑
					 */
					Goods good = goodsService.getById(wcuovo.getGoods_no());
					if(good == null) {
						return ActionResult.fail(ErrCodeEnum.pay_error_no_found_goods.getCode(), ErrCodeEnum.pay_error_no_found_goods.getDesc());
					}

					UnifiedOrderReqData order = new UnifiedOrderReqData();
					order.setAppid(WeChatConst.appid);
					order.setMch_id(WeChatConst.mch_id);
					order.setNonce_str(WeChatUtil.KeyCreate(16));// 随机字符串，不长于32位。推荐随机数生成算法
					order.setBody(WeChatConst.body);
					order.setOut_trade_no(wcuovo.getOut_trade_no());
					order.setTotal_fee(good.getPrice() == null ? 0 : good.getPrice());
					order.setSpbill_create_ip(spbill_create_ip);
					// 从数据字典获取微信异步通知地址
					DataDictionary dictionary = dictionaryService.getDicByKey("WECHAT_NOTIFY_URL");
					String notifyUrl = dictionary.getValue();
					if(StringUtils.isNotEmpty(notifyUrl)) {
						order.setNotify_url(notifyUrl);// 通知地址---目前为空，还需要赶紧补上
					}
					else {
						order.setNotify_url(WeChatConst.notify_url);// 通知地址---目前为空，还需要赶紧补上
					}

					order.setTrade_type("APP");
					// 进行排序并产生sign签名 po 转 sortmap
					String orderJson = JsonHelper.toJson(order);// fastjson好像没有po直接转map
					Map<String, Object> map = JsonHelper.toMap(orderJson);
					SortedMap<String, Object> parameters = new TreeMap<String, Object>(map);
					/**
					 * KEY 需要夏军给验证短信才行
					 */
					String sign = WeChatUtil.createSign("UTF-8", parameters, WeChatConst.key);
					order.setSign(sign);
					parameters.put("sign", sign);
					// 发送xml到微信
					String resultXml = WeChatUtil.httpsRequest(WeChatConst.unifiedorder, "POST", WeChatUtil.getRequestXml(parameters));
					System.out.println("1：发送订单给微信后，微信的响应xml：" + resultXml);
					String resutlJson = XmlUtil.getJsonFromXml(resultXml);
					System.out.println("2：微信响应的xml转json：" + resutlJson);
					/**
					 * 微信返回消息转vo
					 */
					WeChatUnifiedOrderReturnVo vo1 = JsonHelper.toObject(resutlJson, WeChatUnifiedOrderReturnVo.class);
					// 创建预付费订单
					if("SUCCESS".equals(vo1.getReturn_code()) && "SUCCESS".equals(vo1.getResult_code())) {// //注意：result_code
						                                                                                  // 业务结果才是关键
						createOrder(actor, good, wcuovo.getOut_trade_no(), vo1, true);// 存储到t_account_detail表中
						// 后补的给客户端返回和随机字符串、签名用的属性
						vo1.setPartnerid(WeChatConst.mch_id);
						vo1.setAppid(WeChatConst.appid);
						vo1.setNoncestr(order.getNonce_str());
						vo1.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));
						parameters = new TreeMap<String, Object>();
						parameters.put("appid", WeChatConst.appid);
						parameters.put("partnerid", WeChatConst.mch_id);
						parameters.put("prepayid", vo1.getPrepay_id());
						parameters.put("package", "Sign=WXPay");
						parameters.put("noncestr", vo1.getNoncestr());
						parameters.put("timestamp", vo1.getTimestamp());
						sign = WeChatUtil.createSign("UTF-8", parameters, WeChatConst.key);
						vo1.setSign(sign);
					}
					else {
						createOrder(actor, good, wcuovo.getOut_trade_no(), vo1, false);
					}
					return ActionResult.success(vo1);// 返回vo1，这里面才是wechat的返回内容。
				}
			}
			else {
				return ActionResult.fail(ErrCodeEnum.userNotExist.getCode(), ErrCodeEnum.userNotExist.getDesc());
			}
		}
		catch(Exception e) {
			logger.error("微信订单号[" + wcuovo.getOut_trade_no() + "]统一下单异常", e);
		}
		return ActionResult.fail(ErrCodeEnum.wechatpay_error.getCode(), ErrCodeEnum.wechatpay_error.getDesc());
	}

	/*
	 * 微信订单通知逻辑
	 * 参数：见WechatOrderNotificationData
	 */
	public Boolean orderNotification(WechatOrderNotificationData data) {
		try {
			// 需要获取原订单，根据返回结果更新订单状态；
			AccountDetail po = new AccountDetail();
			po.setOutTradeNo(data.getOut_trade_no());// '商户网站唯一订单号-64,我方生成的订单号,同一个订单多次请求记为重复订单；
			List<AccountDetail> poList = accountDetailService.getListByPo(po);
			po = poList.get(0);
			if(po.getState() == 9) {// 防止重复充值漏洞
				logger.error("[订单已经成功，重复成功状态不能被处理" + data.getOut_trade_no() + "]");
				return true;
			}
			if("SUCCESS".equals(data.getReturn_code()) && "SUCCESS".equals(data.getResult_code())) {// 注意：result_code
				                                                                                    // 业务结果才是关键
				po.setState(9);
				po.setCauses("SUCCESS");
				modifyActorAccount(po.getGoodsId(), po.getActorId());// 返回都成则更细账号余额表
			}
			else {
				po.setState(3);
				po.setCauses("Err_code:" + data.getErr_code() + "|Err_code_des " + data.getErr_code_des());
			}
			po.setUpdateTime(new Date());
			accountDetailService.update(po);//
			return true;
		}
		catch(Exception e) {
			logger.error("[处理微信订单通知异常" + data.getOut_trade_no() + "]", e);
		}
		return false;
	}

	/**
	 * 创建订单内部逻辑
	 * @param actor
	 * @param goods
	 * @param vo1
	 * @param isSuccess
	 * @throws SQLException
	 */
	private void createOrder(Actor actor, Goods goods, String out_trade_no, WeChatUnifiedOrderReturnVo vo1, boolean isSuccess) throws SQLException {
		AccountDetail po = new AccountDetail();
		po.setActorId(actor.getId());
		po.setOpenId(actor.getOpenId());
		po.setPayType("1");// '支付类型 1- 微信 2-支付宝',
		po.setGoodsType(String.valueOf(goods.getType()));// '1-购买嗨币 2-购买会员',
		po.setGoodsId(goods.getId());// '购买商品的商品id，goods_code可以考虑不用',
		po.setMoney(goods.getPrice());// '金额 分
		po.setOutTradeNo(out_trade_no);// '商户网站唯一订单号-64,我方生成的订单号,同一个订单多次请求记为重复订单；',
		if(isSuccess) {
			po.setState(1);
			po.setCauses("SUCCESS");
		}
		else {
			po.setState(2);
			po.setCauses("Err_code:" + vo1.getErr_code() + "|Err_code_des " + vo1.getErr_code_des());
		}
		po.setCreateTime(new Date());
		po.setUpdateTime(new Date());
		accountDetailService.insert(po);// sql中加入IGNORE 防止重复插入（可以根据实际情况选择：Replace 或者 ON DUPLICATE KEY UPDAT）
		logger.info("[创建用户订单详情AccountDetail数据成功：" + po.toString());
	}

	/**
	 * 更新余额内部逻辑
	 * @throws SQLException
	 */
	private void modifyActorAccount(int goodsId, int actorId) {
		try {
			System.out.println("收到异步通知，更新用户账户余额");
			// 更新用户账户余额
			Goods goods = goodsService.getById(goodsId);
			if(goods == null)
				throw new Exception("获取产品信息时无法找到对应的产品数据，产品id=" + goodsId);
			Account apo = new Account();
			apo.setUserId(actorId);
			List<Account> listapo = accountService.getListByPo(apo);// 获取用户的账号信息
			if(listapo != null && !listapo.isEmpty()) {// 说明以前充值过。
				apo = listapo.get(0);
				if(goods.getType() == 1) {// 购买hi币，直接增加余额
					apo.setMoney(apo.getMoney() + goods.getValue());// 增加余额
				}
				else if(goods.getType() == 2) {// 够买会员，不但增加hi币，还要更新身份和有效期
					apo.setGrade(goods.getValue());
					apo.setIsvip(1);
					apo.setMoney(apo.getMoney() + goods.getGiveValue());// 如果购买的是会员，则goods的value是会员等级，giveValue才是赠送的hi币
					if(apo.getVipActiveTime() == null || DateUtil.isBefore(apo.getVipActiveTime(), new Date())) {
						apo.setVipActiveTime(DateUtil.afterNDaysDate(goods.getActiveTime()));
					}
					else {
						apo.setVipActiveTime(DateUtil.afterNDaysDate(apo.getVipActiveTime(), goods.getActiveTime()));// 在原有时间上增加一定的天数
					}
				}
				apo.setUpdateTime(new Date());
				accountService.update(apo);// 不是第一次充值则更新
			}
			else {// 没有记录，说明第一次充值
				if(goods.getType() == 1) {// 购买hi币，直接增加余额
					apo.setMoney(goods.getValue());// 增加余额
				}
				else if(goods.getType() == 2) {// 够买会员，不但增加hi币，还要更新身份和有效期
					apo.setGrade(goods.getValue());// 此时这里是级别 用户等级：1- 白银会员 2- 黄金会员 3-钻石会员',
					apo.setIsvip(1);
					apo.setMoney(goods.getGiveValue());// 如果购买的是会员，则goods的value是会员等级，giveValue才是赠送的hi币
					apo.setVipActiveTime(DateUtil.afterNDaysDate(goods.getActiveTime()));
				}
				apo.setCreateTime(new Date());
				apo.setUpdateTime(new Date());
				accountService.insert(apo);
			}
		}
		catch(Exception e) {
			logger.error("[接收微信或支付宝订单通知后进行Accout更新或增加时异常：actorId=" + actorId + "]", e);
		}
	}

	/*
	 * 支付宝订单异步通知逻辑--先和微信放到一起，以后接口多的话在单独写包.
	 * 放在一起可以复用表的更新操作等
	 * 支付宝只接收：success
	 * https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.prttAj&treeId=59&articleId=103666&docType=1
	 * 参数：见WechatOrderNotificationData
	 */
	public Boolean alipayOderNotifyUpdateUserAccount(String out_trade_no, String trade_status) {
		try {
			if(StringUtils.isEmpty(out_trade_no))
				throw new Exception("支付宝异步通知中订单号为空：" + out_trade_no);	
			AccountDetail po = new AccountDetail();
			po.setOutTradeNo(out_trade_no);// 商户网站唯一订单号-64,我方生成的订单号,同一个订单多次请求记为重复订单；
			List<AccountDetail> poList = accountDetailService.getListByPo(po);
			if(poList == null || poList.isEmpty())
				throw new Exception("账号详情表无法获取订单原始信息，订单号：" + out_trade_no);
			po = poList.get(0);
			if(po.getState() == 9) {//防止重复充值漏洞
				logger.error("[订单已经成功，重复成功状态不能被处理" + out_trade_no + "]");
				return true;
			}
			if("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {//状态取值范围： https://doc.open.alipay.com/doc2/detail?treeId=59&articleId=103672&docType=1
				po.setState(9);
				po.setCauses(trade_status);
				//重要：更新用户的账号余额
				modifyActorAccount(po.getGoodsId(), po.getActorId());// 返回都成则更细账号余额表
			}
			else {
				po.setState(3);
				po.setCauses("trade_status:" + trade_status);
			}
			po.setUpdateTime(new Date());
			accountDetailService.update(po);//
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("[处理支付宝订单通知异常" + out_trade_no + "]", e);
		}
		return false;
	}
	// public static void main(String[] args) {
	// UnifiedOrderReqData order = new UnifiedOrderReqData();
	// order.setAppid(WeChatConst.appid);
	// order.setMch_id(WeChatConst.mch_id);
	// order.setNonce_str(WeChatUtil.KeyCreate(16));
	// order.setBody(WeChatConst.body);
	// order.setOut_trade_no("1234567890");
	// order.setTotal_fee(100);
	// order.setSpbill_create_ip("127.0.0.1");
	// order.setTrade_type("APP");
	// // 进行排序并产生sign签名 po 转 sortmap
	// String orderJson = JsonHelper.toJson(order);// fastjson好像没有po直接转map
	// Map<String, Object> map = JsonHelper.toMap(orderJson);
	// SortedMap<String, Object> parameters = new TreeMap<String, Object>(map);
	// /**
	// * KEY 需要夏军给验证短信才行
	// */
	// String sign = WeChatUtil.createSign("UTF-8", parameters, WeChatConst.key);
	// System.out.println(sign);
	// order.setSign(sign);
	// parameters.put("sign", sign);
	// // System.out.println(WeChatUtil.getRequestXml(parameters));
	// // 设置请求方式（GET/POST）
	// // 当outputStr不为null时向输出流写数据
	// String jsonResult = WeChatUtil.httpsRequest(WeChatConst.unifiedorder, "POST",
	// WeChatUtil.getRequestXml(parameters));
	// String resutlJson = XmlUtil.getJsonFromXml(jsonResult);
	// System.out.println(resutlJson);
	// System.out.println("##################");
	// System.out.println(JsonHelper.toObject(resutlJson, WeChatUnifiedOrderReturnVo.class));
	// }
}
