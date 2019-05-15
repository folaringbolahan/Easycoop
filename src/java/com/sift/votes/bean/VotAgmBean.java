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
public class VotAgmBean {
    private Integer id;
   
    private int companyid;
    
    private Integer ballotid;
   
    private Date startdate;
    
    private Date enddate;
  
    private String starttime;
    
    
    private String endtime;
   
    private String baseurl;
   
    private String closed;
  
    private Integer agmyear;
   
    private Date createdate;
    private String createdby;
    private String description;
    private String importsource; 
   private int companyrefid;
   private String ballotType;
   private String active;
   private String companyName;
   private int memberCount;
   private String modifiedby;
   private Date modifieddate;
   private Date lastreminderdate;
   private int reminderfrequency;
   
   
   
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
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
     * @return the ballotid
     */
    public Integer getBallotid() {
        return ballotid;
    }

    /**
     * @param ballotid the ballotid to set
     */
    public void setBallotid(Integer ballotid) {
        this.ballotid = ballotid;
    }

    /**
     * @return the startdate
     */
    public Date getStartdate() {
        return startdate;
    }

    /**
     * @param startdate the startdate to set
     */
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    /**
     * @return the enddate
     */
    public Date getEnddate() {
        return enddate;
    }

    /**
     * @param enddate the enddate to set
     */
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    /**
     * @return the starttime
     */
    public String getStarttime() {
        return starttime;
    }

    /**
     * @param starttime the starttime to set
     */
    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    /**
     * @return the endtime
     */
    public String getEndtime() {
        return endtime;
    }

    /**
     * @param endtime the endtime to set
     */
    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    /**
     * @return the baseurl
     */
    public String getBaseurl() {
        return baseurl;
    }

    /**
     * @param baseurl the baseurl to set
     */
    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    /**
     * @return the closed
     */
    public String getClosed() {
        return closed;
    }

    /**
     * @param closed the closed to set
     */
    public void setClosed(String closed) {
        this.closed = closed;
    }

    /**
     * @return the agmyear
     */
    public Integer getAgmyear() {
        return agmyear;
    }

    /**
     * @param agmyear the agmyear to set
     */
    public void setAgmyear(Integer agmyear) {
        this.agmyear = agmyear;
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
     * @return the importsource
     */
    public String getImportsource() {
        return importsource;
    }

    /**
     * @param importsource the importsource to set
     */
    public void setImportsource(String importsource) {
        this.importsource = importsource;
    }

    /**
     * @return the companyrefid
     */
    public int getCompanyrefid() {
        return companyrefid;
    }

    /**
     * @param companyrefid the companyrefid to set
     */
    public void setCompanyrefid(int companyrefid) {
        this.companyrefid = companyrefid;
    }

    /**
     * @return the ballotType
     */
    public String getBallotType() {
        return ballotType;
    }

    /**
     * @param ballotType the ballotType to set
     */
    public void setBallotType(String ballotType) {
        this.ballotType = ballotType;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the active
     */
    public String getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
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
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the memberCount
     */
    public int getMemberCount() {
        return memberCount;
    }

    /**
     * @param memberCount the memberCount to set
     */
    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    /**
     * @return the modifiedby
     */
    public String getModifiedby() {
        return modifiedby;
    }

    /**
     * @param modifiedby the modifiedby to set
     */
    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    /**
     * @return the modifieddate
     */
    public Date getModifieddate() {
        return modifieddate;
    }

    /**
     * @param modifieddate the modifieddate to set
     */
    public void setModifieddate(Date modifieddate) {
        this.modifieddate = modifieddate;
    }

    /**
     * @return the lastreminderdate
     */
    public Date getLastreminderdate() {
        return lastreminderdate;
    }

    /**
     * @param lastreminderdate the lastreminderdate to set
     */
    public void setLastreminderdate(Date lastreminderdate) {
        this.lastreminderdate = lastreminderdate;
    }

    /**
     * @return the reminderfrequency
     */
    public int getReminderfrequency() {
        return reminderfrequency;
    }

    /**
     * @param reminderfrequency the reminderfrequency to set
     */
    public void setReminderfrequency(int reminderfrequency) {
        this.reminderfrequency = reminderfrequency;
    }

   
   
}
