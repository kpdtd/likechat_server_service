package com.fun.likechat.junit.control;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.fun.likechat.util.HttpClient;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HomePageControllerTest {

	@Test
	public void a_getHomePageContent() throws IOException {
		String result = HttpClient.postRequest("http://localhost:8080/likechat_server_service/home/getHomePageContent", "");
		System.out.println("result:" + result);
	}
	
	@Test
	public void b_getActorListByTag() throws IOException {
		String json = "{\"identifying\":\"1\"}";
		String result = HttpClient.postRequest("http://localhost:8080/likechat_server_service/home/getActorListByTag", json);
		System.out.println("result:" + result);
	}
	
	@Test
	public void c_getActorPage() throws IOException {
		String json = "{\"identifying\":1}";
		String result = HttpClient.postRequest("http://localhost:8080/likechat_server_service/home/getActorPage", json);
		System.out.println("result:" + result);
	}
	
	@Test
	public void d_addAttention() throws IOException {
		String json = "{\"userId\":1,\"actorId\":1}";
		String result = HttpClient.postRequest("http://localhost:8080/likechat_server_service/home/addAttention", json);
		System.out.println("result:" + result);
	}
	
	@Test
	public void e_cancelAttention() throws IOException {
		String json = "{\"userId\":1,\"actorId\":1}";
		String result = HttpClient.postRequest("http://localhost:8080/likechat_server_service/home/cancelAttention", json);
		System.out.println("result:" + result);
	}
	


}
