/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
/**import javax.persistence.Basic;
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


 *
 * @author kola
 
@Entity
@Table(name = "vot_agm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VotAgm.findAll", query = "SELECT v FROM VotAgm v"),
    @NamedQuery(name = "VotAgm.findById", query = "SELECT v FROM VotAgm v WHERE v.id = :id"),
    @NamedQuery(name = "VotAgm.findByCompanyid", query = "SELECT v FROM VotAgm v WHERE v.companyid = :companyid"),
    @NamedQuery(name = "VotAgm.findByBallotid", query = "SELECT v FROM VotAgm v WHERE v.ballotid = :ballotid"),
    @NamedQuery(name = "VotAgm.findByStartdate", query = "SELECT v FROM VotAgm v WHERE v.startdate = :startdate"),
    @NamedQuery(name = "VotAgm.findByEnddate", query = "SELECT v FROM VotAgm v WHERE v.enddate = :enddate"),
    @NamedQuery(name = "VotAgm.findByStarttime", query = "SELECT v FROM VotAgm v WHERE v.starttime = :starttime"),
    @NamedQuery(name = "VotAgm.findByEndtime", query = "SELECT v FROM VotAgm v WHERE v.endtime = :endtime"),
    @NamedQuery(name = "VotAgm.findByBaseurl", query = "SELECT v FROM VotAgm v WHERE v.baseurl = :baseurl"),
    @NamedQuery(name = "VotAgm.findByClosed", query = "SELECT v FROM VotAgm v WHERE v.closed = :closed"),
    @NamedQuery(name = "VotAgm.findByAgmyear", query = "SELECT v FROM VotAgm v WHERE v.agmyear = :agmyear"),
    @NamedQuery(name = "VotAgm.findByCreatedate", query = "SELECT v FROM VotAgm v WHERE v.createdate = :createdate")})
*/
@Entity
@Table(name = "vot_agm")
public class VotAgm implements Serializable {
    private static final long serialVersionUID = 1L;
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    //@Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //@Basic(optional = false)
    @Column(name = "companyid")
    private int companyid;
    @Column(name = "ballotid")
    private Integer ballotid;
    
    @Column(name = "startdate")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startdate;
    
    @Column(name = "enddate") 
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date enddate;
    
   @Column(name = "starttime")
    private String starttime;
   @Column(name = "endtime")
    
    private String endtime;
   
    @Column(name = "baseurl")
    private String baseurl;
    
   @Column(name = "closed")
    private String closed;
    @Column(name = "agmyear")
    private Integer agmyear;
 
    @Column(name = "createdate")
    @Temporal(javax.persistence.TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdate;
    
    @Column(name = "createdby")
     private String createdby;
    
    @Column(name = "importsource")
    private String importsource;
    @Column(name = "description")
    private String description;
    @Column(name = "active")
    private String active;
    
    @Column(name = "modifiedby")
    private String modifiedby;
    
     @Column(name = "modifieddate")
     private Date modifieddate;
     
     @Column(name = "lastreminderdate")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
     private Date lastreminderdate;
      
       @Column(name = "reminderfrequency")
        private int reminderfrequency;
     
     

    public VotAgm() {
    }

    public VotAgm(Integer id) {
        this.id = id;
    }

    public VotAgm(Integer id, int companyid) {
        this.id = id;
        this.companyid = companyid;
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

    public Integer getBallotid() {
        return ballotid;
    }

    public void setBallotid(Integer ballotid) {
        this.ballotid = ballotid;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    public Integer getAgmyear() {
        return agmyear;
    }

    public void setAgmyear(Integer agmyear) {
        this.agmyear = agmyear;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
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
        if (!(object instanceof VotAgm)) {
            return false;
        }
        VotAgm other = (VotAgm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.VotAgm[ id=" + id + " ]";
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
