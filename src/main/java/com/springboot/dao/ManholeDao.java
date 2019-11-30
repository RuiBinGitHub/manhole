package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Manhole;

public interface ManholeDao {

	public void insertManhole(Manhole manhole);

	public void updateManhole(Manhole manhole);

	public void deleteManhole(Manhole manhole);

	public Manhole findInfoManhole(Map<String, Object> map);

	public List<Manhole> findListManhole(Map<String, Object> map);

}
