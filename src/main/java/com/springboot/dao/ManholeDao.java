package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Manhole;

public interface ManholeDao {

	void insertManhole(Manhole manhole);

	void updateManhole(Manhole manhole);

	void deleteManhole(Manhole manhole);

	Manhole findInfoManhole(Map<String, Object> map);

	List<Manhole> findListManhole(Map<String, Object> map);

}
