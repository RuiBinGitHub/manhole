package com.springboot.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.springboot.util.AppUtils;

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

    @RequestMapping(value = "/showlist", method = RequestMethod.GET)
    public ModelAndView showList(String name, @RequestParam(defaultValue = "1") int page) {
        ModelAndView view = new ModelAndView("userinfo/showlist");
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("company", user.getCompany(), "page", page);
        if (!StringUtils.isEmpty(name))
            map.put("name", name);
        PageInfo<User> info = userBiz.findListUser(map);
        view.addObject("users", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("page", page);
        return view;
    }

    @RequestMapping(value = "/findinfo")
    public ModelAndView findInfo(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("userview/failure");
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        User temp = userBiz.findInfoUser(map);
        if (StringUtils.isEmpty(temp))
            return view;
        // 计算未提交项目
        map = AppUtils.getMap("user", temp, "state", "未提交");
        PageInfo<Project> info1 = projectBiz.findListProject(map, 0, 0);
        // 计算已提交项目0
        map = AppUtils.getMap("user", temp, "state", "已提交");
        PageInfo<Project> info2 = projectBiz.findListProject(map, 0, 0);
        // 计算评分项目
        int count = 0;
        map = AppUtils.getMap("temp", temp);
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

    @RequestMapping(value = "/resetpass")
    public boolean resetPass(String name, String pass) {
        User user = (User) AppUtils.findMap("user");
        if (!user.getPassword().equals(name))
            return false;
        user.setPassword(pass);
        userBiz.updateUser(user);
        return true;
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(String date1, String date2, String scope, int index) throws IOException {
        User user = (User) AppUtils.findMap("user");
        List<User> users = userBiz.exportUser(date1, date2, scope, user.getCompany());

        List<String> keys = users.stream().filter(value -> !StringUtils.isEmpty(value.getDate())).map(value -> AppUtils.substring(value.getDate(), 0, index)).distinct().collect(Collectors.toList());
        List<User> collect = users.stream().map(User::getId).distinct().map(id -> {
            User temp = users.stream().filter(value -> Objects.equals(value.getId(), id)).findFirst().orElse(null);
            assert temp != null : "获取用户对象为null";

            Map<String, Integer> collect1 = users.stream().filter(value -> Objects.equals(id, value.getId()) && !StringUtils.isEmpty(value.getDate())).
                    collect(Collectors.groupingBy(value -> AppUtils.substring(value.getDate(), 0, index), Collectors.summingInt(User::getPcount)));
            Map<String, Integer> collect2 = users.stream().filter(value -> Objects.equals(id, value.getId()) && !StringUtils.isEmpty(value.getDate())).
                    collect(Collectors.groupingBy(value -> AppUtils.substring(value.getDate(), 0, index), Collectors.summingInt(User::getQcount)));
            temp.setCollect1(collect1);
            temp.setCollect2(collect2);
            return temp;
        }).collect(Collectors.toList());

        ExcelWriter workbook = ExcelUtil.getWriter(true);
        workbook.setColumnWidth(-1, 12);
        workbook.setDefaultRowHeight(18);
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 14);
        workbook.getStyleSet().setFont(font, true);

        workbook.merge(0, 1, 0, 0, "No.", false);
        workbook.merge(0, 1, 1, 1, "UserName", false);
        workbook.merge(0, 1, 2, 2, "Name", false);
        for (int i = 0; i < keys.size(); i++) {
            workbook.merge(0, 0, i * 2 + 3, i * 2 + 4, keys.get(i), false);
            workbook.writeCellValue(3 + i * 2, 1, "项目数量");
            workbook.writeCellValue(4 + i * 2, 1, "沙井数量");
        }

        for (int i = 0; i < collect.size(); i++) {
            User value = collect.get(i);
            workbook.writeCellValue(0, i + 2, i + 1);
            workbook.writeCellValue(1, i + 2, value.getUsername());
            workbook.writeCellValue(2, i + 2, value.getName());
            for (int j = 0; j < keys.size(); j++) {
                workbook.writeCellValue(3 + j * 2, i + 2, value.getCollect1().get(keys.get(j)));
                workbook.writeCellValue(4 + j * 2, i + 2, value.getCollect2().get(keys.get(j)));
            }
        }

        workbook.merge(2 + collect.size(), 2 + collect.size(), 0, 2, "总数", false);
        for (String key : keys) {
            int i = keys.indexOf(key);
            int pcount = users.stream().filter(value -> Objects.equals(key, AppUtils.substring(value.getDate(), 0, index))).mapToInt(User::getPcount).sum();
            int qcount = users.stream().filter(value -> Objects.equals(key, AppUtils.substring(value.getDate(), 0, index))).mapToInt(User::getQcount).sum();
            workbook.writeCellValue(3 + i * 2, 2 + collect.size(), pcount);
            workbook.writeCellValue(4 + i * 2, 2 + collect.size(), qcount);
        }

        HttpServletResponse response = AppUtils.getResponse();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=user.xlsx");
        workbook.flush(response.getOutputStream(), true);
        response.flushBuffer();
        workbook.close();
    }

}
