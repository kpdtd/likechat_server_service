package com.likechat.test;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.yunxin.data.CreateIdReqData;
import com.fun.likechat.yunxin.data.CreateIdResData;
import com.fun.likechat.yunxin.data.ResultInfo;
import com.fun.likechat.yunxin.im.YxIdAction;

@RunWith(SpringJUnit4ClassRunner.class)
public class CreatYXIDTest extends BaseTest {

	@Autowired
	YxIdAction creatYXID;
	@Test
	public void testCreateYXId() {
		
		String json="{\"code\":200,\"info\":{\"token\":\"xx\",\"accid\":\"xx\",\"name\":\"xx\"}}";
		
		ResultInfo info=JsonHelper.toObject(JsonHelper.toJsonObject(json).toJSONString(),ResultInfo.class);
		Map<String, String> map=info.getInfo();
		System.out.println(info.getCode());
		System.out.println(map.get("token"));
		
	}
}
