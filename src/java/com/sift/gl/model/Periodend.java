package com.sift.gl.model;

import java.util.Date;

public class Periodend{   
    private Date periodStart;
    private String opmessage;
    private String startdatestr;
    private int completionstatus;
    private Integer company;
    private String userId;
    private Integer branchId;
    
	public Date getPeriodStart() {
		return periodStart;
	}
	public void setPeriodStart(Date periodStart) {
		this.periodStart = periodStart;
	}
	public String getStartdatestr() {
		return startdatestr;
	}
        public void setStartdatestr(String startdatestr) {
		this.startdatestr = startdatestr;
	}  
        public String getOpmessage() {
		return opmessage;
	}
        public void setOpmessage(String opmessage) {
		this.opmessage = opmessage;
	}  
        public int getCompletionstatus() {
		return completionstatus;
	}
        public void setCompletionstatus(int completionstatus) {
		this.completionstatus = completionstatus;
	} 
        public Integer getBranchId() {
               return this.branchId;
        }
        public void setBranchId(Integer branchId) {
               this.branchId = branchId;
        }
        public Integer getCompany() {
               return this.company;
        }
        public void setCompany(Integer company) {
               this.company = company;
        }
        public String getUserId() {
               return this.userId;
        }
        public void setUserId(String userId) {
               this.userId = userId;
        }
}
