package com.springboot.biz;

import java.util.List;

import com.springboot.entity.Item;
import com.springboot.entity.Manhole;

public interface ItemBiz {

	public void insertItem(Item item);

	public void updateItem(Item item);

	public void deleteItem(Item item);

	public Item findInfoItem(int id, String name);

	public List<Item> findListItem(Manhole manhole);
	
	public List<Item> appendItem(Manhole manhole);
}
