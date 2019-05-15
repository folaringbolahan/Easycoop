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
@Table(name = "vot_deactivateagm")
public class VotAgmDeactivation {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
   
  @Column(name = "agmid")
  private int agmid;
  
   @Column(name = "description")
  private String description;
    
  @Column(name = "status")
  private int status;
  
  @Column(name = "createdate")
  @Temporal(javax.persistence.TemporalType.DATE)
  private Date createdate;
    
 @Column(name = "createdby")
  private String createdby;

  @Column(name = "approvedby")
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
}
