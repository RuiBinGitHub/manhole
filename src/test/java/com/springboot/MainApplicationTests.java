package com.springboot;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.pagehelper.PageInfo;
import com.springboot.biz.MarkItemBiz;
import com.springboot.entity.MarkItem;
import com.springboot.util.MyHelper;

@SpringBootTest
public class MainApplicationTests {

	@Resource
	private MarkItemBiz markItemBiz;

	private Map<String, Object> map = null;

	@Test
	void contextLoads() {
		map = MyHelper.getMap("size", "");
		PageInfo<MarkItem> info = markItemBiz.findViewMarkItem(map);
		List<MarkItem> markItems = info.getList();
		if (markItems != null) {
			System.out.println(markItems.size());
			for (MarkItem markItem : markItems) {
				System.out.println(markItem.getId());
				System.out.print(markItem.getManhole().getId());
				System.out.print(markItem.getManhole().getProjectno());
			}
		}
		System.out.println("--");
	}

}
