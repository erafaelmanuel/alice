package com.remswork.project.alice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="tblsubject")
public class Subject {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private int id;
	private String name;
	private String code;
	private String description;
	private int unit;
	
	public Subject() {
		super();
	}
	
	public Subject(String name, String code, String description, int unit) {
		this();
		this.name = name;
		this.code = code;
		this.description = description;
		this.unit = unit;
	}
	
	public Subject(int id, String name, String code, String description, int unit) {
		this(name, code, description, unit);
		this.id = id;
	}

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}
}
