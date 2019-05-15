/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


// *
// * @author kola
 
@Entity
@Table(name = "vot_voteoptions")
/**
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VotVoteoptions.findAll", query = "SELECT v FROM VotVoteoptions v"),
    @NamedQuery(name = "VotVoteoptions.findById", query = "SELECT v FROM VotVoteoptions v WHERE v.id = :id"),
    @NamedQuery(name = "VotVoteoptions.findByDescription", query = "SELECT v FROM VotVoteoptions v WHERE v.description = :description"),
    @NamedQuery(name = "VotVoteoptions.findByVoteid", query = "SELECT v FROM VotVoteoptions v WHERE v.voteid = :voteid"),
    @NamedQuery(name = "VotVoteoptions.findByDeleted", query = "SELECT v FROM VotVoteoptions v WHERE v.deleted = :deleted")})
*/
public class VotVoteoptions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    
    private Integer id;
    //@Basic(optional = false)
   @Column(name = "description")
    private String description;
    @Column(name = "voteid")
    private Integer voteid;
    @Column(name = "deleted")
    private String deleted;

    public VotVoteoptions() {
    }

    public VotVoteoptions(Integer id) {
        this.id = id;
    }

    public VotVoteoptions(Integer id, String description) {
        this.id = id;
        this.description = description;
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

    public Integer getVoteid() {
        return voteid;
    }

    public void setVoteid(Integer voteid) {
        this.voteid = voteid;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
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
        if (!(object instanceof VotVoteoptions)) {
            return false;
        }
        VotVoteoptions other = (VotVoteoptions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.VotVoteoptions[ id=" + id + " ]";
    }
    
}
