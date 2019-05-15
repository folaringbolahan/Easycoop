package com.sift.admin.bean;

public class ModuleMenuBean {
    private String id;
    private String module;
    private String menucode;
    private String menupath;
    private String displaytext;
    private String menusortorder;
    private String menurole;
    private String accessLevelCode;
    
	public String getAccessLevelCode() {
		return accessLevelCode;
	}

	public void setAccessLevelCode(String accessLevelCode) {
		this.accessLevelCode = accessLevelCode;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getModule() {
		return module;
	}
	
	public void setModule(String module) {
		this.module = module;
	}
	
	public String getMenucode() {
		return menucode;
	}
	
	public void setMenucode(String menucode) {
		this.menucode = menucode;
	}
	
	public String getMenupath() {
		return menupath;
	}
	
	public void setMenupath(String menupath) {
		this.menupath = menupath;
	}
	
	public String getDisplaytext() {
		return displaytext;
	}
	
	public void setDisplaytext(String displaytext) {
		this.displaytext = displaytext;
	}
	
	public String getMenusortorder() {
		return menusortorder;
	}
	
	public void setMenusortorder(String menusortorder) {
		this.menusortorder = menusortorder;
	}
	
	public String getMenurole() {
		return menurole;
	}
	
	public void setMenurole(String menurole) {
		this.menurole = menurole;
	}      
}