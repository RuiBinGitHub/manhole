package com.springboot.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.biz.ItemBiz;
import com.springboot.biz.ManholeBiz;
import com.springboot.entity.Item;
import com.springboot.entity.Manhole;
import com.springboot.entity.User;
import com.springboot.util.MyHelper;

@RestController
@RequestMapping(value = "/item")
public class ItemController {

	@Value(value = "${myfile}")
	private String myfile;
	@Value(value = "${mypath}")
	private String mypath;

	@Resource
	private ManholeBiz manholeBiz;
	@Resource
	private ItemBiz itemBiz;

	@RequestMapping(value = "/editinfo")
	public ModelAndView editInfo(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) MyHelper.findMap("user");
		Manhole manhole = manholeBiz.findInfoManhole(id, user);
		if (StringUtils.isEmpty(manhole))
			return view;
		List<Item> items = itemBiz.findListItem(manhole);
		if (items == null || items.size() == 0)
			items = itemBiz.appendItem(manhole);
		view.setViewName("iteminfo/editinfo");
		view.addObject("manhole", manhole);
		view.addObject("items", items);
		view.addObject("path", mypath);
		return view;
	}

	@RequestMapping(value = "/findinfo")
	public ModelAndView findInfo(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		Manhole manhole = manholeBiz.findInfoManhole(id, null);
		if (StringUtils.isEmpty(manhole))
			return view;
		List<Item> items = itemBiz.findListItem(manhole);
		if (items == null || items.size() == 0)
			items = itemBiz.appendItem(manhole);
		view.setViewName("iteminfo/findinfo");
		view.addObject("manhole", manhole);
		view.addObject("items", items);
		view.addObject("path", mypath);
		return view;
	}

	@RequestMapping(value = "/commit", method = RequestMethod.POST)
	public ModelAndView commit(Manhole manhole, MultipartFile[] files) {
		ModelAndView view = new ModelAndView("redirect:/success");
		itemBiz.replacItem(manhole, files);
		return view;
	}

	@RequestMapping(value = "/delete")
	public boolean delete(@RequestParam(defaultValue = "0") int id) {
		User user = (User) MyHelper.findMap("user");
		Item item = itemBiz.findInfoItem(id, user);
		if (!StringUtils.isEmpty(item))
			itemBiz.deleteItem(item);
		return true;
	}

}
