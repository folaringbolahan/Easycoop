package com.sift.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="BRANCH")
public class Branch{    
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
 
    @Column(name="COMPANY_ID")
    private String companyId;

    @Column(name="BRANCH_CODE")
    private String branchCode;

    @Column(name="BRANCH_NAME")
    private String branchName;

    @Column(name="COUNTRY_ID")
    private String countryId;

    @Column(name="PHONE_1")
    private String phone1;
    
    @Column(name="PHONE_2")
    private String phone2;
    
    @Column(name="EMAIL")
    private String email;

    @Column(name="ACTIVE")
    private String active;
    
    @Column(name="DELETED")
    private String deleted;
    
    @Column(name="PostDate")
    private Date postDate;
    
    @Column(name="CurrentPeriod")
    private Integer currentPeriod;
    
    @Column(name="CurrentYear")
    private Integer currentYear;
    
    @Column(name="BaseCurrency")
    private String baseCurrency;
    
    @Column(name="SetupDate")
    private Date setupDate;
    
    @Column(name="CREATION_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date creationDate;
    
    @Column(name="CREATED_BY")
    private String createdBy;
 
    @Column(name="LAST_MODIFICATION_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date lastModificationDate;
    
    @Column(name="LAST_MODIFIED_BY")
    private String lastModifiedBy;
    
    @Column(name="CONNECT_EASYCOOP")
    private String connectToEazyCoop;
    
    /****
    @OneToMany(mappedBy = "Branch")//http://www.beingjavaguys.com/2013/09/hibernate-one-to-many-mapping.html
    private Set<AddressEntries> addressEntries;  

	public Set<AddressEntries> getAddressEntries(){
		return addressEntries;
	}

	public void setAddressEntries(Set<AddressEntries> addressEntries){
		this.addressEntries = addressEntries;
	}***/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName(){
		return branchName;
	}

	public void setBranchName(String branchName){
		this.branchName = branchName;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public Integer getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(Integer currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public Integer getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(Integer currentYear) {
		this.currentYear = currentYear;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public Date getSetupDate() {
		return setupDate;
	}

	public void setSetupDate(Date setupDate) {
		this.setupDate = setupDate;
	}

	public String getConnectToEazyCoop() {
		return connectToEazyCoop;
	}

	public void setConnectToEazyCoop(String connectToEazyCoop) {
		this.connectToEazyCoop = connectToEazyCoop;
	}    
 }