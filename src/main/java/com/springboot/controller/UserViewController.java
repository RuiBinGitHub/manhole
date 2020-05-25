package com.springboot.controller;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.springboot.bean.MailBean;
import com.springboot.biz.UserBiz;
import com.springboot.entity.User;
import com.springboot.util.MyHelper;

@RestController
@RequestMapping(value = "/userview")
public class UserViewController {

	@Resource
	private UserBiz userBiz;
	@Resource
	private MailBean mailBean;
	
	
	private Map<String, Object> map = null;
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

	/** 判断账号和邮箱 */
	@RequestMapping(value = "/checknamemail")
	public boolean checkNameMail(String username, String mail) {
		map = MyHelper.getMap("username", username, "email", mail);
		if (userBiz.findInfoUser(map) == null)
			return false;
		return true;
	}
	
	/** 重置密码 */
	@RequestMapping(value = "/resetpass", method = RequestMethod.POST)
	public ModelAndView resetpass(String name, String pass, String mail) {
		ModelAndView view = new ModelAndView("userview/resetpass");
		map = MyHelper.getMap("username", name, "email", mail);
		User user = userBiz.findInfoUser(map);
		if (StringUtils.isEmpty(user)) {
			view.addObject("tips", "*登录账号与邮箱不匹配！");
			view.addObject("name", name);
			view.addObject("mail", mail);
			return view;
		}
		view.setViewName("redirect:/completes");
		user.setPassword(pass);
		userBiz.updateUser(user);
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

	/** 发送电子邮件 */
	@RequestMapping(value = "/sendmail")
	public String sendmail(String mail) {
		String ireg = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$";
		Pattern pattern = Pattern.compile(ireg);
		Matcher matcher = pattern.matcher(mail);
		if (!matcher.matches())
			return "";
		int random = (int) (Math.random() * 899999);
		String code = String.valueOf(100000 + random);
		mailBean.sendMail(mail, code);
		return code;
	}
	
	/** 切换语言 */
	@RequestMapping(value = "/change")
	public boolean change(String lang) {
		MyHelper.pushMap("i18n", lang);
		System.out.println("0001");
		return true;
	}
	
}
