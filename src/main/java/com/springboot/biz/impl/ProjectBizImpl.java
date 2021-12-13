package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.springboot.entity.Manhole;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.biz.ProjectBiz;
import com.springboot.dao.ProjectDao;
import com.springboot.entity.Company;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;

@Service
public class ProjectBizImpl implements ProjectBiz {

	@Resource
	private ProjectDao projectDao;
	private Map<String, Object> map = null;

	public void insertProject(Project project) {
		projectDao.insertProject(project);
	}

	public void updateProject(Project project) {
		projectDao.updateProject(project);
	}

	public void deleteProject(Project project) {
		projectDao.deleteProject(project);
	}

	public Project findInfoProject(int id, User user) {
		map = AppUtils.getMap("id", id, "user", user);
		return projectDao.findInfoProject(map);
	}

	public Project findInfoProject(Map<String, Object> map) {
		return projectDao.findInfoProject(map);
	}

	public PageInfo<Project> findListProject(Map<String, Object> map, int page, int size) {
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		PageHelper.startPage(page, size);
		List<Project> projects = projectDao.findListProject(map);
		return new PageInfo<>(projects);
	}

	public PageInfo<Project> showListProject(Map<String, Object> map, int page, int size, String sort, String type) {
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		String order = StringUtils.isEmpty(sort) ? null : sort + " " + type;
		PageHelper.startPage(page, size, order);
		List<Project> projects = projectDao.showListProject(map);
		return new PageInfo<>(projects);
	}

	public PageInfo<Manhole> queryListManhole(Map<String, Object> map, int page, int size) {
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		PageHelper.startPage(page, size);
		List<Manhole> manholes = projectDao.queryListManhole(map);
		return new PageInfo<>(manholes);
	}

	public List<Project> mapListProject(Company company) {
		map = AppUtils.getMap("company", company);
		return projectDao.mapListProject(map);
	}

	public int appendProject(Project project, User user) {
		project.setDate(AppUtils.getDate(null));
		project.setState("未提交");
		project.setUser(user);
		insertProject(project);
		return project.getId();
	}

	public int replacProject(Project project, User user) {
		project.setState("未提交");
		project.setUser(user);
		updateProject(project);
		return project.getId();
	}

}
