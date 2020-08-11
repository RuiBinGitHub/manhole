package com.springboot.entity;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String operator;
	private String datetime1;
	private String datetime2;
	private String content;
	private String state;
	private String date;
	private String path;
	private User user;

	private List<Manhole> manholes;
	private double x;
	private double y;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getDatetime1() {
		return datetime1;
	}

	public void setDatetime1(String datetime1) {
		this.datetime1 = datetime1;
	}

	public String getDatetime2() {
		return datetime2;
	}

	public void setDatetime2(String datetime2) {
		this.datetime2 = datetime2;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Manhole> getManholes() {
		return manholes;
	}

	public void setManholes(List<Manhole> manholes) {
		this.manholes = manholes;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
