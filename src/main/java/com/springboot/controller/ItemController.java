package com.springboot.controller;

import java.io.File;
import java.io.IOException;
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
import com.springboot.util.AppHelper;

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
	// private Map<String, Object> map = null;

	@RequestMapping(value = "/editinfo")
	public ModelAndView editInfo(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("user/failure");
		Manhole manhole = manholeBiz.findInfoManhole(id);
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
		ModelAndView view = new ModelAndView("user/failure");
		Manhole manhole = manholeBiz.findInfoManhole(id);
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
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public boolean update(Manhole manhole, MultipartFile[] files) throws IOException {
		List<Item> items = manhole.getItems();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isEmpty())
				continue;
			String name = AppHelper.UUIDCode();
			File dest = new File(myfile + "ItemImage/" + name + ".png");
			files[i].transferTo(dest);
			if (i % 2 == 0)
				items.get(i / 2).setPath1(name);
			else
				items.get(i / 2).setPath2(name);
		}
		int no = 0;
		for (Item item : items) {
			item.setNo(no++);
			item.setManhole(manhole);
			if (item.getId() == 0)
				itemBiz.insertItem(item);
			else
				itemBiz.updateItem(item);
		}
		return true;
	}

	@RequestMapping(value = "/delete")
	public boolean delete(@RequestParam(defaultValue = "0") int id) {
		Item item = itemBiz.findInfoItem(id, null);
		if (!StringUtils.isEmpty(item))
			itemBiz.deleteItem(item);
		return true;
	}

}
