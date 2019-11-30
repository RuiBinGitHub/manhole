package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.springboot.biz.PipeBiz;
import com.springboot.dao.PipeDao;
import com.springboot.entity.Manhole;
import com.springboot.entity.Pipe;
import com.springboot.util.MyHelper;

@Service
public class PipeBizImpl implements PipeBiz {

	@Resource
	private PipeDao pipeDao;
	private Map<String, Object> map = null;
	
	
	public void insertPipe(Pipe pipe) {
		pipeDao.insertPipe(pipe);
	}

	public void updatePipe(Pipe pipe) {
		pipeDao.updatePipe(pipe);
	}

	public void deletePipe(Pipe pipe) {
		pipeDao.deletePipe(pipe);
	}

	public Pipe findInfoPipe(Map<String, Object> map) {
		return pipeDao.findInfoPipe(map);
	}

	public List<Pipe> findListPipe(Map<String, Object> map) {
		return pipeDao.findListPipe(map);
	}

	public List<Pipe> findListPipe(Manhole manhole) {
		map = MyHelper.getMap("manhole", manhole);
		return findListPipe(map);
	}

}
