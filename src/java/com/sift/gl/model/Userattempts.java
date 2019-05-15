package com.sift.gl.model;

import java.util.Date;

public class Userattempts{  	
	private Integer id;
	
        private String userid;
	private int attempts;
	private Date lastModified;
    
       public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAttempts() {
		return attempts;
	}
	
	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}
	
	public String getUserid() {
		return userid;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public Date getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
}