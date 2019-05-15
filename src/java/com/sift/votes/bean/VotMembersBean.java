/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.bean;

/**
 *
 * @author Nelson Akpos
 */
public class VotMembersBean {
    private Integer memberid;
    
    private int memberrefid;
 
    private String userid;
    
    private String firstname;
   
    private String middlename;
  
    private String surname;
 
    private String email;
  
    private String phone;
 
    private int branchid;
  
    private int companyid;
   
    private int agmid;
   
    private String concluded;
    
    private int votes;
    private  int selectedMemberId;
    private int createdby;
    private String newemail;
    private String oldemail;
    /**
     * @return the memberid
     */
    public Integer getMemberid() {
        return memberid;
    }

    /**
     * @param memberid the memberid to set
     */
    public void setMemberid(Integer memberid) {
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

    /**
     * @return the votes
     */
    public int getVotes() {
        return votes;
    }

    /**
     * @param votes the votes to set
     */
    public void setVotes(int votes) {
        this.votes = votes;
    }

    /**
     * @return the selectedMemberId
     */
    public int getSelectedMemberId() {
        return selectedMemberId;
    }

    /**
     * @param selectedMemberId the selectedMemberId to set
     */
    public void setSelectedMemberId(int selectedMemberId) {
        this.selectedMemberId = selectedMemberId;
    }
    /**
     * @return the createdby
     */
    public int getCreatedby() {
        return createdby;
    }

    /**
     * @param createdby the createdby to set
     */
    public void setCreatedby(int createdby) {
        this.createdby = createdby;
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

}
