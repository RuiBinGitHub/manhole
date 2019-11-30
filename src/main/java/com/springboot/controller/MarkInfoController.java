package com.springboot.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.springboot.biz.ItemBiz;
import com.springboot.biz.ManholeBiz;
import com.springboot.biz.MarkItemBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.entity.Item;
import com.springboot.entity.Manhole;
import com.springboot.entity.MarkItem;
import com.springboot.entity.Pipe;
import com.springboot.entity.User;
import com.springboot.util.MyHelper;

@RestController
@RequestMapping(value = "/markinfo")
public class MarkInfoController {

	@Value("${mypath}")
	private String mypath;
	
	@Resource
	private MarkItemBiz markItemBiz;
	@Resource
	private ManholeBiz manholeBiz;
	@Resource
	private PipeBiz pipeBiz;
	@Resource
	private ItemBiz itemBiz;
	
	
	private Map<String, Object> map = null;
	private PageInfo<MarkItem> info = null;

	/** 沙井列表 */
	@RequestMapping(value = "/markview")
	public ModelAndView markView(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("markinfo/markview");
		User user = (User) MyHelper.findMap("user");
		map = MyHelper.getMap("user", user, "company", user.getCompany());
		if (!StringUtils.isEmpty(name))
			map.put("name", name);
		info = markItemBiz.findViewMarkItem(map);
		view.addObject("markItems", info.getList());
		view.addObject("cont", info.getPages());
		view.addObject("page", page);
		return view;
	}

	/** 评分列表 */
	@RequestMapping(value = "/showlist")
	public ModelAndView showList(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("markinfo/showlist");
		User user = (User) MyHelper.findMap("user");
		map = MyHelper.getMap("user", user, "page", page);
		if (!StringUtils.isEmpty(name))
			map.put("name", name);
		info = markItemBiz.findListMarkItem(map);
		view.addObject("markItems", info.getList());
		view.addObject("cont", info.getPages());
		view.addObject("page", page);
		return view;
	}

	/** 评分列表 */
	@RequestMapping(value = "/findlist")
	public ModelAndView findList(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("markinfo/findlist");
		User user = (User) MyHelper.findMap("user");
		map = MyHelper.getMap("company", user.getCompany(), "page", page);
		if (!StringUtils.isEmpty(name))
			map.put("name", name);
		info = markItemBiz.findListMarkItem(map);
		view.addObject("markItems", info.getList());
		view.addObject("cont", info.getPages());
		view.addObject("page", page);
		return view;
	}

	/** 项目评分列表 */
	@RequestMapping(value = "/marklist")
	public List<MarkItem> markList(@RequestParam(defaultValue = "0") int id) {
		User user = (User) MyHelper.findMap("user");
		map = MyHelper.getMap("id", id, "user", user);
		info = markItemBiz.findListMarkItem(map);
		return info.getList();
	}
	
	@RequestMapping(value = "/editinfo")
	public ModelAndView editInfo(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("markinfo/editinfo");
		Manhole manhole = manholeBiz.findInfoManhole(6, null);
		List<Pipe> pipes = pipeBiz.findListPipe(manhole);
		List<Item> items = itemBiz.findListItem(manhole);
		view.addObject("manhole", manhole);
		view.addObject("pipes", pipes);
		view.addObject("items", items);
		view.addObject("path", mypath);
		return view;
	}
}
