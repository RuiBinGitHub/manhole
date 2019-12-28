package com.springboot.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.springboot.biz.ProjectBiz;
import com.springboot.biz.ManholeBiz;
import com.springboot.biz.OperatorBiz;
import com.springboot.entity.Manhole;
import com.springboot.entity.Operator;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.MyHelper;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {

	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private ManholeBiz manholeBiz;
	@Resource
	private OperatorBiz operatorBiz;

	private Map<String, Object> map = null;
	private List<Operator> operators = null;
	private List<Manhole> manholes = null;

	/** 个人沙井列表 */
	@RequestMapping(value = "/showlist")
	public ModelAndView showList(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("project/showlist");
		User user = (User) MyHelper.findMap("user");
		map = MyHelper.getMap("state", "未提交", "page", page, "user", user);
		if (!StringUtils.isEmpty(name))
			map.put("name", name);
		PageInfo<Project> info = projectBiz.findListProject(map);
		view.addObject("proejcts", info.getList());
		view.addObject("cont", info.getPages());
		view.addObject("page", page);
		return view;
	}

	/** 公司沙井列表 */
	@RequestMapping(value = "/findlist")
	public ModelAndView findList(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("project/findlist");
		User user = (User) MyHelper.findMap("user");
		map = MyHelper.getMap("state", "已提交", "page", page, "company", user.getCompany());
		if (!StringUtils.isEmpty(name))
			map.put("name", name);
		PageInfo<Project> info = projectBiz.findListProject(map);
		view.addObject("proejcts", info.getList());
		view.addObject("cont", info.getPages());
		view.addObject("page", page);
		return view;
	}

	@RequestMapping(value = "/insertview")
	public ModelAndView insertView() {
		ModelAndView view = new ModelAndView("redirect:/failure");
		User user = (User) MyHelper.findMap("user");
		operators = operatorBiz.findListOperator(user.getCompany());
		view.setViewName("project/insert");
		view.addObject("operators", operators);
		return view;
	}

	@RequestMapping(value = "/updateview")
	public ModelAndView updateView(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("redirect:/failure");
		User user = (User) MyHelper.findMap("user");
		Project project = projectBiz.findInfoProject(id, user);
		if (StringUtils.isEmpty(project))
			return view;
		operators = operatorBiz.findListOperator(user.getCompany());
		view.setViewName("project/update");
		view.addObject("project", project);
		view.addObject("operators", operators);
		return view;
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView insert(Project project) {
		ModelAndView view = new ModelAndView();
		User user = (User) MyHelper.findMap("user");
		int id = projectBiz.appendProject(project, user);
		view.setViewName("redirect:editinfo?id=" + id);
		return view;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(Project project) {
		ModelAndView view = new ModelAndView();
		User user = (User) MyHelper.findMap("user");
		int id = projectBiz.replacProject(project, user);
		view.setViewName("redirect:editinfo?id=" + id);
		return view;
	}

	/** 提交数据 */
	@RequestMapping(value = "/submit")
	public boolean submit(@RequestParam(defaultValue = "0") int id) {
		User user = (User) MyHelper.findMap("user");
		Project project = projectBiz.findInfoProject(id, user);
		if (!StringUtils.isEmpty(project)) {
			project.setState("已提交");
			projectBiz.updateProject(project);
		}
		return true;
	}

	/** 撤回项目 */
	@RequestMapping(value = "/revoke")
	public boolean revoke(@RequestParam(defaultValue = "0") int id) {
		User user = (User) MyHelper.findMap("user");
		map = MyHelper.getMap("id", id, "company", user.getCompany());
		Project project = projectBiz.findInfoProject(map);
		if (!StringUtils.isEmpty(project)) {
			project.setState("未提交");
			projectBiz.updateProject(project);
		}
		return true;
	}

	/** 删除项目 */
	@RequestMapping(value = "/delete")
	public boolean delete(@RequestParam(defaultValue = "0") int id) {
		User user = (User) MyHelper.findMap("user");
		Project project = projectBiz.findInfoProject(id, user);
		if (!StringUtils.isEmpty(project))
			projectBiz.deleteProject(project);
		return true;
	}

	/** 移除项目 */
	@RequestMapping(value = "/remove")
	public boolean remove(@RequestParam(defaultValue = "0") int id) {
		User user = (User) MyHelper.findMap("user");
		map = MyHelper.getMap("id", id, "company", user.getCompany());
		Project project = projectBiz.findInfoProject(map);
		if (!StringUtils.isEmpty(project))
			projectBiz.deleteProject(project);
		return true;
	}

	/** 编辑数据 */
	@RequestMapping(value = "/editinfo")
	public ModelAndView findview(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) MyHelper.findMap("user");
		Project project = projectBiz.findInfoProject(id, user);
		if (StringUtils.isEmpty(project))
			return view;
		manholes = manholeBiz.findListManhole(project);
		view.setViewName("project/editinfo");
		view.addObject("project", project);
		view.addObject("manholes", manholes);
		return view;
	}

	/** 浏览数据 */
	@RequestMapping(value = "/findinfo")
	public ModelAndView viewInfo(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		Project project = projectBiz.findInfoProject(id, null);
		if (StringUtils.isEmpty(project))
			return view;
		manholes = manholeBiz.findListManhole(project);
		view.setViewName("project/findinfo");
		view.addObject("project", project);
		view.addObject("manholes", manholes);
		return view;
	}

}
