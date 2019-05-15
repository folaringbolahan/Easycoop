package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;

/**
 * PatronageRefundType entity. @author MyEclipse Persistence Tools
 */

public class PatronageRefundType implements java.io.Serializable {

	// Fields

	private Integer patronageRefundTypeId;
	private String patronageRefundTypeName;
	private String patronageRefundTypeShort;
	private String delFlg;
	private Set patronageRefunds = new HashSet(0);

	// Constructors

	/** default constructor */
	public PatronageRefundType() {
	}

	/** minimal constructor */
	public PatronageRefundType(String patronageRefundTypeName,
			String patronageRefundTypeShort, String delFlg) {
		this.patronageRefundTypeName = patronageRefundTypeName;
		this.patronageRefundTypeShort = patronageRefundTypeShort;
		this.delFlg = delFlg;
	}

	/** full constructor */
	public PatronageRefundType(String patronageRefundTypeName,
			String patronageRefundTypeShort, String delFlg, Set patronageRefunds) {
		this.patronageRefundTypeName = patronageRefundTypeName;
		this.patronageRefundTypeShort = patronageRefundTypeShort;
		this.delFlg = delFlg;
		this.patronageRefunds = patronageRefunds;
	}

	// Property accessors

	public Integer getPatronageRefundTypeId() {
		return this.patronageRefundTypeId;
	}

	public void setPatronageRefundTypeId(Integer patronageRefundTypeId) {
		this.patronageRefundTypeId = patronageRefundTypeId;
	}

	public String getPatronageRefundTypeName() {
		return this.patronageRefundTypeName;
	}

	public void setPatronageRefundTypeName(String patronageRefundTypeName) {
		this.patronageRefundTypeName = patronageRefundTypeName;
	}

	public String getPatronageRefundTypeShort() {
		return this.patronageRefundTypeShort;
	}

	public void setPatronageRefundTypeShort(String patronageRefundTypeShort) {
		this.patronageRefundTypeShort = patronageRefundTypeShort;
	}

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public Set getPatronageRefunds() {
		return this.patronageRefunds;
	}

	public void setPatronageRefunds(Set patronageRefunds) {
		this.patronageRefunds = patronageRefunds;
	}

}