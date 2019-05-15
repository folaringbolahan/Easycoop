
package com.sift.easycoopfin.models;

import java.io.Serializable;
public class ProductAccountType implements Serializable {
	public ProductAccountType() {
	}
	
	private int id;
	
	private String code;
	
	private String description;
	
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
	
	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String toString() {
		return "Product Account Type: ID "+String.valueOf(getId())+" Description: "+getDescription();
	}
	
}
