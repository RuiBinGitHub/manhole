package com.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

	/** 定义识图控制器 */
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/manhole/insertview").setViewName("manhole/insert");
	}

}
