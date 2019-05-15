/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Gbolahan.Folarin
 */
@Entity
@Table(name = "countries")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Countries.findAll", query = "SELECT c FROM Countries c")
    , @NamedQuery(name = "Countries.findById", query = "SELECT c FROM Countries c WHERE c.id = :id")
    , @NamedQuery(name = "Countries.findByActive", query = "SELECT c FROM Countries c WHERE c.active = :active")
    , @NamedQuery(name = "Countries.findByCountryCode", query = "SELECT c FROM Countries c WHERE c.countryCode = :countryCode")
    , @NamedQuery(name = "Countries.findByCountryName", query = "SELECT c FROM Countries c WHERE c.countryName = :countryName")
    , @NamedQuery(name = "Countries.findByCurrencyCode", query = "SELECT c FROM Countries c WHERE c.currencyCode = :currencyCode")
    , @NamedQuery(name = "Countries.findByCreatedBy", query = "SELECT c FROM Countries c WHERE c.createdBy = :createdBy")
    , @NamedQuery(name = "Countries.findByCreationDate", query = "SELECT c FROM Countries c WHERE c.creationDate = :creationDate")
    , @NamedQuery(name = "Countries.findByDeleted", query = "SELECT c FROM Countries c WHERE c.deleted = :deleted")
    , @NamedQuery(name = "Countries.findByLastModificationDate", query = "SELECT c FROM Countries c WHERE c.lastModificationDate = :lastModificationDate")
    , @NamedQuery(name = "Countries.findByLastModifiedBy", query = "SELECT c FROM Countries c WHERE c.lastModifiedBy = :lastModifiedBy")
    , @NamedQuery(name = "Countries.findByTimez", query = "SELECT c FROM Countries c WHERE c.timez = :timez")})
public class Countries implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "ACTIVE")
    private String active;
    @Size(max = 255)
    @Column(name = "COUNTRY_CODE")
    private String countryCode;
    @Size(max = 255)
    @Column(name = "COUNTRY_NAME")
    private String countryName;
    @Size(max = 15)
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;
    @Size(max = 255)
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Size(max = 255)
    @Column(name = "DELETED")
    private String deleted;
    @Column(name = "LAST_MODIFICATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModificationDate;
    @Size(max = 255)
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;
    @Size(max = 40)
    @Column(name = "TIMEZ")
    private String timez;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "countryId")
    private Collection<Bank> bankCollection;

    public Countries() {
    }

    public Countries(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getTimez() {
        return timez;
    }

    public void setTimez(String timez) {
        this.timez = timez;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Bank> getBankCollection() {
        return bankCollection;
    }

    public void setBankCollection(Collection<Bank> bankCollection) {
        this.bankCollection = bankCollection;
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
        if (!(object instanceof Countries)) {
            return false;
        }
        Countries other = (Countries) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sift.admin.model.Countries[ id=" + id + " ]";
    }
    
}
