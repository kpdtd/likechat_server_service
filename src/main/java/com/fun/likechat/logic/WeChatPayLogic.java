package com.fun.likechat.logic;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.constant.ErrCodeEnum;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.interceptor.BeatContext;
import com.fun.likechat.interceptor.RequestUtils;
import com.fun.likechat.persistence.po.AccountDetail;
import com.fun.likechat.persistence.po.Actor;
import com.fun.likechat.persistence.po.Goods;
import com.fun.likechat.service.AccountDetailService;
import com.fun.likechat.service.ActorService;
import com.fun.likechat.service.GoodsService;
import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.vo.PayInfoVo;
import com.fun.likechat.vo.WeChatUnifiedOrderReturnVo;
import com.fun.likechat.wechat.WeChatConst;
import com.fun.likechat.wechat.data.UnifiedOrderReqData;
import com.fun.likechat.wechat.util.WeChatUtil;
import com.fun.likechat.wechat.util.XmlUtil;

@Service
public class WeChatPayLogic {

	@Autowired
	ActorService actorService;

	@Autowired
	GoodsService goodsService;
	
	@Autowired
	AccountDetailService accountDetailService;

	private static final Logger logger = LogFactory.getInstance().getLogger();

	/*
	 * 统一下单主逻辑
	 * 参数：
	 * out_trade_no： 订单号
	 * goods_no：产品id
	 */
	public ActionResult unifiedOrder(PayInfoVo vo, String spbill_create_ip) {
		try {
			BeatContext beatContext = RequestUtils.getCurrent();
			if(beatContext != null && beatContext.getUserid() > 0) {
				Actor actor = actorService.getById(beatContext.getUserid());
				if(actor != null) {
					/**
					 * 缺少判断是否重复的逻辑--或者避免也行---没有生成订单
					 */

					/**
					 * 缺少获取产品信息的逻辑
					 */
					Goods good = goodsService.getById(vo.getGoodsId());

					UnifiedOrderReqData order = new UnifiedOrderReqData();
					order.setAppid(WeChatConst.appid);
					order.setMch_id(WeChatConst.mch_id);
					order.setNonce_str(WeChatUtil.KeyCreate(16));
					order.setBody(WeChatConst.body);
					order.setOut_trade_no(vo.getOutTradeNo());
					order.setTotal_fee(good.getPrice());
					order.setSpbill_create_ip(spbill_create_ip);
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
					String resutlJson = XmlUtil.getJsonFromXml(resultXml);
					//创建预付费订单
					createOrder(vo);//存储到t_account_detail表中
					
					
					11WeChatUnifiedOrderReturnVo vo = JsonHelper.toObject(resutlJson, WeChatUnifiedOrderReturnVo.class);
					return ActionResult.success(vo);
				}
			}
			else {
				return ActionResult.fail(ErrCodeEnum.userNotExist.getCode(), ErrCodeEnum.userNotExist.getDesc());
			}
		}
		catch(Exception e) {
			logger.error("微信订单号[" + out_trade_no + "]统一下单异常：xml=", e);
		}
		return ActionResult.fail(ErrCodeEnum.wechatpay_error.getCode(), ErrCodeEnum.wechatpay_error.getDesc());
	}
	
	private void createOrder(PayInfoVo vo) throws SQLException {
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
	}

	public static void main(String[] args) {
		UnifiedOrderReqData order = new UnifiedOrderReqData();
		order.setAppid(WeChatConst.appid);
		order.setMch_id(WeChatConst.mch_id);
		order.setNonce_str(WeChatUtil.KeyCreate(16));
		order.setBody(WeChatConst.body);
		order.setOut_trade_no("1234567890");
		order.setTotal_fee(100);
		order.setSpbill_create_ip("127.0.0.1");
		order.setTrade_type("APP");
		// 进行排序并产生sign签名 po 转 sortmap
		String orderJson = JsonHelper.toJson(order);// fastjson好像没有po直接转map
		Map<String, Object> map = JsonHelper.toMap(orderJson);
		SortedMap<String, Object> parameters = new TreeMap<String, Object>(map);
		/**
		 * KEY 需要夏军给验证短信才行
		 */
		String sign = WeChatUtil.createSign("UTF-8", parameters, WeChatConst.key);
		System.out.println(sign);
		order.setSign(sign);
		parameters.put("sign", sign);
		// System.out.println(WeChatUtil.getRequestXml(parameters));
		// 设置请求方式（GET/POST）
		// 当outputStr不为null时向输出流写数据
		String jsonResult = WeChatUtil.httpsRequest(WeChatConst.unifiedorder, "POST", WeChatUtil.getRequestXml(parameters));
		String resutlJson = XmlUtil.getJsonFromXml(jsonResult);
		System.out.println(resutlJson);
		System.out.println("##################");
		System.out.println(JsonHelper.toObject(resutlJson, WeChatUnifiedOrderReturnVo.class));
	}
}
