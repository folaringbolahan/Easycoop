package com.sift.hp.model;

import java.sql.Timestamp;
import java.util.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Member entity. @author MyEclipse Persistence Tools
 */

public class Member implements java.io.Serializable {

	// Fields

	private Integer memberId;
	private Integer companyid;
	private Integer branchid;
	private String memberCompId;
	private String firstName;
	private String middleName;
	private String surname;
        private String names;
	private Date dob;
	private Date createdDate;
	private String createdBy;
	private String delFlg;
	private Date delDate;
	private String gender;
	private String identificationCode;
	private String phoneNo1;
	private String phoneNo2;
	private String phoneNo3;
	private String emailAdd1;
	private String emailAdd2;
	private String emailAdd3;
	private String memberNo;
	private Set memberHoldingsMovements = new HashSet(0);

	private String  bankAccount;
        
        
	// Constructors

	/** default constructor */
	public Member() {
	}

	/** minimal constructor */
	public Member(String memberCompId, String firstName, String surname,Integer companyid, Integer branchid) {
		this.companyid = companyid;
		this.branchid = branchid;
		this.memberCompId = memberCompId;
		this.firstName = firstName;
		this.surname = surname;
	}

	/** full constructor */
	
	// Property accessors

	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public Integer getBranchid() {
		return this.branchid;
	}

	public void setBranchid(Integer branchid) {
		this.branchid = branchid;
	}

	public String getMemberCompId() {
		return this.memberCompId;
	}

	public void setMemberCompId(String memberCompId) {
		this.memberCompId = memberCompId;
	}
        public String getNames() {
		return this.names;
	}

	public void setNames(String names) {
		this.names = names;
	}
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public Date getDelDate() {
		return this.delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdentificationCode() {
		return this.identificationCode;
	}

	public void setIdentificationCode(String identificationCode) {
		this.identificationCode = identificationCode;
	}

	public String getPhoneNo1() {
		return this.phoneNo1;
	}

	public void setPhoneNo1(String phoneNo1) {
		this.phoneNo1 = phoneNo1;
	}

	public String getPhoneNo2() {
		return this.phoneNo2;
	}

	public void setPhoneNo2(String phoneNo2) {
		this.phoneNo2 = phoneNo2;
	}

	public String getPhoneNo3() {
		return this.phoneNo3;
	}

	public void setPhoneNo3(String phoneNo3) {
		this.phoneNo3 = phoneNo3;
	}

	public String getEmailAdd1() {
		return this.emailAdd1;
	}

	public void setEmailAdd1(String emailAdd1) {
		this.emailAdd1 = emailAdd1;
	}

	public String getEmailAdd2() {
		return this.emailAdd2;
	}

	public void setEmailAdd2(String emailAdd2) {
		this.emailAdd2 = emailAdd2;
	}

	public String getEmailAdd3() {
		return this.emailAdd3;
	}

	public void setEmailAdd3(String emailAdd3) {
		this.emailAdd3 = emailAdd3;
	}

	public String getMemberNo() {
		return this.memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

}