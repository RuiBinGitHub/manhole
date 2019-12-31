package com.springboot;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.biz.ManholeBiz;
import com.springboot.biz.MarkItemBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.entity.Manhole;
import com.springboot.entity.Project;

@SpringBootTest
public class MainApplicationTests {

	@Resource
	private ManholeBiz manholeBiz;
	@Resource
	private MarkItemBiz markItemBiz;
	@Resource
	private PipeBiz pipeBiz;

	@Test
	void contextLoads() {
		Project project = new Project();
		project.setId(6);
		Manhole manhole = manholeBiz.findLastManhole(project);
		System.out.println(manhole.getId());
	}

}
