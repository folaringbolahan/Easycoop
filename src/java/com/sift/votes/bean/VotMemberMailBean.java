/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.bean;

import java.util.Date;

/**
 *
 * @author Nelson Akpos
 */
public class VotMemberMailBean {
  private int id ;
  private int agmid;
  private int memberid;
  private String firstname;
  private String middlename;
  private String surname;
  private String oldemail;
  private String newemail;
  private Date createdate;
  private String createdby;
  private int status;
  private String approvedby;  

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
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

    /*
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
