
package com.sift.easycoopfin.models;

import java.io.Serializable;
public class ProductType implements Serializable {
	public ProductType() {
	}
	
	private int id;
	
	private String name;
	
	private byte attractsInterest = 0;
        
        private String code;
	
	private void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	
	public int getORMID() {
		return getId();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	public void setCode(String value) {
		this.code = value;
	}
	
	public String getCode() {
		return code;
	}
	public void setAttractsInterest(byte value) {
		this.attractsInterest = value;
	}
	
	public byte getAttractsInterest() {
		return attractsInterest;
	}
	
	public String toString() {
		return String.valueOf(getId());
	}
	
}
