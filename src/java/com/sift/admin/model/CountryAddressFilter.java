package com.sift.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="COUNTRY_ADDRESS_FILTER")
public class CountryAddressFilter{    
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
    
    @Column(name="COUNTRY_ID")
    private String countryId;

    @Column(name="ADDR_FIELD_NAME")
    private String addrFieldName;

    @Column(name="ADDR_FIELD_IDX")
    private String addrFieldIndx;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public String getCountryId(){
		return countryId;
	}

	public void setCountryId(String countryId){
		this.countryId = countryId;
	}

	public String getAddrFieldName(){
		return addrFieldName;
	}

	public void setAddrFieldName(String addrFieldName){
		this.addrFieldName = addrFieldName;
	}

	public String getAddrFieldIndx(){
		return addrFieldIndx;
	}

	public void setAddrFieldIndx(String addrFieldIndx){
		this.addrFieldIndx = addrFieldIndx;
	}
 }