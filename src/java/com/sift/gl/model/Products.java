/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "products")
@XmlRootElement
public class Products implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private short isDeleted;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "company_id")
    private int companyId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "initial_amount_max")
    private float initialAmountMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "initial_amount_min")
    private float initialAmountMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "interest_rate_min")
    private float interestRateMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "interest_rate_max")
    private float interestRateMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "interest_rate")
    private float interestRate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "has_interest")
    private short hasInterest;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "segment_code")
    private String segmentCode;
    @Column(name = "CURRENCY_ID")
    private Integer currencyId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "branch_id")
    private int branchId;
    @Size(max = 4)
    @Column(name = "product_type_code")
    private String productTypeCode;
    @Column(name = "PRODUCT_TYPE_ID")
    private Integer productTypeId;
    @Column(name = "is_taxable")
    private Short isTaxable;
    @Size(max = 6)
    @Column(name = "tax_code1")
    private String taxCode1;
    @Size(max = 6)
    @Column(name = "tax_code2")
    private String taxCode2;
    @Size(max = 6)
    @Column(name = "tax_code3")
    private String taxCode3;
    @Column(name = "is_default")
    private Short isDefault;
    @Column(name = "has_penalty")
    private Short hasPenalty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "penalty")
    private Float penalty;
    @Size(max = 6)
    @Column(name = "loan_type_code")
    private String loanTypeCode;
    @Column(name = "is_active")
    private Integer isActive;
    @Column(name = "loan_duration")
    private Integer loanDuration;
    @Size(max = 255)
    @Column(name = "penalty_formula")
    private String penaltyFormula;

    public Products() {
    }

    public Products(Integer id) {
        this.id = id;
    }

    public Products(Integer id, String code, short isDeleted, String name, int companyId, float initialAmountMax, float initialAmountMin, float interestRateMin, float interestRateMax, float interestRate, short hasInterest, String segmentCode, int branchId) {
        this.id = id;
        this.code = code;
        this.isDeleted = isDeleted;
        this.name = name;
        this.companyId = companyId;
        this.initialAmountMax = initialAmountMax;
        this.initialAmountMin = initialAmountMin;
        this.interestRateMin = interestRateMin;
        this.interestRateMax = interestRateMax;
        this.interestRate = interestRate;
        this.hasInterest = hasInterest;
        this.segmentCode = segmentCode;
        this.branchId = branchId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public float getInitialAmountMax() {
        return initialAmountMax;
    }

    public void setInitialAmountMax(float initialAmountMax) {
        this.initialAmountMax = initialAmountMax;
    }

    public float getInitialAmountMin() {
        return initialAmountMin;
    }

    public void setInitialAmountMin(float initialAmountMin) {
        this.initialAmountMin = initialAmountMin;
    }

    public float getInterestRateMin() {
        return interestRateMin;
    }

    public void setInterestRateMin(float interestRateMin) {
        this.interestRateMin = interestRateMin;
    }

    public float getInterestRateMax() {
        return interestRateMax;
    }

    public void setInterestRateMax(float interestRateMax) {
        this.interestRateMax = interestRateMax;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public short getHasInterest() {
        return hasInterest;
    }

    public void setHasInterest(short hasInterest) {
        this.hasInterest = hasInterest;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Short getIsTaxable() {
        return isTaxable;
    }

    public void setIsTaxable(Short isTaxable) {
        this.isTaxable = isTaxable;
    }

    public String getTaxCode1() {
        return taxCode1;
    }

    public void setTaxCode1(String taxCode1) {
        this.taxCode1 = taxCode1;
    }

    public String getTaxCode2() {
        return taxCode2;
    }

    public void setTaxCode2(String taxCode2) {
        this.taxCode2 = taxCode2;
    }

    public String getTaxCode3() {
        return taxCode3;
    }

    public void setTaxCode3(String taxCode3) {
        this.taxCode3 = taxCode3;
    }

    public Short getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Short isDefault) {
        this.isDefault = isDefault;
    }

    public Short getHasPenalty() {
        return hasPenalty;
    }

    public void setHasPenalty(Short hasPenalty) {
        this.hasPenalty = hasPenalty;
    }

    public Float getPenalty() {
        return penalty;
    }

    public void setPenalty(Float penalty) {
        this.penalty = penalty;
    }

    public String getLoanTypeCode() {
        return loanTypeCode;
    }

    public void setLoanTypeCode(String loanTypeCode) {
        this.loanTypeCode = loanTypeCode;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(Integer loanDuration) {
        this.loanDuration = loanDuration;
    }

    public String getPenaltyFormula() {
        return penaltyFormula;
    }

    public void setPenaltyFormula(String penaltyFormula) {
        this.penaltyFormula = penaltyFormula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sift.transaction.model.Products[ id=" + id + " ]";
    }
    
}
