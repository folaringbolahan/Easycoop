package com.sift.financial.member;

import java.util.Date;

/**
 * BatchMember entity. @author MyEclipse Persistence Tools
 */

public class BatchMember implements java.io.Serializable {

	// Fields

	private Integer batchMemberId;
	private Status status;
        private BatchUploadFile batchUploadFile;
	private String memberCompId;
	private String firstName;
	private String middleName;
	private String surname;
	private Date dob;
	private String religionId;
	private String memberTypeId;
	private Date createdDate;
	private String createdBy;
	private String delFlg;
	private Date delDate;
	private String gender;
	private Integer branchId;
	private String identificationId;
	private String identificationCode;
	private Integer companyId;
	private String phoneNo1;
	private String phoneNo2;
	private String phoneNo3;
	private String emailAdd1;
	private String emailAdd2;
	private String emailAdd3;
	private String memberNo;
	private Date resignDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private Float contribution;
        private String batchUploadRefId;
        private String valError;
        private String bank;
        private String bankAcct;
        private transient boolean hasErrors;
        private transient Member  member;
        
        private String nokName;
        private String nokMiddleName;
        private String nokSurname;
        private String nokPhone;
      

	// Constructors

	/** default constructor */
	public BatchMember() {
	}

	/** minimal constructor */
	public BatchMember(Status status, String memberCompId, String firstName,
			String surname, String memberTypeId, Date createdDate,
			String createdBy, String delFlg, String gender, Integer branchId,
			String identificationId, String identificationCode,
			Integer companyId, String phoneNo1, String emailAdd1,String batchUploadRefId, BatchUploadFile batchUploadFile, String nokName,String nokSurname,String nokPhone) {
		this.status = status;
		this.memberCompId = memberCompId;
		this.firstName = firstName;
		this.surname = surname;
		this.memberTypeId = memberTypeId;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.delFlg = delFlg;
		this.gender = gender;
		this.branchId = branchId;
		this.identificationId = identificationId;
		this.identificationCode = identificationCode;
		this.companyId = companyId;
		this.phoneNo1 = phoneNo1;
		this.emailAdd1 = emailAdd1;
                this.batchUploadRefId= batchUploadRefId;
                this.batchUploadFile = batchUploadFile;
                this.nokName = nokName;
                this.nokSurname =nokSurname;
                this.nokPhone= nokPhone;
	}

	/** full constructor */
	public BatchMember(Status status, String memberCompId, String firstName,
			String middleName, String surname, Date dob, String religionId,
			String memberTypeId, Date createdDate, String createdBy,
			String delFlg, Date delDate, String gender, Integer branchId,
			String identificationId, String identificationCode,
			Integer companyId, String phoneNo1, String phoneNo2,
			String phoneNo3, String emailAdd1, String emailAdd2,
			String emailAdd3, String memberNo, Date resignDate,
			String modifiedBy, Date modifiedDate, String approvedBy,
			Date approvedDate, Float contribution,String batchUploadRefId, String valError,String bank,String bankAcct,BatchUploadFile batchUploadFile, String nokName,String nokMiddleName,String nokSurname,String nokPhone) {
		this.status = status;
		this.memberCompId = memberCompId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.surname = surname;
		this.dob = dob;
		this.religionId = religionId;
		this.memberTypeId = memberTypeId;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.delFlg = delFlg;
		this.delDate = delDate;
		this.gender = gender;
		this.branchId = branchId;
		this.identificationId = identificationId;
		this.identificationCode = identificationCode;
		this.companyId = companyId;
		this.phoneNo1 = phoneNo1;
		this.phoneNo2 = phoneNo2;
		this.phoneNo3 = phoneNo3;
		this.emailAdd1 = emailAdd1;
		this.emailAdd2 = emailAdd2;
		this.emailAdd3 = emailAdd3;
		this.memberNo = memberNo;
		this.resignDate = resignDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.contribution = contribution;
                this.batchUploadRefId= batchUploadRefId;
                this.valError = valError;
                this.bank = bank;
                this.bankAcct = bankAcct;
                this.batchUploadFile = batchUploadFile;
                this.nokName = nokName;
                this.nokSurname =nokSurname;
                this.nokPhone= nokPhone;
                this.nokMiddleName = nokMiddleName;
               
	}

	// Property accessors

	public Integer getBatchMemberId() {
		return this.batchMemberId;
	}

	public void setBatchMemberId(Integer batchMemberId) {
		this.batchMemberId = batchMemberId;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMemberCompId() {
		return this.memberCompId;
	}

	public void setMemberCompId(String memberCompId) {
		this.memberCompId = memberCompId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getReligionId() {
		return this.religionId;
	}

	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}

	public String getMemberTypeId() {
		return this.memberTypeId;
	}

	public void setMemberTypeId(String memberTypeId) {
		this.memberTypeId = memberTypeId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public Date getDelDate() {
		return this.delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getBranchId() {
		return this.branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public String getIdentificationId() {
		return this.identificationId;
	}

	public void setIdentificationId(String identificationId) {
		this.identificationId = identificationId;
	}

	public String getIdentificationCode() {
		return this.identificationCode;
	}

	public void setIdentificationCode(String identificationCode) {
		this.identificationCode = identificationCode;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getPhoneNo1() {
		return this.phoneNo1;
	}

	public void setPhoneNo1(String phoneNo1) {
		this.phoneNo1 = phoneNo1;
	}

	public String getPhoneNo2() {
		return this.phoneNo2;
	}

	public void setPhoneNo2(String phoneNo2) {
		this.phoneNo2 = phoneNo2;
	}

	public String getPhoneNo3() {
		return this.phoneNo3;
	}

	public void setPhoneNo3(String phoneNo3) {
		this.phoneNo3 = phoneNo3;
	}

	public String getEmailAdd1() {
		return this.emailAdd1;
	}

	public void setEmailAdd1(String emailAdd1) {
		this.emailAdd1 = emailAdd1;
	}

	public String getEmailAdd2() {
		return this.emailAdd2;
	}

	public void setEmailAdd2(String emailAdd2) {
		this.emailAdd2 = emailAdd2;
	}

	public String getEmailAdd3() {
		return this.emailAdd3;
	}

	public void setEmailAdd3(String emailAdd3) {
		this.emailAdd3 = emailAdd3;
	}

	public String getMemberNo() {
		return this.memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public Date getResignDate() {
		return this.resignDate;
	}

	public void setResignDate(Date resignDate) {
		this.resignDate = resignDate;
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

	public Float getContribution() {
		return this.contribution;
	}

	public void setContribution(Float contribution) {
		this.contribution = contribution;
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

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBankAcct() {
            return bankAcct;
        }

        public void setBankAcct(String bankAcct) {
            this.bankAcct = bankAcct;
        }

        public boolean hasErrors() {
            return hasErrors;
        }

        public void setHasErrors(boolean hasErrors) {
            this.hasErrors = hasErrors;
        }

    public BatchUploadFile getBatchUploadFile() {
        return batchUploadFile;
    }

    public void setBatchUploadFile(BatchUploadFile batchUploadFile) {
        this.batchUploadFile = batchUploadFile;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getNokName() {
        return nokName;
    }

    public void setNokName(String nokName) {
        this.nokName = nokName;
    }

    public String getNokMiddleName() {
        return nokMiddleName;
    }

    public void setNokMiddleName(String nokMiddleName) {
        this.nokMiddleName = nokMiddleName;
    }

    public String getNokSurname() {
        return nokSurname;
    }

    public void setNokSurname(String nokSurname) {
        this.nokSurname = nokSurname;
    }

    public String getNokPhone() {
        return nokPhone;
    }

    public void setNokPhone(String nokPhone) {
        this.nokPhone = nokPhone;
    }
    
    
    
  
}
        
        
        
        
 