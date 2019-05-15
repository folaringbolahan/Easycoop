/*
 * To change this template, choose Tools | Templates
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yomi-pc
 */
@Entity
@Table(name = "txnsheader")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Txnsheader.findAll", query = "SELECT t FROM Txnsheader t"),
    @NamedQuery(name = "Txnsheader.findByHeaderID", query = "SELECT t FROM Txnsheader t WHERE t.headerID = :headerID"),
    @NamedQuery(name = "Txnsheader.findByTxnSerial", query = "SELECT t FROM Txnsheader t WHERE t.txnSerial = :txnSerial"),
    @NamedQuery(name = "Txnsheader.findByTxnType", query = "SELECT t FROM Txnsheader t WHERE t.txnType = :txnType"),
    @NamedQuery(name = "Txnsheader.findByYear", query = "SELECT t FROM Txnsheader t WHERE t.year = :year"),
    @NamedQuery(name = "Txnsheader.findByPeriod", query = "SELECT t FROM Txnsheader t WHERE t.period = :period"),
    @NamedQuery(name = "Txnsheader.findByTxnDate", query = "SELECT t FROM Txnsheader t WHERE t.txnDate = :txnDate"),
    @NamedQuery(name = "Txnsheader.findByPostdate", query = "SELECT t FROM Txnsheader t WHERE t.postdate = :postdate"),
    @NamedQuery(name = "Txnsheader.findByEntrydate", query = "SELECT t FROM Txnsheader t WHERE t.entrydate = :entrydate"),
    @NamedQuery(name = "Txnsheader.findByDocRef", query = "SELECT t FROM Txnsheader t WHERE t.docRef = :docRef"),
    @NamedQuery(name = "Txnsheader.findByHeadernarrative", query = "SELECT t FROM Txnsheader t WHERE t.headernarrative = :headernarrative"),
    @NamedQuery(name = "Txnsheader.findBySource", query = "SELECT t FROM Txnsheader t WHERE t.source = :source"),
    @NamedQuery(name = "Txnsheader.findByUserID", query = "SELECT t FROM Txnsheader t WHERE t.userID = :userID"),
    @NamedQuery(name = "Txnsheader.findByBranchID", query = "SELECT t FROM Txnsheader t WHERE t.branchID = :branchID")})
public class Txnsheaderhib implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "HeaderID")
    private Integer headerID;
    @Basic(optional = false)
    @Column(name = "TxnSerial")
    private String txnSerial;
    @Basic(optional = false)
    @Column(name = "TxnType")
    private String txnType;
    @Basic(optional = false)
    @Column(name = "Year")
    private long year;
    @Basic(optional = false)
    @Column(name = "Period")
    private int period;
    @Basic(optional = false)
    @Column(name = "TxnDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date txnDate;
    @Basic(optional = false)
    @Column(name = "Postdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postdate;
    @Column(name = "Entrydate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entrydate;
    @Basic(optional = false)
    @Column(name = "DocRef")
    private String docRef;
    @Column(name = "Headernarrative")
    private String headernarrative;
    @Column(name = "Source")
    private String source;
    @Basic(optional = false)
    @Column(name = "UserID")
    private String userID;
    @Basic(optional = false)
    @Column(name = "BranchID")
    private String branchID;

    public Txnsheaderhib() {
    }

    public Txnsheaderhib(Integer headerID) {
        this.headerID = headerID;
    }

    public Txnsheaderhib(Integer headerID, String txnSerial, String txnType, long year, int period, Date txnDate, Date postdate, String docRef, String userID, String branchID) {
        this.headerID = headerID;
        this.txnSerial = txnSerial;
        this.txnType = txnType;
        this.year = year;
        this.period = period;
        this.txnDate = txnDate;
        this.postdate = postdate;
        this.docRef = docRef;
        this.userID = userID;
        this.branchID = branchID;
    }

    public Integer getHeaderID() {
        return headerID;
    }

    public void setHeaderID(Integer headerID) {
        this.headerID = headerID;
    }

    public String getTxnSerial() {
        return txnSerial;
    }

    public void setTxnSerial(String txnSerial) {
        this.txnSerial = txnSerial;
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

    public Date getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    public Date getPostdate() {
        return postdate;
    }

    public void setPostdate(Date postdate) {
        this.postdate = postdate;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

    public String getDocRef() {
        return docRef;
    }

    public void setDocRef(String docRef) {
        this.docRef = docRef;
    }

    public String getHeadernarrative() {
        return headernarrative;
    }

    public void setHeadernarrative(String headernarrative) {
        this.headernarrative = headernarrative;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
        hash += (headerID != null ? headerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Txnsheaderhib)) {
            return false;
        }
        Txnsheaderhib other = (Txnsheaderhib) object;
        if ((this.headerID == null && other.headerID != null) || (this.headerID != null && !this.headerID.equals(other.headerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sift.gl.Txnsheader[ headerID=" + headerID + " ]";
    }
    
}
