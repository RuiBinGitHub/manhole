package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.biz.MessageBiz;
import com.springboot.dao.MessageDao;
import com.springboot.entity.MarkItem;
import com.springboot.entity.Message;
import com.springboot.entity.User;
import com.springboot.util.MyHelper;

@Service
public class MessageBizImpl implements MessageBiz {

	@Resource
	private MessageDao messageDao;
	private Map<String, Object> map = null;

	public void insertMessage(Message message) {
		messageDao.insertMessage(message);
	}

	public void updateMessage(Message message) {
		messageDao.updateMessage(message);
	}

	public void deleteMessage(Message message) {
		messageDao.deleteMessage(message);
	}

	public Message findInfoMessage(int id, User user) {
		map = MyHelper.getMap("id", id, "user", user);
		return findInfoMessage(map);
	}

	public Message findInfoMessage(Map<String, Object> map) {
		return messageDao.findInfoMessage(map);
	}

	public PageInfo<Message> findListMessage(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("page")))
			PageHelper.startPage((int) map.get("page"), 15);
		List<Message> messages = messageDao.findListMessage(map);
		PageInfo<Message> info = new PageInfo<Message>(messages);
		return info;
	}

	public void sendMessage(MarkItem markItem) {
		String data = MyHelper.getDate(null);
		Message message = new Message();
		message.setTitle("您有项目被评分");
		message.setState("未读");
		message.setDate(data);
		message.setMarkItem(markItem);
		message.setManhole(markItem.getManhole());
		message.setUser(markItem.getUser());
		insertMessage(message);
	}

	public int getCount(Map<String, Object> map) {
		return messageDao.getCount(map);
	}

}
