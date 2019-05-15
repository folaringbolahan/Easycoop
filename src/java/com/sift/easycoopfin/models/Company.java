
package com.sift.easycoopfin.models;

import java.io.Serializable;
public class Company implements Serializable {
	public Company() {
	}
	
	private int id;
	
	private int companyId;
        
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
	
	public void setCompanyId(int value) {
		this.companyId = value;
	}
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCode(String code){
            this.code = code;
        }
        public String getCode(){
            return this.code;
        }
        public void setName(String value){
            this.name = value;
        }
        public String getName(){
            return name;
        }
	public String toString() {
		return String.valueOf(getId());
	}
	
}
