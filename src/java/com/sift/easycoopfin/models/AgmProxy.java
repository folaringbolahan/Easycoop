/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models;
import java.io.Serializable;

/**
 *
 * @author logzy
 */
public class AgmProxy implements Serializable {
 public AgmProxy(){
     
 }  
        private int id;
	private int memberId;
	private int companyId;
        private int agmId;
        private String firstName;
        private String surname;
        private String middleName;
        private String gender;
        private String address;
        private String phoneNumber;
        private String emailAddress;
	
	private void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	private void setAgmId(int value) {
		this.agmId = value;
	}
	
	public int getAgmId() {
		return agmId;
	}
	public int getORMID() {
		return getId();
	}
	public void setAddress(String value) {
		this.address = value;
	}
	
	public String getAddress() {
		return address;
	}
	public void setCompanyId(int value) {
		this.companyId = value;
	}
	
	public int getCompanyId() {
		return companyId;
	}
	public void setMemberId(int value) {
		this.memberId = value;
	}
	
	public int getMemberId() {
		return memberId;
	}
        public String getFirstName(){
            return firstName;
        }
        public void setFirstName(String value){
            this.firstName = value;
        }
        public String getSurname(){
            return surname;
        }
        public void setSurname(String value){
            this.surname = value;
        }
        public String getMiddleName(){
            return middleName;
        }
        public void setMiddleName(String value){
            this.middleName = value;
        }
        public String getGender(){
            return gender;
        }
        public void setGender(String value){
            this.gender = value;
        }
        public void setPhoneNumber(String value){
            this.phoneNumber = value;
        }
        public String getPhoneNumber(){
            return phoneNumber;
        }
         public void setEmailAddress(String value){
            this.emailAddress = value;
        }
        public String getEmailAddress(){
            return emailAddress;
        }
	public String toString() {
		return String.valueOf(getId());
	}
}
