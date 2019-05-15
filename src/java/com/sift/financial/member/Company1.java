package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Company entity. @author MyEclipse Persistence Tools
 */

public class Company1 implements java.io.Serializable {

	// Fields

	private Integer companyId;
	private CompanyBusinessArea companyBusinessArea;
	private CompanyCategory companyCategory;
	private Countries countries;
	private CompanyType companyType;
	private CompanyOwnershipStructure companyOwnershipStructure;
	private CompanyBusinessActivity companyBusinessActivity;
	private String name;
	private Date dateOfIncorp;
	private Integer companyZoneId;
	private String shortName;
	private String fax;
	private String website;
	private String active;
	private String deleted;
	private String lastModifiedBy;
	private Timestamp lastModificationDate;
	private String regno;
	private Set compStockTypes = new HashSet(0);
	private Set branchs = new HashSet(0);
	private Set members = new HashSet(0);
        private String  code;
        private Date postDate;
        private Integer currentPeriod;
   
	// Constructors

	/** default constructor */
	public Company1() {
	}

	/** minimal constructor */
	public Company1(CompanyBusinessArea companyBusinessArea,
			CompanyCategory companyCategory, Countries countries,
			CompanyType companyType,
			CompanyOwnershipStructure companyOwnershipStructure,
			CompanyBusinessActivity companyBusinessActivity, String name,
			Date dateOfIncorp, Integer companyZoneId, String shortName,
			String active, String deleted, String regno) {
		this.companyBusinessArea = companyBusinessArea;
		this.companyCategory = companyCategory;
		this.countries = countries;
		this.companyType = companyType;
		this.companyOwnershipStructure = companyOwnershipStructure;
		this.companyBusinessActivity = companyBusinessActivity;
		this.name = name;
		this.dateOfIncorp = dateOfIncorp;
		this.companyZoneId = companyZoneId;
		this.shortName = shortName;
		this.active = active;
		this.deleted = deleted;
		this.regno = regno;
	}

	/** full constructor */
	public Company1(CompanyBusinessArea companyBusinessArea,
			CompanyCategory companyCategory, Countries countries,
			CompanyType companyType,
			CompanyOwnershipStructure companyOwnershipStructure,
			CompanyBusinessActivity companyBusinessActivity, String name,
			Date dateOfIncorp, Integer companyZoneId, String shortName,
			String fax, String website, String active, String deleted,
			String lastModifiedBy, Timestamp lastModificationDate,
			String regno, Set compStockTypes, Set branchs, Set members) {
		this.companyBusinessArea = companyBusinessArea;
		this.companyCategory = companyCategory;
		this.countries = countries;
		this.companyType = companyType;
		this.companyOwnershipStructure = companyOwnershipStructure;
		this.companyBusinessActivity = companyBusinessActivity;
		this.name = name;
		this.dateOfIncorp = dateOfIncorp;
		this.companyZoneId = companyZoneId;
		this.shortName = shortName;
		this.fax = fax;
		this.website = website;
		this.active = active;
		this.deleted = deleted;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModificationDate = lastModificationDate;
		this.regno = regno;
		this.compStockTypes = compStockTypes;
		this.branchs = branchs;
		this.members = members;
	}

	// Property accessors

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public CompanyBusinessArea getCompanyBusinessArea() {
		return this.companyBusinessArea;
	}

	public void setCompanyBusinessArea(CompanyBusinessArea companyBusinessArea) {
		this.companyBusinessArea = companyBusinessArea;
	}

	public CompanyCategory getCompanyCategory() {
		return this.companyCategory;
	}

	public void setCompanyCategory(CompanyCategory companyCategory) {
		this.companyCategory = companyCategory;
	}

	public Countries getCountries() {
		return this.countries;
	}

	public void setCountries(Countries countries) {
		this.countries = countries;
	}

	public CompanyType getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}

	public CompanyOwnershipStructure getCompanyOwnershipStructure() {
		return this.companyOwnershipStructure;
	}

	public void setCompanyOwnershipStructure(
			CompanyOwnershipStructure companyOwnershipStructure) {
		this.companyOwnershipStructure = companyOwnershipStructure;
	}

	public CompanyBusinessActivity getCompanyBusinessActivity() {
		return this.companyBusinessActivity;
	}

	public void setCompanyBusinessActivity(
			CompanyBusinessActivity companyBusinessActivity) {
		this.companyBusinessActivity = companyBusinessActivity;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfIncorp() {
		return this.dateOfIncorp;
	}

	public void setDateOfIncorp(Date dateOfIncorp) {
		this.dateOfIncorp = dateOfIncorp;
	}

	public Integer getCompanyZoneId() {
		return this.companyZoneId;
	}

	public void setCompanyZoneId(Integer companyZoneId) {
		this.companyZoneId = companyZoneId;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDeleted() {
		return this.deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Timestamp getLastModificationDate() {
		return this.lastModificationDate;
	}

	public void setLastModificationDate(Timestamp lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public String getRegno() {
		return this.regno;
	}

	public void setRegno(String regno) {
		this.regno = regno;
	}

	public Set getCompStockTypes() {
		return this.compStockTypes;
	}

	public void setCompStockTypes(Set compStockTypes) {
		this.compStockTypes = compStockTypes;
	}

	public Set getBranchs() {
		return this.branchs;
	}

	public void setBranchs(Set branchs) {
		this.branchs = branchs;
	}

	public Set getMembers() {
		return this.members;
	}

	public void setMembers(Set members) {
		this.members = members;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

}