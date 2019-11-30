package com.springboot.biz;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.Manhole;
import com.springboot.entity.User;

public interface ManholeBiz {

	public void insertManhole(Manhole manhole);

	public void updateManhole(Manhole manhole);

	public void deleteManhole(Manhole manhole);

	public Manhole findInfoManhole(int id, User user);

	public Manhole findInfoManhole(Map<String, Object> map);

	public PageInfo<Manhole> findListManhole(Map<String, Object> map);

	public int replacManhole(Manhole manhole, User user);

}
