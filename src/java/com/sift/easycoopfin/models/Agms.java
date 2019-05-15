
package com.sift.easycoopfin.models;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Agms implements Serializable {
	public Agms() {
	}
	
	private int id;
	
	private int companyId;
	
	private java.util.Date startDate;
        
	
	private java.util.Date endDate;
	
	private byte isDeleted = 0;
        
        private byte allowProxy = 0;
        
        private String note;
        
        private String stringStartDate;
        
        private String stringEndDate;
        
      
        private java.sql.Time startTime;
        
        private java.sql.Time endTime;
        
        private String stringStartTime;
        
        private String stringEndTime;
        //private List<Agmattendees> attendees;
	
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
	
	public void setStartDate(java.util.Date value) {
		this.startDate = value;
	}
	
	public java.util.Date getStartDate() {
		return startDate;
	}
	
	public void setEndDate(java.util.Date value) {
		this.endDate = value;
	}
	
	public java.util.Date getEndDate() {
		return endDate;
	}
	public void setStartTime(java.sql.Time value){
            this.startTime = value;
        }
        public java.sql.Time getStartTime(){
            return startTime;
        }
        public void setEndTime(java.sql.Time value){
            this.endTime = value;
        }
        public java.sql.Time getEndTime(){
            return endTime;
        }
        public void setStringStartTime(String value){
            this.stringStartTime = value;
        }
        public String getStringStartTime(){
            return stringStartTime;
        }
        public void setStringEndTime(String value){
            this.stringEndTime = value;
        }
        public String getStringEndTime(){
            return stringEndTime;
        } 
	public void setIsDeleted(byte value) {
		this.isDeleted = value;
	}
	
	public byte getIsDeleted() {
		return isDeleted;
	}
	public void setAllowProxy(byte value) {
		this.allowProxy = value;
	}
	
	public byte getAllowProxy() {
		return allowProxy;
	}
        public String getNote(){
            return note;
        }
        public void setNote(String value){
            this.note = value;
        }
        public String getStringStartDate()
        {
            return stringStartDate;
        }
        public void setStringStartDate(String value){
            this.stringStartDate = value;
        }
        public String getStringEndDate()
        {
            return stringEndDate;
        }
        public void setStringEndDate(String value){
            this.stringEndDate = value;
        }
       
	public String toString() {
		return String.valueOf(getId());
	}
	
}
