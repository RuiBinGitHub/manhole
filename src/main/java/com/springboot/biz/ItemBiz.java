package com.springboot.biz;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.entity.Item;
import com.springboot.entity.Manhole;
import com.springboot.entity.User;

public interface ItemBiz {

	void insertItem(Item item);

	void updateItem(Item item);

	void deleteItem(Item item);

	Item findInfoItem(int id, User user);

	List<Item> findListItem(Manhole manhole);
	
	List<Item> appendItem(Manhole manhole);
	
	void replacItem(Manhole manhole, MultipartFile[] files);

}
