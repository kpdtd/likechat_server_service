package com.fun.likechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.logic.FindPageLogic;
import com.fun.likechat.util.JsonHelper;

/**
 * 发现栏目
 */
@Controller
@RequestMapping(value = "/find")
public class FindPageController extends BaseController {

    @Autowired
    FindPageLogic FindPageLogic;

    
    @RequestMapping(value = "getActorDynamicList", method = RequestMethod.POST)
    public @ResponseBody ActionResult getActorDynamicList(@RequestBody String body) {
        try {
            JSONObject json = JsonHelper.toJsonObject(body);
            int actorId = json.getIntValue("id");
            String stamp = json.getString("stamp");
            // 根据条件获取动态列表
            return FindPageLogic.getActorDynamicList(actorId, stamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
    
    /**
     * 获取动态列表： 
     * 最新：按照时间排序，最新的放在最上面； 
     * 热门：按照观看的人数排序，最多的放在最上面； 
     * 关注：按照用户关注的主播发的动态最新的放在最上面；
     * 
     * @param
     * @return
     */
    @RequestMapping(value = "getFindList", method = RequestMethod.POST)
    public @ResponseBody ActionResult getFindList(@RequestBody String body) {
        try {
            JSONObject json = JsonHelper.toJsonObject(body);
            int tag = json.getIntValue("tag");
            String stamp = json.getString("stamp");
            // 根据条件获取动态列表
            return FindPageLogic.getFindList(tag, stamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }

    /**
     * 增加动态
     * 
     * @param
     * @return
     */
    @RequestMapping(value = "addDynamic", method = RequestMethod.POST)
    public @ResponseBody ActionResult addDynamic(@RequestBody String body) {
        try {
            JSONObject json = JsonHelper.toJsonObject(body);
            int actorId = json.getIntValue("condition");
            // 根据条件获取动态列表

            return new ActionResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
    
    /**
     * 增加动态的浏览量
     * @param:动态id
     * @return
     */
    @RequestMapping(value = "addDynamicPageView", method = RequestMethod.POST)
    public @ResponseBody ActionResult addDynamicPageView(@RequestBody String body) {
        try {
            JSONObject json = JsonHelper.toJsonObject(body);
            int id = json.getIntValue("id");
            return FindPageLogic.addDynamicPageView(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }

}
