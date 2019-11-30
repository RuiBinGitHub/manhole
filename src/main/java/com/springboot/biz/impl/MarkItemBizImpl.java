package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.biz.MarkItemBiz;
import com.springboot.dao.MarkItemDao;
import com.springboot.entity.MarkItem;
import com.springboot.entity.User;
import com.springboot.util.MyHelper;

@Service
@Transactional
public class MarkItemBizImpl implements MarkItemBiz {

	@Resource
	private MarkItemDao markItemDao;

	private Map<String, Object> map = null;

	public void insertMarkItem(MarkItem MarkItem) {
		markItemDao.insertMarkItem(MarkItem);
	}

	public void updateMarkItem(MarkItem markItem) {
		markItemDao.updateMarkItem(markItem);
	}

	public void deleteMarkItem(MarkItem MarkItem) {
		markItemDao.deleteMarkItem(MarkItem);
	}

	public MarkItem findInfoMarkItem(int id, User user) {
		map = MyHelper.getMap("id", id, "user", user);
		return markItemDao.findInfoMarkItem(map);
	}

	public MarkItem findInfoMarkItem(Map<String, Object> map) {
		return markItemDao.findInfoMarkItem(map);
	}

	public PageInfo<MarkItem> findViewMarkItem(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("page")))
			PageHelper.startPage((int) map.get("page"), 15);
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		List<MarkItem> markItems = markItemDao.findViewMarkItem(map);
		PageInfo<MarkItem> info = new PageInfo<MarkItem>(markItems);
		return info;
	}

	public PageInfo<MarkItem> findListMarkItem(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("page")))
			PageHelper.startPage((int) map.get("page"), 15);
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		List<MarkItem> markItems = markItemDao.findListMarkItem(map);
		PageInfo<MarkItem> info = new PageInfo<MarkItem>(markItems);
		return info;
	}

}
