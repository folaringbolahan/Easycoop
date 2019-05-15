package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Member entity. @author MyEclipse Persistence Tools
 */

public class Member implements java.io.Serializable {

	// Fields

	private Integer memberId;
	private Company company;
	private Branch branch;
	private Status status;
	private Religion religion;
	private MemberType memberType;
	private IdentificationDoc identificationDoc;
        private MemberExtrafldoption memberExtrafldoption;
	private TaxGroups taxGroups;
	private Banks banks;
	private String memberCompId;
	private String firstName;
	private String middleName;
	private String surname;
	private Date dob;
	private Date createdDate;
	private String createdBy;
	private String delFlg;
	private Date delDate;
	private String gender;
	private String identificationCode;
	private String phoneNo1;
	private String phoneNo2;
	private String phoneNo3;
	private String emailAdd1;
	private String emailAdd2;
	private String emailAdd3;
	private String memberNo;
	private Set memberHoldingsMovements = new HashSet(0);

	private String  bankAccount;
        
        private Timestamp approvedDate;
	private String approvedBy;
        private Timestamp modifiedDate;
	private String modifiedBy;
        
        private Set memberContributions = new HashSet(0);
        private transient MemberContribution memberContribution;
	
	private transient String event;
	private transient String action;
	private transient Map<String, List<AddressEntries>> AddressEntriesList = new HashMap<String, List<AddressEntries>>();
        private transient List<MembersExtrafldEntries> MembersExtrafldEntriesList;
        
        
        private transient String addressStr;
        private transient String stockStr;
        private transient String contribVal;
        private transient String state;
        private transient String extrafldStr;
        
        private Integer uploadId;
        private Integer batchMemberId;
        
        private String nokName;
        private String nokMiddleName;
        private String nokSurname;
        private String nokPhone;
        
        //enquriy fields

        
	// Constructors

	/** default constructor */
	public Member() {
	}

	/** minimal constructor */
	public Member(Company company, Branch branch, Status status,
			MemberType memberType, IdentificationDoc identificationDoc,
			String memberCompId, String firstName, String surname,
			Date createdDate, String createdBy, String delFlg, String gender,
			String identificationCode, String phoneNo1, String emailAdd1,String nokName,String nokSurname,String nokPhone) {
		this.company = company;
		this.branch = branch;
		this.status = status;
		this.memberType = memberType;
		this.identificationDoc = identificationDoc;
		this.memberCompId = memberCompId;
		this.firstName = firstName;
		this.surname = surname;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.delFlg = delFlg;
		this.gender = gender;
		this.identificationCode = identificationCode;
		this.phoneNo1 = phoneNo1;
		this.emailAdd1 = emailAdd1;
                this.nokName = nokName;
                this.nokSurname =nokSurname;
                this.nokPhone= nokPhone;
	}
        //Set memberContributions,
	/** full constructor */
	public Member(Company company, Branch branch, Status status,
			Religion religion, MemberType memberType,
			IdentificationDoc identificationDoc, String memberCompId,
			String firstName, String middleName, String surname, Date dob,
			Date createdDate, String createdBy, String delFlg, Date delDate,
			String gender, String identificationCode, String phoneNo1,
			String phoneNo2, String phoneNo3, String emailAdd1,
			String emailAdd2, String emailAdd3, String memberNo,
			Set memberHoldingsMovements, TaxGroups taxGroups,String bankAccount, Banks banks,Timestamp modifiedDate, String modifiedBy,
                        Timestamp approvedDate, String approvedBy, Integer uploadId,Integer batchMemberId, Set memberContributions, String nokName,String nokMiddleName,String nokSurname,String nokPhone) {
		this.company = company;
		this.branch = branch;
		this.status = status;
		this.religion = religion;
		this.memberType = memberType;
		this.identificationDoc = identificationDoc;
		this.memberCompId = memberCompId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.surname = surname;
		this.dob = dob;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.delFlg = delFlg;
		this.delDate = delDate;
		this.gender = gender;
		this.identificationCode = identificationCode;
		this.phoneNo1 = phoneNo1;
		this.phoneNo2 = phoneNo2;
		this.phoneNo3 = phoneNo3;
		this.emailAdd1 = emailAdd1;
		this.emailAdd2 = emailAdd2;
		this.emailAdd3 = emailAdd3;
		this.memberNo = memberNo;
		this.memberHoldingsMovements = memberHoldingsMovements;
		this.taxGroups = taxGroups;
		this.bankAccount = bankAccount;
		this.banks=  banks;
              //  this.memberContributions = memberContributions;
                this.uploadId = uploadId;
                this.batchMemberId = batchMemberId;
                this.memberContributions = memberContributions;
                this.nokName = nokName;
                this.nokSurname =nokSurname;
                this.nokPhone= nokPhone;
                this.nokMiddleName = nokMiddleName;
	}

	// Property accessors

	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Branch getBranch() {
		return this.branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Religion getReligion() {
		return this.religion;
	}

	public void setReligion(Religion religion) {
		this.religion = religion;
	}

	public MemberType getMemberType() {
		return this.memberType;
	}

	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}

	public IdentificationDoc getIdentificationDoc() {
		return this.identificationDoc;
	}

	public void setIdentificationDoc(IdentificationDoc identificationDoc) {
		this.identificationDoc = identificationDoc;
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

	public String getIdentificationCode() {
		return this.identificationCode;
	}

	public void setIdentificationCode(String identificationCode) {
		this.identificationCode = identificationCode;
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

	public Set getMemberHoldingsMovements() {
		return this.memberHoldingsMovements;
	}

	public void setMemberHoldingsMovements(Set memberHoldingsMovements) {
		this.memberHoldingsMovements = memberHoldingsMovements;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String, List<AddressEntries>> getAddressEntriesList() {
		return AddressEntriesList;
	}

	public void setAddressEntriesList(
			Map<String, List<AddressEntries>> addressEntriesList) {
		AddressEntriesList = addressEntriesList;
	}
        
        public  List<MembersExtrafldEntries> getMembersExtrafldEntriesList() {
		return MembersExtrafldEntriesList;
	}

	public void setMembersExtrafldEntriesList(
			List<MembersExtrafldEntries> membersExtrafldEntriesList) {
		MembersExtrafldEntriesList = membersExtrafldEntriesList;
	}

	public TaxGroups getTaxGroups() {
		return taxGroups;
	}

	public void setTaxGroups(TaxGroups taxGroups) {
		this.taxGroups = taxGroups;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Banks getBanks() {
		return banks;
	}

	public void setBanks(Banks banks) {
		this.banks = banks;
	}
        public MemberExtrafldoption getMemberExtrafldoption() {
		return memberExtrafldoption;
	}

	public void setMemberExtrafldoption(MemberExtrafldoption memberExtrafldoption) {
		this.memberExtrafldoption = memberExtrafldoption;
	}
        

    public String getAddressStr() {
        return addressStr;
    }

    public void setAddressStr(String addressStr) {
        this.addressStr = addressStr;
    }
    public String getExtrafldStr() {
        return extrafldStr;
    }

    public void setExtrafldStr(String extrafldStr) {
        this.extrafldStr = extrafldStr;
    }
    

    public String getStockStr() {
        return stockStr;
    }

    public void setStockStr(String stockStr) {
        this.stockStr = stockStr;
    }

    public Timestamp getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Timestamp approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
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

   public Set getMemberContributions() {
        return memberContributions;
    }

    public void setMemberContributions(Set memberContributions) {
        this.memberContributions = memberContributions;
    }
/** 
    public MemberContribution getMemberContribution() {
        return memberContribution;
    }

    public void setMemberContribution(MemberContribution memberContribution) {
        this.memberContribution = memberContribution;
    }
    * **/

    public String getContribVal() {
        return contribVal;
    }

    public void setContribVal(String contribVal) {
        this.contribVal = contribVal;
    }

    public Integer getUploadId() {
        return uploadId;
    }

    public void setUploadId(Integer uploadId) {
        this.uploadId = uploadId;
    }

    public Integer getBatchMemberId() {
        return batchMemberId;
    }

    public void setBatchMemberId(Integer batchMemberId) {
        this.batchMemberId = batchMemberId;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public MemberContribution getMemberContribution() {
        return memberContribution;
    }

    public void setMemberContribution(MemberContribution memberContribution) {
        this.memberContribution = memberContribution;
    }
    
    
    
}