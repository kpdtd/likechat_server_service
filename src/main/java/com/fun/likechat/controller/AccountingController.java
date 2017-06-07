package com.fun.likechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.logic.AccountingLogic;
import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.vo.PayInfoVo;

/**
 */
@Controller
@RequestMapping(value = "/accounting")
public class AccountingController extends BaseController {
    
	@Autowired
	AccountingLogic accountingLogic;
	
    /*
     * 我的账户account
     */
//    @RequestMapping(value = "myAccount", method = RequestMethod.POST)
//    public @ResponseBody ActionResult myAccount(@RequestBody String page) {
//        try {
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ActionResult.fail();
//    }
// 
    /*
     * 我的付款记录--订单生成
     * 1：客户端在用户调用支付接口前发起支付记录，创建订单。主要同步一下openid 和 订单号，真正的
     */
    @RequestMapping(value = "createOder", method = RequestMethod.POST)
    public @ResponseBody ActionResult createOder(@RequestBody String body) {
        try {
        	PayInfoVo vo = JsonHelper.toObject(body, PayInfoVo.class);
        	return accountingLogic.createOder(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
    
    /*
     * 支付宝回传结果充值处理--不是JSON。
     * 1：支付宝回传后，如果订单成功，则为用户充值；
     */
    @RequestMapping(value = "alipayOderNotify", method = RequestMethod.POST)
    public @ResponseBody ActionResult alipayOderNotify() {
        try {
        	
        	
//        	return accountingLogic.createOder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
    
//    /*
//     * 我的收入记录
//     */
//    @RequestMapping(value = "myIncomeLog", method = RequestMethod.POST)
//    public @ResponseBody ActionResult myIncomeLog(@RequestBody String page) {
//        try {
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ActionResult.fail();
//    }
//    
//   
//    
//    /*
//     * 获取兑换列表
//     */
//    @RequestMapping(value = "exchangeList", method = RequestMethod.POST)
//    public @ResponseBody ActionResult getExchangeList(@RequestBody String page) {
//        try {
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ActionResult.fail();
//    }
    
}
