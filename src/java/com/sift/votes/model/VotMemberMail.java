/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Nelson Akpos
 */
@Entity
@Table(name = "vot_emailchange")
public class VotMemberMail {
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
  @Column(name = "agmid")  
  private int agmid;
  
  @Column(name = "memberid")  
  private int memberid;
  
  @Column(name = "firstname")
  private String firstname;
  
  @Column(name = "middlename")
  private String middlename;
   
  @Column(name = "surname")
  private String surname;
    
  @Column(name = "oldemail")
  private String  oldemail;
  @Column(name = "newemail")
  private String newemail;
  @Column(name = "createdate")
  @Temporal(javax.persistence.TemporalType.DATE)
  private Date createdate;
  @Column(name = "createdby")
  private String createdby;
  @Column(name = "status")  
  private int status;
  @Column(name = "approvedby")
  private String approvedby;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the agmid
     */
    public int getAgmid() {
        return agmid;
    }

    /**
     * @param agmid the agmid to set
     */
    public void setAgmid(int agmid) {
        this.agmid = agmid;
    }

   

    /**
     * @return the oldemail
     */
    public String getOldemail() {
        return oldemail;
    }

    /**
     * @param oldemail the oldemail to set
     */
    public void setOldemail(String oldemail) {
        this.oldemail = oldemail;
    }

    /**
     * @return the newemail
     */
    public String getNewemail() {
        return newemail;
    }

    /**
     * @param newemail the newemail to set
     */
    public void setNewemail(String newemail) {
        this.newemail = newemail;
    }

    /**
     * @return the createdate
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * @param createdate the createdate to set
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * @return the createdby
     */
    public String getCreatedby() {
        return createdby;
    }

    /**
     * @param createdby the createdby to set
     */
    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    /**
     * @return the approvedby
     */
    public String getApprovedby() {
        return approvedby;
    }

    /**
     * @param approvedby the approvedby to set
     */
    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the middlename
     */
    public String getMiddlename() {
        return middlename;
    }

    /**
     * @param middlename the middlename to set
     */
    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the memberid
     */
    public int getMemberid() {
        return memberid;
    }

    /**
     * @param memberid the memberid to set
     */
    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }
    
}
