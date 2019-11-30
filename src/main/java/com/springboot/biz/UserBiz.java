package com.springboot.biz;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.Company;
import com.springboot.entity.User;

public interface UserBiz {

	public void insertUser(User user);

	public void updateUser(User user);

	public void deleteUser(User user);

	public User findInfoUser(Map<String, Object> map);

	public PageInfo<User> findListUser(Map<String, Object> map);

	public PageInfo<User> findListUser(Company company);

}
