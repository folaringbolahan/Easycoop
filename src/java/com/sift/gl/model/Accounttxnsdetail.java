/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.model;

/**
 *
 * @author yomi-pc
 */
public class Accounttxnsdetail {
    private Integer txnId;
     private String txnType;
     private long year;
     private Integer period;
     private String accountno;
     private String docref;
     private String narrative;
     private String txnSerial;
     private double ccyDebit;
     private double ccyCredit;
     private double ccyAmount;
     private double debit;
     private double credit;
     private double amount;
     private String entryref;
     private String userId;
     private String postdate;
     private String valuedate;
     private Integer branchid;
     private Integer companyid;

    public Accounttxnsdetail() {
    }

	
    public Accounttxnsdetail(String txnType, long year, Integer period, String accountno, String docref, String narrative, String txnSerial, double ccyDebit, double ccyCredit, double ccyAmount, double debit, double credit, double amount, String entryref, String userId, Integer branchid,Integer companyid) {
        this.txnType = txnType;
        this.year = year;
        this.period = period;
        this.accountno = accountno;
        this.docref = docref;
        this.narrative = narrative;
        this.txnSerial = txnSerial;
        this.ccyDebit = ccyDebit;
        this.ccyCredit = ccyCredit;
        this.ccyAmount = ccyAmount;
        this.debit = debit;
        this.credit = credit;
        this.amount = amount;
        this.entryref = entryref;
        this.userId = userId;
        this.branchid = branchid;
        this.companyid = companyid;
    }
    public Accounttxnsdetail(String txnType, long year, Integer period, String accountno, String docref, String narrative, String txnSerial, double ccyDebit, double ccyCredit, double ccyAmount, double debit, double credit, double amount, String entryref, String userId, String postdate, String valuedate,Integer branchid,Integer companyid) {
       this.txnType = txnType;
       this.year = year;
       this.period = period;
       this.accountno = accountno;
       this.docref = docref;
       this.narrative = narrative;
       this.txnSerial = txnSerial;
       this.ccyDebit = ccyDebit;
       this.ccyCredit = ccyCredit;
       this.ccyAmount = ccyAmount;
       this.debit = debit;
       this.credit = credit;
       this.amount = amount;
       this.entryref = entryref;
       this.userId = userId;
       this.postdate = postdate;
       this.valuedate = valuedate;
       this.branchid = branchid;
       this.companyid = companyid;
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
    public long getYear() {
        return this.year;
    }
    
    public void setYear(long year) {
        this.year = year;
    }
    public Integer getPeriod() {
        return this.period;
    }
    
    public void setPeriod(Integer period) {
        this.period = period;
    }
    public String getAccountno() {
        return this.accountno;
    }
    
    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }
    public String getDocref() {
        return this.docref;
    }
    
    public void setDocref(String docref) {
        this.docref = docref;
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
    public String getEntryref() {
        return this.entryref;
    }
    
    public void setEntryref(String entryref) {
        this.entryref = entryref;
    }
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPostdate() {
        return this.postdate;
    }
    
    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }
    public String getValuedate() {
        return this.valuedate;
    }
    
    public void setValuedate(String valuedate) {
        this.valuedate = valuedate;
    }
    public Integer getBranchid() {
        return this.branchid;
    }
    
    public void setBranchid(Integer branchid) {
        this.branchid = branchid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }
    public Integer getCompanyid() {
        return this.companyid;
    }
}
