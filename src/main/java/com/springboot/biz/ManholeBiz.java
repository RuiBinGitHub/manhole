package com.springboot.biz;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Manhole;

public interface ManholeBiz {

	public void insertManhole(Manhole manhole);

	public void updateManhole(Manhole manhole);

	public void deleteManhole(Manhole manhole);

	public Manhole findInfoManhole(Map<String, Object> map);

	public List<Manhole> findListManhole(Map<String, Object> map);
	
	public int getPage(Map<String, Object> map, int size);

	public Manhole findInfoManhole(int id);

	public int appendManhole(Manhole manhole);
	
	public int replacManhole(Manhole manhole);

	

}
