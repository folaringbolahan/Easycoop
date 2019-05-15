package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;

/**
 * DividendType entity. @author MyEclipse Persistence Tools
 */

public class DividendType implements java.io.Serializable {

	// Fields

	private Integer dividendTypeId;
	private String dividendTypeName;
	private String delFlg;
	private String dividendTypeCode;
	private Set dividends = new HashSet(0);

	// Constructors

	/** default constructor */
	public DividendType() {
	}

	/** minimal constructor */
	public DividendType(String dividendTypeName, String delFlg,
			String dividendTypeCode) {
		this.dividendTypeName = dividendTypeName;
		this.delFlg = delFlg;
		this.dividendTypeCode = dividendTypeCode;
	}

	/** full constructor */
	public DividendType(String dividendTypeName, String delFlg,
			String dividendTypeCode, Set dividends) {
		this.dividendTypeName = dividendTypeName;
		this.delFlg = delFlg;
		this.dividendTypeCode = dividendTypeCode;
		this.dividends = dividends;
	}

	// Property accessors

	public Integer getDividendTypeId() {
		return this.dividendTypeId;
	}

	public void setDividendTypeId(Integer dividendTypeId) {
		this.dividendTypeId = dividendTypeId;
	}

	public String getDividendTypeName() {
		return this.dividendTypeName;
	}

	public void setDividendTypeName(String dividendTypeName) {
		this.dividendTypeName = dividendTypeName;
	}

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getDividendTypeCode() {
		return this.dividendTypeCode;
	}

	public void setDividendTypeCode(String dividendTypeCode) {
		this.dividendTypeCode = dividendTypeCode;
	}

	public Set getDividends() {
		return this.dividends;
	}

	public void setDividends(Set dividends) {
		this.dividends = dividends;
	}

}