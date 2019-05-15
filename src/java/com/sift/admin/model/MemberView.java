package com.sift.admin.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MEMBER")
public class MemberView{
    @Id
    @Column(name="MEMBER_ID")
    @GeneratedValue
	private String memberId;
    
    @Column(name="MEMBER_COMP_ID")
	private String compmemberId;
    
    @Column(name="MEMBER_NO")
	private String memberNo;
    
    @Column(name="FIRST_NAME")
	private String firstname;

    @Column(name="MIDDLE_NAME")
	private String middlename;
    
    @Column(name="SURNAME")
	private String surname;
    
    @Column(name="DOB")
	private Date   dob;
    
    @Column(name="RELIGION_ID")
	private String religionId;
    
    @Column(name="MEMBER_TYPE_ID")
	private String memberTypeId;
    
    @Column(name="CREATED_DATE")
	private String createdDate;
    
    @Column(name="CREATED_BY")
	private String createdBy;
    
    @Column(name="DEL_FLG")
	private String delFlg;
    
    @Column(name="DEL_DATE")
	private Date   delDate;
    
    @Column(name="GENDER")
	private String gender;
	
    @Column(name="BRANCH_ID")
	private String branchId;
	
    @Column(name="IDENTIFICATION_ID")
	private String identificationId;
	
    @Column(name="IDENTIFICATION_CODE")
	private String identificationCode;
	
    @Column(name="STATUS_ID")
	private String statusId;
	
    @Column(name="COMPANY_ID")
	private String companyId;
    
    @Column(name="EMAIL_ADD_1")
    private String email;
    
    @Column(name="EMAIL_ADD_2")
    private String email2;
    
    @Column(name="EMAIL_ADD_3")
    private String email3;
    
    @Column(name="PHONE_NO_1")
    private String phone1;
    
    @Column(name="PHONE_NO_2")
    private String phone2;
    
    @Column(name="PHONE_NO_3")
    private String phone3;
    
    @Column(name="BANK_ACCOUNT")
    private String bankAccount;

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMemberId() {
		return memberId;
	}
	
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	public String getCompmemberId() {
		return compmemberId;
	}
	
	public void setCompmemberId(String compmemberId) {
		this.compmemberId = compmemberId;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getMiddlename() {
		return middlename;
	}
	
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public Date getDob() {
		return dob;
	}
	
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	public String getReligionId() {
		return religionId;
	}
	
	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}
	
	public String getMemberTypeId() {
		return memberTypeId;
	}
	public void setMemberTypeId(String memberTypeId) {
		this.memberTypeId = memberTypeId;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getDelFlg() {
		return delFlg;
	}
	
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	public Date getDelDate() {
		return delDate;
	}
	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getIdentificationId(){
		return identificationId;
	}
	
	public void setIdentificationId(String identificationId){
		this.identificationId = identificationId;
	}
	
	public String getIdentificationCode(){
		return identificationCode;
	}
	
	public void setIdentificationCode(String identificationCode){
		this.identificationCode = identificationCode;
	}
	
	public String getStatusId(){
		return statusId;
	}
	
	public void setStatusId(String statusId){
		this.statusId = statusId;
	}
	
	public String getCompanyId(){
		return companyId;
	}
	
	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getEmail3() {
		return email3;
	}

	public void setEmail3(String email3) {
		this.email3 = email3;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}	
}