package com.sift.financial.member;

import java.sql.Timestamp;

/**
 * MemberContribution entity. @author MyEclipse Persistence Tools
 */

public class MemberContribution implements java.io.Serializable {

	// Fields

	private Integer memberContribId;
	private Member member;
	private Double memberContribValue;
	private String createdBy;
	private Timestamp createdDate;
	private String modifiedBy;
	private Timestamp modifiedDate;
	private String approvedBy;
	private Timestamp approvedDate;
        private String contribProd;
        private Integer companyId;
        private Integer branchId;
        private Integer countryId;

	// Constructors

	/** default constructor */
	public MemberContribution() {
	}

	/** minimal constructor */
	public MemberContribution(Member member, Double memberContribValue,
			String createdBy, Timestamp createdDate, String contribProd, Integer companyId,Integer branchId, Integer countryId) {
		this.member= member;
		this.memberContribValue = memberContribValue;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
                this.contribProd = contribProd;
                this.countryId =countryId;
                this.companyId =companyId;
                this.branchId =branchId;
	}

	/** full constructor */
	public MemberContribution(Member member, Double memberContribValue,
			String createdBy, Timestamp createdDate, String modifiedBy,
			Timestamp modifiedDate, String approvedBy, Timestamp approvedDate, String contribProd , Integer companyId,Integer branchId, Integer countryId) {
		this.member = member;
		this.memberContribValue = memberContribValue;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
                this.contribProd = contribProd;
                this.countryId =countryId;
                this.companyId =companyId;
                this.branchId =branchId;
	}

	// Property accessors

	public Integer getMemberContribId() {
		return this.memberContribId;
	}

	public void setMemberContribId(Integer memberContribId) {
		this.memberContribId = memberContribId;
	}

	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Double getMemberContribValue() {
		return this.memberContribValue;
	}

	public void setMemberContribValue(Double memberContribValue) {
		this.memberContribValue = memberContribValue;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Timestamp getApprovedDate() {
		return this.approvedDate;
	}

	public void setApprovedDate(Timestamp approvedDate) {
		this.approvedDate = approvedDate;
	}

        public String getContribProd() {
            return contribProd;
        }

        public void setContribProd(String contribProd) {
            this.contribProd = contribProd;
        }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }
        
        
}