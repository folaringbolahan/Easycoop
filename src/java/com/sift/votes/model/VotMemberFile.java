/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Nelson Akpos
 */
@Entity
@Table(name="vot_members_file")
public class VotMemberFile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int memberid ;
  
  @Column(name = "memberrefid")
  private int memberrefid;
  
  @Column(name = "userid")
  private String userid;
  
  @Column(name = "firstname")
  private String firstname;
  
  @Column(name = "middlename")
  private String middlename;
  
  @Column(name = "surname")
  private String surname;
  
  @Column(name = "email")
  private String email;
  
  @Column(name = "phone")
  private String phone;
  
  @Column(name = "branchid")
  private int branchid;
  
  @Column(name = "companyid")
  private int companyid;
  
  @Column(name = "agmid")
  private int agmid;
  
  @Column(name = "voteunits")
  private int voteunits;
  
 @Column(name = "concluded")
  private String concluded;
  
  @Column(name = "file_ref")
  private String file_ref;

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

    /**
     * @return the memberrefid
     */
    public int getMemberrefid() {
        return memberrefid;
    }

    /**
     * @param memberrefid the memberrefid to set
     */
    public void setMemberrefid(int memberrefid) {
        this.memberrefid = memberrefid;
    }

    /**
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the branchid
     */
    public int getBranchid() {
        return branchid;
    }

    /**
     * @param branchid the branchid to set
     */
    public void setBranchid(int branchid) {
        this.branchid = branchid;
    }

    /**
     * @return the companyid
     */
    public int getCompanyid() {
        return companyid;
    }

    /**
     * @param companyid the companyid to set
     */
    public void setCompanyid(int companyid) {
        this.companyid = companyid;
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
     * @return the voteunits
     */
    public int getVoteunits() {
        return voteunits;
    }

    /**
     * @param voteunits the voteunits to set
     */
    public void setVoteunits(int voteunits) {
        this.voteunits = voteunits;
    }

    /**
     * @return the file_ref
     */
    public String getFile_ref() {
        return file_ref;
    }

    /**
     * @param file_ref the file_ref to set
     */
    public void setFile_ref(String file_ref) {
        this.file_ref = file_ref;
    }

    /**
     * @return the concluded
     */
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
