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
@Table(name = "custaccountdetails")
public class Custaccountdetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "AccountNo")
    private String accountNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "Product")
    private String product;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "AccountSet")
    private String accountSet;
    @Size(max = 70)
    @Column(name = "Title")
    private String title;
    @Size(max = 1)
    @Column(name = "LimitCheckType")
    private String limitCheckType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AccountLimit")
    private Double accountLimit;
    @Column(name = "LimitEffDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date limitEffDate;
    @Column(name = "LimitExpDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date limitExpDate;
    @Size(max = 1)
    @Column(name = "StatementFrequency")
    private String statementFrequency;
    @Column(name = "StatementBaseMonth")
    private Short statementBaseMonth;
    @Column(name = "StatementDay")
    private Short statementDay;
    @Column(name = "LastStatementDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastStatementDate;
    @Column(name = "LastStatementBalance")
    private Double lastStatementBalance;
    @Column(name = "LastStatementNumber")
    private Integer lastStatementNumber;
    @Column(name = "LastStatementTransactionNo")
    private Integer lastStatementTransactionNo;
    @Column(name = "NoofStatementCopies")
    private Short noofStatementCopies;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DRBaseRate")
    private boolean dRBaseRate;
    @Size(max = 10)
    @Column(name = "DRBaseRateCode")
    private String dRBaseRateCode;
    @Column(name = "DRMarginoverBaseRate")
    private Double dRMarginoverBaseRate;
    @Column(name = "DRFixedRate")
    private Double dRFixedRate;
    @Column(name = "DRRateEffectiveDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dRRateEffectiveDate;
    @Column(name = "DRInterestBaseDays")
    private Short dRInterestBaseDays;
    @Size(max = 1)
    @Column(name = "DRInterestFrequency")
    private String dRInterestFrequency;
    @Column(name = "DRInterestBaseMonth")
    private Short dRInterestBaseMonth;
    @Column(name = "DRInterestApplicationDay")
    private Short dRInterestApplicationDay;
    @Size(max = 1)
    @Column(name = "DRInterestApplicationMethod")
    private String dRInterestApplicationMethod;
    @Column(name = "MinimumDRInterestRate")
    private Double minimumDRInterestRate;
    @Column(name = "MaximumDRInterestRate")
    private Double maximumDRInterestRate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ChargeMinimumDRInterest")
    private boolean chargeMinimumDRInterest;
    @Column(name = "MinimumDRInterestAmount")
    private Double minimumDRInterestAmount;
    @Column(name = "AccruedDRInterest")
    private Double accruedDRInterest;
    @Size(max = 45)
    @Column(name = "DRInterestAccount")
    private String dRInterestAccount;
    @Column(name = "LastDRInterestPaidDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastDRInterestPaidDate;
    @Column(name = "LastDRInterestPaidAmount")
    private Double lastDRInterestPaidAmount;
    @Column(name = "LastDRAccrualDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastDRAccrualDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CRBaseRate")
    private boolean cRBaseRate;
    @Size(max = 10)
    @Column(name = "CRBaseRateCode")
    private String cRBaseRateCode;
    @Column(name = "CRMarginoverBaseRate")
    private Double cRMarginoverBaseRate;
    @Column(name = "CRFixedRate")
    private Double cRFixedRate;
    @Column(name = "CRRateEffectiveDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cRRateEffectiveDate;
    @Size(max = 1)
    @Column(name = "CRInterestFrequency")
    private String cRInterestFrequency;
    @Column(name = "CRInterestBaseMonth")
    private Short cRInterestBaseMonth;
    @Column(name = "CRInterestBaseDays")
    private Short cRInterestBaseDays;
    @Column(name = "CRInterestApplicationDay")
    private Short cRInterestApplicationDay;
    @Size(max = 1)
    @Column(name = "CRInterestApplicationMethod")
    private String cRInterestApplicationMethod;
    @Column(name = "MinimumCRInterestRate")
    private Double minimumCRInterestRate;
    @Column(name = "MaximumCRInterestRate")
    private Double maximumCRInterestRate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ChargeMinimumCRInterest")
    private boolean chargeMinimumCRInterest;
    @Column(name = "MinimumCRInterestAmount")
    private Double minimumCRInterestAmount;
    @Column(name = "AccruedCRInterest")
    private Double accruedCRInterest;
    @Size(max = 45)
    @Column(name = "CRInterestAccount")
    private String cRInterestAccount;
    @Column(name = "LastCRInterestPaidDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastCRInterestPaidDate;
    @Column(name = "LastCRInterestPaidAmount")
    private Double lastCRInterestPaidAmount;
    @Column(name = "LastCRAccrualDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastCRAccrualDate;
    @Column(name = "DateofLastDRRateChange")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateofLastDRRateChange;
    @Column(name = "DateofLastCRRateChange")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateofLastCRRateChange;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Active")
    private boolean active;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ChargeWithholdingTax")
    private boolean chargeWithholdingTax;
    @Column(name = "UserNo1")
    private Short userNo1;
    @Column(name = "UserNo2")
    private Integer userNo2;
    @Column(name = "UserNo3")
    private Double userNo3;
    @Column(name = "UserNo4")
    private Float userNo4;
    @Column(name = "UserDate1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userDate1;
    @Column(name = "UserDate2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userDate2;
    @Column(name = "UserAmount1")
    private Double userAmount1;
    @Column(name = "UserAmount2")
    private Double userAmount2;
    @Column(name = "UserAmount3")
    private Double userAmount3;
    @Size(max = 2)
    @Column(name = "UserText1")
    private String userText1;
    @Size(max = 6)
    @Column(name = "UserText2")
    private String userText2;
    @Size(max = 10)
    @Column(name = "UserText3")
    private String userText3;
    @Size(max = 80)
    @Column(name = "UserText4")
    private String userText4;
    @Size(max = 50)
    @Column(name = "UserText5")
    private String userText5;
    @Column(name = "Branchid")
    private Integer branchid;
    @Column(name = "Companyid")
    private Integer companyid;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;

    public Custaccountdetails() {
    }

    public Custaccountdetails(Integer id) {
        this.id = id;
    }

    public Custaccountdetails(Integer id, String accountNo, String product, String accountSet, boolean dRBaseRate, boolean chargeMinimumDRInterest, boolean cRBaseRate, boolean chargeMinimumCRInterest, boolean active, boolean chargeWithholdingTax) {
        this.id = id;
        this.accountNo = accountNo;
        this.product = product;
        this.accountSet = accountSet;
        this.dRBaseRate = dRBaseRate;
        this.chargeMinimumDRInterest = chargeMinimumDRInterest;
        this.cRBaseRate = cRBaseRate;
        this.chargeMinimumCRInterest = chargeMinimumCRInterest;
        this.active = active;
        this.chargeWithholdingTax = chargeWithholdingTax;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAccountSet() {
        return accountSet;
    }

    public void setAccountSet(String accountSet) {
        this.accountSet = accountSet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLimitCheckType() {
        return limitCheckType;
    }

    public void setLimitCheckType(String limitCheckType) {
        this.limitCheckType = limitCheckType;
    }

    public Double getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(Double accountLimit) {
        this.accountLimit = accountLimit;
    }

    public Date getLimitEffDate() {
        return limitEffDate;
    }

    public void setLimitEffDate(Date limitEffDate) {
        this.limitEffDate = limitEffDate;
    }

    public Date getLimitExpDate() {
        return limitExpDate;
    }

    public void setLimitExpDate(Date limitExpDate) {
        this.limitExpDate = limitExpDate;
    }

    public String getStatementFrequency() {
        return statementFrequency;
    }

    public void setStatementFrequency(String statementFrequency) {
        this.statementFrequency = statementFrequency;
    }

    public Short getStatementBaseMonth() {
        return statementBaseMonth;
    }

    public void setStatementBaseMonth(Short statementBaseMonth) {
        this.statementBaseMonth = statementBaseMonth;
    }

    public Short getStatementDay() {
        return statementDay;
    }

    public void setStatementDay(Short statementDay) {
        this.statementDay = statementDay;
    }

    public Date getLastStatementDate() {
        return lastStatementDate;
    }

    public void setLastStatementDate(Date lastStatementDate) {
        this.lastStatementDate = lastStatementDate;
    }

    public Double getLastStatementBalance() {
        return lastStatementBalance;
    }

    public void setLastStatementBalance(Double lastStatementBalance) {
        this.lastStatementBalance = lastStatementBalance;
    }

    public Integer getLastStatementNumber() {
        return lastStatementNumber;
    }

    public void setLastStatementNumber(Integer lastStatementNumber) {
        this.lastStatementNumber = lastStatementNumber;
    }

    public Integer getLastStatementTransactionNo() {
        return lastStatementTransactionNo;
    }

    public void setLastStatementTransactionNo(Integer lastStatementTransactionNo) {
        this.lastStatementTransactionNo = lastStatementTransactionNo;
    }

    public Short getNoofStatementCopies() {
        return noofStatementCopies;
    }

    public void setNoofStatementCopies(Short noofStatementCopies) {
        this.noofStatementCopies = noofStatementCopies;
    }

    public boolean getDRBaseRate() {
        return dRBaseRate;
    }

    public void setDRBaseRate(boolean dRBaseRate) {
        this.dRBaseRate = dRBaseRate;
    }

    public String getDRBaseRateCode() {
        return dRBaseRateCode;
    }

    public void setDRBaseRateCode(String dRBaseRateCode) {
        this.dRBaseRateCode = dRBaseRateCode;
    }

    public Double getDRMarginoverBaseRate() {
        return dRMarginoverBaseRate;
    }

    public void setDRMarginoverBaseRate(Double dRMarginoverBaseRate) {
        this.dRMarginoverBaseRate = dRMarginoverBaseRate;
    }

    public Double getDRFixedRate() {
        return dRFixedRate;
    }

    public void setDRFixedRate(Double dRFixedRate) {
        this.dRFixedRate = dRFixedRate;
    }

    public Date getDRRateEffectiveDate() {
        return dRRateEffectiveDate;
    }

    public void setDRRateEffectiveDate(Date dRRateEffectiveDate) {
        this.dRRateEffectiveDate = dRRateEffectiveDate;
    }

    public Short getDRInterestBaseDays() {
        return dRInterestBaseDays;
    }

    public void setDRInterestBaseDays(Short dRInterestBaseDays) {
        this.dRInterestBaseDays = dRInterestBaseDays;
    }

    public String getDRInterestFrequency() {
        return dRInterestFrequency;
    }

    public void setDRInterestFrequency(String dRInterestFrequency) {
        this.dRInterestFrequency = dRInterestFrequency;
    }

    public Short getDRInterestBaseMonth() {
        return dRInterestBaseMonth;
    }

    public void setDRInterestBaseMonth(Short dRInterestBaseMonth) {
        this.dRInterestBaseMonth = dRInterestBaseMonth;
    }

    public Short getDRInterestApplicationDay() {
        return dRInterestApplicationDay;
    }

    public void setDRInterestApplicationDay(Short dRInterestApplicationDay) {
        this.dRInterestApplicationDay = dRInterestApplicationDay;
    }

    public String getDRInterestApplicationMethod() {
        return dRInterestApplicationMethod;
    }

    public void setDRInterestApplicationMethod(String dRInterestApplicationMethod) {
        this.dRInterestApplicationMethod = dRInterestApplicationMethod;
    }

    public Double getMinimumDRInterestRate() {
        return minimumDRInterestRate;
    }

    public void setMinimumDRInterestRate(Double minimumDRInterestRate) {
        this.minimumDRInterestRate = minimumDRInterestRate;
    }

    public Double getMaximumDRInterestRate() {
        return maximumDRInterestRate;
    }

    public void setMaximumDRInterestRate(Double maximumDRInterestRate) {
        this.maximumDRInterestRate = maximumDRInterestRate;
    }

    public boolean getChargeMinimumDRInterest() {
        return chargeMinimumDRInterest;
    }

    public void setChargeMinimumDRInterest(boolean chargeMinimumDRInterest) {
        this.chargeMinimumDRInterest = chargeMinimumDRInterest;
    }

    public Double getMinimumDRInterestAmount() {
        return minimumDRInterestAmount;
    }

    public void setMinimumDRInterestAmount(Double minimumDRInterestAmount) {
        this.minimumDRInterestAmount = minimumDRInterestAmount;
    }

    public Double getAccruedDRInterest() {
        return accruedDRInterest;
    }

    public void setAccruedDRInterest(Double accruedDRInterest) {
        this.accruedDRInterest = accruedDRInterest;
    }

    public String getDRInterestAccount() {
        return dRInterestAccount;
    }

    public void setDRInterestAccount(String dRInterestAccount) {
        this.dRInterestAccount = dRInterestAccount;
    }

    public Date getLastDRInterestPaidDate() {
        return lastDRInterestPaidDate;
    }

    public void setLastDRInterestPaidDate(Date lastDRInterestPaidDate) {
        this.lastDRInterestPaidDate = lastDRInterestPaidDate;
    }

    public Double getLastDRInterestPaidAmount() {
        return lastDRInterestPaidAmount;
    }

    public void setLastDRInterestPaidAmount(Double lastDRInterestPaidAmount) {
        this.lastDRInterestPaidAmount = lastDRInterestPaidAmount;
    }

    public Date getLastDRAccrualDate() {
        return lastDRAccrualDate;
    }

    public void setLastDRAccrualDate(Date lastDRAccrualDate) {
        this.lastDRAccrualDate = lastDRAccrualDate;
    }

    public boolean getCRBaseRate() {
        return cRBaseRate;
    }

    public void setCRBaseRate(boolean cRBaseRate) {
        this.cRBaseRate = cRBaseRate;
    }

    public String getCRBaseRateCode() {
        return cRBaseRateCode;
    }

    public void setCRBaseRateCode(String cRBaseRateCode) {
        this.cRBaseRateCode = cRBaseRateCode;
    }

    public Double getCRMarginoverBaseRate() {
        return cRMarginoverBaseRate;
    }

    public void setCRMarginoverBaseRate(Double cRMarginoverBaseRate) {
        this.cRMarginoverBaseRate = cRMarginoverBaseRate;
    }

    public Double getCRFixedRate() {
        return cRFixedRate;
    }

    public void setCRFixedRate(Double cRFixedRate) {
        this.cRFixedRate = cRFixedRate;
    }

    public Date getCRRateEffectiveDate() {
        return cRRateEffectiveDate;
    }

    public void setCRRateEffectiveDate(Date cRRateEffectiveDate) {
        this.cRRateEffectiveDate = cRRateEffectiveDate;
    }

    public String getCRInterestFrequency() {
        return cRInterestFrequency;
    }

    public void setCRInterestFrequency(String cRInterestFrequency) {
        this.cRInterestFrequency = cRInterestFrequency;
    }

    public Short getCRInterestBaseMonth() {
        return cRInterestBaseMonth;
    }

    public void setCRInterestBaseMonth(Short cRInterestBaseMonth) {
        this.cRInterestBaseMonth = cRInterestBaseMonth;
    }

    public Short getCRInterestBaseDays() {
        return cRInterestBaseDays;
    }

    public void setCRInterestBaseDays(Short cRInterestBaseDays) {
        this.cRInterestBaseDays = cRInterestBaseDays;
    }

    public Short getCRInterestApplicationDay() {
        return cRInterestApplicationDay;
    }

    public void setCRInterestApplicationDay(Short cRInterestApplicationDay) {
        this.cRInterestApplicationDay = cRInterestApplicationDay;
    }

    public String getCRInterestApplicationMethod() {
        return cRInterestApplicationMethod;
    }

    public void setCRInterestApplicationMethod(String cRInterestApplicationMethod) {
        this.cRInterestApplicationMethod = cRInterestApplicationMethod;
    }

    public Double getMinimumCRInterestRate() {
        return minimumCRInterestRate;
    }

    public void setMinimumCRInterestRate(Double minimumCRInterestRate) {
        this.minimumCRInterestRate = minimumCRInterestRate;
    }

    public Double getMaximumCRInterestRate() {
        return maximumCRInterestRate;
    }

    public void setMaximumCRInterestRate(Double maximumCRInterestRate) {
        this.maximumCRInterestRate = maximumCRInterestRate;
    }

    public boolean getChargeMinimumCRInterest() {
        return chargeMinimumCRInterest;
    }

    public void setChargeMinimumCRInterest(boolean chargeMinimumCRInterest) {
        this.chargeMinimumCRInterest = chargeMinimumCRInterest;
    }

    public Double getMinimumCRInterestAmount() {
        return minimumCRInterestAmount;
    }

    public void setMinimumCRInterestAmount(Double minimumCRInterestAmount) {
        this.minimumCRInterestAmount = minimumCRInterestAmount;
    }

    public Double getAccruedCRInterest() {
        return accruedCRInterest;
    }

    public void setAccruedCRInterest(Double accruedCRInterest) {
        this.accruedCRInterest = accruedCRInterest;
    }

    public String getCRInterestAccount() {
        return cRInterestAccount;
    }

    public void setCRInterestAccount(String cRInterestAccount) {
        this.cRInterestAccount = cRInterestAccount;
    }

    public Date getLastCRInterestPaidDate() {
        return lastCRInterestPaidDate;
    }

    public void setLastCRInterestPaidDate(Date lastCRInterestPaidDate) {
        this.lastCRInterestPaidDate = lastCRInterestPaidDate;
    }

    public Double getLastCRInterestPaidAmount() {
        return lastCRInterestPaidAmount;
    }

    public void setLastCRInterestPaidAmount(Double lastCRInterestPaidAmount) {
        this.lastCRInterestPaidAmount = lastCRInterestPaidAmount;
    }

    public Date getLastCRAccrualDate() {
        return lastCRAccrualDate;
    }

    public void setLastCRAccrualDate(Date lastCRAccrualDate) {
        this.lastCRAccrualDate = lastCRAccrualDate;
    }

    public Date getDateofLastDRRateChange() {
        return dateofLastDRRateChange;
    }

    public void setDateofLastDRRateChange(Date dateofLastDRRateChange) {
        this.dateofLastDRRateChange = dateofLastDRRateChange;
    }

    public Date getDateofLastCRRateChange() {
        return dateofLastCRRateChange;
    }

    public void setDateofLastCRRateChange(Date dateofLastCRRateChange) {
        this.dateofLastCRRateChange = dateofLastCRRateChange;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getChargeWithholdingTax() {
        return chargeWithholdingTax;
    }

    public void setChargeWithholdingTax(boolean chargeWithholdingTax) {
        this.chargeWithholdingTax = chargeWithholdingTax;
    }

    public Short getUserNo1() {
        return userNo1;
    }

    public void setUserNo1(Short userNo1) {
        this.userNo1 = userNo1;
    }

    public Integer getUserNo2() {
        return userNo2;
    }

    public void setUserNo2(Integer userNo2) {
        this.userNo2 = userNo2;
    }

    public Double getUserNo3() {
        return userNo3;
    }

    public void setUserNo3(Double userNo3) {
        this.userNo3 = userNo3;
    }

    public Float getUserNo4() {
        return userNo4;
    }

    public void setUserNo4(Float userNo4) {
        this.userNo4 = userNo4;
    }

    public Date getUserDate1() {
        return userDate1;
    }

    public void setUserDate1(Date userDate1) {
        this.userDate1 = userDate1;
    }

    public Date getUserDate2() {
        return userDate2;
    }

    public void setUserDate2(Date userDate2) {
        this.userDate2 = userDate2;
    }

    public Double getUserAmount1() {
        return userAmount1;
    }

    public void setUserAmount1(Double userAmount1) {
        this.userAmount1 = userAmount1;
    }

    public Double getUserAmount2() {
        return userAmount2;
    }

    public void setUserAmount2(Double userAmount2) {
        this.userAmount2 = userAmount2;
    }

    public Double getUserAmount3() {
        return userAmount3;
    }

    public void setUserAmount3(Double userAmount3) {
        this.userAmount3 = userAmount3;
    }

    public String getUserText1() {
        return userText1;
    }

    public void setUserText1(String userText1) {
        this.userText1 = userText1;
    }

    public String getUserText2() {
        return userText2;
    }

    public void setUserText2(String userText2) {
        this.userText2 = userText2;
    }

    public String getUserText3() {
        return userText3;
    }

    public void setUserText3(String userText3) {
        this.userText3 = userText3;
    }

    public String getUserText4() {
        return userText4;
    }

    public void setUserText4(String userText4) {
        this.userText4 = userText4;
    }

    public String getUserText5() {
        return userText5;
    }

    public void setUserText5(String userText5) {
        this.userText5 = userText5;
    }

    public Integer getBranchid() {
        return branchid;
    }

    public void setBranchid(Integer branchid) {
        this.branchid = branchid;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Custaccountdetails)) {
            return false;
        }
        Custaccountdetails other = (Custaccountdetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sift.gl.model.Custaccountdetails[ id=" + id + " ]";
    }
    
}
