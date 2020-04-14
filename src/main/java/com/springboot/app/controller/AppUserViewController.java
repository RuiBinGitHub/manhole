package com.springboot.app.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.biz.UserBiz;
import com.springboot.entity.User;
import com.springboot.util.JWTHelper;
import com.springboot.util.MyHelper;

@RestController
@RequestMapping(value = "/app/userview")
public class AppUserViewController {

	@Resource
	private UserBiz userBiz;

	private Map<String, Object> map = null;

	/** 用户登录 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String username, String password) {
		if (username == null || password == null)
			return null;
		map = MyHelper.getMap("username", username, "password", password);
		User user = userBiz.findInfoUser(map);
		if (StringUtils.isEmpty(user))
			return null;
		return JWTHelper.sign(user);
	}

	@RequestMapping(value = "/find")
	public String find(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader("token");
		System.out.println(JWTHelper.verify(token));
		System.out.println(JWTHelper.getClaim(token, "id"));
		System.out.println(JWTHelper.getClaim(token, "name"));
		return "000";
	}

}
