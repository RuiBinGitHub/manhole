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

import com.springboot.biz.ManholeBiz;
import com.springboot.biz.OperatorBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Manhole;
import com.springboot.entity.Operator;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
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
	private ProjectBiz projectBiz;
	@Resource
	private ManholeBiz manholeBiz;
	@Resource
	private PipeBiz pipeBiz;

	private Map<String, Object> map = null;
	private List<Operator> operators = null;
	private List<Pipe> pipes = null;

	/** 编辑数据 */
	@RequestMapping(value = "/editinfo")
	public ModelAndView editInfo(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) MyHelper.findMap("user");
		Manhole manhole = manholeBiz.findInfoManhole(id, user);
		if (id != 0 && StringUtils.isEmpty(manhole))
			return view;
		operators = operatorBiz.findListOperator(user.getCompany());
		pipes = pipeBiz.findListPipe(manhole);
		view.setViewName("manhole/editinfo");
		view.addObject("manhole", manhole);
		view.addObject("pipes", pipes);
		view.addObject("operators", operators);
		view.addObject("path", mypath);
		return view;
	}

	/** 浏览数据 */
	@RequestMapping(value = "/findinfo")
	public ModelAndView findInfo(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) MyHelper.findMap("user");
		Manhole manhole = manholeBiz.findInfoManhole(id, null);
		if (id != 0 && StringUtils.isEmpty(manhole))
			return view;
		operators = operatorBiz.findListOperator(user.getCompany());
		pipes = pipeBiz.findListPipe(manhole);
		view.setViewName("manhole/findinfo");
		view.addObject("manhole", manhole);
		view.addObject("pipes", pipes);
		view.addObject("operators", operators);
		view.addObject("path", mypath);
		return view;
	}

	@RequestMapping(value = "/insert")
	public ModelAndView insert(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) MyHelper.findMap("user");
		Project project = projectBiz.findInfoProject(id, user);
		if (StringUtils.isEmpty(project))
			return view;
		operators = operatorBiz.findListOperator(user.getCompany());
		Manhole manhole = new Manhole();
		Manhole temp = manholeBiz.findLastManhole(project);
		if (StringUtils.isEmpty(temp)) {
			manhole.setProjectno(project.getName());
			manhole.setSurveyname(project.getOperator());
			manhole.setSurveydate(project.getDatetime1());
		} else {
			manhole.setAreacode(temp.getAreacode());
			manhole.setSurveyname(temp.getSurveyname());
			manhole.setSurveydate(temp.getSurveydate());
			manhole.setProjectno(temp.getProjectno());
			manhole.setWorkorder(temp.getWorkorder());
			manhole.setLocation(temp.getLocation());
		}
		manhole.setCond("N");
		manhole.setCrit("N");
		manhole.setCtype("N,N,N,N,N");
		manhole.setWith1("Y");
		manhole.setWith2("Y");
		manhole.setWith3("Y");
		manhole.setProject(project);
		view.setViewName("manhole/editinfo");
		view.addObject("manhole", manhole);
		view.addObject("operators", operators);
		view.addObject("path", mypath);
		return view;
	}

	/** 保存数据 */
	@RequestMapping(value = "/commit", method = RequestMethod.POST)
	public ModelAndView findInfo(Manhole manhole, String type) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) MyHelper.findMap("user");
		int id = manholeBiz.replacManhole(manhole, user);
		System.out.println("**" + manhole.getIron());
		if ("next".equals(type))
			view.setViewName("redirect:/item/editinfo?id=" + id);
		else
			view.setViewName("redirect:editinfo?id=" + id);
		return view;
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
