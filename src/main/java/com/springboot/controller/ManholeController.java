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
import com.springboot.biz.PipeBiz;
import com.springboot.entity.Manhole;
import com.springboot.entity.Pipe;
import com.springboot.util.AppHelper;

@RestController
@RequestMapping(value = "/manhole")
public class ManholeController {

	@Value("${myfile}")
	private String myfile;
	@Value("${mypath}")
	private String mypath;
	@Resource
	private ManholeBiz manholeBiz;
	@Resource
	private PipeBiz pipeBiz;

	private Map<String, Object> map = null;

	@RequestMapping(value = "/showlist")
	public ModelAndView showList(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("manhole/showlist");
		map = AppHelper.getMap("name", name);
		int cont = manholeBiz.getPage(map, 15);
		page = page > cont ? cont : page;
		map = AppHelper.getMap("name", name, "page", page);
		List<Manhole> manholes = manholeBiz.findListManhole(map);
		view.addObject("manholes", manholes);
		view.addObject("page", page);
		view.addObject("cont", cont);
		return view;
	}

	@RequestMapping(value = "/findlist")
	public ModelAndView findList(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("manhole/findlist");
		map = AppHelper.getMap("name", name);
		int cont = manholeBiz.getPage(map, 15);
		page = page > cont ? cont : page;
		map = AppHelper.getMap("name", name, "page", page);
		List<Manhole> manholes = manholeBiz.findListManhole(map);
		view.addObject("manholes", manholes);
		view.addObject("page", page);
		view.addObject("cont", cont);
		return view;
	}

	@RequestMapping(value = "/findinfo")
	public ModelAndView findInfo(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("user/failure");
		Manhole manhole = manholeBiz.findInfoManhole(id);
		if (StringUtils.isEmpty(manhole))
			return view;
		List<Pipe> pipes = pipeBiz.findListPipe(manhole);
		view.setViewName("manhole/findinfo");
		view.addObject("manhole", manhole);
		view.addObject("pipes", pipes);
		view.addObject("path", mypath);
		return view;
	}

	@RequestMapping(value = "/updateview")
	public ModelAndView updateView(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("user/failure");
		Manhole manhole = manholeBiz.findInfoManhole(id);
		if (StringUtils.isEmpty(manhole))
			return view;
		List<Pipe> pipes = pipeBiz.findListPipe(manhole);
		view.setViewName("manhole/update");
		view.addObject("manhole", manhole);
		view.addObject("pipes", pipes);
		view.addObject("path", mypath);
		return view;
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView insert(Manhole manhole, String type) {
		ModelAndView view = new ModelAndView();
		int id = manholeBiz.appendManhole(manhole);
		if ("1".contentEquals(type))
			view.setViewName("redirect:/item/editinfo?id=" + id);
		else
			view.setViewName("redirect:updateview?id=" + id);
		return view;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(Manhole manhole, String type) {
		ModelAndView view = new ModelAndView();
		int id = manholeBiz.replacManhole(manhole);
		if ("1".contentEquals(type))
			view.setViewName("redirect:/item/editinfo?id=" + id);
		else
			view.setViewName("redirect:updateview?id=" + id);
		return view;
	}

	@RequestMapping(value = "/delete")
	public boolean delete(@RequestParam(defaultValue = "0") int id) {
		Manhole manhole = manholeBiz.findInfoManhole(id);
		if (!StringUtils.isEmpty(manhole))
			manholeBiz.deleteManhole(manhole);
		return true;
	}

}
