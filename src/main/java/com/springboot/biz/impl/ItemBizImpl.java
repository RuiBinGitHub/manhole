package com.springboot.biz.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.biz.ItemBiz;
import com.springboot.dao.ItemDao;
import com.springboot.entity.Item;
import com.springboot.entity.Manhole;
import com.springboot.entity.User;
import com.springboot.util.MyHelper;

@Service
public class ItemBizImpl implements ItemBiz {

	
	private static final Logger log = LoggerFactory.getLogger(ItemBizImpl.class);

	
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

	public Item findInfoItem(int id, User user) {
		map = MyHelper.getMap("id", id, "user", user);
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
		item.setPhoto1(manhole.getPhotono1());
		item.setPhoto2(manhole.getPhotono2());
		item.setLocation1(manhole.getLocation());
		item.setLocation2(manhole.getLocation());
		item.setExplain1("Manhole Location");
		item.setExplain2("Manhole Internal Photo");
		item.setManhole(manhole);
		items.add(item);
		return items;
	}

	public void replacItem(Manhole manhole, MultipartFile[] files) {
		List<Item> items = manhole.getItems();
		String path = myfile + "/ItemImage/";
		log.info(files.length + "");
		for (int i = 0; i < files.length; i++) {
			if (files[i].isEmpty())
				continue;
			String name = MyHelper.UUIDCode();
			File dest = new File(path + name + ".png");
			MyHelper.moveFile(files[i], dest);
			if (i % 2 == 0)
				items.get(i / 2).setPath1(name);
			else
				items.get(i / 2).setPath2(name);
		}
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			item.setNo(i);
			item.setManhole(manhole);
			if (item.getId() == 0)
				insertItem(item);
			else
				updateItem(item);
		}
	}

}
