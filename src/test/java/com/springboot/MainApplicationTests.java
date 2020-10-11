package com.springboot;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.biz.PipeBiz;
import com.springboot.biz.UserBiz;
import com.springboot.util.MyHelper;

@SpringBootTest
public class MainApplicationTests {

	@Resource
	private PipeBiz pipeBiz;
	@Resource
	private UserBiz userBiz;
	
	@Test
	public void contextLoads() {
		Map<String, Object> map = MyHelper.getMap("id", "2");
		System.out.println(pipeBiz.findInfoPipe(map));
	}

}
