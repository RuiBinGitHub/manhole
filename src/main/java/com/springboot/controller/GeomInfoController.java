package com.springboot.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.biz.ItemBiz;
import com.springboot.biz.ManholeBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Manhole;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;

@RestController
@RequestMapping(value = "/geominfo")
public class GeomInfoController {

	@Value("${mypath}")
	private String mypath;

	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private ManholeBiz manholeBiz;
	@Resource
	private PipeBiz pipeBiz;
	@Resource
	private ItemBiz itemBiz;

	private Map<String, Object> map = null;

	@RequestMapping(value = "/showlist")
	public ModelAndView showlist() {
		ModelAndView view = new ModelAndView("geominfo/showlist");
		User user = (User) AppUtils.findMap("user");
		map = AppUtils.getMap("xy", "", "company", user.getCompany());
		List<Project> projects = projectBiz.mapListProject(user.getCompany());
		List<Manhole> manholes = manholeBiz.findListManhole(map);
		view.addObject("projects", projects);
		view.addObject("manholes", manholes);
		view.addObject("path", mypath);
		return view;
	}

	@RequestMapping(value = "/findinfo")
	public Manhole findinfo(@RequestParam(defaultValue = "0") int id) {
		Manhole manhole = manholeBiz.findInfoManhole(id, null);
		manhole.setPipes(pipeBiz.findListPipe(manhole));
		manhole.setItems(itemBiz.findListItem(manhole));
		return manhole;
	}
}
