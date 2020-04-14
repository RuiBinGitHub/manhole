package com.springboot.app.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.biz.ManholeBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.biz.UserBiz;
import com.springboot.entity.Manhole;
import com.springboot.entity.Pipe;
import com.springboot.entity.User;
import com.springboot.util.JWTHelper;
import com.springboot.util.MyHelper;

@RestController
@RequestMapping(value = "/app/manhole")
public class AppManholeController {

	@Value("${myfile}")
	private String myfile;
	@Value("${mypath}")
	private String mypath;

	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private ManholeBiz manholeBiz;
	@Resource
	private PipeBiz pipeBiz;
	@Resource
	private UserBiz userBiz;

	private Map<String, Object> map = null;

	/** 编辑数据 */
	@RequestMapping(value = "/editinfo")
	public Manhole editInfo(@RequestParam(defaultValue = "0") int id) {
		HttpServletRequest request = MyHelper.getRequest();
		String token = request.getHeader("token");
		map = MyHelper.getMap("id", JWTHelper.getClaim(token, "id"));
		User user = userBiz.findInfoUser(map);
		Manhole manhole = manholeBiz.findInfoManhole(id, user);
		if (id != 0 && StringUtils.isEmpty(manhole))
			return null;
		List<Pipe> pipes = pipeBiz.findListPipe(manhole);
		manhole.setPipes(pipes);
		return manhole;
	}
}
