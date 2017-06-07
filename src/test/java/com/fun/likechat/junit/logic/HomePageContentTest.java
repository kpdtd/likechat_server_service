package com.fun.likechat.junit.logic;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.fun.likechat.constant.Constant;
import com.fun.likechat.junit.BaseTest;
import com.fun.likechat.logic.HomePageLogic;
import com.fun.likechat.vo.ActorVo;
import com.fun.likechat.vo.BannerVo;
import com.fun.likechat.vo.TagVo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) //按字母顺序执行method
public class HomePageContentTest extends BaseTest {
	@Autowired
	HomePageLogic homePageLogic;
	
	
	
	/**
	 *  1、获取tag列表
	 */
	@Test
	public void a_getAllSystemTagVo() throws SQLException {
		List<TagVo> tagsVo = homePageLogic.getAllSystemTagVo();
		System.out.println("================ 标签列表 =================");
		for(TagVo vo : tagsVo) {
			System.out.println(tagsVo.size()+"result = " + vo.toString());
		}
		System.out.println("================ 标签结束 =================");
	}
	
	/**
	 *  // 2、获取banner,banner是用频道来描述的
	 * @throws Exception 
	 */
	@Test
	public void b_getBannerListVo() throws Exception {
		List<BannerVo> bannerVo = homePageLogic.getBannerListVo("banner");
		System.out.println("================ banner列表 =================");
		for(BannerVo vo : bannerVo) {
			System.out.println(bannerVo.size()+"result = " + vo.toString());
		}
		System.out.println("================ banner结束 =================");
	}
	
	/**
	 * // 3、获取随机（20个）主播列表，每次刷新都是随机20个
	 * @throws Exception 
	 */
	@Test
	public void c_getRandomActorsVo() throws Exception {
		List<ActorVo> actorsVo = homePageLogic.getRandomActorsVo(5);
		System.out.println("================ （20个）主播列表 =================");
		for(ActorVo vo : actorsVo) {
			System.out.println(actorsVo.size()+"result = " + vo.toString());
		}
		System.out.println("================ （20个）主播结束 =================");
	}

	/**
	 * // 4、根据tag获取随机（20--5个）主播列表，每次刷新都是随机20个
	 * @throws Exception 
	 */
	@Test
	public void d_getRandomActorsByCondition() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limit", 5);//
		map.put("tagIdentifying", "ZUIXIN");
		List<ActorVo> actorsVo = homePageLogic.getRandomActorsByCondition(map);
		System.out.println("================ （最新5个随机）主播列表 =================");
		for(ActorVo vo : actorsVo) {
			System.out.println(actorsVo.size()+"result = " + vo.toString());
		}
		System.out.println("================ （最新5个随机）主播结束 =================");
		
		map.put("limit", 5);//
		map.put("tagIdentifying", "DAXIONG");
		actorsVo = homePageLogic.getRandomActorsByCondition(map);
		System.out.println("================ （长腿5个随机）主播列表 =================");
		for(ActorVo vo : actorsVo) {
			System.out.println(actorsVo.size()+"result = " + vo.toString());
		}
		System.out.println("================ （长腿5个随机）主播结束 =================");
	}
	
	/**
	 * 主播详情页信息测试
	 * 	homePageLogic.getActorPage(identifying);
	 */
	@Test
	public void e_getActorPage() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limit", 5);//
		map.put("tagIdentifying", "ZUIXIN");
		List<ActorVo> actorsVo = homePageLogic.getRandomActorsByCondition(map);
		System.out.println("================ （最新5个随机）主播列表 =================");
		for(ActorVo vo : actorsVo) {
			System.out.println(actorsVo.size()+"result = " + vo.toString());
		}
		System.out.println("================ （最新5个随机）主播结束 =================");
		System.out.println("================ （长腿5个随机）主播结束 =================");
	}
}