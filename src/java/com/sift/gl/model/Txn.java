/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.model;


/**
 *
 * @author yomi-pc
 */

public class Txn  {
    private Integer txnID;
    private String txnType;
    private long year;
    private Integer period;
    private String accountno;
    private String reference;
    private String headerdocref;
    private String narrative;
    private String txnSerial;
    private String transactionCode;
    private double cCyDebit;
    private double cCyCredit;
    private double cCyAmount;
    private double debit=0.00;
    private double credit=0.00;
    private double amount;
    private double rate;
    private String userID;
    private String title;
    private String currency;
    private Integer branchID;
    private Integer company;
    
    public Txn() {
    }

    public Txn(Integer txnID) {
        this.txnID = txnID;
    }

    public Txn(Integer txnID, String txnType, long year, Integer period, String accountno, String reference, String narrative, String txnSerial, double cCyDebit, double cCyCredit, double cCyAmount, double debit, double credit, double amount, double rate, String userID, Integer branchID,Integer company) {
        this.txnID = txnID;
        this.txnType = txnType;
        this.year = year;
        this.period = period;
        this.accountno = accountno;
        this.reference = reference;
        this.narrative = narrative;
        this.txnSerial = txnSerial;
        this.cCyDebit = cCyDebit;
        this.cCyCredit = cCyCredit;
        this.cCyAmount = cCyAmount;
        this.debit = debit;
        this.credit = credit;
        this.amount = amount;
        this.rate = rate;
        this.userID = userID;
        this.branchID = branchID;
        this.company = company;
    }

    public Integer getTxnID() {
        return txnID;
    }

    public void setTxnID(Integer txnID) {
        this.txnID = txnID;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getHeaderdocref() {
        return headerdocref;
    }

    public void setHeaderdocref(String headerdocref) {
        this.headerdocref = headerdocref;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public String getTxnSerial() {
        return txnSerial;
    }

    public void setTxnSerial(String txnSerial) {
        this.txnSerial = txnSerial;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public double getCCyDebit() {
        return cCyDebit;
    }

    public void setCCyDebit(double cCyDebit) {
        this.cCyDebit = cCyDebit;
    }

    public double getCCyCredit() {
        return cCyCredit;
    }

    public void setCCyCredit(double cCyCredit) {
        this.cCyCredit = cCyCredit;
    }

    public double getCCyAmount() {
        return cCyAmount;
    }

    public void setCCyAmount(double cCyAmount) {
        this.cCyAmount = cCyAmount;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBranchID() {
        return branchID;
    }

    public void setBranchID(Integer branchID) {
        this.branchID = branchID;
    }
    public Integer getCompany() {
        return this.company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }

   
}
