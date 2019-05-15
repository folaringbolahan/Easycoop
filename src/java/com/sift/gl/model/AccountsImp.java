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

/**
 *
 * @author Olakunle Awotunbo
 */
@Entity
@Table(name = "accounts")
@NamedQueries({
    @NamedQuery(name = "AccountsImp.findAll", query = "SELECT a FROM AccountsImp a")})
public class AccountsImp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AcID")
    private Integer acID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "AccountNo")
    private String accountNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Name")
    private String name;
    @Size(max = 15)
    @Column(name = "Shortname")
    private String shortname;
    @Size(max = 15)
    @Column(name = "AcType")
    private String acType;
    @Column(name = "AcGroup")
    private Integer acGroup;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "AcStruct")
    private String acStruct;
    @Size(max = 15)
    @Column(name = "Aseg1")
    private String aseg1;
    @Size(max = 15)
    @Column(name = "Aseg2")
    private String aseg2;
    @Size(max = 15)
    @Column(name = "Aseg3")
    private String aseg3;
    @Size(max = 15)
    @Column(name = "Aseg4")
    private String aseg4;
    @Size(max = 15)
    @Column(name = "Aseg5")
    private String aseg5;
    @Size(max = 15)
    @Column(name = "Aseg6")
    private String aseg6;
    @Size(max = 15)
    @Column(name = "Aseg7")
    private String aseg7;
    @Size(max = 15)
    @Column(name = "Aseg8")
    private String aseg8;
    @Size(max = 15)
    @Column(name = "Aseg9")
    private String aseg9;
    @Size(max = 15)
    @Column(name = "Aseg10")
    private String aseg10;
    @Size(max = 15)
    @Column(name = "Asegc")
    private String asegc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "Currency")
    private String currency;
    @Column(name = "SubAccount")
    private Boolean subAccount;
    @Column(name = "ControlAccount")
    private Boolean controlAccount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "BalanceType")
    private String balanceType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CCyBalance")
    private double cCyBalance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CCyClearedBalance")
    private double cCyClearedBalance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Balance")
    private double balance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ClearedBalance")
    private double clearedBalance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Active")
    private boolean active;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Closed")
    private boolean closed;
    @Column(name = "Blocked")
    private Boolean blocked;
    @Column(name = "DateOpened")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOpened;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Branch")
    private int branch;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Companyid")
    private int companyid;
    @Size(max = 1)
    @Column(name = "Accounttype")
    private String accounttype;
    @Size(max = 20)
    @Column(name = "Controlaccountno")
    private String controlaccountno;

    public AccountsImp() {
    }

    public AccountsImp(Integer acID) {
        this.acID = acID;
    }

    public AccountsImp(Integer acID, String accountNo, String name, String acStruct, String currency, String balanceType, double cCyBalance, double cCyClearedBalance, double balance, double clearedBalance, boolean active, boolean closed, int branch, int companyid) {
        this.acID = acID;
        this.accountNo = accountNo;
        this.name = name;
        this.acStruct = acStruct;
        this.currency = currency;
        this.balanceType = balanceType;
        this.cCyBalance = cCyBalance;
        this.cCyClearedBalance = cCyClearedBalance;
        this.balance = balance;
        this.clearedBalance = clearedBalance;
        this.active = active;
        this.closed = closed;
        this.branch = branch;
        this.companyid = companyid;
    }

    public Integer getAcID() {
        return acID;
    }

    public void setAcID(Integer acID) {
        this.acID = acID;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Integer getAcGroup() {
        return acGroup;
    }

    public void setAcGroup(Integer acGroup) {
        this.acGroup = acGroup;
    }

    public String getAcStruct() {
        return acStruct;
    }

    public void setAcStruct(String acStruct) {
        this.acStruct = acStruct;
    }

    public String getAseg1() {
        return aseg1;
    }

    public void setAseg1(String aseg1) {
        this.aseg1 = aseg1;
    }

    public String getAseg2() {
        return aseg2;
    }

    public void setAseg2(String aseg2) {
        this.aseg2 = aseg2;
    }

    public String getAseg3() {
        return aseg3;
    }

    public void setAseg3(String aseg3) {
        this.aseg3 = aseg3;
    }

    public String getAseg4() {
        return aseg4;
    }

    public void setAseg4(String aseg4) {
        this.aseg4 = aseg4;
    }

    public String getAseg5() {
        return aseg5;
    }

    public void setAseg5(String aseg5) {
        this.aseg5 = aseg5;
    }

    public String getAseg6() {
        return aseg6;
    }

    public void setAseg6(String aseg6) {
        this.aseg6 = aseg6;
    }

    public String getAseg7() {
        return aseg7;
    }

    public void setAseg7(String aseg7) {
        this.aseg7 = aseg7;
    }

    public String getAseg8() {
        return aseg8;
    }

    public void setAseg8(String aseg8) {
        this.aseg8 = aseg8;
    }

    public String getAseg9() {
        return aseg9;
    }

    public void setAseg9(String aseg9) {
        this.aseg9 = aseg9;
    }

    public String getAseg10() {
        return aseg10;
    }

    public void setAseg10(String aseg10) {
        this.aseg10 = aseg10;
    }

    public String getAsegc() {
        return asegc;
    }

    public void setAsegc(String asegc) {
        this.asegc = asegc;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(Boolean subAccount) {
        this.subAccount = subAccount;
    }

    public Boolean getControlAccount() {
        return controlAccount;
    }

    public void setControlAccount(Boolean controlAccount) {
        this.controlAccount = controlAccount;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    public double getCCyBalance() {
        return cCyBalance;
    }

    public void setCCyBalance(double cCyBalance) {
        this.cCyBalance = cCyBalance;
    }

    public double getCCyClearedBalance() {
        return cCyClearedBalance;
    }

    public void setCCyClearedBalance(double cCyClearedBalance) {
        this.cCyClearedBalance = cCyClearedBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getClearedBalance() {
        return clearedBalance;
    }

    public void setClearedBalance(double clearedBalance) {
        this.clearedBalance = clearedBalance;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getControlaccountno() {
        return controlaccountno;
    }

    public void setControlaccountno(String controlaccountno) {
        this.controlaccountno = controlaccountno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acID != null ? acID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountsImp)) {
            return false;
        }
        AccountsImp other = (AccountsImp) object;
        if ((this.acID == null && other.acID != null) || (this.acID != null && !this.acID.equals(other.acID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sift.gl.model.AccountsImp[ acID=" + acID + " ]";
    }
    
}
