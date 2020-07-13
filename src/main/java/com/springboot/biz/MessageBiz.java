package com.springboot.biz;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.MarkItem;
import com.springboot.entity.Message;
import com.springboot.entity.User;

public interface MessageBiz {

	void insertMessage(Message message);

	void updateMessage(Message message);

	void deleteMessage(Message message);

	Message findInfoMessage(int id, User user);

	PageInfo<Message> findListMessage(Map<String, Object> map);

	void sendMessage(MarkItem markItem);

	int getCount(Map<String, Object> map);
}
