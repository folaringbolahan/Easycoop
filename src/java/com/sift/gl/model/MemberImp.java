/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "member")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MemberImp.findAll", query = "SELECT m FROM MemberImp m")})
public class MemberImp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "member_id")
    private Integer memberId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "member_comp_id")
    private String memberCompId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 45)
    @Column(name = "middle_name")
    private String middleName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "surname")
    private String surname;
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Column(name = "religion_id")
    private Integer religionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "member_type_id")
    private int memberTypeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "created_by")
    private String createdBy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "del_flg")
    private String delFlg;
    @Column(name = "del_date")
    @Temporal(TemporalType.DATE)
    private Date delDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "gender")
    private String gender;
    @Basic(optional = false)
    @NotNull
    @Column(name = "branch_id")
    private int branchId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "identification_id")
    private int identificationId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "identification_code")
    private String identificationCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status_id")
    private int statusId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "company_id")
    private int companyId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "phone_no_1")
    private String phoneNo1;
    @Size(max = 45)
    @Column(name = "phone_no_2")
    private String phoneNo2;
    @Size(max = 45)
    @Column(name = "phone_no_3")
    private String phoneNo3;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email_add_1")
    private String emailAdd1;
    @Size(max = 100)
    @Column(name = "email_add_2")
    private String emailAdd2;
    @Size(max = 100)
    @Column(name = "email_add_3")
    private String emailAdd3;
    @Size(max = 6)
    @Column(name = "member_no")
    private String memberNo;
    @Column(name = "resign_date")
    @Temporal(TemporalType.DATE)
    private Date resignDate;
    @Column(name = "tax_group")
    private Integer taxGroup;
    @Column(name = "bank_id")
    private Integer bankId;
    @Size(max = 45)
    @Column(name = "bank_account")
    private String bankAccount;
    @Size(max = 200)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.DATE)
    private Date modifiedDate;
    @Size(max = 200)
    @Column(name = "approved_by")
    private String approvedBy;
    @Column(name = "approved_date")
    @Temporal(TemporalType.DATE)
    private Date approvedDate;

    public MemberImp() {
    }

    public MemberImp(Integer memberId) {
        this.memberId = memberId;
    }

    public MemberImp(Integer memberId, String memberCompId, String firstName, String surname, int memberTypeId, Date createdDate, String createdBy, String delFlg, String gender, int branchId, int identificationId, String identificationCode, int statusId, int companyId, String phoneNo1, String emailAdd1) {
        this.memberId = memberId;
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
        this.statusId = statusId;
        this.companyId = companyId;
        this.phoneNo1 = phoneNo1;
        this.emailAdd1 = emailAdd1;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberCompId() {
        return memberCompId;
    }

    public void setMemberCompId(String memberCompId) {
        this.memberCompId = memberCompId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getReligionId() {
        return religionId;
    }

    public void setReligionId(Integer religionId) {
        this.religionId = religionId;
    }

    public int getMemberTypeId() {
        return memberTypeId;
    }

    public void setMemberTypeId(int memberTypeId) {
        this.memberTypeId = memberTypeId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public Date getDelDate() {
        return delDate;
    }

    public void setDelDate(Date delDate) {
        this.delDate = delDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getIdentificationId() {
        return identificationId;
    }

    public void setIdentificationId(int identificationId) {
        this.identificationId = identificationId;
    }

    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        this.identificationCode = identificationCode;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getPhoneNo1() {
        return phoneNo1;
    }

    public void setPhoneNo1(String phoneNo1) {
        this.phoneNo1 = phoneNo1;
    }

    public String getPhoneNo2() {
        return phoneNo2;
    }

    public void setPhoneNo2(String phoneNo2) {
        this.phoneNo2 = phoneNo2;
    }

    public String getPhoneNo3() {
        return phoneNo3;
    }

    public void setPhoneNo3(String phoneNo3) {
        this.phoneNo3 = phoneNo3;
    }

    public String getEmailAdd1() {
        return emailAdd1;
    }

    public void setEmailAdd1(String emailAdd1) {
        this.emailAdd1 = emailAdd1;
    }

    public String getEmailAdd2() {
        return emailAdd2;
    }

    public void setEmailAdd2(String emailAdd2) {
        this.emailAdd2 = emailAdd2;
    }

    public String getEmailAdd3() {
        return emailAdd3;
    }

    public void setEmailAdd3(String emailAdd3) {
        this.emailAdd3 = emailAdd3;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public Date getResignDate() {
        return resignDate;
    }

    public void setResignDate(Date resignDate) {
        this.resignDate = resignDate;
    }

    public Integer getTaxGroup() {
        return taxGroup;
    }

    public void setTaxGroup(Integer taxGroup) {
        this.taxGroup = taxGroup;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memberId != null ? memberId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemberImp)) {
            return false;
        }
        MemberImp other = (MemberImp) object;
        if ((this.memberId == null && other.memberId != null) || (this.memberId != null && !this.memberId.equals(other.memberId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sift.transaction.model.MemberImp[ memberId=" + memberId + " ]";
    }
    
}
