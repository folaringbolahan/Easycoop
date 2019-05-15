/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


 *
 * @author kola
 
@Entity
@Table(name = "vot_company")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VotCompany.findAll", query = "SELECT v FROM VotCompany v"),
    @NamedQuery(name = "VotCompany.findByCompanyid", query = "SELECT v FROM VotCompany v WHERE v.companyid = :companyid"),
    @NamedQuery(name = "VotCompany.findByCompanyrefid", query = "SELECT v FROM VotCompany v WHERE v.companyrefid = :companyrefid"),
    @NamedQuery(name = "VotCompany.findByCompanyname", query = "SELECT v FROM VotCompany v WHERE v.companyname = :companyname"),
    @NamedQuery(name = "VotCompany.findByDescription", query = "SELECT v FROM VotCompany v WHERE v.description = :description"),
    @NamedQuery(name = "VotCompany.findByActive", query = "SELECT v FROM VotCompany v WHERE v.active = :active")})
*/
@Entity
@Table(name = "vot_company")
public class VotCompany implements Serializable {
    private static final long serialVersionUID = 1L;
    //@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @Basic(optional = false)
    @Column(name = "companyid")
    @Id
    private Integer companyid;
    @Column(name = "companyrefid")
    private Integer companyrefid;
   // @Basic(optional = false)
    @Column(name = "companyname")
    private String companyname;
   // @Basic(optional = false)
    
   @Column(name = "active")
    private String active;

    public VotCompany() {
    }

    public VotCompany(Integer companyid) {
        this.companyid = companyid;
    }
    

    public VotCompany(Integer companyid, Integer companyrefid, String companyname, String description) {
        this.companyid = companyid;
        this.companyrefid = companyrefid;
        this.companyname = companyname;
        
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }


    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

   

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companyid != null ? companyid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VotCompany)) {
            return false;
        }
        VotCompany other = (VotCompany) object;
        if ((this.companyid == null && other.companyid != null) || (this.companyid != null && !this.companyid.equals(other.companyid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.VotCompany[ companyid=" + companyid + " ]";
    }

    /**
     * @return the companyrefid
     */
    public Integer getCompanyrefid() {
        return companyrefid;
    }

    /**
     * @param companyrefid the companyrefid to set
     */
    public void setCompanyrefid(Integer companyrefid) {
        this.companyrefid = companyrefid;
    }
    
}
