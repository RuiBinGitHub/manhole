package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.springboot.biz.ManholeBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.dao.ManholeDao;
import com.springboot.entity.Manhole;
import com.springboot.entity.Pipe;
import com.springboot.util.AppHelper;

@Service
@Transactional
public class ManholeBizImpl implements ManholeBiz {

	@Value("${myfile}")
	private String myfile;

	@Resource
	private ManholeDao manholeDao;
	@Resource
	private PipeBiz pipeBiz;

	private Map<String, Object> map = null;

	public void insertManhole(Manhole manhole) {
		manholeDao.insertManhole(manhole);
	}

	public void updateManhole(Manhole manhole) {
		manholeDao.updateManhole(manhole);
	}

	public void deleteManhole(Manhole manhole) {
		manholeDao.deleteManhole(manhole);
	}

	public Manhole findInfoManhole(Map<String, Object> map) {
		return manholeDao.findInfoManhole(map);
	}

	public List<Manhole> findListManhole(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		if (!StringUtils.isEmpty(map.get("page")))
			map.put("size", ((int) map.get("page") - 1) * 15);
		return manholeDao.findListManhole(map);
	}

	public int getPage(Map<String, Object> map, int size) {
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		int count = manholeDao.getCount(map);
		return (int) Math.ceil((double) count / size);
	}

	public Manhole findInfoManhole(int id) {
		map = AppHelper.getMap("id", id);
		return manholeDao.findInfoManhole(map);
	}

	public int appendManhole(Manhole manhole) {
		String path1 = manhole.getPath1();
		String path2 = manhole.getPath2();
		if (!StringUtils.isEmpty(path1) && path1.length() > 40) {
			String name = AppHelper.UUIDCode();
			AppHelper.saveImage(path1, myfile + "ItemImage/", name);
			manhole.setPath1(name);
		}
		if (!StringUtils.isEmpty(path2) && path2.length() > 40) {
			String name = AppHelper.UUIDCode();
			AppHelper.saveImage(path2, myfile + "ItemImage/", name);
			manhole.setPath2(name);
		}
		int no = 0;
		this.insertManhole(manhole);
		for (Pipe pipe : manhole.getPipes()) {
			pipe.setNo(no++);
			pipe.setManhole(manhole);
			pipeBiz.insertPipe(pipe);
		}
		return manhole.getId();
	}

	public int replacManhole(Manhole manhole) {
		String path1 = manhole.getPath1();
		String path2 = manhole.getPath2();
		if (!StringUtils.isEmpty(path1) && path1.length() > 40) {
			String name = AppHelper.UUIDCode();
			AppHelper.saveImage(path1, myfile + "ItemImage/", name);
			manhole.setPath1(name);
		}
		if (!StringUtils.isEmpty(path2) && path2.length() > 40) {
			String name = AppHelper.UUIDCode();
			AppHelper.saveImage(path2, myfile + "ItemImage/", name);
			manhole.setPath2(name);
		}
		int no = 0;
		this.updateManhole(manhole);
		for (Pipe pipe : manhole.getPipes()) {
			pipe.setNo(no++);
			pipe.setManhole(manhole);
			pipeBiz.updatePipe(pipe);
		}
		return manhole.getId();
	}

}
