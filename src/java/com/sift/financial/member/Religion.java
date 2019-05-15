package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;


/**
 * Religion entity. @author MyEclipse Persistence Tools
 */

public class Religion  implements java.io.Serializable {


    // Fields    

     private Integer religionId;
     private Countries countries;
     private String religionName;
     private String religionDesc;
     private String delFlg;
     private Set members = new HashSet(0);


    // Constructors

    /** default constructor */
    public Religion() {
    }

	/** minimal constructor */
    public Religion(Countries countries, String religionName, String delFlg) {
        this.countries = countries;
        this.religionName = religionName;
        this.delFlg = delFlg;
    }
    
    /** full constructor */
    public Religion(Countries countries, String religionName, String religionDesc, String delFlg, Set members) {
        this.countries = countries;
        this.religionName = religionName;
        this.religionDesc = religionDesc;
        this.delFlg = delFlg;
        this.members = members;
    }

   
    // Property accessors

    public Integer getReligionId() {
        return this.religionId;
    }
    
    public void setReligionId(Integer religionId) {
        this.religionId = religionId;
    }

    public Countries getCountries() {
        return this.countries;
    }
    
    public void setCountries(Countries countries) {
        this.countries = countries;
    }

    public String getReligionName() {
        return this.religionName;
    }
    
    public void setReligionName(String religionName) {
        this.religionName = religionName;
    }

    public String getReligionDesc() {
        return this.religionDesc;
    }
    
    public void setReligionDesc(String religionDesc) {
        this.religionDesc = religionDesc;
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
   








}