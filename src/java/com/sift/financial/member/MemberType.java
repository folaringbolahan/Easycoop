package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;

/**
 * MemberType entity. @author MyEclipse Persistence Tools
 */

public class MemberType implements java.io.Serializable {

	// Fields

	private Integer memberTypeId;
	private String memberTypeVal;
	private String memberTypeDesc;
	private Set members = new HashSet(0);

	// Constructors

	/** default constructor */
	public MemberType() {
	}

	/** minimal constructor */
	public MemberType(String memberTypeVal, String memberTypeDesc) {
		this.memberTypeVal = memberTypeVal;
		this.memberTypeDesc = memberTypeDesc;
	}

	/** full constructor */
	public MemberType(String memberTypeVal, String memberTypeDesc, Set members) {
		this.memberTypeVal = memberTypeVal;
		this.memberTypeDesc = memberTypeDesc;
		this.members = members;
	}

	// Property accessors

	public Integer getMemberTypeId() {
		return this.memberTypeId;
	}

	public void setMemberTypeId(Integer memberTypeId) {
		this.memberTypeId = memberTypeId;
	}

	public String getMemberTypeVal() {
		return this.memberTypeVal;
	}

	public void setMemberTypeVal(String memberTypeVal) {
		this.memberTypeVal = memberTypeVal;
	}

	public String getMemberTypeDesc() {
		return this.memberTypeDesc;
	}

	public void setMemberTypeDesc(String memberTypeDesc) {
		this.memberTypeDesc = memberTypeDesc;
	}

	public Set getMembers() {
		return this.members;
	}

	public void setMembers(Set members) {
		this.members = members;
	}

}