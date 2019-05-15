
package com.sift.easycoopfin.models;

import java.io.Serializable;
public class Member implements Serializable {
	public Member() {
	}
	
	private int id;
	private int branchId;
	private int companyId;
        private String firstName;
        private String surname;
        private String middleName;
        private String gender;
        private String memberNo;
        private String phoneNumber;
        private String emailAddress;
	private int statusId;
        
	private void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	private void setStatusId(int value) {
		this.statusId = value;
	}
	
	public int getStatusId() {
		return statusId;
	}
	
	public int getORMID() {
		return getId();
	}
	public void setMemberNo(String value) {
		this.memberNo = value;
	}
	
	public String getMemberNo() {
		return memberNo;
	}
	public void setCompanyId(int value) {
		this.companyId = value;
	}
	
	public int getCompanyId() {
		return companyId;
	}
	public void setBranchId(int value) {
		this.branchId = value;
	}
	
	public int getBranchId() {
		return branchId;
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
