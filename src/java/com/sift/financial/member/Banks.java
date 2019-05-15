package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;


/**
 * Banks entity. @author MyEclipse Persistence Tools
 */

public class Banks  implements java.io.Serializable {


    // Fields    

     private Integer bankId;
     private Countries countries;
     private String bankName;
     private String delFlg;
     private Set members = new HashSet(0);
     private String bankCode;


    // Constructors

    /** default constructor */
    public Banks() {
    }

	/** minimal constructor */
    public Banks(Countries countries, String bankName, String delFlg,String bankCode) {
        this.countries = countries;
        this.bankName = bankName;
        this.delFlg = delFlg;
        this.bankCode = bankCode;
    }
    
    /** full constructor */
    public Banks(Countries countries, String bankName, String delFlg, Set members,String bankCode) {
        this.countries = countries;
        this.bankName = bankName;
        this.delFlg = delFlg;
        this.members = members;
        this.bankCode = bankCode;
    }

   
    // Property accessors

    public Integer getBankId() {
        return this.bankId;
    }
    
    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Countries getCountries() {
        return this.countries;
    }
    
    public void setCountries(Countries countries) {
        this.countries = countries;
    }

    public String getBankName() {
        return this.bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDelFlg() {
        return this.delFlg;
    }
    
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public Set getMembers() {
        return this.members;
    }
    
    public void setMembers(Set members) {
        this.members = members;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
   
}