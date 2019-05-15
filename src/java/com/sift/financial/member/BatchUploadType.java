package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;

/**
 * BatchUploadType entity. @author MyEclipse Persistence Tools
 */

public class BatchUploadType implements java.io.Serializable {

	// Fields

	private Integer batchUploadTypeId;
	private String uploadTypeName;
	private String delFlg;
	private String uploadTypeShort;
       
	private Set batchUploadFiles = new HashSet(0);
        private String valApproveLink;
        private String valRefuseLink;
        private String apprWithLink;
        private String apprRejectLink;
        private String apprWithoutLink;
        private String viewPassLink;
        private String viewFailLink;
        private String viewValLink;
        
	// Constructors

	/** default constructor */
	public BatchUploadType() {
	}

	/** minimal constructor */
	public BatchUploadType(String uploadTypeName, String delFlg,
			String uploadTypeShort,String valApproveLink, String valRefuseLink,String apprWithLink,String apprRejectLink,String apprWithoutLink, String viewPassLink,String viewFailLink, String viewValLink) {
		this.uploadTypeName = uploadTypeName;
		this.delFlg = delFlg;
		this.uploadTypeShort = uploadTypeShort;
                this.valApproveLink= valApproveLink;
                this.valRefuseLink = valRefuseLink;
                this.apprWithLink = apprWithLink;
                this.apprRejectLink = apprRejectLink;
                this.apprWithoutLink = apprWithoutLink;
                this.viewPassLink = viewPassLink;
                this.viewFailLink = viewFailLink;
                this.viewValLink = viewValLink;
	}

	/** full constructor */
	public BatchUploadType(String uploadTypeName, String delFlg,
			String uploadTypeShort, Set batchUploadFiles,String valApproveLink, String valRefuseLink,String apprWithLink,String apprRejectLink,String apprWithoutLink, String viewPassLink,String viewFailLink, String viewValLink) {
		this.uploadTypeName = uploadTypeName;
		this.delFlg = delFlg;
		this.uploadTypeShort = uploadTypeShort;
		this.batchUploadFiles = batchUploadFiles;
                this.uploadTypeShort = uploadTypeShort;
                this.valApproveLink= valApproveLink;
                this.valRefuseLink = valRefuseLink;
                this.apprWithLink = apprWithLink;
                this.apprRejectLink = apprRejectLink;
                this.apprWithoutLink = apprWithoutLink;
                this.viewPassLink = viewPassLink;
                this.viewFailLink = viewFailLink;
                this.viewValLink = viewValLink;
	}

	// Property accessors

	public Integer getBatchUploadTypeId() {
		return this.batchUploadTypeId;
	}

	public void setBatchUploadTypeId(Integer batchUploadTypeId) {
		this.batchUploadTypeId = batchUploadTypeId;
	}

	public String getUploadTypeName() {
		return this.uploadTypeName;
	}

	public void setUploadTypeName(String uploadTypeName) {
		this.uploadTypeName = uploadTypeName;
	}

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getUploadTypeShort() {
		return this.uploadTypeShort;
	}

	public void setUploadTypeShort(String uploadTypeShort) {
		this.uploadTypeShort = uploadTypeShort;
	}

	public Set getBatchUploadFiles() {
		return this.batchUploadFiles;
	}

	public void setBatchUploadFiles(Set batchUploadFiles) {
		this.batchUploadFiles = batchUploadFiles;
	}

    public String getValApproveLink() {
        return valApproveLink;
    }

    public void setValApproveLink(String valApproveLink) {
        this.valApproveLink = valApproveLink;
    }

    public String getValRefuseLink() {
        return valRefuseLink;
    }

    public void setValRefuseLink(String valRefuseLink) {
        this.valRefuseLink = valRefuseLink;
    }

    public String getApprWithLink() {
        return apprWithLink;
    }

    public void setApprWithLink(String apprWithLink) {
        this.apprWithLink = apprWithLink;
    }

    public String getApprRejectLink() {
        return apprRejectLink;
    }

    public void setApprRejectLink(String apprRejectLink) {
        this.apprRejectLink = apprRejectLink;
    }

    public String getApprWithoutLink() {
        return apprWithoutLink;
    }

    public void setApprWithoutLink(String apprWithoutLink) {
        this.apprWithoutLink = apprWithoutLink;
    }

    public String getViewPassLink() {
        return viewPassLink;
    }

    public void setViewPassLink(String viewPassLink) {
        this.viewPassLink = viewPassLink;
    }

    public String getViewFailLink() {
        return viewFailLink;
    }

    public void setViewFailLink(String viewFailLink) {
        this.viewFailLink = viewFailLink;
    }

    public String getViewValLink() {
        return viewValLink;
    }

    public void setViewValLink(String viewValLink) {
        this.viewValLink = viewValLink;
    }
        
        
        

}