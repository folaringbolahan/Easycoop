package com.sift.gl.model;



import java.util.Date;


public class Company{    

    private Integer id;
    private String regNo;
    private String name;
    private String  code;
    private String shortName;
    private String countryId;
    private String phone1;
    private String phone2;
    private String email;
    private String fax;
    private String website;
    private Date creationDate;
    private String createdBy;
    private String active;
    private String deleted;
    private Date lastModificationDate;
    private String lastModifiedBy;
    //
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
     private String timezone;
     private String procmethod;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}   
        
        
        
    public Date getSetupDate() {
        return setupDate;
    }

    public void setSetupDate(Date setupDatex) {
        setupDate = setupDatex;
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
    public String getStartofDay() {
        return startofDay;
    }

    public void setStartofDay(String startofDayx) {
        startofDay = startofDayx;
    }
    public String getEndofDay() {
        return endofDay;
    }

    public void setEndofDay(String endofDayx) {
        endofDay = endofDayx;
    }
    public int getAccountingPeriods() {
        return accountingPeriods;
    }

    public void setAccountingPeriods(int accountingPeriodsx) {
        accountingPeriods = accountingPeriodsx;
    }
    public Date getCurrentsystemDate() {
        return currentsystemDate;
    }
    public void setCurrentsystemDate(Date currentsystemDatex) {
        currentsystemDate = currentsystemDatex;
    }
    
     public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
    
     public String getProcmethod() {
        return procmethod;
    }

    public void setProcmethod(String procmethod) {
        this.procmethod = procmethod;
    }
 }