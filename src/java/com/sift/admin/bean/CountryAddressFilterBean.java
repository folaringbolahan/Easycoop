package com.sift.admin.bean;

public class CountryAddressFilterBean {
    private Integer id;
    private String countryId;
    private String addrFieldName;
    private String addrFieldIndx;
    private String countryName;
    private String countryCode;
	
    public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getAddrFieldName() {
		return addrFieldName;
	}
	public void setAddrFieldName(String addrFieldName) {
		this.addrFieldName = addrFieldName;
	}
	public String getAddrFieldIndx() {
		return addrFieldIndx;
	}
	public void setAddrFieldIndx(String addrFieldIndx) {
		this.addrFieldIndx = addrFieldIndx;
	}    
}