package com.sift.financial.member;

import com.sift.financial.ApprovaInterface;
import java.sql.Timestamp;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * BatchUploadFile entity. @author MyEclipse Persistence Tools
 */

public class BatchUploadFile implements java.io.Serializable {

	// Fields

	private Integer batchUploadFileId;
	private BatchUploadType batchUploadType;
	private BatchUploadReference batchUploadReference;
	private Status status;
	private String batchUploadFileName;
	private Integer batchRecordCount;
	private String createdBy;
	private Timestamp createdDate;
	private String modifiedBy;
	private Timestamp modifiedDate;
	private String approvedBy;
	private Timestamp approvedDate;
        private CommonsMultipartFile file;
        private Integer companyId;
        private Integer branchId;
        private String originalFileName;
        private String postInfo;
       
       private transient String action;
       private transient String reference;
      
      
	// Constructors

	/** default constructor */
	public BatchUploadFile() {
	}

	/** minimal constructor */
	public BatchUploadFile(BatchUploadType batchUploadType,
			BatchUploadReference batchUploadReference, Status status,
			String batchUploadFileName, Integer batchRecordCount,
			String createdBy, Timestamp createdDate, Integer companyId, Integer branchId,String originalFileName) {
		this.batchUploadType = batchUploadType;
		this.batchUploadReference = batchUploadReference;
		this.status = status;
		this.batchUploadFileName = batchUploadFileName;
		this.batchRecordCount = batchRecordCount;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
                this.companyId = companyId;
                this.branchId = branchId;
                this.originalFileName= originalFileName;
	}

	/** full constructor */
	public BatchUploadFile(BatchUploadType batchUploadType,
			BatchUploadReference batchUploadReference, Status status,
			String batchUploadFileName, Integer batchRecordCount,
			String createdBy, Timestamp createdDate, String modifiedBy,
			Timestamp modifiedDate, String approvedBy, Timestamp approvedDate, Integer companyId, Integer branchId,String originalFileName, String postInfo) {
		this.batchUploadType = batchUploadType;
		this.batchUploadReference = batchUploadReference;
		this.status = status;
		this.batchUploadFileName = batchUploadFileName;
		this.batchRecordCount = batchRecordCount;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
                this.companyId = companyId;
                this.branchId = branchId;
                this.originalFileName= originalFileName;
                this.postInfo = postInfo;
	}

	// Property accessors

	public Integer getBatchUploadFileId() {
		return this.batchUploadFileId;
	}

	public void setBatchUploadFileId(Integer batchUploadFileId) {
		this.batchUploadFileId = batchUploadFileId;
	}

	public BatchUploadType getBatchUploadType() {
		return this.batchUploadType;
	}

	public void setBatchUploadType(BatchUploadType batchUploadType) {
		this.batchUploadType = batchUploadType;
	}

	public BatchUploadReference getBatchUploadReference() {
		return this.batchUploadReference;
	}

	public void setBatchUploadReference(
			BatchUploadReference batchUploadReference) {
		this.batchUploadReference = batchUploadReference;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getBatchUploadFileName() {
		return this.batchUploadFileName;
	}

	public void setBatchUploadFileName(String batchUploadFileName) {
		this.batchUploadFileName = batchUploadFileName;
	}

	public Integer getBatchRecordCount() {
		return this.batchRecordCount;
	}

	public void setBatchRecordCount(Integer batchRecordCount) {
		this.batchRecordCount = batchRecordCount;
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

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getPostInfo() {
        return postInfo;
    }

    public void setPostInfo(String postInfo) {
        this.postInfo = postInfo;
    }
    
}