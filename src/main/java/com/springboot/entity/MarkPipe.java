package com.springboot.entity;

import java.io.Serializable;

import com.springboot.entity.Manhole;
import com.springboot.entity.MarkItem;

public class MarkPipe implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int score;
	private String remark;
	private Manhole manhole;
	private MarkItem markItem;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Manhole getManhole() {
		return manhole;
	}

	public void setManhole(Manhole manhole) {
		this.manhole = manhole;
	}

	public MarkItem getMarkItem() {
		return markItem;
	}

	public void setMarkItem(MarkItem markItem) {
		this.markItem = markItem;
	}

}
