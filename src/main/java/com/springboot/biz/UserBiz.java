package com.springboot.biz;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.Company;
import com.springboot.entity.User;

public interface UserBiz {

	void insertUser(User user);

	void updateUser(User user);

	void deleteUser(User user);

	User findInfoUser(Map<String, Object> map);

	PageInfo<User> findListUser(Map<String, Object> map);

	PageInfo<User> findListUser(Company company);

}
