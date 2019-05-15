/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;

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
 * @author kola
*/

@Entity
@Table(name = "vot_voteresults")
/*@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VotVoteresults.findAll", query = "SELECT v FROM VotVoteresults v"),
    @NamedQuery(name = "VotVoteresults.findById", query = "SELECT v FROM VotVoteresults v WHERE v.id = :id"),
    @NamedQuery(name = "VotVoteresults.findByCompanyid", query = "SELECT v FROM VotVoteresults v WHERE v.companyid = :companyid"),
    @NamedQuery(name = "VotVoteresults.findByVotequestid", query = "SELECT v FROM VotVoteresults v WHERE v.votequestid = :votequestid"),
    @NamedQuery(name = "VotVoteresults.findByVoteoptionid", query = "SELECT v FROM VotVoteresults v WHERE v.voteoptionid = :voteoptionid"),
    @NamedQuery(name = "VotVoteresults.findByMemberid", query = "SELECT v FROM VotVoteresults v WHERE v.memberid = :memberid"),
    @NamedQuery(name = "VotVoteresults.findByBranchid", query = "SELECT v FROM VotVoteresults v WHERE v.branchid = :branchid"),
    @NamedQuery(name = "VotVoteresults.findByAnswerdate", query = "SELECT v FROM VotVoteresults v WHERE v.answerdate = :answerdate"),
    @NamedQuery(name = "VotVoteresults.findByUnitsofvote", query = "SELECT v FROM VotVoteresults v WHERE v.unitsofvote = :unitsofvote")})
*/
public class VotVoteresults implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    @Column(name = "id")
    private Integer id;
   // @Basic(optional = false)
    @Column(name = "companyid")
    private int companyid;
    @Column(name = "votequestid")
    private Integer votequestid;
    @Column(name = "voteoptionid")
    private Integer voteoptionid;
    @Column(name = "memberid")
    private Integer memberid;
   // @Basic(optional = false)
    @Column(name = "branchid")
    private int branchid;
   // @Basic(optional = false)
    @Column(name = "answerdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date answerdate;
   // @Basic(optional = false)
    @Column(name = "unitsofvote")
    private int unitsofvote;

    public VotVoteresults() {
    }

    public VotVoteresults(Integer id) {
        this.id = id;
    }

    public VotVoteresults(Integer id, int companyid, int branchid, Date answerdate, int unitsofvote) {
        this.id = id;
        this.companyid = companyid;
        this.branchid = branchid;
        this.answerdate = answerdate;
        this.unitsofvote = unitsofvote;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    public Integer getVotequestid() {
        return votequestid;
    }

    public void setVotequestid(Integer votequestid) {
        this.votequestid = votequestid;
    }

    public Integer getVoteoptionid() {
        return voteoptionid;
    }

    public void setVoteoptionid(Integer voteoptionid) {
        this.voteoptionid = voteoptionid;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public int getBranchid() {
        return branchid;
    }

    public void setBranchid(int branchid) {
        this.branchid = branchid;
    }

    public Date getAnswerdate() {
        return answerdate;
    }

    public void setAnswerdate(Date answerdate) {
        this.answerdate = answerdate;
    }

    public int getUnitsofvote() {
        return unitsofvote;
    }

    public void setUnitsofvote(int unitsofvote) {
        this.unitsofvote = unitsofvote;
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
        if (!(object instanceof VotVoteresults)) {
            return false;
        }
        VotVoteresults other = (VotVoteresults) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.VotVoteresults[ id=" + id + " ]";
    }
    
}
