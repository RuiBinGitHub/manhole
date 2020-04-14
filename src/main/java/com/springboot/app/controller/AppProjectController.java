package com.springboot.app.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.springboot.biz.ManholeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.biz.UserBiz;
import com.springboot.entity.Manhole;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.JWTHelper;
import com.springboot.util.MyHelper;

@RestController
@RequestMapping(value = "/app/project")
public class AppProjectController {

	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private ManholeBiz manholeBiz;
	@Resource
	private UserBiz userBiz;

	private Map<String, Object> map = null;

	@RequestMapping(value = "/showlist")
	public List<Project> showList(String name,@RequestParam(defaultValue = "1") int page) {
		HttpServletRequest request = MyHelper.getRequest();
		String token = request.getHeader("token");
		map = MyHelper.getMap("id", JWTHelper.getClaim(token, "id"));
		User user = userBiz.findInfoUser(map);
		map = MyHelper.getMap("state", "未提交", "page", page, "user", user);
		if (!StringUtils.isEmpty(name))
			map.put("name", name);
		PageInfo<Project> info = projectBiz.findListProject(map);
		return info.getList();
	}
	
	@RequestMapping(value = "/insert")
	public int insert(Project project) {
		HttpServletRequest request = MyHelper.getRequest();
		String token = request.getHeader("token");
		map = MyHelper.getMap("id", JWTHelper.getClaim(token, "id"));
		User user = userBiz.findInfoUser(map);
		project.setState("未提交");
		project.setDate(MyHelper.getDate(null));
		project.setUser(user);
		projectBiz.insertProject(project);
		return project.getId();
	}

	/** 编辑数据 */
	@RequestMapping(value = "/editinfo")
	public Project findview(@RequestParam(defaultValue = "0") int id) {
		HttpServletRequest request = MyHelper.getRequest();
		String token = request.getHeader("token");
		map = MyHelper.getMap("id", JWTHelper.getClaim(token, "id"));
		User user = userBiz.findInfoUser(map);
		Project project = projectBiz.findInfoProject(id, user);
		if (StringUtils.isEmpty(project))
			return null;
		List<Manhole> manholes = manholeBiz.findListManhole(project);
		project.setManholes(manholes);
		return project;
	}

}
