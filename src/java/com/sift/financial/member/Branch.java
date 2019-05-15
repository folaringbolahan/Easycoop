package com.sift.financial.member;

import java.util.*;
import java.sql.*;

/**
 * Branch entity. @author MyEclipse Persistence Tools
 */

public class Branch implements java.io.Serializable {

	// Fields
	//private Integer branchId;
        private Integer Id;
	private Company company;
	private String branchName;
	//private String delFlg;
        private String deleted;
        private String active;
	private Set members = new HashSet(0);
	private String countryId;
        private String branchCode;
        private String createdBy;
        private Timestamp creationDate;
        private String email;
        private Timestamp lastModificationDate;
        private String lastModifiedBy;
        private String phone1;
        private String phone2;
        private String baseCurrency;
        private Timestamp setupDate;
        private Timestamp postDate;
        private Timestamp entryDate;
        private Timestamp currentSystemDate;
        private Integer  currentPeriod;
        private Integer  currentYear;
        private Integer  accountingPeriods;
        private String  startofDay;
        private String  endofDay;
        private Integer  lastPeriod;
        private Integer  lastYear;
        private java.util.Date  lastPostDate;
        private java.util.Date  period1StartDate;
      // private String connectEasycoop;
                
	// Constructors

	/** default constructor */
	public Branch() {
	}

	/** minimal constructor */
	public Branch(Company company, String branchName, String deleted) {
		this.company = company;
		this.branchName = branchName;
		this.deleted = deleted;
	}

	/** full constructor */
	public Branch(Company company, String branchName, String deleted, Set members) {
		this.company = company;
		this.branchName = branchName;
		this.deleted = deleted;
		this.members = members;
	}

	// Property accessors

	public Integer getId() {
		return this.Id;
	}

	public void setId(Integer Id) {
		this.Id = Id;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getDeleted() {
		return this.deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Set getMembers() {
		return this.members;
	}

	public void setMembers(Set members) {
		this.members = members;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Timestamp lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Timestamp getSetupDate() {
        return setupDate;
    }

    public void setSetupDate(Timestamp setupDate) {
        this.setupDate = setupDate;
    }

    public Timestamp getPostDate() {
        return postDate;
    }

    public void setPostDate(Timestamp postDate) {
        this.postDate = postDate;
    }

    public Timestamp getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Timestamp entryDate) {
        this.entryDate = entryDate;
    }

    public Timestamp getCurrentSystemDate() {
        return currentSystemDate;
    }

    public void setCurrentSystemDate(Timestamp currentSystemDate) {
        this.currentSystemDate = currentSystemDate;
    }

    public Integer getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(Integer currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public Integer getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Integer currentYear) {
        this.currentYear = currentYear;
    }

    public Integer getAccountingPeriods() {
        return accountingPeriods;
    }

    public void setAccountingPeriods(Integer accountingPeriods) {
        this.accountingPeriods = accountingPeriods;
    }

    public String getStartofDay() {
        return startofDay;
    }

    public void setStartofDay(String startofDay) {
        this.startofDay = startofDay;
    }

    public String getEndofDay() {
        return endofDay;
    }

    public void setEndofDay(String endofDay) {
        this.endofDay = endofDay;
    }

    public Integer getLastPeriod() {
        return lastPeriod;
    }

    public void setLastPeriod(Integer lastPeriod) {
        this.lastPeriod = lastPeriod;
    }

    public Integer getLastYear() {
        return lastYear;
    }

    public void setLastYear(Integer lastYear) {
        this.lastYear = lastYear;
    }

    public java.util.Date getLastPostDate() {
        return lastPostDate;
    }

    public void setLastPostDate(java.util.Date lastPostDate) {
        this.lastPostDate = lastPostDate;
    }

    public java.util.Date getPeriod1StartDate() {
        return period1StartDate;
    }

    public void setPeriod1StartDate(java.util.Date period1StartDate) {
        this.period1StartDate = period1StartDate;
    }

   // public String getConnectEasycoop() {
   //     return connectEasycoop;
  //  }

   // public void setConnectEasycoop(String connectEasycoop) {
   //     this.connectEasycoop = connectEasycoop;
   // }
        
        

}