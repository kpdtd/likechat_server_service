package com.fun.likechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fun.likechat.constant.ErrCodeEnum;
import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.logic.MinePageLogic;
import com.fun.likechat.persistence.po.Actor;
import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.vo.UserRegisterVo;

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
    @RequestMapping(value = "getMineInfo", method = RequestMethod.GET)
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
    /*
     * 我的动态
     */
    @RequestMapping(value = "getMyDynamics", method = RequestMethod.POST)
    public @ResponseBody ActionResult getMyDynamics(@RequestBody String body) {
        try {
        	JSONObject json = JsonHelper.toJsonObject(body);
            String stamp = json.getString("stamp");
            return minePageLogic.getMyDynamic(stamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
    
    /*
     * 微信和qq登录注册接口。
	 * app成功在为微信或qq登录后，获取用户相关信息，包装UserRegisterVo对象传递给服务器
	 * 1、每次用户通过微信或qq登录都要调用此接口。如果返回失败，客户端应该重新登录；
	 * 2、如果用户登录成功，但获取用户详细信息失败，也应该重新登录
     * 参数：
     * 	openId：唯一标识
     * 	type：qq 微信
     */
    @RequestMapping(value = "registerAndLogin", method = RequestMethod.POST)
    public @ResponseBody ActionResult registerAndLogin(@RequestBody String body) {
        try {
        	UserRegisterVo vo = JsonHelper.toObject(body, UserRegisterVo.class);
        	if(vo == null || StringUtils.isEmpty(vo.getOpenId())) {
        		return ActionResult.fail(ErrCodeEnum.userInfo_error.getCode(), ErrCodeEnum.userInfo_error.getDesc());
        	}
            return minePageLogic.registerAndLogin(vo);//进行校验，如果用户不存在则生成用户信息
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail(ErrCodeEnum.userInfo_error.getCode(), ErrCodeEnum.userInfo_error.getDesc());
    }
}
