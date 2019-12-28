package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.springboot.biz.ManholeBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.dao.ManholeDao;
import com.springboot.entity.Manhole;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.MyHelper;

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

	public Manhole findLastManhole(Project project) {
		PageHelper.startPage(1, 1);
		map = MyHelper.getMap("project", project);
		List<Manhole> manholes = manholeDao.findListManhole(map);
		if (manholes != null && manholes.size() != 0)
			return manholes.get(0);
		else
			return null;
	}

	public Manhole findInfoManhole(int id, User user) {
		map = MyHelper.getMap("id", id, "user", user);
		return manholeDao.findInfoManhole(map);
	}

	public Manhole findInfoManhole(Map<String, Object> map) {
		return manholeDao.findInfoManhole(map);
	}

	public List<Manhole> findListManhole(Project project) {
		map = MyHelper.getMap("project", project);
		return manholeDao.findListManhole(map);
	}

	public List<Manhole> findListManhole(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("page")))
			PageHelper.startPage((int) map.get("page"), 15);
		return manholeDao.findListManhole(map);
	}

	public int replacManhole(Manhole manhole, User user) {
		String path = myfile + "/ItemImage/";
		String path1 = manhole.getPath1();
		String path2 = manhole.getPath2();
		if (!StringUtils.isEmpty(path1) && path1.length() > 40) {
			String name = MyHelper.UUIDCode();
			MyHelper.saveImage(path1, path, name);
			manhole.setPath1(name);
		}
		if (!StringUtils.isEmpty(path2) && path2.length() > 40) {
			String name = MyHelper.UUIDCode();
			MyHelper.saveImage(path2, path, name);
			manhole.setPath2(name);
		}
		int no = 0;
		if (manhole.getId() == 0) {
			this.insertManhole(manhole);
			for (Pipe pipe : manhole.getPipes()) {
				pipe.setNo(no++);
				pipe.setManhole(manhole);
				pipeBiz.insertPipe(pipe);
			}
		} else {
			this.updateManhole(manhole);
			for (Pipe pipe : manhole.getPipes()) {
				pipe.setNo(no++);
				pipe.setManhole(manhole);
				pipeBiz.updatePipe(pipe);
			}
		}
		return manhole.getId();
	}

}
