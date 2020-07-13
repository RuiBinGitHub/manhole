package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Pipe;

public interface PipeDao {

	void insertPipe(Pipe pipe);

	void updatePipe(Pipe pipe);

	void deletePipe(Pipe pipe);

	Pipe findInfoPipe(Map<String, Object> map);

	List<Pipe> findListPipe(Map<String, Object> map);
	
}
