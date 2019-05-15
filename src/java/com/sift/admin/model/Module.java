package com.sift.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="modules")
public class Module{
    @Id
    @Column(name="ID")
    @GeneratedValue
    private String id;
    
    @Column(name="code")
    private String code;
    
    @Column(name="description")
    private String description;
    
    @Column(name="icon")
    private String icon;
    
    @Column(name="sortorder")
    private String sortorder;
    
    @Column(name="modulerole")
    private String modulerole;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}

	public String getModulerole() {
		return modulerole;
	}

	public void setModulerole(String modulerole) {
		this.modulerole = modulerole;
	}
}