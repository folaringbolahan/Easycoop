package com.sift.gl.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Branch{  	
	private Integer id;
	
    private Integer companyId;
    private String branchCode;
    private String branchName;
    private Integer countryId;
    private String phone1; 
    private String phone2;
    private String email;
	
    private String active;    
    private String deleted;
    private Date creationDate;
    private String createdBy;
    private Date lastModificationDate;
    private String lastModifiedBy;
    
    private Date setupDate;
     private Date postDate;
     private Date entryDate;
     private Date currentsystemDate;
     private int currentPeriod;
     private int currentYear;
     private int lastPeriod;
     private int lastYear;
     private String baseCurrency;
     private int accountingPeriods;
     private String startofDay;
     private String endofDay;

     	
    public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public String getBranchCode() {
		return branchCode;
	}
	
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
	public String getBranchName() {
		return branchName;
	}
	
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public Integer getCountryId() {
		return countryId;
	}
	
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getActive() {
		return active;
	}
	
	public void setActive(String active) {
		this.active = active;
	}
	
	public String getDeleted() {
		return deleted;
	}
	
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getLastModificationDate() {
		return lastModificationDate;
	}
	
	public void setLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}
	
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDatex) {
        postDate = postDatex;
    }
    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDatex) {
        entryDate = entryDatex;
    }
    public int getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(int currentPeriodx) {
        currentPeriod = currentPeriodx;
    }
    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYearx) {
        currentYear = currentYearx;
    }
    public int getLastPeriod() {
        return lastPeriod;
    }
    public void setLastPeriod(int lastPeriodx) {
        lastPeriod = lastPeriodx;
    }
    public int getLastYear() {
        return lastYear;
    }
    public void setLastYear(int lastYearx) {
        lastYear = lastYearx;
    }
    public String getBaseCurrency() {
        return baseCurrency;
    }
    public void setBaseCurrency(String baseCurrencyx) {
        baseCurrency = baseCurrencyx;
    }
    public String getstartofDay() {
        return startofDay;
    }
    public void setstartofDay(String startofDayx) {
        startofDay = startofDayx;
    }
    public String getendofDay() {
        return endofDay;
    }
    public void setendofDay(String endofDayx) {
        endofDay = endofDayx;
    }
    public int getaccountingPeriods() {
        return accountingPeriods;
    }

    public void setaccountingPeriods(int accountingPeriodsx) {
        accountingPeriods = accountingPeriodsx;
    }
    public Date getCurrentsystemDate() {
        return currentsystemDate;
    }
    public void setCurrentsystemDate(Date currentsystemDatex) {
        currentsystemDate = currentsystemDatex;
    }    
 }