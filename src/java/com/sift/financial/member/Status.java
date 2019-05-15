package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;

/**
 * Status entity. @author MyEclipse Persistence Tools
 */

public class Status implements java.io.Serializable {

	// Fields

	private Integer statusId;
	private String statusDesc;
	private String statusShort;
	private String delFlg;
	private String failState;
	private Set members = new HashSet(0);
	private Set statusFlows = new HashSet(0);
	private Set compStockTypes = new HashSet(0);
	

	// Constructors

	/** default constructor */
	public Status() {
	}

	/** minimal constructor */
	public Status(String statusDesc, String statusShort, String delFlg,
			String failState) {
		this.statusDesc = statusDesc;
		this.statusShort = statusShort;
		this.delFlg = delFlg;
		this.failState = failState;
	}

	/** full constructor */
	public Status(String statusDesc, String statusShort, String delFlg,
			String failState, Set members, Set statusFlows,Set compStockTypes) {
		this.statusDesc = statusDesc;
		this.statusShort = statusShort;
		this.delFlg = delFlg;
		this.failState = failState;
		this.members = members;
		this.statusFlows = statusFlows;
		this.compStockTypes = compStockTypes;
	}

	// Property accessors

	public Integer getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getStatusDesc() {
		return this.statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getStatusShort() {
		return this.statusShort;
	}

	public void setStatusShort(String statusShort) {
		this.statusShort = statusShort;
	}

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getFailState() {
		return this.failState;
	}

	public void setFailState(String failState) {
		this.failState = failState;
	}

	public Set getMembers() {
		return this.members;
	}

	public void setMembers(Set members) {
		this.members = members;
	}

	public Set getStatusFlows() {
		return this.statusFlows;
	}

	public void setStatusFlows(Set statusFlows) {
		this.statusFlows = statusFlows;
	}

	public Set getCompStockTypes() {
		return compStockTypes;
	}

	public void setCompStockTypes(Set compStockTypes) {
		this.compStockTypes = compStockTypes;
	}

}