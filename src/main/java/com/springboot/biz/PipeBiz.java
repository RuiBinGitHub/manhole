package com.springboot.biz;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Manhole;
import com.springboot.entity.Pipe;

public interface PipeBiz {

	void insertPipe(Pipe pipe);

	void updatePipe(Pipe pipe);

	void deletePipe(Pipe pipe);

	Pipe findInfoPipe(Map<String, Object> map);

	List<Pipe> findListPipe(Map<String, Object> map);

	List<Pipe> findListPipe(Manhole manhole);
}
