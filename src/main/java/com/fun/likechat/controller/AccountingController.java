package com.fun.likechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.util.JsonHelper;

/**
 */
@Controller
@RequestMapping(value = "/accounting")
public class AccountingController extends BaseController {
    
    /*
     * 我的账户account
     */
    @RequestMapping(value = "myAccount", method = RequestMethod.POST)
    public @ResponseBody ActionResult myAccount(@RequestBody String page) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
    
    
    /*
     * 获取兑换列表
     */
    @RequestMapping(value = "exchangeList", method = RequestMethod.POST)
    public @ResponseBody ActionResult getExchangeList(@RequestBody String page) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
    
    /*
     * 我的付款记录
     */
    @RequestMapping(value = "myPaymentLog", method = RequestMethod.POST)
    public @ResponseBody ActionResult myPaymentLog(@RequestBody String page) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
    
    /*
     * 我的收入记录
     */
    @RequestMapping(value = "myIncomeLog", method = RequestMethod.POST)
    public @ResponseBody ActionResult myIncomeLog(@RequestBody String page) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
}
