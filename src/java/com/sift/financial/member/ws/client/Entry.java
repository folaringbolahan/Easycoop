/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.ws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yomi-pc
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Entry implements java.io.Serializable{
    @XmlElement(name = "txnId")    
     private Integer txnId;
    @XmlElement(name = "txnType")
     private String txnType;
    @XmlElement(name = "docref")
     private String docref;
    @XmlElement(name = "headerdocref")
     private String headerdocref;
    @XmlElement(name = "narrative")
     private String narrative;
    @XmlElement(name = "txnSerial")
     private String txnSerial;
    @XmlElement(name = "ccyDebit")
     private double ccyDebit;
    @XmlElement(name = "ccyCredit")
     private double ccyCredit;
    @XmlElement(name = "ccyAmount")
     private double ccyAmount;
    @XmlElement(name = "debit")
     private double debit;
    @XmlElement(name = "credit")
     private double credit;
    @XmlElement(name = "amount")
     private double amount;
    @XmlElement(name = "rate")
     private double rate;
    @XmlElement(name = "userId")
     private String userId;
    @XmlElement(name = "accountNo")
     private String accountNo;
    @XmlElement(name = "analysiscode2")
     private String analysiscode2;
    @XmlElement(name = "branchId")
     private Integer branchId;
    @XmlElement(name = "txnCode") 
    private String txnCode;
    @XmlElement(name = "year")
     private Integer year;
     @XmlElement(name = "period")
     private Integer period;
     @XmlElement(name = "company")
     private Integer company;

    public Entry() {
    }
/*
    public Entry(String txnType,String txnCode,String accountNo,String valueDate,String docref ,String headerdocref, String narrative, String txnSerial, double ccyAmount, double amount, double rate, String userId, String branchId) {
       this.txnType = txnType;
       this.txnCode= txnCode;
       this.docref = docref;
       this.headerdocref = headerdocref;
       this.narrative = narrative;
       this.txnSerial = txnSerial;
       this.accountNo = accountNo;
       if (ccyAmount > 0)
        {
           this.ccyCredit = ccyAmount;
        }
       else
        {
           this.ccyDebit = -1 * ccyAmount ;
        }
       //this.ccyCredit = 0;
       this.ccyAmount = ccyAmount;
        if (amount > 0)
        {
          this.credit = amount;
        }
        else
        {
           this.debit = -1 * amount;
        }
       this.amount = amount;
       this.rate = rate;
       this.userId = userId;
       this.branchId = branchId;
     }
     * 
     */
     
    public Entry(String txnType,String txnCode,String accountNo,String valueDate,String docref ,String headerdocref, String narrative, String txnSerial, double ccyAmount, double amount, double rate, String userId, Integer branchId,Integer year,Integer period,Integer company) {
       this.txnType = txnType;
       this.txnCode= txnCode;
       this.docref = docref;
       this.year= year;
       this.period = period;
       this.headerdocref = headerdocref;
       this.narrative = narrative;
       this.txnSerial = txnSerial;
       this.accountNo = accountNo;
       if (ccyAmount > 0)
        {
           this.ccyCredit = ccyAmount;
        }
       else
        {
           this.ccyDebit = -1 * ccyAmount ;
        }
       //this.ccyCredit = 0;
       this.ccyAmount = ccyAmount;
        if (amount > 0)
        {
          this.credit = amount;
        }
        else
        {
           this.debit = -1 * amount;
        }
       this.amount = amount;
       this.rate = rate;
       this.userId = userId;
       this.branchId = branchId;
       this.company = company;
     }


    public Integer getTxnId() {
        return this.txnId;
    }

    public void setTxnId(Integer txnId) {
        this.txnId = txnId;
    }
    public String getTxnType() {
        return this.txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
    public String getDocref() {
        return this.docref;
    }

    public void setDocref(String docref) {
        this.docref = docref;
    }
    public String getHeaderdocref() {
        return this.headerdocref;
    }

    public void setHeaderdocref(String headerdocref) {
        this.headerdocref = headerdocref;
    }
    public String getNarrative() {
        return this.narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }
    public String getTxnSerial() {
        return this.txnSerial;
    }

    public void setTxnSerial(String txnSerial) {
        this.txnSerial = txnSerial;
    }
    public double getCcyDebit() {
        return this.ccyDebit;
    }

    public void setCcyDebit(double ccyDebit) {
        this.ccyDebit = ccyDebit;
    }
    public double getCcyCredit() {
        return this.ccyCredit;
    }

    public void setCcyCredit(double ccyCredit) {
        this.ccyCredit = ccyCredit;
    }
    public double getCcyAmount() {
        return this.ccyAmount;
    }

    public void setCcyAmount(double ccyAmount) {
        this.ccyAmount = ccyAmount;
    }
    public double getDebit() {
        return this.debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }
    public double getCredit() {
        return this.credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getRate() {
        return this.rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getAccountNo() {
        return this.accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    public Integer getBranchId() {
        return this.branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }
    public String getTxncode() {
        return this.txnCode;
    }

    public void setTxncode(String txnCode) {
        this.txnCode = txnCode;
    }
    public Integer getPeriod() {
        return this.period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }
    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getCompany() {
        return this.company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }
}
