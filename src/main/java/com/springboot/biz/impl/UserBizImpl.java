package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.biz.UserBiz;
import com.springboot.dao.UserDao;
import com.springboot.entity.Company;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;

@Service
public class UserBizImpl implements UserBiz {

    @Resource
    private UserDao userDao;
    private Map<String, Object> map = null;

    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    public User findInfoUser(int id) {
        map = AppUtils.getMap("id", id);
        return userDao.findInfoUser(map);
    }

    public User findInfoUser(Map<String, Object> map) {
        return userDao.findInfoUser(map);
    }

    public PageInfo<User> findListUser(Map<String, Object> map) {
        if (!StringUtils.isEmpty(map.get("name")))
            map.put("name", "%" + map.get("name") + "%");
        if (!StringUtils.isEmpty(map.get("page")))
            PageHelper.startPage((int) map.get("page"), 15);
        List<User> users = userDao.findListUser(map);
        return new PageInfo<>(users);
    }

    public PageInfo<User> findListUser(Company company) {
        map = AppUtils.getMap("company", company);
        return findListUser(map);
    }

    public List<User> exportUser(String date1, String date2, String scope, Company company) {
        map = AppUtils.getMap("date1", date1, "date2", date2, "scope", scope, "company", company);
        return userDao.exportUser(map);
    }
}
