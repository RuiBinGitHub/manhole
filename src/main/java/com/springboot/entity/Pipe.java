package com.springboot.entity;

public class Pipe {

	private int id;
	private int no;
	private String upstream;
	private String shape;
	private String size1;
	private String size2;
	private String backdrop;
	private String material;
	private String lining;
	private String depth;
	private String level;
	private String photo;
	private String office1;
	private String office2;
	
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

	public String getUpstream() {
		return upstream;
	}

	public void setUpstream(String upstream) {
		this.upstream = upstream;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getSize1() {
		return size1;
	}

	public void setSize1(String size1) {
		this.size1 = size1;
	}

	public String getSize2() {
		return size2;
	}

	public void setSize2(String size2) {
		this.size2 = size2;
	}

	public String getBackdrop() {
		return backdrop;
	}

	public void setBackdrop(String backdrop) {
		this.backdrop = backdrop;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getLining() {
		return lining;
	}

	public void setLining(String lining) {
		this.lining = lining;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getOffice1() {
		return office1;
	}

	public void setOffice1(String office1) {
		this.office1 = office1;
	}

	public String getOffice2() {
		return office2;
	}

	public void setOffice2(String office2) {
		this.office2 = office2;
	}

	public Manhole getManhole() {
		return manhole;
	}

	public void setManhole(Manhole manhole) {
		this.manhole = manhole;
	}

}
