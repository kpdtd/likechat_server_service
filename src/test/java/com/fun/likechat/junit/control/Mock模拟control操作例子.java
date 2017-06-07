package com.fun.likechat.junit.control;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fun.likechat.controller.HomePageController;
import com.fun.likechat.junit.BaseTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
// 按字母顺序执行method
public class Mock模拟control操作例子 extends BaseTest {
	MockMvc mockMvc;
	
	@Autowired  
    private HomePageController homePageController;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(homePageController).build(); 
	}

	/**
	 * 例子
	 * http://jiuyuehe.iteye.com/blog/1882424
	 */
	@Test
	public void getHomePageContentTest() {
		MvcResult result = null;
        try {
	        result = mockMvc.perform(
	        				MockMvcRequestBuilders.get("/home/getHomePageContent"))//构造一个请求
	        				
//	        				.andExpect(MockMvcResultMatchers.view().name("user/view"))//ResultActions.andExpect添加执行完成后的断言
//	        				.andExpect(MockMvcResultMatchers.model().attributeExists("user"))
	        				.andDo(MockMvcResultHandlers.print())//添加一个结果处理器，表示要对结果做点什么事情，比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
	        				.andReturn();//表示执行完成后返回相应的结果。
	        
	        System.out.println("content:"+result.getResponse().getContentAsString());
        }
        catch(Exception e) {
	        e.printStackTrace();
        }

//		Assert.assertNotNull(result.getModelAndView().getModel().get("user"));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
