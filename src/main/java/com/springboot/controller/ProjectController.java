package com.springboot.controller;

import com.github.pagehelper.PageInfo;
import com.springboot.biz.ManholeBiz;
import com.springboot.biz.OperatorBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Manhole;
import com.springboot.entity.Operator;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {

    @Value(value = "${myfile}")
    private String myfile;

    @Resource
    private ProjectBiz projectBiz;
    @Resource
    private ManholeBiz manholeBiz;
    @Resource
    private OperatorBiz operatorBiz;

    private Map<String, Object> map = null;
    private List<Operator> operators = null;
    private List<Manhole> manholes = null;

    /**
     * 个人沙井列表
     */
    @RequestMapping(value = "/showlist", method = RequestMethod.GET)
    public ModelAndView showList(String name, String sort, @RequestParam(defaultValue = "desc") String type, @RequestParam(defaultValue = "1") int page) {
        ModelAndView view = new ModelAndView("project/showlist");
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        map = AppUtils.getMap("name", name, "state", "未提交", "user", user);
        PageInfo<Project> info = projectBiz.showListProject(map, page, 15, sort, type);
        view.addObject("projects", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("page", page);
        return view;
    }

    /**
     * 公司沙井列表
     */
    @RequestMapping(value = "/findlist", method = RequestMethod.GET)
    public ModelAndView findList(String name, String sort, @RequestParam(defaultValue = "desc") String type, @RequestParam(defaultValue = "1") int page) {
        ModelAndView view = new ModelAndView("project/findlist");
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        map = AppUtils.getMap("name", name, "state", "已提交", "company", user.getCompany());
        PageInfo<Project> info = projectBiz.showListProject(map, page, 15, sort, type);
        view.addObject("projects", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("page", page);
        return view;
    }

    @RequestMapping(value = "/showreal", method = RequestMethod.POST)
    public List<Project> showReal(String real) {
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("real", real, "state", "未提交", "user", user);
        return projectBiz.findListProject(map, 0, 0).getList();
    }

    @RequestMapping(value = "/findreal", method = RequestMethod.POST)
    public List<Project> findReal(String real) {
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("real", real, "state", "已提交", "company", user.getCompany());
        return projectBiz.findListProject(map, 0, 0).getList();
    }

    @RequestMapping(value = "/insertview")
    public ModelAndView insertView() {
        ModelAndView view = new ModelAndView("redirect:/failure");
        User user = (User) AppUtils.findMap("user");
        operators = operatorBiz.findListOperator(user.getCompany());
        view.setViewName("project/insert");
        view.addObject("operators", operators);
        return view;
    }

    @RequestMapping(value = "/updateview")
    public ModelAndView updateView(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("redirect:/failure");
        User user = (User) AppUtils.findMap("user");
        Project project = projectBiz.findInfoProject(id, user);
        if (StringUtils.isEmpty(project))
            return view;
        operators = operatorBiz.findListOperator(user.getCompany());
        view.setViewName("project/update");
        view.addObject("project", project);
        view.addObject("operators", operators);
        return view;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(Project project) {
        ModelAndView view = new ModelAndView();
        User user = (User) AppUtils.findMap("user");
        int id = projectBiz.appendProject(project, user);
        view.setViewName("redirect:editinfo?id=" + id);
        return view;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(Project project) {
        ModelAndView view = new ModelAndView();
        User user = (User) AppUtils.findMap("user");
        int id = projectBiz.replacProject(project, user);
        view.setViewName("redirect:editinfo?id=" + id);
        return view;
    }

    /**
     * 提交数据
     */
    @RequestMapping(value = "/submit")
    public boolean submit(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.findMap("user");
        Project project = projectBiz.findInfoProject(id, user);
        if (!StringUtils.isEmpty(project)) {
            project.setState("已提交");
            projectBiz.updateProject(project);
        }
        return true;
    }

    /**
     * 撤回项目
     */
    @RequestMapping(value = "/revoke")
    public boolean revoke(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        Project project = projectBiz.findInfoProject(map);
        if (!StringUtils.isEmpty(project)) {
            project.setState("未提交");
            projectBiz.updateProject(project);
        }
        return true;
    }

    /**
     * 删除项目
     */
    @RequestMapping(value = "/delete")
    public boolean delete(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.findMap("user");
        Project project = projectBiz.findInfoProject(id, user);
        if (!StringUtils.isEmpty(project))
            projectBiz.deleteProject(project);
        return true;
    }

    /**
     * 移除项目
     */
    @RequestMapping(value = "/remove")
    public boolean remove(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        Project project = projectBiz.findInfoProject(map);
        if (!StringUtils.isEmpty(project))
            projectBiz.deleteProject(project);
        return true;
    }

    /**
     * 编辑数据
     */
    @RequestMapping(value = "/editinfo")
    public ModelAndView findview(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("userview/failure");
        User user = (User) AppUtils.findMap("user");
        Project project = projectBiz.findInfoProject(id, user);
        if (StringUtils.isEmpty(project))
            return view;
        manholes = manholeBiz.findListManhole(project);
        view.setViewName("project/editinfo");
        view.addObject("project", project);
        view.addObject("manholes", manholes);
        return view;
    }

    /**
     * 浏览数据
     */
    @RequestMapping(value = "/findinfo")
    public ModelAndView viewInfo(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("userview/failure");
        Project project = projectBiz.findInfoProject(id, null);
        if (StringUtils.isEmpty(project))
            return view;
        manholes = manholeBiz.findListManhole(project);
        view.setViewName("project/findinfo");
        view.addObject("project", project);
        view.addObject("manholes", manholes);
        return view;
    }

    /**
     * 浏览数据
     */
    @RequestMapping(value = "/checkview")
    public ModelAndView checkview(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("userview/failure");
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        Project project = projectBiz.findInfoProject(map);
        if (StringUtils.isEmpty(project))
            return view;
        manholes = manholeBiz.findListManhole(project);
        view.setViewName("project/checkview");
        view.addObject("project", project);
        view.addObject("manholes", manholes);
        return view;
    }

    /**
     * 修改项目logo
     */
    @RequestMapping(value = "/editlogo", method = RequestMethod.POST)
    public ModelAndView editLogo(int id, String path, MultipartFile file) {
        ModelAndView view = new ModelAndView("userview/failure");
        User user = (User) AppUtils.findMap("user");
        Project project = projectBiz.findInfoProject(id, user);
        if (StringUtils.isEmpty(project))
            return view;
        String name = AppUtils.UUIDCode();
        File dest = new File(myfile + "/ItemImage/" + name + ".png");
        project.setPath(name);
        AppUtils.moveFile(file, dest);
        projectBiz.updateProject(project);
        view.setViewName("redirect:" + path);
        return view;
    }


    @RequestMapping(value = "/iquerylist", method = RequestMethod.GET)
    public ModelAndView iQueryList(String name, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "15") int size) {
        ModelAndView view = new ModelAndView("project/iquerylist");
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        map = AppUtils.getMap("name", name, "state", "未提交", "user", user);
        PageInfo<Manhole> info = projectBiz.queryListManhole(map, page, size);
        view.addObject("lists", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("page", page);
        return view;
    }

    @RequestMapping(value = "/querylists", method = RequestMethod.GET)
    public ModelAndView QueryLists(String name, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "15") int size) {
        ModelAndView view = new ModelAndView("project/querylists");
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        map = AppUtils.getMap("name", name, "state", "已提交", "company", user.getCompany());
        PageInfo<Manhole> info = projectBiz.queryListManhole(map, page, size);
        view.addObject("lists", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("page", page);
        return view;
    }

}
