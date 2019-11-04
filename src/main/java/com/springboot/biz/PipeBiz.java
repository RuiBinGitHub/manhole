package com.springboot.biz;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Manhole;
import com.springboot.entity.Pipe;

public interface PipeBiz {

	public void insertPipe(Pipe pipe);

	public void updatePipe(Pipe pipe);

	public void deletePipe(Pipe pipe);

	public Pipe findInfoPipe(Map<String, Object> map);

	public List<Pipe> findListPipe(Map<String, Object> map);

	public List<Pipe> findListPipe(Manhole manhole);
}
