/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;

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
import javax.xml.bind.annotation.XmlRootElement;


 
 
@Entity
@Table(name = "vot_members")

/**
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VotMembers.findAll", query = "SELECT v FROM VotMembers v"),
    @NamedQuery(name = "VotMembers.findByMemberid", query = "SELECT v FROM VotMembers v WHERE v.memberid = :memberid"),
    @NamedQuery(name = "VotMembers.findByMemberrefid", query = "SELECT v FROM VotMembers v WHERE v.memberrefid = :memberrefid"),
    @NamedQuery(name = "VotMembers.findByUserid", query = "SELECT v FROM VotMembers v WHERE v.userid = :userid"),
    @NamedQuery(name = "VotMembers.findByFirstname", query = "SELECT v FROM VotMembers v WHERE v.firstname = :firstname"),
    @NamedQuery(name = "VotMembers.findByMiddlename", query = "SELECT v FROM VotMembers v WHERE v.middlename = :middlename"),
    @NamedQuery(name = "VotMembers.findBySurname", query = "SELECT v FROM VotMembers v WHERE v.surname = :surname"),
    @NamedQuery(name = "VotMembers.findByEmail", query = "SELECT v FROM VotMembers v WHERE v.email = :email"),
    @NamedQuery(name = "VotMembers.findByPhone", query = "SELECT v FROM VotMembers v WHERE v.phone = :phone"),
    @NamedQuery(name = "VotMembers.findByBranchid", query = "SELECT v FROM VotMembers v WHERE v.branchid = :branchid"),
    @NamedQuery(name = "VotMembers.findByCompanyid", query = "SELECT v FROM VotMembers v WHERE v.companyid = :companyid")})
*/
public class VotMembers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "memberid")
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    
    private Integer memberid;
    //@Basic(optional = false)
    private int memberrefid;
   // @Basic(optional = false)
   @Column(name = "userid")
    private String userid;
    //@Basic(optional = false)
   @Column(name = "firstname")
    private String firstname;
    //@Basic(optional = false)
    @Column(name = "middlename")
    private String middlename;
    //@Basic(optional = false)
    @Column(name = "surname")
    private String surname;
   // @Basic(optional = false)
    @Column(name = "email")
    private String email;
   // @Column(name = "phone")
    private String phone;
   // @Basic(optional = false)
    @Column(name = "branchid")
    private int branchid;
   // @Basic(optional = false)
    @Column(name = "companyid")
    private int companyid;
    
    @Column(name = "agmid")
    private int agmid;
    
    @Column(name = "voteunits")
    private int voteunits;
    
    
    @Column(name = "concluded")  
    private String concluded;
    

    public VotMembers() {
    }

    public VotMembers(Integer memberid) {
        this.memberid = memberid;
    }

    public VotMembers(Integer memberid, int memberrefid, String userid, String firstname, String middlename, String surname, String email, int branchid, int companyid,int agmid,int voteunits) {
        this.memberid = memberid;
        this.memberrefid = memberrefid;
        this.userid = userid;
        this.firstname = firstname;
        this.middlename = middlename;
        this.surname = surname;
        this.email = email;
        this.branchid = branchid;
        this.companyid = companyid;
        this.agmid = agmid;
        this.voteunits = voteunits;
    }
    
    
    public VotMembers(Integer memberid, int memberrefid, String userid, String firstname, String middlename, String surname, String email, int branchid, int companyid) {
        this.memberid = memberid;
        this.memberrefid = memberrefid;
        this.userid = userid;
        this.firstname = firstname;
        this.middlename = middlename;
        this.surname = surname;
        this.email = email;
        this.branchid = branchid;
        this.companyid = companyid;
    
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public int getMemberrefid() {
        return memberrefid;
    }

    public void setMemberrefid(int memberrefid) {
        this.memberrefid = memberrefid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getBranchid() {
        return branchid;
    }

    public void setBranchid(int branchid) {
        this.branchid = branchid;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }
    
    public int getAgmid() {
        return agmid;
    }

    public void setAgmid(int agmid) {
        this.agmid = agmid;
    }
    public int getVoteunits() {
        return voteunits;
    }

    public void setVoteunits(int voteunits) {
        this.voteunits = voteunits;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memberid != null ? memberid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VotMembers)) {
            return false;
        }
        VotMembers other = (VotMembers) object;
        if ((this.memberid == null && other.memberid != null) || (this.memberid != null && !this.memberid.equals(other.memberid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.VotMembers[ memberid=" + memberid + " ]";
    }
    
    public String getConcluded() {
        return concluded;
    }

    /**
     * @param concluded the concluded to set
     */
    public void setConcluded(String concluded) {
        this.concluded = concluded;
    }

    
}
