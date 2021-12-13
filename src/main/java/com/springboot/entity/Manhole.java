package com.springboot.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Manhole implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String node;
	private String gridref;
	private String gridx;
	private String gridy;
	private String areacode;
	private String surveyname;
	private String surveydate;
	private String projectno;
	private String workorder;
	private String manholeid;
	private String drainage;
	private String location;
	private String Drawing;
	private String yearlaid;
	private String status;
	private String method;
	private String nodetype;
	private String shape;
	private String hinge;
	private String locker;
	private String duties;
	private String cover1;
	private String cover2;
	private String side;
	private String course;
	private String depths;
	private String shaft1;
	private String shaft2;
	private String soffit;
	private String steps;
	private String ladder;
	private String lndgs;
	private String csize1;
	private String csize2;
	private String toxic;
	private String evidences;
	private String construct;
	private String depthflow;
	private String depthsilt;
	private String height;
	private String mdepth;
	private String mcover;
	private String cond;
	private String crit;
	private String cover;
	private String iron;
	private String shaft;
	private String chambers;
	private String benching;
	private String other;
	private String photo1;
	private String photo2;
	private String photo3;
	private String photo4;
	private String photo5;
	private String photo6;
	private String util1;
	private String util2;
	private String util3;
	private String util4;
	private String util5;
	private String util6;
	private String photono1;
	private String photono2;
	private String slopeno;
	private String remarks;
	private String dplan;
	private String rplan;
	private String ctype;
	private String rtype;
	private String path1;
	private String path2;
	private String with1;
	private String with2;
	private String with3;
	private Project project;

	private List<Pipe> pipes;
	private List<Item> items;

}
