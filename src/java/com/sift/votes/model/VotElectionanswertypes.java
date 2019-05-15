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

/**
 *
 * @author kola
 
@Entity
@Table(name = "vot_electionanswertypes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VotElectionanswertypes.findAll", query = "SELECT v FROM VotElectionanswertypes v"),
    @NamedQuery(name = "VotElectionanswertypes.findById", query = "SELECT v FROM VotElectionanswertypes v WHERE v.id = :id"),
    @NamedQuery(name = "VotElectionanswertypes.findByDescription", query = "SELECT v FROM VotElectionanswertypes v WHERE v.description = :description")})
*/
@Entity
@Table(name = "vot_electionanswertypes")
public class VotElectionanswertypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  //  @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
  //  @Basic(optional = false)
    @Column(name = "description")
    private String description;

    public VotElectionanswertypes() {
    }

    public VotElectionanswertypes(Integer id) {
        this.id = id;
    }

    public VotElectionanswertypes(Integer id, String description) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VotElectionanswertypes)) {
            return false;
        }
        VotElectionanswertypes other = (VotElectionanswertypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.VotElectionanswertypes[ id=" + id + " ]";
    }
    
}
