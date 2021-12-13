package com.springboot.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.springboot.biz.ItemBiz;
import com.springboot.biz.ManholeBiz;
import com.springboot.biz.MarkItemBiz;
import com.springboot.biz.MarkPipeBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Item;
import com.springboot.entity.Manhole;
import com.springboot.entity.MarkItem;
import com.springboot.entity.MarkPipe;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;

@RestController
@RequestMapping(value = "/markinfo")
public class MarkInfoController {

    @Value("${mypath}")
    private String mypath;

    @Resource
    private MarkItemBiz markItemBiz;
    @Resource
    private MarkPipeBiz markPipeBiz;
    @Resource
    private ProjectBiz projectBiz;
    @Resource
    private ManholeBiz manholeBiz;
    @Resource
    private PipeBiz pipeBiz;
    @Resource
    private ItemBiz itemBiz;

    private Map<String, Object> map = null;
    private PageInfo<MarkItem> info = null;

    /**
     * 沙井列表
     */
    @RequestMapping(value = "/markview")
    public ModelAndView markView(String name, @RequestParam(defaultValue = "1") int page) {
        ModelAndView view = new ModelAndView("markinfo/markview");
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("page", page, "company", user.getCompany());
        if (!StringUtils.isEmpty(name))
            map.put("name", name);
        info = markItemBiz.findViewMarkItem(map);
        view.addObject("markItems", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("page", page);
        return view;
    }

    /**
     * 评分列表
     */
    @RequestMapping(value = "/showlist")
    public ModelAndView showList(String name, @RequestParam(defaultValue = "1") int page) {
        ModelAndView view = new ModelAndView("markinfo/showlist");
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("page", page, "user", user);
        if (!StringUtils.isEmpty(name))
            map.put("name", name);
        info = markItemBiz.findListMarkItem(map);
        view.addObject("markItems", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("page", page);
        return view;
    }

    /**
     * 评分列表
     */
    @RequestMapping(value = "/findlist")
    public ModelAndView findList(String name, @RequestParam(defaultValue = "1") int page) {
        ModelAndView view = new ModelAndView("markinfo/findlist");
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("page", page, "company", user.getCompany());
        if (!StringUtils.isEmpty(name))
            map.put("name", name);
        info = markItemBiz.findListMarkItem(map);
        view.addObject("markItems", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("page", page);
        return view;
    }

    /**
     * 项目评分列表
     */
    @RequestMapping(value = "/marklist")
    public List<MarkItem> markList(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        Project project = projectBiz.findInfoProject(map);
        if (StringUtils.isEmpty(project))
            return null;
        map = AppUtils.getMap("project", project, "user", user);
        info = markItemBiz.findListMarkItem(map);
        return info.getList();
    }

    /**
     * 项目评分
     */
    @RequestMapping(value = "/markitem")
    public ModelAndView markItem(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("userview/failure");
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        Project project = projectBiz.findInfoProject(map);
        if (StringUtils.isEmpty(project))
            return view;
        id = markItemBiz.appendMarkItem(project, user);
        view.setViewName("redirect:editmark?id=" + id);
        return view;
    }

    /**
     * 编辑评分
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public boolean update(MarkPipe markPipe) {
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("id", markPipe.getId(), "user", user);
        if (markPipeBiz.findInfoMarkPipe(map) != null)
            markPipeBiz.updateMarkPipe(markPipe);
        return true;
    }

    /**
     * 删除数据
     */
    @RequestMapping(value = "/delete")
    public boolean delete(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.findMap("user");
        MarkItem markItem = markItemBiz.findInfoMarkItem(id, user);
        if (!StringUtils.isEmpty(markItem))
            markItemBiz.deleteMarkItem(markItem);
        return true;
    }

    /**
     * 移除数据
     */
    @RequestMapping(value = "/remove")
    public boolean remove(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        MarkItem markItem = markItemBiz.findInfoMarkItem(map);
        if (!StringUtils.isEmpty(markItem))
            markItemBiz.deleteMarkItem(markItem);
        return true;
    }

    @RequestMapping(value = "/editmark")
    public ModelAndView editMark(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("userview/failure");
        User user = (User) AppUtils.findMap("user");
        MarkItem markItem = markItemBiz.findInfoMarkItem(id, user);
        if (StringUtils.isEmpty(markItem))
            return view;
        List<MarkPipe> markPipes = markPipeBiz.findListMarkPipe(markItem);
        view.setViewName("markinfo/editmark");
        view.addObject("markItem", markItem);
        view.addObject("markPipes", markPipes);
        return view;
    }

    @RequestMapping(value = "/findmark")
    public ModelAndView findMark(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("userview/failure");
        MarkItem markItem = markItemBiz.findInfoMarkItem(id, null);
        if (StringUtils.isEmpty(markItem))
            return view;
        List<MarkPipe> markPipes = markPipeBiz.findListMarkPipe(markItem);
        view.setViewName("markinfo/findmark");
        view.addObject("markItem", markItem);
        view.addObject("markPipes", markPipes);
        return view;
    }

    @RequestMapping(value = "/editinfo")
    public ModelAndView editInfo(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("userview/failure");
        User user = (User) AppUtils.findMap("user");
        MarkPipe markPipe = markPipeBiz.findInfoMarkPipe(id, user);
        if (StringUtils.isEmpty(markPipe))
            return view;
        Manhole manhole = markPipe.getManhole();
        List<Pipe> pipes = pipeBiz.findListPipe(manhole);
        List<Item> items = itemBiz.findListItem(manhole);
        view.setViewName("markinfo/editinfo");
        view.addObject("markPipe", markPipe);
        view.addObject("manhole", manhole);
        view.addObject("pipes", pipes);
        view.addObject("items", items);
        view.addObject("path", mypath);
        return view;
    }

    @RequestMapping(value = "/findinfo")
    public ModelAndView findInfo(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("userview/failure");
        MarkPipe markPipe = markPipeBiz.findInfoMarkPipe(id, null);
        if (StringUtils.isEmpty(markPipe))
            return view;
        Manhole manhole = markPipe.getManhole();
        List<Pipe> pipes = pipeBiz.findListPipe(manhole);
        List<Item> items = itemBiz.findListItem(manhole);
        view.setViewName("markinfo/findinfo");
        view.addObject("markPipe", markPipe);
        view.addObject("manhole", manhole);
        view.addObject("pipes", pipes);
        view.addObject("items", items);
        view.addObject("path", mypath);
        return view;
    }

}
