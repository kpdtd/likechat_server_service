package com.usefulfunctions.actor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.fun.likechat.service.ActorService;
import com.fun.likechat.util.HttpClient;
import com.fun.likechat.util.JsonHelper;
import com.usefulfunctions.BaseTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
// 按字母顺序执行method
public class ActorPicBatch extends BaseTest {

	@Autowired
	ActorService actorService;

	/**
	 * /Users/suntao/Documents/项目参考资料/网络主播图片信息.csv
	 * @throws SQLException
	 */
	@Test
	public void 批量添加主播信息() throws SQLException {
		// 1 批量读取文本；
		
	}

	public static Map<String, List>  StringDeal(String data) {
		Map<String, List> map = new HashMap<String, List>();
		String[] t_a = data.split(",");
		System.out.println(t_a[0]);
		String[] add = t_a[1].split("@");
		map.put(t_a[0], Arrays.asList(add));
		 return map;
	}

	public static void main(String[] args) {
		try {
			// read file content from file
			StringBuffer sb = new StringBuffer("");
			FileReader reader = new FileReader("/Users/suntao/Documents/项目参考资料/网络主播图片信息-11.csv");
			BufferedReader br = new BufferedReader(reader);
			String str = null;
			Map<String, List> map = new HashMap<String, List>();
			while((str = br.readLine()) != null) {
				// sb.append(str+"/n");
				System.out.println(str);
				map = StringDeal(str) ;//每次返回一个新map，只有一个主播元素
				try {
					String result=HttpClient.postRequest("http://114.215.221.15:8080/likechat_server_service/useful/batchAddActor", JsonHelper.toJson(map));
//					String result=HttpClient.postRequest("http://localhost:8080/likechat_server_service/useful/batchAddActor", JsonHelper.toJson(map));
					System.out.println("result:"+result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			br.close();
			reader.close();
			//插入动态数据表
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
//	public static void main(String[] args) {
//		String info = "猫女郎,http://www.192tt.com/d/file/p/2016-11-30/5hte2hdybmz2801.jpg@http://www.192tt.com/d/file/p/2016-11-30/r4ndlv0amul2802.jpg@http://www.192tt.com/d/file/p/2016-11-30/yymokecnfpj2803.jpg@http://www.192tt.com/d/file/p/2016-11-30/rnnveeoqenq2804.jpg@http://www.192tt.com/d/file/p/2016-11-30/gayhbwzvhpk2805.jpg@http://www.192tt.com/d/file/p/2016-11-30/wwzlifmhhyo2806.jpg@http://www.192tt.com/d/file/p/2016-11-30/bkp4nzulukq2807.jpg@http://www.192tt.com/d/file/p/2016-11-30/emxftwuuf2o2808.jpg@http://www.192tt.com/d/file/p/2016-11-30/stwprvanpzs2809.jpg@http://www.192tt.com/d/file/p/2016-11-30/irrddwirhjn2810.jpg@http://www.192tt.com/d/file/p/2016-11-30/o5eh1mcrgql2811.jpg@http://www.192tt.com/d/file/p/2016-11-30/sobq1lzntpl2812.jpg@http://www.192tt.com/d/file/p/2016-11-30/1lj14s0y3cg2813.jpg@http://www.192tt.com/d/file/p/2016-11-30/eslvcbidpt02814.jpg@http://www.192tt.com/d/file/p/2016-11-30/zf421cbpfzq2815.jpg@http://www.192tt.com/d/file/p/2016-11-30/jsj4ppdbyci2816.jpg@http://www.192tt.com/d/file/p/2016-11-30/a24hsxfdldk2817.jpg@";
//		String[] t_a = info.split(",");
//		System.out.println(t_a[0]);
//		String[] add = t_a[1].split("@");
//		for(String s : add) {
//			System.out.println(s);
//		}
//	}
}
