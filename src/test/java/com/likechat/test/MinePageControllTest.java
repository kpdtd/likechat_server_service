package com.likechat.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fun.likechat.util.HttpClient;
import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.util.LogFactory;
import com.likechat.test.helper.EmbeddedTomcat;

@RunWith(SpringJUnit4ClassRunner.class)
public class MinePageControllTest extends BaseTest {
	private static EmbeddedTomcat tomcat = new EmbeddedTomcat();

	@Before
	public void setup() {
		if (!tomcat.isRunning()) {
			tomcat.start();
			tomcat.deploy("likechat_server_service");
			LogFactory.getInstance().getLogger().debug("tomcat-start ...");
		}
	}
	@After
	public void tearDown() {
		if (tomcat.isRunning()) {
			tomcat.stop();
			LogFactory.getInstance().getLogger().debug("tomcat-stop ...");
		}
	}

	@Test
	public void testGetMineInfo() {
		//拼装测试json
		Map<String , Object> data=new HashMap<>();
		data.put("stamp", "0");
		try {
			String result=HttpClient.postRequest("http://127.0.0.1:8081/likechat_server_service/mine/getMineInfo", JsonHelper.toJson(data));
			System.out.println("result:"+result);
			Assert.assertNotNull(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertFalse(false);
		}
	}
	
	@Test
	public void testGetMyFriends() {
		//拼装测试json
		Map<String , Object> data=new HashMap<>();
		data.put("stamp", "0");
		try {
			String result=HttpClient.postRequest("http://127.0.0.1:8081/likechat_server_service/mine/getMyFriends", JsonHelper.toJson(data));
			System.out.println("result:"+result);
			Assert.assertNotNull(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertFalse(false);
		}
	}
	
	@Test
	public void testGetMyFans() {
		//拼装测试json
		Map<String , Object> data=new HashMap<>();
		data.put("stamp", "0");
		try {
			String result=HttpClient.postRequest("http://127.0.0.1:8081/likechat_server_service/mine/getMyFans", JsonHelper.toJson(data));
			System.out.println("result:"+result);
			Assert.assertNotNull(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertFalse(false);
		}
	}
}
