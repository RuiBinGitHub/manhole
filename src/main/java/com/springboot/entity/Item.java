package com.springboot.entity;

import java.io.Serializable;

public class Item  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int no;
	private String path1;
	private String path2;
	private String photo1;
	private String photo2;
	private String explain1;
	private String explain2;
	private String remark1;
	private String remark2;
	private Manhole manhole;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getPath1() {
		return path1;
	}

	public void setPath1(String path1) {
		this.path1 = path1;
	}

	public String getPath2() {
		return path2;
	}

	public void setPath2(String path2) {
		this.path2 = path2;
	}

	public String getPhoto1() {
		return photo1;
	}

	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}

	public String getPhoto2() {
		return photo2;
	}

	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}

	public String getExplain1() {
		return explain1;
	}

	public void setExplain1(String explain1) {
		this.explain1 = explain1;
	}

	public String getExplain2() {
		return explain2;
	}

	public void setExplain2(String explain2) {
		this.explain2 = explain2;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public Manhole getManhole() {
		return manhole;
	}

	public void setManhole(Manhole manhole) {
		this.manhole = manhole;
	}

}
