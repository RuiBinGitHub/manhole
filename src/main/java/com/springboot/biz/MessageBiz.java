package com.springboot.biz;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.MarkItem;
import com.springboot.entity.Message;
import com.springboot.entity.User;

public interface MessageBiz {

	public void insertMessage(Message message);

	public void updateMessage(Message message);

	public void deleteMessage(Message message);

	public Message findInfoMessage(int id, User user);

	public PageInfo<Message> findListMessage(Map<String, Object> map);

	public void sendMessage(MarkItem markItem);

	public int getCount(Map<String, Object> map);
}
