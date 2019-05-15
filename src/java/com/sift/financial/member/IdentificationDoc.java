package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;

/**
 * IdentificationDoc entity. @author MyEclipse Persistence Tools
 */

public class IdentificationDoc implements java.io.Serializable {

	// Fields

	private Integer identificationDocId;
	private String identificationDocName;
	private String identificationDocDesc;
	private String delFlg;
	private Integer countryId;
	private Set members = new HashSet(0);

	// Constructors

	/** default constructor */
	public IdentificationDoc() {
	}

	/** minimal constructor */
	public IdentificationDoc(String identificationDocName, String delFlg
			) {
		this.identificationDocName = identificationDocName;
		this.delFlg = delFlg;
	}

	/** full constructor */
	public IdentificationDoc(String identificationDocName,
			String identificationDocDesc, String delFlg, Integer countryId,
			Set members) {
		this.identificationDocName = identificationDocName;
		this.identificationDocDesc = identificationDocDesc;
		this.delFlg = delFlg;
		this.countryId = countryId;
		this.members = members;
	}

	// Property accessors

	public Integer getIdentificationDocId() {
		return this.identificationDocId;
	}

	public void setIdentificationDocId(Integer identificationDocId) {
		this.identificationDocId = identificationDocId;
	}

	public String getIdentificationDocName() {
		return this.identificationDocName;
	}

	public void setIdentificationDocName(String identificationDocName) {
		this.identificationDocName = identificationDocName;
	}

	public String getIdentificationDocDesc() {
		return this.identificationDocDesc;
	}

	public void setIdentificationDocDesc(String identificationDocDesc) {
		this.identificationDocDesc = identificationDocDesc;
	}

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public Integer getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Set getMembers() {
		return this.members;
	}

	public void setMembers(Set members) {
		this.members = members;
	}

}