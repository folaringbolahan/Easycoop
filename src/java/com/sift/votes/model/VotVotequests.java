/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;

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
import javax.persistence.Transient;


@Entity
@Table(name = "vot_votequests")
/**
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VotVotequests.findAll", query = "SELECT v FROM VotVotequests v"),
    @NamedQuery(name = "VotVotequests.findById", query = "SELECT v FROM VotVotequests v WHERE v.id = :id"),
    @NamedQuery(name = "VotVotequests.findByDescription", query = "SELECT v FROM VotVotequests v WHERE v.description = :description"),
    @NamedQuery(name = "VotVotequests.findByVotetypeid", query = "SELECT v FROM VotVotequests v WHERE v.votetypeid = :votetypeid"),
    @NamedQuery(name = "VotVotequests.findByElectionanswertypeid", query = "SELECT v FROM VotVotequests v WHERE v.electionanswertypeid = :electionanswertypeid"),
    @NamedQuery(name = "VotVotequests.findByCompanyid", query = "SELECT v FROM VotVotequests v WHERE v.companyid = :companyid"),
    @NamedQuery(name = "VotVotequests.findByCreatedate", query = "SELECT v FROM VotVotequests v WHERE v.createdate = :createdate"),
    @NamedQuery(name = "VotVotequests.findByDeleted", query = "SELECT v FROM VotVotequests v WHERE v.deleted = :deleted"),
    @NamedQuery(name = "VotVotequests.findBySortorder", query = "SELECT v FROM VotVotequests v WHERE v.sortorder = :sortorder")})
*/
public class VotVotequests implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    
    private Integer id;
   // @Basic(optional = false)
    @Column(name = "description")
    private String description;
   // @Basic(optional = false)
    @Column(name = "votetypeid")
    private int votetypeid;
   // @Basic(optional = false)
    @Column(name = "electionanswertypeid")
    private int electionanswertypeid;
    @Column(name = "agmid")
    private Integer agmid;
    @Column(name = "createdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdate;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "sortorder")
    private Integer sortorder;
    @Column(name = "createdby")
    private String createdby;
     
      @Column(name = "active")
      private String active;
      @Column(name = "modifiedby")
    private String modifiedby;
      @Column(name = "maxoption")
      private int maxoption;

    @Transient
    private List<VotVoteoptions> voteoptions;

    public VotVotequests() {
    }

    public VotVotequests(Integer id) {
        this.id = id;
    }

    public VotVotequests(Integer id, String description, int votetypeid, int electionanswertypeid) {
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
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VotVotequests)) {
            return false;
        }
        VotVotequests other = (VotVotequests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
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
    
    public String getModifiedby() {
        return modifiedby;
    }

    /**
     * @param createdby the createdby to set
     */
    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
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
