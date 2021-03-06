package com.springboot.biz;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.Company;
import com.springboot.entity.Project;
import com.springboot.entity.User;

public interface ProjectBiz {

	void insertProject(Project project);

	void updateProject(Project project);

	void deleteProject(Project project);

	Project findInfoProject(int id, User user);
	
	Project findInfoProject(Map<String, Object> map);

	PageInfo<Project> findListProject(Map<String, Object> map);

	List<Project> mapListProject(Company company);
	
	int appendProject(Project project, User user);
	
	int replacProject(Project project, User user);

}
