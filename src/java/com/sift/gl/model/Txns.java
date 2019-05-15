/*
 * To change this template, choose Tools | Templates
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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yomi-pc
 */
@Entity
@Table(name = "txns")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Txns.findAll", query = "SELECT t FROM Txns t"),
    @NamedQuery(name = "Txns.findByTxnID", query = "SELECT t FROM Txns t WHERE t.txnID = :txnID"),
    @NamedQuery(name = "Txns.findByTxnType", query = "SELECT t FROM Txns t WHERE t.txnType = :txnType"),
    @NamedQuery(name = "Txns.findByYear", query = "SELECT t FROM Txns t WHERE t.year = :year"),
    @NamedQuery(name = "Txns.findByPeriod", query = "SELECT t FROM Txns t WHERE t.period = :period"),
    @NamedQuery(name = "Txns.findByAccountno", query = "SELECT t FROM Txns t WHERE t.accountno = :accountno"),
    @NamedQuery(name = "Txns.findByDocref", query = "SELECT t FROM Txns t WHERE t.docref = :docref"),
    @NamedQuery(name = "Txns.findByHeaderdocref", query = "SELECT t FROM Txns t WHERE t.headerdocref = :headerdocref"),
    @NamedQuery(name = "Txns.findByNarrative", query = "SELECT t FROM Txns t WHERE t.narrative = :narrative"),
    @NamedQuery(name = "Txns.findByTxnSerial", query = "SELECT t FROM Txns t WHERE t.txnSerial = :txnSerial"),
    @NamedQuery(name = "Txns.findByTransactionCode", query = "SELECT t FROM Txns t WHERE t.transactionCode = :transactionCode"),
    @NamedQuery(name = "Txns.findByCCyDebit", query = "SELECT t FROM Txns t WHERE t.cCyDebit = :cCyDebit"),
    @NamedQuery(name = "Txns.findByCCyCredit", query = "SELECT t FROM Txns t WHERE t.cCyCredit = :cCyCredit"),
    @NamedQuery(name = "Txns.findByCCyAmount", query = "SELECT t FROM Txns t WHERE t.cCyAmount = :cCyAmount"),
    @NamedQuery(name = "Txns.findByDebit", query = "SELECT t FROM Txns t WHERE t.debit = :debit"),
    @NamedQuery(name = "Txns.findByCredit", query = "SELECT t FROM Txns t WHERE t.credit = :credit"),
    @NamedQuery(name = "Txns.findByAmount", query = "SELECT t FROM Txns t WHERE t.amount = :amount"),
    @NamedQuery(name = "Txns.findByRate", query = "SELECT t FROM Txns t WHERE t.rate = :rate"),
    @NamedQuery(name = "Txns.findByUserID", query = "SELECT t FROM Txns t WHERE t.userID = :userID"),
    @NamedQuery(name = "Txns.findByAnalysiscode1", query = "SELECT t FROM Txns t WHERE t.analysiscode1 = :analysiscode1"),
    @NamedQuery(name = "Txns.findByAnalysiscode2", query = "SELECT t FROM Txns t WHERE t.analysiscode2 = :analysiscode2"),
    @NamedQuery(name = "Txns.findByBranchID", query = "SELECT t FROM Txns t WHERE t.branchID = :branchID")})
public class Txns implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TxnID")
    @XmlElement(name = "txnID")
    private Integer txnID;
    @Basic(optional = false)
    @Column(name = "TxnType")
    @XmlElement(name = "txnType")
    private String txnType;
    @Basic(optional = false)
    @Column(name = "Year")
    @XmlElement(name = "year")
    private long year;
    @Basic(optional = false)
    @Column(name = "Period")
    @XmlElement(name = "period")
    private int period;
    @Basic(optional = false)
    @Column(name = "Accountno")
    @XmlElement(name = "accountno")
    private String accountno;
    @Basic(optional = false)
    @Column(name = "Docref")
    @XmlElement(name = "docref")
    private String docref;
    @Column(name = "Headerdocref")
    @XmlElement(name = "headerdocref")
    private String headerdocref;
    @Basic(optional = false)
    @Column(name = "Narrative")
    @XmlElement(name = "narrative")
    private String narrative;
    @Basic(optional = false)
    @Column(name = "TxnSerial")
    @XmlElement(name = "txnSerial")
    private String txnSerial;
    @Column(name = "TransactionCode")
    @XmlElement(name = "transactionCode")
    private String transactionCode;
    @Basic(optional = false)
    @Column(name = "CCyDebit")
    @XmlElement(name = "cCyDebit")
    private double cCyDebit;
    @Basic(optional = false)
    @Column(name = "CCyCredit")
    @XmlElement(name = "cCyCredit")
    private double cCyCredit;
    @Basic(optional = false)
    @Column(name = "CCyAmount")
    @XmlElement(name = "cCyAmount")
    private double cCyAmount;
    @Basic(optional = false)
    @Column(name = "Debit")
    @XmlElement(name = "debit")
    private double debit;
    @Basic(optional = false)
    @Column(name = "Credit")
    @XmlElement(name = "credit")
    private double credit;
    @Basic(optional = false)
    @Column(name = "Amount")
    @XmlElement(name = "amount")
    private double amount;
    @Basic(optional = false)
    @Column(name = "Rate")
    @XmlElement(name = "rate")
    private double rate;
    @Basic(optional = false)
    @Column(name = "UserID")
    @XmlElement(name = "userID")
    private String userID;
    @Column(name = "Analysiscode1")
    @XmlElement(name = "analysiscode1")
    private String analysiscode1;
    @Column(name = "Analysiscode2")
    @XmlElement(name = "analysiscode2")
    private String analysiscode2;
    @Basic(optional = false)
    @Column(name = "BranchID")
    @XmlElement(name = "branchID")
    private String branchID;

    public Txns() {
    }

    public Txns(Integer txnID) {
        this.txnID = txnID;
    }

    public Txns(Integer txnID, String txnType, long year, int period, String accountno, String docref, String narrative, String txnSerial, double cCyDebit, double cCyCredit, double cCyAmount, double debit, double credit, double amount, double rate, String userID, String branchID) {
        this.txnID = txnID;
        this.txnType = txnType;
        this.year = year;
        this.period = period;
        this.accountno = accountno;
        this.docref = docref;
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

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getDocref() {
        return docref;
    }

    public void setDocref(String docref) {
        this.docref = docref;
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

    public String getAnalysiscode1() {
        return analysiscode1;
    }

    public void setAnalysiscode1(String analysiscode1) {
        this.analysiscode1 = analysiscode1;
    }

    public String getAnalysiscode2() {
        return analysiscode2;
    }

    public void setAnalysiscode2(String analysiscode2) {
        this.analysiscode2 = analysiscode2;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txnID != null ? txnID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Txns)) {
            return false;
        }
        Txns other = (Txns) object;
        if ((this.txnID == null && other.txnID != null) || (this.txnID != null && !this.txnID.equals(other.txnID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sift.gl.Txns[ txnID=" + txnID + " ]";
    }
    
}
