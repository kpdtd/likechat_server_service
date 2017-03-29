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
 * 
 */
@Controller
@RequestMapping(value="/app")
public class AppStartController extends BaseController{
    
    /**
     * 获得app启动画面 ,错误码定义2开头的4位
     */
    @RequestMapping(value="/getFlashAd",method=RequestMethod.POST)
    public @ResponseBody ActionResult getFlashAd(@RequestBody String body){
        try {
            JSONObject json = JsonHelper.toJsonObject(body);
            int versionId = json.getIntValue("versionId"); 
            return new ActionResult();//appStartLogic.getAppAdBanner(versionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
    
    /*
     * 软件升级:
     * 	 1、已经是最新版本；
     * 	 2、非强制升级：返回版本号，升级信息，升级地址；
     * 	 3、强制升级：返回版本号，升级信息，升级地址；
     */
    @RequestMapping(value = "appUpdate", method = RequestMethod.POST)
    public @ResponseBody ActionResult appUpdate(@RequestBody String page) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
    
}
