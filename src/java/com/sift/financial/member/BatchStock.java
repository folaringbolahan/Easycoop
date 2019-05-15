package com.sift.financial.member;

import java.sql.Timestamp;

/**
 * BatchStock entity. @author MyEclipse Persistence Tools
 */

public class BatchStock implements java.io.Serializable {

	// Fields
	private Integer batchStockId;
	private Status status;
	private String stockShort;
	private Double stockValue;
	private String email1;
	private String createdBy;
	private Timestamp createdDate;
	private String modifiedBy;
	private Timestamp modifiedDate;
	private String approvedBy;
	private Timestamp approvedDate;
        private Integer runCount;
        private BatchUploadFile batchUploadFile;
        private String batchUploadRefId;
        private String valError;
        private transient boolean hasErrors;
        private String recAction;
        private Integer companyId;
        private Integer branchId;
        private transient Member member;
        private transient CompStockType compStockType;
        private transient String buySellIndicator;

	// Constructors

	/** default constructor */
	public BatchStock() {
            
	}

	/** minimal constructor */
	public BatchStock(String stockShort, Double stockValue, String email1,
			String createdBy, Timestamp createdDate,Integer runCount,Integer companyId, Integer branchId,String batchUploadRefId,BatchUploadFile batchUploadFile) {
		this.stockShort = stockShort;
		this.stockValue = stockValue;
		this.email1 = email1;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
                this.runCount = runCount;
                this.companyId = companyId;
                this.branchId = branchId;
                this.batchUploadFile = batchUploadFile;
                this.batchUploadRefId = batchUploadRefId;
	}

	/** full constructor */
	public BatchStock(Status status, String stockShort, Double stockValue,
			String email1, String createdBy, Timestamp createdDate,
			String modifiedBy, Timestamp modifiedDate, String approvedBy,
			Timestamp approvedDate,Integer runCount, String recAction,Integer companyId, Integer branchId,String batchUploadRefId, String valError,BatchUploadFile batchUploadFile) {
		this.status = status;
		this.stockShort = stockShort;
		this.stockValue = stockValue;
		this.email1 = email1;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
                this.runCount = runCount;
                this.recAction = recAction;
                this.companyId = companyId;
                this.branchId = branchId;
                this.batchUploadFile = batchUploadFile;
                this.batchUploadRefId = batchUploadRefId;
                this.valError = valError;
	}

	// Property accessors

	public Integer getBatchStockId() {
		return this.batchStockId;
	}

	public void setBatchStockId(Integer batchStockId) {
		this.batchStockId = batchStockId;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getStockShort() {
		return this.stockShort;
	}

	public void setStockShort(String stockShort) {
		this.stockShort = stockShort;
	}

	public Double getStockValue() {
		return this.stockValue;
	}

	public void setStockValue(Double stockValue) {
		this.stockValue = stockValue;
	}

	public String getEmail1() {
		return this.email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
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

        public Integer getRunCount() {
            return runCount;
        }

        public void setRunCount(Integer runCount) {
            this.runCount = runCount;
        }

        public BatchUploadFile getBatchUploadFile() {
            return batchUploadFile;
        }

        public void setBatchUploadFile(BatchUploadFile batchUploadFile) {
            this.batchUploadFile = batchUploadFile;
        }

        public String getBatchUploadRefId() {
            return batchUploadRefId;
        }

        public void setBatchUploadRefId(String batchUploadRefId) {
            this.batchUploadRefId = batchUploadRefId;
        }

        public String getValError() {
            return valError;
        }

        public void setValError(String valError) {
            this.valError = valError;
        }

        public boolean hasErrors() {
                return hasErrors;
            }

        public void setHasErrors(boolean hasErrors) {
            this.hasErrors = hasErrors;
        }

        public String getRecAction() {
            return recAction;
        }

        public void setRecAction(String recAction) {
            this.recAction = recAction;
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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public CompStockType getCompStockType() {
        return compStockType;
    }

    public void setCompStockType(CompStockType compStockType) {
        this.compStockType = compStockType;
    }

    public String getBuySellIndicator() {
        return buySellIndicator;
    }

    public void setBuySellIndicator(String buySellIndicator) {
        this.buySellIndicator = buySellIndicator;
    }
       
}