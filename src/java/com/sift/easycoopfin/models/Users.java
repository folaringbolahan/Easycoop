
package com.sift.easycoopfin.models;

import java.io.Serializable;
public class Users implements Serializable {
	public Users() {
	}
	
	private int id;
        
        private String UserId;
        
        private String UserName;
        
        
	private void setUserId(String value) {
		this.UserId = value;
	}
	
	public String getUserId() {
		return UserId;
	}
        private void setUserName(String value) {
		this.UserName = value;
	}
	
	public String getUserName() {
		return UserName;
	}
	private void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	
	public int getORMID() {
		return getId();
	}
	
	public String toString() {
		return String.valueOf(getUserId());
	}
	
}
