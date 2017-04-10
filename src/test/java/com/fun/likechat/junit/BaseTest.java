package com.fun.likechat.junit;

import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 请设置好要装载的spring配置文件,一般开发数据库与测试数据库分开
 * 所以你要装载的资源文件应改为"classpath:/spring/*-test-resource.xml"
 */
@RunWith(SpringJUnit4ClassRunner.class)  //让测试运行与spring环境
//@ContextConfiguration(locations = {"classpath:test-applicationContext.xml", "classpath:spring-mybatis.xml"})
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml", "classpath:mybatis-config.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)  // true：回滚数据
public abstract class BaseTest {
	protected MockHttpServletRequest request = new MockHttpServletRequest();
	protected MockHttpServletResponse response;
	protected MockHttpSession session;
    
    
}
