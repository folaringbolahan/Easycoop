package com.sift.financial.member;

/**
 * CountryAddressFilter entity. @author MyEclipse Persistence Tools
 */

public class CountryAddressFilter implements java.io.Serializable {

	// Fields

	private Integer id;
	private Countries countries;
	private String addrFieldIdx;
	private String addrFieldName;

	// Constructors

	/** default constructor */
	public CountryAddressFilter() {
	}

	/** full constructor */
	public CountryAddressFilter(Countries countries, String addrFieldIdx,
			String addrFieldName) {
		this.countries = countries;
		this.addrFieldIdx = addrFieldIdx;
		this.addrFieldName = addrFieldName;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Countries getCountries() {
		return this.countries;
	}

	public void setCountries(Countries countries) {
		this.countries = countries;
	}

	public String getAddrFieldIdx() {
		return this.addrFieldIdx;
	}

	public void setAddrFieldIdx(String addrFieldIdx) {
		this.addrFieldIdx = addrFieldIdx;
	}

	public String getAddrFieldName() {
		return this.addrFieldName;
	}

	public void setAddrFieldName(String addrFieldName) {
		this.addrFieldName = addrFieldName;
	}

}