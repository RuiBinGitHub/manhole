package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.biz.ProjectBiz;
import com.springboot.dao.ProjectDao;
import com.springboot.entity.Company;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.MyHelper;

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
		map = MyHelper.getMap("id", id, "user", user);
		return projectDao.findInfoProject(map);
	}

	public Project findInfoProject(Map<String, Object> map) {
		return projectDao.findInfoProject(map);
	}

	public PageInfo<Project> findListProject(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		if (!StringUtils.isEmpty(map.get("page")))
			PageHelper.startPage((int) map.get("page"), 15);
		List<Project> projects = projectDao.findListProject(map);
		PageInfo<Project> info = new PageInfo<Project>(projects);
		return info;
	}

	public List<Project> mapListProject(Company company) {
		map = MyHelper.getMap("company", company);
		return projectDao.mapListProject(map);
	}

	public int appendProject(Project project, User user) {
		project.setDate(MyHelper.getDate(null));
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
