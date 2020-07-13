package com.springboot;

import java.util.HashMap;
import java.util.Map;

public class MainTests {

	public static void main(String[] args) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("001", "张三");
		map.put("002", "张三");
		map.put("100", "张三");
		map.put("200", "张三");

//		map.forEach(new BiConsumer<String, String>() {
//			public void accept(String t, String u) {
//				System.out.println(k + v);
//			}
//		});

		map.forEach((String k, String v) -> {
			System.out.println(k + v);
		});
		
	}

}
