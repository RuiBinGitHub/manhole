package com.springboot.biz;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Manhole;
import com.springboot.entity.Project;
import com.springboot.entity.User;

public interface ManholeBiz {

	void insertManhole(Manhole manhole);

	void updateManhole(Manhole manhole);

	void deleteManhole(Manhole manhole);

	Manhole findLastManhole(Project project);
	
	Manhole findInfoManhole(int id, User user);

	Manhole findInfoManhole(Map<String, Object> map);

	List<Manhole> findListManhole(Project project);

	List<Manhole> findListManhole(Map<String, Object> map);

	int replacManhole(Manhole manhole, User user);

	void checkManhole(Manhole manhole, User user);

}
