package com.springboot.biz;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.entity.Item;
import com.springboot.entity.Manhole;
import com.springboot.entity.User;

public interface ItemBiz {

	public void insertItem(Item item);

	public void updateItem(Item item);

	public void deleteItem(Item item);

	public Item findInfoItem(int id, User user);

	public List<Item> findListItem(Manhole manhole);
	
	public List<Item> appendItem(Manhole manhole);
	
	public void replacItem(Manhole manhole, MultipartFile[] files);
}
