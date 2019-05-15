package com.sift.financial.member;

import java.util.Date;

/**
 * DividendSchedule entity. @author MyEclipse Persistence Tools
 */

public class DividendSchedule implements java.io.Serializable {

	// Fields

	private Integer dividendScheduleId;
	private Integer dividendId;
	private Date effectiveDate;
	private String memberNo;
	private String paid;
	private String companyCode;
	private String branchCode;
	private Double dividendGrossValue;
	private String dividendType;
	private Double dividendNetValue;
	private Double dividendTaxValue;
	private Date paidDate;
	private String payChannel;
	private String paidBy;
	private Double dividendTax;
        private String stockShort;
        private Integer memberId;
        
        private String createdBy;
        private Date createdDate;
        private String approvedBy;
        private Date approvedDate;
        private String modifiedBy;
        private Date modifiedDate;
        
	// Constructors

	/** default constructor */
	public DividendSchedule() {
	}

	/** minimal constructor */
	public DividendSchedule(Integer dividendId, Date createdDate,
			String memberNo, String paid, String companyCode,
			String branchCode, Double dividendGrossValue, String dividendType,
			Double dividendNetValue, Double dividendTaxValue, Double dividendTax, String stockShort, Integer memberId, String createdBy ) {
		this.dividendId = dividendId;
		this.createdDate = createdDate;
		this.memberNo = memberNo;
		this.paid = paid;
		this.companyCode = companyCode;
		this.branchCode = branchCode;
		this.dividendGrossValue = dividendGrossValue;
		this.dividendType = dividendType;
		this.dividendNetValue = dividendNetValue;
		this.dividendTaxValue = dividendTaxValue;
		this.dividendTax = dividendTax;
                this.stockShort = stockShort;
                this.memberId = memberId;
                this.createdBy = createdBy;
	}

	/** full constructor */
	public DividendSchedule(Integer dividendId, Date effectiveDate,
			String memberNo, String paid, String companyCode,
			String branchCode, Double dividendGrossValue, String dividendType,
			Double dividendNetValue, Double dividendTaxValue, Date paidDate,
			String payChannel, String paidBy, Double dividendTax, String stockShort, Integer memberId,Date createdDate,String createdBy,Date approvedDate,String approvedBy,Date modifiedDate,String modifiedBy) {
		this.dividendId = dividendId;
		this.effectiveDate = effectiveDate;
		this.memberNo = memberNo;
		this.paid = paid;
		this.companyCode = companyCode;
		this.branchCode = branchCode;
		this.dividendGrossValue = dividendGrossValue;
		this.dividendType = dividendType;
		this.dividendNetValue = dividendNetValue;
		this.dividendTaxValue = dividendTaxValue;
		this.paidDate = paidDate;
		this.payChannel = payChannel;
		this.paidBy = paidBy;
		this.dividendTax = dividendTax;
                this.stockShort = stockShort;
                this.memberId = memberId;
                this.paidDate = createdDate;
		this.paidBy = createdBy;
                this.paidDate = approvedDate;
		this.paidBy = approvedBy;
                this.paidDate = modifiedDate;
		this.paidBy = modifiedBy;
	}

	// Property accessors

	public Integer getDividendScheduleId() {
		return this.dividendScheduleId;
	}

	public void setDividendScheduleId(Integer dividendScheduleId) {
		this.dividendScheduleId = dividendScheduleId;
	}

	public Integer getDividendId() {
		return this.dividendId;
	}

	public void setDividendId(Integer dividendId) {
		this.dividendId = dividendId;
	}

	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getMemberNo() {
		return this.memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getPaid() {
		return this.paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBranchCode() {
		return this.branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Double getDividendGrossValue() {
		return this.dividendGrossValue;
	}

	public void setDividendGrossValue(Double dividendGrossValue) {
		this.dividendGrossValue = dividendGrossValue;
	}

	public String getDividendType() {
		return this.dividendType;
	}

	public void setDividendType(String dividendType) {
		this.dividendType = dividendType;
	}

	public Double getDividendNetValue() {
		return this.dividendNetValue;
	}

	public void setDividendNetValue(Double dividendNetValue) {
		this.dividendNetValue = dividendNetValue;
	}

	public Double getDividendTaxValue() {
		return this.dividendTaxValue;
	}

	public void setDividendTaxValue(Double dividendTaxValue) {
		this.dividendTaxValue = dividendTaxValue;
	}

	public Date getPaidDate() {
		return this.paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public String getPayChannel() {
		return this.payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getPaidBy() {
		return this.paidBy;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}

	public Double getDividendTax() {
		return this.dividendTax;
	}

	public void setDividendTax(Double dividendTax) {
		this.dividendTax = dividendTax;
	}

    public String getStockShort() {
        return stockShort;
    }

    public void setStockShort(String stockShort) {
        this.stockShort = stockShort;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    
        
}