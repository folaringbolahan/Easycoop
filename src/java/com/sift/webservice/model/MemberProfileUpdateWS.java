/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.webservice.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Nelson Akpos
 */
 @Entity
@Table(name="memberupdatepending")
@XmlRootElement(name="memberupdatepending")
@XmlAccessorType(XmlAccessType.NONE) 
public class MemberProfileUpdateWS {
  @Id
    @Column(name="id")
    @GeneratedValue
    private Integer id;
 
    @Column(name="memberno")
    private String memberno;
    
    @Column(name="branchid")
    private String branchid;
    
    @Column(name="companyid")
    private String companyid;
    
    @Column(name="changetype")
    private String changetype;
 
    @Column(name="fieldvalue")
    private String fieldvalue;
 
    @Column(name="created_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date created_date;
     
    @Column(name="approved")
    private String approved;
     
    @Column(name="approvedby")
    private String approvedby;
    
   @Column(name="createdby")
    private String createdby;

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
     * @return the memberno
     */
    public String getMemberno() {
        return memberno;
    }

    /**
     * @param memberno the memberno to set
     */
    public void setMemberno(String memberno) {
        this.memberno = memberno;
    }

    /**
     * @return the branchid
     */
    public String getBranchid() {
        return branchid;
    }

    /**
     * @param branchid the branchid to set
     */
    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    /**
     * @return the companyid
     */
    public String getCompanyid() {
        return companyid;
    }

    /**
     * @param companyid the companyid to set
     */
    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    /**
     * @return the changetype
     */
    public String getChangetype() {
        return changetype;
    }

    /**
     * @param changetype the changetype to set
     */
    public void setChangetype(String changetype) {
        this.changetype = changetype;
    }

    /**
     * @return the fieldvalue
     */
    public String getFieldvalue() {
        return fieldvalue;
    }

    /**
     * @param fieldvalue the fieldvalue to set
     */
    public void setFieldvalue(String fieldvalue) {
        this.fieldvalue = fieldvalue;
    }

    /**
     * @return the created_date
     */
    public Date getCreated_date() {
        return created_date;
    }

    /**
     * @param created_date the created_date to set
     */
    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    /**
     * @return the approved
     */
    public String getApproved() {
        return approved;
    }

    /**
     * @param approved the approved to set
     */
    public void setApproved(String approved) {
        this.approved = approved;
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
}
