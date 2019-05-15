package com.sift.admin.bean;

public class UsergrpmdlBean{
    private String id;
    private String usergroup;
    private String menu;
    private String companyid;
    private String branchid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsergroup() {
		return usergroup;
	}

	public void setUsergroup(String usergroup) {
		this.usergroup = usergroup;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu){
		this.menu = menu;
	}

	public String getCompanyid(){
		return companyid;
	}

	public void setCompanyid(String companyid){
		this.companyid = companyid;
	}

	public String getBranchid(){
		return branchid;
	}

	public void setBranchid(String branchid){
		this.branchid = branchid;
	}
}