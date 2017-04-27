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
 * 从别处拷贝过来，并没有用到这个controller
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
	
	/**
	 * 获取短信验证码
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "getSmsCode", method = RequestMethod.POST)
    public @ResponseBody ActionResult getSmsCode(@RequestBody String phone) {
        try {
            JSONObject json = JsonHelper.toJsonObject(phone);
            String validationCode = json.getString("validationCode");
            return new ActionResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
	
    /*
     * 登录参数为验证码，参数为“手机号”“验证码”  
     * {"phone":"13999999999","validationCode":"123456"}
     */
    @RequestMapping(value = "userLogin", method = RequestMethod.POST)
    public @ResponseBody ActionResult userLogin(@RequestBody String body) {
        try {
            JSONObject json = JsonHelper.toJsonObject(body);
            String validationCode = json.getString("validationCode");
            String mobile = json.getString("mobile");
            return new ActionResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }

    /*
     * 自动登录 ，参数为空，返回用户对象
     */
    @RequestMapping(value = "userAutoLogin", method = RequestMethod.POST)
    public @ResponseBody ActionResult userAutoLogin() {
        try {
            return new ActionResult();//userLoginLogic.userAutoLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }

    /*
     * 登出，参数用户ID，返回成功
     */
    @RequestMapping(value = "userLogout", method = RequestMethod.POST)
    public @ResponseBody ActionResult userLogout() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
    

    
    /*
     * 用户信息修改
     */
    @RequestMapping(value = "userInfoUpdate", method = RequestMethod.POST)
    public @ResponseBody ActionResult userInfoUpdate(@RequestBody String info) {
        try {
//            if (info.getUser() <= 0) {
//                return ActionResult.fail(ErrCodeEnum.error.getCode(), "无法解析出用户信息");
//            } else {
//                return userInfoLogic.updateUserInfo(info);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
    
    /*
     * 获取关注列表
     */
    @RequestMapping(value = "myAttention", method = RequestMethod.POST)
    public @ResponseBody ActionResult myAttention(@RequestBody String page) {
        try {
//            if (info.getUser() <= 0) {
//                return ActionResult.fail(ErrCodeEnum.error.getCode(), "无法解析出用户信息");
//            } else {
//                return userInfoLogic.updateUserInfo(info);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
    
    /*
     * 获取粉丝列表
     */
    @RequestMapping(value = "myFans", method = RequestMethod.POST)
    public @ResponseBody ActionResult myFans(@RequestBody String page) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
    
    /*
     * 成为主播：达人认证
     */
    @RequestMapping(value = "applyForActor", method = RequestMethod.POST)
    public @ResponseBody ActionResult applyForActor(@RequestBody String page) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.fail();
    }
    


}
