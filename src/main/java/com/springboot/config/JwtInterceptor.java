package com.springboot.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.springboot.util.JWTHelper;

public class JwtInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		try {
			String token = request.getHeader("token");
			return JWTHelper.verify(token);
		} catch (Exception e) {
			return false;
		}
	}
}
