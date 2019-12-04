package com.springboot.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.springboot.biz.ManholeBiz;
import com.springboot.biz.OperatorBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.entity.Manhole;
import com.springboot.entity.Pipe;
import com.springboot.entity.User;
import com.springboot.util.MyHelper;

@RestController
@RequestMapping(value = "/manhole")
public class ManholeController {

	@Value("${myfile}")
	private String myfile;
	@Value("${mypath}")
	private String mypath;

	@Resource
	private OperatorBiz operatorBiz;
	@Resource
	private ManholeBiz manholeBiz;
	@Resource
	private PipeBiz pipeBiz;

	private Map<String, Object> map = null;
	private List<String> names = null;
	private List<Pipe> pipes = null;

	/** 个人沙井列表 */
	@RequestMapping(value = "/showlist")
	public ModelAndView showList(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("manhole/showlist");
		User user = (User) MyHelper.findMap("user");
		map = MyHelper.getMap("state", "未提交", "user", user, "page", page);
		if (!StringUtils.isEmpty(name))
			map.put("name", name);
		PageInfo<Manhole> info = manholeBiz.findListManhole(map);
		view.addObject("manholes", info.getList());
		view.addObject("cont", info.getPages());
		view.addObject("page", page);
		return view;
	}

	/** 公司沙井列表 */
	@RequestMapping(value = "/findlist")
	public ModelAndView findList(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("manhole/findlist");
		User user = (User) MyHelper.findMap("user");
		map = MyHelper.getMap("state", "已提交", "company", user.getCompany(), "page", page);
		if (!StringUtils.isEmpty(name))
			map.put("name", name);
		PageInfo<Manhole> info = manholeBiz.findListManhole(map);
		view.addObject("manholes", info.getList());
		view.addObject("cont", info.getPages());
		view.addObject("page", page);
		return view;
	}

	/** 编辑数据 */
	@RequestMapping(value = "/editinfo")
	public ModelAndView findview(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) MyHelper.findMap("user");
		Manhole manhole = manholeBiz.findInfoManhole(id, user);
		if (id != 0 && StringUtils.isEmpty(manhole))
			return view;
		names = operatorBiz.findListName(user.getCompany());
		pipes = pipeBiz.findListPipe(manhole);
		view.setViewName("manhole/editinfo");
		view.addObject("manhole", manhole);
		view.addObject("pipes", pipes);
		view.addObject("names", names);
		view.addObject("path", mypath);
		return view;
	}

	/** 浏览数据 */
	@RequestMapping(value = "/findinfo")
	public ModelAndView viewInfo(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) MyHelper.findMap("user");
		Manhole manhole = manholeBiz.findInfoManhole(id, null);
		if (id != 0 && StringUtils.isEmpty(manhole))
			return view;
		names = operatorBiz.findListName(user.getCompany());
		pipes = pipeBiz.findListPipe(manhole);
		view.setViewName("manhole/findinfo");
		view.addObject("manhole", manhole);
		view.addObject("pipes", pipes);
		view.addObject("names", names);
		view.addObject("path", mypath);
		return view;
	}

	/** 保存数据 */
	@RequestMapping(value = "/commit", method = RequestMethod.POST)
	public ModelAndView findInfo(Manhole manhole, String type) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) MyHelper.findMap("user");
		int id = manholeBiz.replacManhole(manhole, user);
		if ("next".equals(type))
			view.setViewName("redirect:/item/editinfo?id=" + id);
		else
			view.setViewName("redirect:editinfo?id=" + id);
		return view;
	}

	/** 提交数据 */
	@RequestMapping(value = "/submit")
	public boolean submit(@RequestParam(defaultValue = "0") int id) {
		User user = (User) MyHelper.findMap("user");
		Manhole manhole = manholeBiz.findInfoManhole(id, user);
		if (!StringUtils.isEmpty(manhole)) {
			manhole.setState("已提交");
			manholeBiz.updateManhole(manhole);
		}
		return true;
	}

	/** 撤回项目 */
	@RequestMapping(value = "/revoke")
	public boolean revoke(@RequestParam(defaultValue = "0") int id) {
		User user = (User) MyHelper.findMap("user");
		map = MyHelper.getMap("id", id, "company", user.getCompany());
		Manhole manhole = manholeBiz.findInfoManhole(map);
		if (!StringUtils.isEmpty(manhole)) {
			manhole.setState("未提交");
			manholeBiz.updateManhole(manhole);
		}
		return true;
	}

	/** 删除项目 */
	@RequestMapping(value = "/delete")
	public boolean delete(@RequestParam(defaultValue = "0") int id) {
		User user = (User) MyHelper.findMap("user");
		Manhole manhole = manholeBiz.findInfoManhole(id, user);
		if (!StringUtils.isEmpty(manhole))
			manholeBiz.deleteManhole(manhole);
		return true;
	}

	/** 移除项目 */
	@RequestMapping(value = "/remove")
	public boolean remove(@RequestParam(defaultValue = "0") int id) {
		User user = (User) MyHelper.findMap("user");
		map = MyHelper.getMap("id", id, "company", user.getCompany());
		Manhole manhole = manholeBiz.findInfoManhole(map);
		if (!StringUtils.isEmpty(manhole))
			manholeBiz.deleteManhole(manhole);
		return true;
	}

}
