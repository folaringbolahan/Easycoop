package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.Date;

/**
 * BatchAddressEntries entity. @author MyEclipse Persistence Tools
 */

public class BatchExtrafldEntries implements java.io.Serializable {

	// Fields

	private Integer batchExtrafldId;
	private Status status;
	private String active;
	private String extrFieldName;
	private String extrFieldValue;
        private String extrFieldoptValue;
	private Integer extrFieldId;
        private String extrFieldGrouped;
	private String createdBy;
	private Timestamp creationDate;
	private String deleted;
	private String keyId;
	private String serialPos;
	private String typeId;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private Integer companyId;
	private Integer branchId;
        private String email1;
        private Integer runCount;
        private String recAction;
        
        private transient Member member;
        
        private BatchUploadFile batchUploadFile;
        private String batchUploadRefId;
        private String valError;
        private transient boolean hasErrors;
        
         private transient MembersExtrafldEntries addEntries;
         
         private transient MembersExtrafldEntries existingEntries;

	// Constructors

	/** default constructor */
	public BatchExtrafldEntries() {
	}

	/** minimal constructor */
	public BatchExtrafldEntries(Status status, Integer companyId,
			Integer branchId,String email1,Integer runCount,String batchUploadRefId,BatchUploadFile batchUploadFile,String createdBy,Timestamp creationDate) {
		this.status = status;
		this.companyId = companyId;
		this.branchId = branchId;
                this.email1 = email1;
                this.createdBy = createdBy;
		this.creationDate = creationDate;
                this.runCount = runCount;
                this.batchUploadFile = batchUploadFile;
                this.batchUploadRefId = batchUploadRefId;
	}

	/** full constructor */
	public BatchExtrafldEntries(Status status, String active,
			String addrFieldName, String addrFieldValue, String createdBy,
			Timestamp creationDate, String deleted, String keyId,
			String serialPos, String typeId, String modifiedBy,
			Date modifiedDate, String approvedBy, Date approvedDate,
			Integer companyId, Integer branchId, String email1,Integer runCount,String recAction,String batchUploadRefId, String valError,BatchUploadFile batchUploadFile) {
		this.status = status;
		this.active = active;
		this.extrFieldName = addrFieldName;
		this.extrFieldValue = addrFieldValue;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.deleted = deleted;
		this.keyId = keyId;
		this.serialPos = serialPos;
		this.typeId = typeId;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.companyId = companyId;
		this.branchId = branchId;
                this.email1 = email1;
                this.recAction = recAction;
                this.batchUploadFile = batchUploadFile;
                this.batchUploadRefId = batchUploadRefId;
                this.valError = valError;
	}

	// Property accessors

	public Integer getBatchExtrafldId() {
		return this.batchExtrafldId;
	}

	public void setBatchExtrafldId(Integer batchExtrafldId) {
		this.batchExtrafldId = batchExtrafldId;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getExtrFieldName() {
		return this.extrFieldName;
	}

	public void setExtrFieldName(String extrFieldName) {
		this.extrFieldName = extrFieldName;
	}

	public String getExtrFieldValue() {
		return this.extrFieldValue;
	}

	public void setExtrFieldValue(String extrFieldValue) {
		this.extrFieldValue = extrFieldValue;
	}

        public String getExtrFieldoptValue() {
		return this.extrFieldoptValue;
	}

	public void setExtrFieldoptValue(String extrFieldoptValue) {
		this.extrFieldoptValue = extrFieldoptValue;
	}

	public String getExtrFieldGrouped() {
		return this.extrFieldGrouped;
	}

	public void setExtrFieldGrouped(String extrFieldGrouped) {
		this.extrFieldGrouped = extrFieldGrouped;
	}
        
        public Integer getExtrFieldId() {
		return this.extrFieldId;
	}

	public void setExtrFieldId(Integer extrFieldId) {
		this.extrFieldId = extrFieldId;
	}
        
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getDeleted() {
		return this.deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getKeyId() {
		return this.keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getSerialPos() {
		return this.serialPos;
	}

	public void setSerialPos(String serialPos) {
		this.serialPos = serialPos;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return this.approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
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

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public Integer getRunCount() {
        return runCount;
    }

    public void setRunCount(Integer runCount) {
        this.runCount = runCount;
    }
    
     public String getRecAction() {
        return recAction;
    }

    public void setRecAction(String recAction) {
        this.recAction = recAction;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
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

    public MembersExtrafldEntries getAddEntries() {
        return addEntries;
    }

    public void setAddEntries(MembersExtrafldEntries addEntries) {
        this.addEntries = addEntries;
    }

    public MembersExtrafldEntries getExistingEntries() {
        return existingEntries;
    }

    public void setExistingEntries(MembersExtrafldEntries existingEntries) {
        this.existingEntries = existingEntries;
    }
    
}