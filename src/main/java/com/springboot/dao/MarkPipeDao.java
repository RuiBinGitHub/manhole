package com.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.springboot.entity.MarkPipe;

@Mapper
public interface MarkPipeDao {

	void insertMarkPipe(MarkPipe markPipe);

	void updateMarkPipe(MarkPipe markPipe);

	void deleteMarkPipe(MarkPipe markPipe);

	MarkPipe findInfoMarkPipe(Map<String, Object> map);

	List<MarkPipe> findListMarkPipe(Map<String, Object> map);

}
