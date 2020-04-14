package com.springboot.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.biz.UserBiz;
import com.springboot.entity.User;
import com.springboot.util.MyHelper;

@RestController
@RequestMapping(value = "/userview")
public class UserViewController {

	@Resource
	private UserBiz userBiz;

	// private Map<String, Object> map = null;
	private UsernamePasswordToken token = null;

	/** 用户登录 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(String username, String password) {
		ModelAndView view = new ModelAndView("userview/loginview");
		try {
			token = new UsernamePasswordToken(username, password);
			SecurityUtils.getSubject().login(token);
		} catch (IncorrectCredentialsException e) {
			view.addObject("tips", "*登录账号或密码不正确！");
			view.addObject("username", username);
			view.addObject("password", password);
			return view;
		}
		User user = (User) MyHelper.findMap("user");
		SecurityUtils.getSubject().getSession().setTimeout(-2000);
		SavedRequest location = WebUtils.getSavedRequest(MyHelper.getRequest());
		if (!StringUtils.isEmpty(location)) {
			String path = location.getRequestUrl();
			view.setViewName("redirect:" + path.substring(7, path.length()));
		} else if ("role1".equals(user.getRole()))
			view.setViewName("redirect:/company/showlist");
		else
			view.setViewName("redirect:/project/showlist");
		return view;
	}

	/** 退出登录 */
	@RequestMapping(value = "/leave")
	public ModelAndView leave() {
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:loginview");
		SecurityUtils.getSubject().logout();
		MyHelper.removeMap("user");
		return view;
	}

	/** 切换语言 */
	@RequestMapping(value = "/change")
	public boolean change(String lang) {
		MyHelper.pushMap("i18n", lang);
		System.out.println("0001");
		return true;
	}
	
}
