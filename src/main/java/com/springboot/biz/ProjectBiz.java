package com.springboot.biz;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.Company;
import com.springboot.entity.Project;
import com.springboot.entity.User;

public interface ProjectBiz {

	public void insertProject(Project project);

	public void updateProject(Project project);

	public void deleteProject(Project project);

	public Project findInfoProject(int id, User user);
	
	public Project findInfoProject(Map<String, Object> map);

	public PageInfo<Project> findListProject(Map<String, Object> map);

	public List<Project> mapListProject(Company company);
	
	public int appendProject(Project project, User user);
	
	public int replacProject(Project project, User user);

}
