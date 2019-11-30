package com.springboot.biz.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.springboot.biz.ItemBiz;
import com.springboot.dao.ItemDao;
import com.springboot.entity.Item;
import com.springboot.entity.Manhole;
import com.springboot.util.MyHelper;

@Service
public class ItemBizImpl implements ItemBiz {

	@Value(value = "${myfile}")
	private String myfile;

	@Resource
	private ItemDao itemDao;
	private Map<String, Object> map = null;

	public void insertItem(Item item) {
		itemDao.insertItem(item);
	}

	public void updateItem(Item item) {
		itemDao.updateItem(item);
	}

	public void deleteItem(Item item) {
		itemDao.deleteItem(item);
		String path = myfile + "/ItemImage/";
		if (!StringUtils.isEmpty(item.getPath1())) {
			File file = new File(path + item.getPath1());
			file.delete();
		}
		if (!StringUtils.isEmpty(item.getPath2())) {
			File file = new File(path + item.getPath2());
			file.delete();
		}
	}

	public Item findInfoItem(int id, String name) {
		map = MyHelper.getMap("id", id, "name", name);
		return itemDao.findInfoItem(map);
	}

	public List<Item> findListItem(Manhole manhole) {
		map = MyHelper.getMap("manhole", manhole);
		return itemDao.findListItem(map);
	}

	public List<Item> appendItem(Manhole manhole) {
		List<Item> items = new ArrayList<Item>();
		Item item = new Item();
		item.setNo(0);
		item.setManhole(manhole);
		itemDao.insertItem(item);
		items.add(item);
		return items;
	}

}
