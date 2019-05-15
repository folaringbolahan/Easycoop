package com.sift.admin.bean;

public class ModuleBean {
    private String id;
    private String code;
    private String description;
    private String icon;
    private String sortorder;
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