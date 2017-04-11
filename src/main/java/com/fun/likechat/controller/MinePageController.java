package com.fun.likechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.logic.MinePageLogic;
import com.fun.likechat.util.JsonHelper;

/**
 * 我的栏目
 */
@Controller
@RequestMapping(value = "/mine")
public class MinePageController extends BaseController {

    @Autowired
    MinePageLogic minePageLogic;

    /**
     * 获取我的信息 图片、昵称、性别、年龄、hi聊号、签名
     * @param
     * @return
     */
    @RequestMapping(value = "getMineInfo", method = RequestMethod.POST)
    public @ResponseBody ActionResult getMineInfo() {
        try {
            return minePageLogic.getMineInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }

   /*
    * 我的好友,默认显示关注列表
    */
    @RequestMapping(value = "getMyFriends", method = RequestMethod.POST)
    public @ResponseBody ActionResult getMyFriends(@RequestBody String body) {
        try {
        	JSONObject json = JsonHelper.toJsonObject(body);
            String stamp = json.getString("stamp");
            return minePageLogic.getUserFriends(stamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }

    /*
     * 粉丝列表
     */
    @RequestMapping(value = "getMyFans", method = RequestMethod.POST)
    public @ResponseBody ActionResult getMyFans(@RequestBody String body) {
        try {
        	JSONObject json = JsonHelper.toJsonObject(body);
            String stamp = json.getString("stamp");
            return minePageLogic.getFans(stamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
}
