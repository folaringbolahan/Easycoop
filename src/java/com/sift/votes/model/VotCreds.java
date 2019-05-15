/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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


 
@Entity
@Table(name = "vot_creds")


/*
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VotCreds.findAll", query = "SELECT v FROM VotCreds v"),
    @NamedQuery(name = "VotCreds.findById", query = "SELECT v FROM VotCreds v WHERE v.id = :id"),
    @NamedQuery(name = "VotCreds.findByMemberid", query = "SELECT v FROM VotCreds v WHERE v.memberid = :memberid"),
    @NamedQuery(name = "VotCreds.findByAgmid", query = "SELECT v FROM VotCreds v WHERE v.agmid = :agmid"),
    @NamedQuery(name = "VotCreds.findByCompanyid", query = "SELECT v FROM VotCreds v WHERE v.companyid = :companyid"),
    @NamedQuery(name = "VotCreds.findByTok", query = "SELECT v FROM VotCreds v WHERE v.tok = :tok"),
    @NamedQuery(name = "VotCreds.findByTokencrpy", query = "SELECT v FROM VotCreds v WHERE v.tokencrpy = :tokencrpy"),
    @NamedQuery(name = "VotCreds.findByDeleted", query = "SELECT v FROM VotCreds v WHERE v.deleted = :deleted"),
    @NamedQuery(name = "VotCreds.findByCreatedate", query = "SELECT v FROM VotCreds v WHERE v.createdate = :createdate"),
    @NamedQuery(name = "VotCreds.findBySent", query = "SELECT v FROM VotCreds v WHERE v.sent = :sent"),
    @NamedQuery(name = "VotCreds.findByMembermail", query = "SELECT v FROM VotCreds v WHERE v.membermail = :membermail"),
    @NamedQuery(name = "VotCreds.findByLocked", query = "SELECT v FROM VotCreds v WHERE v.locked = :locked"),
    @NamedQuery(name = "VotCreds.findBySalt", query = "SELECT v FROM VotCreds v WHERE v.salt = :salt")})
*/
public class VotCreds implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue
    //@Basic(optional = false)
    //@Column(name = "id")
    private Integer id;
    //@Basic(optional = false)
    @Column(name = "memberid")
    private int memberid;
    @Column(name = "agmid")
    private Integer agmid;
    @Column(name = "companyid")
    private Integer companyid;
    @Column(name = "tok")
    private String tok;
    @Column(name = "tokencrpy")
    private String tokencrpy;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "createdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdate;
    @Column(name = "sent")
    private String sent;
    @Column(name = "membermail")
    private String membermail;
    @Column(name = "locked")
    private String locked;
    @Column(name = "salt")
    private String salt;
    @Column(name = "role")
    private String role;

    public VotCreds() {
    }

    public VotCreds(Integer id) {
        this.id = id;
    }

    public VotCreds(Integer id, int memberid) {
        this.id = id;
        this.memberid = memberid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMemberid() {
        return memberid;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public Integer getAgmid() {
        return agmid;
    }

    public void setAgmid(Integer agmid) {
        this.agmid = agmid;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public String getTok() {
        return tok;
    }

    public void setTok(String tok) {
        this.tok = tok;
    }

    public String getTokencrpy() {
        return tokencrpy;
    }

    public void setTokencrpy(String tokencrpy) {
        this.tokencrpy = tokencrpy;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getMembermail() {
        return membermail;
    }

    public void setMembermail(String membermail) {
        this.membermail = membermail;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
     public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
        if (!(object instanceof VotCreds)) {
            return false;
        }
        VotCreds other = (VotCreds) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.VotCreds[ id=" + id + " ]";
    }
    
}
