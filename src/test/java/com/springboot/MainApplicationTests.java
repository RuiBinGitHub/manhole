package com.springboot;

import java.util.List;
import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.biz.MarkItemBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.entity.Manhole;
import com.springboot.entity.Pipe;

@SpringBootTest
public class MainApplicationTests {

	@Resource
	private MarkItemBiz markItemBiz;
	@Resource
	private PipeBiz pipeBiz;

	@Test
	void contextLoads() {
		Manhole manhole = null;
		List<Pipe> pipes = pipeBiz.findListPipe(manhole);
		System.out.println(pipes);
		System.out.println("--");
	}

}
