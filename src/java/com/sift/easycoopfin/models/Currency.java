
package com.sift.easycoopfin.models;

import java.io.Serializable;
public class Currency implements Serializable {
	public Currency() {
	}
	
	private int id;
	
	private String code;
	
	private String name;
	
	
	private void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	
	public int getORMID() {
		return getId();
	}
	
	public void setCode(String value) {
		this.code = value;
	}
	
	public String getCode() {
		return code;
	}
	
	
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	

	public String toString() {
		return String.valueOf(getId());
	}
	
}
