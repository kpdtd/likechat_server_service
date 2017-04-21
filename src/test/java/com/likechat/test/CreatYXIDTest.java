package com.likechat.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fun.likechat.yunxin.data.CreateIdReqData;
import com.fun.likechat.yunxin.data.CreateIdResData;
import com.fun.likechat.yunxin.im.YxIdAction;

@RunWith(SpringJUnit4ClassRunner.class)
public class CreatYXIDTest extends BaseTest {

	@Autowired
	YxIdAction creatYXID;
	@Test
	public void testCreateYXId() {
		CreateIdReqData data=new CreateIdReqData();
		data.setAccid("test002");
		try {
			CreateIdResData createIdResData=creatYXID.createYXId(data);
			System.out.println(createIdResData.getToken());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
