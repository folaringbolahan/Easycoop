package com.sift.financial.member;

/**
 * CompStockTypeDetail entity. @author MyEclipse Persistence Tools
 */

public class CompStockTypeDetail implements java.io.Serializable {

	// Fields

	private Integer compStockTypeDetailId;
	private CompStockProperty compStockProperty;
	private CompStockType compStockType;
	private String compStockPptyVal;

	// Constructors

	/** default constructor */
	public CompStockTypeDetail() {
	}

	/** full constructor */
	public CompStockTypeDetail(CompStockProperty compStockProperty,
			CompStockType compStockType, String compStockPptyVal) {
		this.compStockProperty = compStockProperty;
		this.compStockType = compStockType;
		this.compStockPptyVal = compStockPptyVal;
	}

	// Property accessors

	public Integer getCompStockTypeDetailId() {
		return this.compStockTypeDetailId;
	}

	public void setCompStockTypeDetailId(Integer compStockTypeDetailId) {
		this.compStockTypeDetailId = compStockTypeDetailId;
	}

	public CompStockProperty getCompStockProperty() {
		return this.compStockProperty;
	}

	public void setCompStockProperty(CompStockProperty compStockProperty) {
		this.compStockProperty = compStockProperty;
	}

	public CompStockType getCompStockType() {
		return this.compStockType;
	}

	public void setCompStockType(CompStockType compStockType) {
		this.compStockType = compStockType;
	}

	public String getCompStockPptyVal() {
		return this.compStockPptyVal;
	}

	public void setCompStockPptyVal(String compStockPptyVal) {
		this.compStockPptyVal = compStockPptyVal;
	}

}