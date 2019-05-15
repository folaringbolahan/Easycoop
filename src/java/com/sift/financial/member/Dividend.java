package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Dividend entity. @author MyEclipse Persistence Tools
 */

public class Dividend implements java.io.Serializable {

	// Fields

	private Integer dividendId;
	private DividendType dividendType;
        private Banks banks;
	private Status status;
	private Integer companyId;
	private Integer branchId;
	private String divYear;
	private String divPeriod;
	private String formula;
	private String divValue;
	private Date divDeclarationDate;
	private Date exDividendDate;
	private Date divDateRecord;
	private Date divPayDate;
	private Timestamp createdDate;
	private String createdBy;
	private Timestamp approvedDate;
	private String approvedBy;
        private Timestamp modifiedDate;
	private String modifiedBy;
	private String dividendPayAccount;
	private String divNumber;
        private String divRetainedEarningsAcct;
        private String divPayableAcct;
        private String divCashAcct;
        private Double divPayable;
        private Double actualDivPayable;
       
	private transient String action;
	private transient Integer countryId;
        private transient String divoperand;
        private String postEntries;
        private transient Double theDivPay;

	// Constructors

	/** default constructor */
	public Dividend() {
	}

	/** minimal constructor */
	public Dividend(DividendType dividendType, Status status,  Banks banks,
			Integer companyId, Integer branchId, String divYear,
			String divPeriod, String formula, String divValue,
			Date divDeclarationDate, Date exDividendDate, Date divDateRecord,
			Date divPayDate, Timestamp createdDate, String createdBy, String postEntries) {
		this.dividendType = dividendType;
		this.status = status;
		this.companyId = companyId;
		this.branchId = branchId;
		this.divYear = divYear;
		this.divPeriod = divPeriod;
		this.formula = formula;
		this.divValue = divValue;
		this.divDeclarationDate = divDeclarationDate;
		this.exDividendDate = exDividendDate;
		this.divDateRecord = divDateRecord;
		this.divPayDate = divPayDate;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
                this.banks=  banks;
                this.postEntries = postEntries;
	}

    public String getDivoperand() {
        return divoperand;
    }

    public void setDivoperand(String divoperand) {
        this.divoperand = divoperand;
    }

	/** full constructor */
	public Dividend(DividendType dividendType, Status status, Banks banks,
			Integer companyId, Integer branchId, String divYear,
			String divPeriod, String formula, String divValue,
			Date divDeclarationDate, Date exDividendDate, Date divDateRecord,
			Date divPayDate, Timestamp createdDate, String createdBy,
			Timestamp approvedDate, String approvedBy, String dividendPayAccount, String divNumber,Timestamp modifiedDate, String modifiedBy, String postEntries, Double divPayable, Double actualDivPayable) {
		this.dividendType = dividendType;
		this.status = status;
		this.companyId = companyId;
		this.branchId = branchId;
		this.divYear = divYear;
		this.divPeriod = divPeriod;
		this.formula = formula;
		this.divValue = divValue;
		this.divDeclarationDate = divDeclarationDate;
		this.exDividendDate = exDividendDate;
		this.divDateRecord = divDateRecord;
		this.divPayDate = divPayDate;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.approvedDate = approvedDate;
		this.approvedBy = approvedBy;
		this.dividendPayAccount = dividendPayAccount;
		this.divNumber =  divNumber;
                this.modifiedDate= modifiedDate;
                this.modifiedBy= modifiedBy;
                this.banks=  banks;
                this.postEntries = postEntries;
                this.divPayable = divPayable;
                this.actualDivPayable = actualDivPayable;
	}

	// Property accessors

	public Integer getDividendId() {
		return this.dividendId;
	}

	public void setDividendId(Integer dividendId) {
		this.dividendId = dividendId;
	}

	public DividendType getDividendType() {
		return this.dividendType;
	}

	public void setDividendType(DividendType dividendType) {
		this.dividendType = dividendType;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getBranchId() {
		return this.branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public String getDivYear() {
		return this.divYear;
	}

	public void setDivYear(String divYear) {
		this.divYear = divYear;
	}

	public String getDivPeriod() {
		return this.divPeriod;
	}

	public void setDivPeriod(String divPeriod) {
		this.divPeriod = divPeriod;
	}

	public String getFormula() {
		return this.formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getDivValue() {
		return this.divValue;
	}

	public void setDivValue(String divValue) {
		this.divValue = divValue;
	}

	public Date getDivDeclarationDate() {
		return this.divDeclarationDate;
	}

	public void setDivDeclarationDate(Date divDeclarationDate) {
		this.divDeclarationDate = divDeclarationDate;
	}

	public Date getExDividendDate() {
		return this.exDividendDate;
	}

	public void setExDividendDate(Date exDividendDate) {
		this.exDividendDate = exDividendDate;
	}

	public Date getDivDateRecord() {
		return this.divDateRecord;
	}

	public void setDivDateRecord(Date divDateRecord) {
		this.divDateRecord = divDateRecord;
	}

	public Date getDivPayDate() {
		return this.divPayDate;
	}

	public void setDivPayDate(Date divPayDate) {
		this.divPayDate = divPayDate;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getApprovedDate() {
		return this.approvedDate;
	}

	public void setApprovedDate(Timestamp approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDividendPayAccount() {
		return dividendPayAccount;
	}

	public void setDividendPayAccount(String dividendPayAccount) {
		this.dividendPayAccount = dividendPayAccount;
	}

	public String getDivNumber() {
		return divNumber;
	}

	public void setDivNumber(String divNumber) {
		this.divNumber = divNumber;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Banks getBanks() {
        return banks;
    }

    public void setBanks(Banks banks) {
        this.banks = banks;
    }

    public String getDivRetainedEarningsAcct() {
        return divRetainedEarningsAcct;
    }

    public void setDivRetainedEarningsAcct(String divRetainedEarningsAcct) {
        this.divRetainedEarningsAcct = divRetainedEarningsAcct;
    }

    public String getDivPayableAcct() {
        return divPayableAcct;
    }

    public void setDivPayableAcct(String divPayableAcct) {
        this.divPayableAcct = divPayableAcct;
    }

    public String getDivCashAcct() {
        return divCashAcct;
    }

    public void setDivCashAcct(String divCashAcct) {
        this.divCashAcct = divCashAcct;
    }

    public Double getDivPayable() {
        return divPayable;
    }

    public void setDivPayable(Double divPayable) {
        this.divPayable = divPayable;
    }

    public String getPostEntries() {
        return postEntries;
    }

    public void setPostEntries(String postEntries) {
        this.postEntries = postEntries;
    }

    public Double getActualDivPayable() {
        return actualDivPayable;
    }

    public void setActualDivPayable(Double actualDivPayable) {
        this.actualDivPayable = actualDivPayable;
    }

    public Double getTheDivPay() {
        return theDivPay;
    }

    public void setTheDivPay(Double theDivPay) {
        this.theDivPay = theDivPay;
    }

  
    

}