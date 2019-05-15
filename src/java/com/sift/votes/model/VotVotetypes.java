/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;

import java.io.Serializable;
//import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
import javax.persistence.Table;
//import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kola
 */
//@Entity
//@Table(name = "vot_votetypes")
//@XmlRootElement
//@NamedQueries({
   // @NamedQuery(name = "VotVotetypes.findAll", query = "SELECT v FROM VotVotetypes v"),
   // @NamedQuery(name = "VotVotetypes.findById", query = "SELECT v FROM VotVotetypes v WHERE v.id = :id"),
   // @NamedQuery(name = "VotVotetypes.findByDescription", query = "SELECT v FROM VotVotetypes v WHERE v.description = :description")})
@Entity
@Table(name = "vot_votetypes")
public class VotVotetypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    //@Basic(optional = false)
    @Column(name = "description")
    private String description;

    public VotVotetypes() {
    }

    public VotVotetypes(Integer id) {
        this.id = id;
    }

    public VotVotetypes(Integer id, String description) {
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
        if (!(object instanceof VotVotetypes)) {
            return false;
        }
        VotVotetypes other = (VotVotetypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.VotVotetypes[ id=" + id + " ]";
    }
    
}
