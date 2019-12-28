package com.springboot.biz;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Manhole;
import com.springboot.entity.Project;
import com.springboot.entity.User;

public interface ManholeBiz {

	public void insertManhole(Manhole manhole);

	public void updateManhole(Manhole manhole);

	public void deleteManhole(Manhole manhole);

	public Manhole findLastManhole(Project project);
	
	public Manhole findInfoManhole(int id, User user);

	public Manhole findInfoManhole(Map<String, Object> map);

	public List<Manhole> findListManhole(Project project);

	public List<Manhole> findListManhole(Map<String, Object> map);

	public int replacManhole(Manhole manhole, User user);

}
