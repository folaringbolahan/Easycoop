/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.bean;

import com.sift.votes.model.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


public class VotVotequestsBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String description;  //this is the value holding the questi
    private int votetypeid;
    private int electionanswertypeid;
    private Integer agmid;
    private Date createdate;
    private String deleted;
    private Integer sortorder;
     private String createdby;
     private String active;
     private String agmName;
     private String voteType;
     private Date modifieddate;
     private  String modifiedby;
     private int optionid;
     private int maxoption;
    
    private List<VotVoteoptions> voteoptions;
    

    public VotVotequestsBean() {
    }

    public VotVotequestsBean(Integer id) {
        this.id = id;
    }

    public VotVotequestsBean(Integer id, String description, int votetypeid, int electionanswertypeid) {
        this.id = id;
        this.description = description;
        this.votetypeid = votetypeid;
        this.electionanswertypeid = electionanswertypeid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVotetypeid() {
        return votetypeid;
    }

    public void setVotetypeid(int votetypeid) {
        this.votetypeid = votetypeid;
    }

    public int getElectionanswertypeid() {
        return electionanswertypeid;
    }

    public void setElectionanswertypeid(int electionanswertypeid) {
        this.electionanswertypeid = electionanswertypeid;
    }

    public Integer getAgmid() {
        return agmid;
    }

    public void setAgmid(Integer agmid) {
        this.agmid = agmid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Integer getSortorder() {
        return sortorder;
    }

    public void setSortorder(Integer sortorder) {
        this.sortorder = sortorder;
    }
    
    public List<VotVoteoptions> getVoteoptions() {
        return voteoptions;
    }

    public void setVoteoptions(List<VotVoteoptions> voteoptions) {
        this.voteoptions = voteoptions;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    
    @Override
    public String toString() {
        return "javaapplication2.VotVotequests[ id=" + id + " ]";
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
     * @return the agmName
     */
    public String getAgmName() {
        return agmName;
    }

    /**
     * @param agmName the agmName to set
     */
    public void setAgmName(String agmName) {
        this.agmName = agmName;
    }

    /**
     * @return the voteType
     */
    public String getVoteType() {
        return voteType;
    }

    /**
     * @param voteType the voteType to set
     */
    public void setVoteType(String voteType) {
        this.voteType = voteType;
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
     * @return the optionid
     */
    public int getOptionid() {
        return optionid;
    }

    /**
     * @param optionid the optionid to set
     */
    public void setOptionid(int optionid) {
        this.optionid = optionid;
    }
    /**
	     * @return the maxoption
	     */
    public int getMaxoption() {
	        return maxoption;
	    }
	    /**
	     * @param maxoption the maxoption to set
	     */
	    public void setMaxoption(int maxoption) {
	        this.maxoption = maxoption;
    }

}
