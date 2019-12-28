package com.springboot.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.springboot.biz.MarkItemBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.biz.UserBiz;
import com.springboot.entity.MarkItem;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.MyHelper;

@RestController
@RequestMapping(value = "/userinfo")
public class UserInfoController {

	@Resource
	private UserBiz userBiz;
	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private MarkItemBiz markItemBiz;

	private Map<String, Object> map = null;

	@RequestMapping(value = "/findinfo")
	public ModelAndView findInfo(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) MyHelper.findMap("user");
		map = MyHelper.getMap("id", id, "company", user.getCompany());
		User temp = userBiz.findInfoUser(map);
		if (StringUtils.isEmpty(temp))
			return view;
		// 计算未提交项目
		map = MyHelper.getMap("user", temp, "state", "未提交");
		PageInfo<Project> info1 = projectBiz.findListProject(map);
		// 计算已提交项目
		map = MyHelper.getMap("user", temp, "state", "已提交");
		PageInfo<Project> info2 = projectBiz.findListProject(map);
		// 计算评分项目
		int count = 0;
		map = MyHelper.getMap("temp", temp);
		PageInfo<MarkItem> markInfo = markItemBiz.findListMarkItem(map);
		List<MarkItem> markItems = markInfo.getList();
		for (int i = 0; i < markItems.size(); i++) {
			MarkItem markItem = markItems.get(i);
			if (markItem.getScore() >= 95)
				count++;
		}
		view.setViewName("userinfo/findinfo");
		view.addObject("manhole1", info1);
		view.addObject("manhole2", info2);
		view.addObject("markItems", markItems);
		view.addObject("count", count);
		view.addObject("temp", temp);
		return view;
	}
}
