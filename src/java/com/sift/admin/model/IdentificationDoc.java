/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.admin.model;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Olakunle Awotunbo
 */
@Entity
@Table(name = "identification_doc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IdentificationDoc.findAll", query = "SELECT i FROM IdentificationDoc i"),
    @NamedQuery(name = "IdentificationDoc.findByIdentificationDocId", query = "SELECT i FROM IdentificationDoc i WHERE i.identificationDocId = :identificationDocId"),
    @NamedQuery(name = "IdentificationDoc.findByIdentificationdocname", query = "SELECT i FROM IdentificationDoc i WHERE i.identificationdocname = :identificationdocname"),
    @NamedQuery(name = "IdentificationDoc.findByIdentificationdocdesc", query = "SELECT i FROM IdentificationDoc i WHERE i.identificationdocdesc = :identificationdocdesc"),
    @NamedQuery(name = "IdentificationDoc.findByDelFlg", query = "SELECT i FROM IdentificationDoc i WHERE i.delFlg = :delFlg")})
public class IdentificationDoc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "identification_doc_id")
    private Integer identificationDocId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "Identification_doc_name")
    private String identificationdocname;
    @Size(max = 250)
    @Column(name = "Identification_doc_desc")
    private String identificationdocdesc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "del_flg")
    private String delFlg;
    @Column(name="country_id")
    private int countryId;
    @Column(name="country_name")
    private String countryName;

    public IdentificationDoc() {
    }

    public IdentificationDoc(Integer identificationDocId) {
        this.identificationDocId = identificationDocId;
    }

    public IdentificationDoc(Integer identificationDocId, String identificationdocname, String delFlg) {
        this.identificationDocId = identificationDocId;
        this.identificationdocname = identificationdocname;
        this.delFlg = delFlg;
    }

    public Integer getIdentificationDocId() {
        return identificationDocId;
    }

    public void setIdentificationDocId(Integer identificationDocId) {
        this.identificationDocId = identificationDocId;
    }

    public String getIdentificationdocname() {
        return identificationdocname;
    }

    public void setIdentificationdocname(String identificationdocname) {
        this.identificationdocname = identificationdocname;
    }

    public String getIdentificationdocdesc() {
        return identificationdocdesc;
    }

    public void setIdentificationdocdesc(String identificationdocdesc) {
        this.identificationdocdesc = identificationdocdesc;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identificationDocId != null ? identificationDocId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IdentificationDoc)) {
            return false;
        }
        IdentificationDoc other = (IdentificationDoc) object;
        if ((this.identificationDocId == null && other.identificationDocId != null) || (this.identificationDocId != null && !this.identificationDocId.equals(other.identificationDocId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sift.admin.model.IdentificationDoc[ identificationDocId=" + identificationDocId + " ]";
    }

    /**
     * @return the countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
}
