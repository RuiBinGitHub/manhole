package com.springboot.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.springboot.biz.MessageBiz;
import com.springboot.entity.Message;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;

@RestController
@RequestMapping(value = "/message")
public class MessageController {

    @Resource
    private MessageBiz messageBiz;
    private Map<String, Object> map = null;

    /**
     * 获取消息列表
     */
    @RequestMapping(value = "/showlist")
    public ModelAndView showlist(String state, @RequestParam(defaultValue = "1") int page) {
        ModelAndView view = new ModelAndView("message/showlist");
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("state", state, "user", user);
        PageInfo<Message> info = messageBiz.findListMessage(map);
        int count1 = getCount("已读");
        int count2 = getCount("未读");
        view.addObject("messages", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("count1", count1);
        view.addObject("count2", count2);
        view.addObject("page", page);
        return view;
    }

    /**
     * 获取消息数量
     */
    @RequestMapping("/getcount")
    public int getCount(String state) {
        User user = (User) AppUtils.findMap("user");
        return messageBiz.getCount(state, user);
    }

    /**
     * 获取消息信息
     */
    @RequestMapping("/findinfo")
    public Message findInfo(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.findMap("user");
        Message message = messageBiz.findInfoMessage(id, user);
        if (StringUtils.isEmpty(message))
            return null;
        if ("未读".equals(message.getState())) {
            message.setState("已读");
            messageBiz.updateMessage(message);
        }
        return message;
    }

    /**
     * 删除信息
     */
    @RequestMapping("/delete")
    public boolean delete(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.findMap("user");
        Message message = messageBiz.findInfoMessage(id, user);
        if (!StringUtils.isEmpty(message))
            messageBiz.deleteMessage(message);
        return true;
    }
}
