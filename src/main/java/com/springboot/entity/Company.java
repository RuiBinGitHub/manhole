package com.springboot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String code;
	private int count;
	private int level;
	private String date;
	private String term;

}
