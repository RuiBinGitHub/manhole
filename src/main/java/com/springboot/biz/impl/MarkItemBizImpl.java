package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.biz.ManholeBiz;
import com.springboot.biz.MarkItemBiz;
import com.springboot.biz.MarkPipeBiz;
import com.springboot.biz.MessageBiz;
import com.springboot.dao.MarkItemDao;
import com.springboot.entity.Manhole;
import com.springboot.entity.MarkItem;
import com.springboot.entity.MarkPipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.MyHelper;

@Service
@Transactional
public class MarkItemBizImpl implements MarkItemBiz {

	@Resource
	private MarkItemDao markItemDao;
	@Resource
	private MarkPipeBiz markPipeBiz;
	@Resource
	private ManholeBiz manholeBiz;
	@Resource
	private MessageBiz messageBiz;

	private Map<String, Object> map = null;

	public void insertMarkItem(MarkItem markItem) {
		markItemDao.insertMarkItem(markItem);
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
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		if (!StringUtils.isEmpty(map.get("page")))
			PageHelper.startPage((int) map.get("page"), 15);

		List<MarkItem> markItems = markItemDao.findViewMarkItem(map);
		PageInfo<MarkItem> info = new PageInfo<MarkItem>(markItems);
		return info;
	}

	public PageInfo<MarkItem> findListMarkItem(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		if (!StringUtils.isEmpty(map.get("page")))
			PageHelper.startPage((int) map.get("page"), 15);
		List<MarkItem> markItems = markItemDao.findListMarkItem(map);
		PageInfo<MarkItem> info = new PageInfo<MarkItem>(markItems);
		return info;
	}

	public int appendMarkItem(Project project, User user) {
		MarkItem markItem = new MarkItem();
		markItem.setDate(MyHelper.getDate(null));
		markItem.setProject(project);
		markItem.setUser(user);
		insertMarkItem(markItem);
		List<Manhole> manholes = manholeBiz.findListManhole(project);
		for (Manhole manhole : manholes) {
			MarkPipe markPipe = new MarkPipe();
			markPipe.setScore(0);
			markPipe.setManhole(manhole);
			markPipe.setMarkItem(markItem);
			markPipeBiz.insertMarkPipe(markPipe);
		}
		messageBiz.sendMessage(markItem);
		return markItem.getId();
	}

}
