package com.sift.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="modulemenu")
public class ModuleMenu{
    @Id
    @Column(name="ID")
    @GeneratedValue
    private String id;
    
    @Column(name="module")
    private String module;
    
    @Column(name="menucode")
    private String menucode;
    
    @Column(name="menupath")
    private String menupath;
    
    @Column(name="displaytext")
    private String displaytext;
    
    @Column(name="menusortorder")
    private String menusortorder;
    
    @Column(name="menurole")
    private String menurole;
    
    @Column(name="accessLevelCode")
    private String accessLevelCode;
    
	public String getAccessLevelCode() {
		return accessLevelCode;
	}

	public void setAccessLevelCode(String accessLevelCode) {
		this.accessLevelCode = accessLevelCode;
	}

	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getModule(){
		return module;
	}
	
	public void setModule(String module){
		this.module = module;
	}
	
	public String getMenucode(){
		return menucode;
	}
	
	public void setMenucode(String menucode){
		this.menucode = menucode;
	}
	
	public String getMenupath(){
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