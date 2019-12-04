package com.springboot.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.springboot.bean.MyRealm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

	@Resource
	private MyRealm myRealm;

	private SecurityManager manager = null;
	private ShiroFilterFactoryBean factoryBean = null;

	
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
		manager = new DefaultWebSecurityManager(myRealm);
		factoryBean = new ShiroFilterFactoryBean();
		// 设置安全管理器
		factoryBean.setSecurityManager(manager);
		Map<String, String> filterMap = new HashMap<>();
		filterMap.put("/userview/**", "anon");
		filterMap.put("/company/**", "roles[role1]"); // 公司操作
		
		filterMap.put("/manhole/**", "roles[role2]"); // 沙井操作
		filterMap.put("/operator/**", "roles[role2]"); // 沙井操作
		filterMap.put("/markinfo/**", "roles[role4]"); // 评分操作
		
		filterMap.put("/userinfo/update", "roles[role2]"); // 更新人员
		filterMap.put("/userinfo/center", "roles[role4]"); // 个人中心
		
		factoryBean.setFilterChainDefinitionMap(filterMap);
		// 配置跳转的登录页面
		factoryBean.setLoginUrl("/userview/loginview");
		// 设置未授权提示页面
		factoryBean.setUnauthorizedUrl("/authorize");
		return factoryBean;
	}
	
	/** 定义识图控制器 */
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/operator/insertview").setViewName("operator/insert");
		registry.addViewController("/userinfo/center").setViewName("userinfo/center");
		
		registry.addViewController("**/authorize").setViewName("userview/authorize");
		registry.addViewController("**/loginview").setViewName("userview/loginview");
		registry.addViewController("**/success").setViewName("userview/success");
		registry.addViewController("**/failure").setViewName("userview/failure");
	}
	
	@Bean
	public ShiroDialect shiroDialect() {
		ShiroDialect dialect = new ShiroDialect();
		return dialect;
	}

	@Bean
	public LocaleResolver localeResolver() {
		LocaleResolver resolver = new MyLocaleResolver();
		return resolver;
	}
}
