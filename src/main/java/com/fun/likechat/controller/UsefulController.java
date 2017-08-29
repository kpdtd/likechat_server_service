package com.fun.likechat.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.logic.UtilLogic;
import com.fun.likechat.logic.YunXinLogic;
import com.fun.likechat.persistence.po.Actor;
import com.fun.likechat.persistence.po.ActorDynamic;
import com.fun.likechat.persistence.po.ActorDynamicPv;
import com.fun.likechat.service.ActorDynamicPvService;
import com.fun.likechat.service.ActorDynamicService;
import com.fun.likechat.service.ActorService;
import com.fun.likechat.service.DictionaryService;
import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.util.SeqIdGenerator;

/**
 * 网易云信相关接口
 */
@Controller
@RequestMapping(value = "/useful")
public class UsefulController extends BaseController {
	@Autowired
	ActorDynamicService actorDynamicService;
	@Autowired
	ActorDynamicPvService actorDynamicPvService;
	@Autowired
	ActorService actorService;
	@Autowired
	YunXinLogic yunXinLogic;
	@Autowired
	DictionaryService dictionaryService;
	@Autowired
	UtilLogic utilLogic;
	
	/**
	 * 批量增加主播：
	 * @param
	 * @return
	 */
	@RequestMapping(value = "batchAddActor", method = RequestMethod.POST)
	public @ResponseBody ActionResult batchAddActor(@RequestBody String body) {
		try {
			Map<String, List<String> > map = JsonHelper.toMap(body);
			int i = 1000;
			for(Entry<String, List<String>> entry : map.entrySet()) {
				String nickname = entry.getKey();
				List<String> picList = entry.getValue();//
	            System.out.println("nickname=" + nickname + "| picArray=" + picList.size());
	            
				//插入主播表
				Actor po = new Actor();
				String idcard = "9"+SeqIdGenerator.getFixLenthString(3) + i;
				po.setIdcard(idcard);
				po.setIdentity(1);
				po.setOpenId(idcard);//@@@@@@ 先用idcard代替 @@@@@@@@
				po.setLoginType("roboot");
				po.setName(nickname);
				po.setNickname(nickname);
				po.setFans(300 + new Random().nextInt(5000));
				po.setAttention(0);
				String picPath = utilLogic.savePic((String)picList.get(0));
				po.setIcon(picPath);
				po.setPrice(300);
				po.setState(1);
				po.setSex(2);
				po.setCreateTime(new Date());
				po.setUpdateTime(new Date());
				actorService.insert(po);
				/**
				 * 调用云信--单独实现--先不调用。应该在用其他方法，循环生成云信id。这里仅做我们的生成。（open还不知道）
				 */
				//插入动态表
				ActorDynamic adpo = new ActorDynamic();
				adpo.setActorId(po.getId());
				adpo.setType(2);
				adpo.setContent("快来找我聊天~");
				adpo.setPrice(0);
				adpo.setPageView(new Random().nextInt(1000) +8);
				adpo.setCreateTime(new Date());
				actorDynamicService.insert(adpo);
				
				//插入动态pv表
				for(int j = 0; j < picList.size() ; j++) {
					ActorDynamicPv adpvpo = new ActorDynamicPv();
					adpvpo.setActorId(po.getId());
					adpvpo.setDynamicId(adpo.getId());
					adpvpo.setType(2);
					String tempPath = utilLogic.savePic(picList.get(j));
					adpvpo.setSavePath(tempPath);
					adpvpo.setPrice(0);
					adpvpo.setCreateTime(new Date());
					actorDynamicPvService.insert(adpvpo);
				}
            }
			return ActionResult.success(map); 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}
	
	/**
	 * 制定某吧某主播添加到云信用户：
	 * @param
	 * @return
	 */
	@RequestMapping(value = "addToYunXin", method = RequestMethod.POST)
	public @ResponseBody ActionResult addToYunXin(@RequestBody String body) {
		try {
			Map<String, String > map = JsonHelper.toMap(body);
			String openId = map.get("openId");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ActionResult.fail();
	}
	
	public static void main(String[] args) {
		System.out.println(SeqIdGenerator.getFixLenthString(2));
    }
}
