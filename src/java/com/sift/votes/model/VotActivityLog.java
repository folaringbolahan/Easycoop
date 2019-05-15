/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "vot_activity_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VotActivityLog.findAll", query = "SELECT v FROM VotActivityLog v"),
    @NamedQuery(name = "VotActivityLog.findByLogId", query = "SELECT v FROM VotActivityLog v WHERE v.logId = :logId"),
    @NamedQuery(name = "VotActivityLog.findByEventId", query = "SELECT v FROM VotActivityLog v WHERE v.eventId = :eventId"),
    @NamedQuery(name = "VotActivityLog.findByIpaddress", query = "SELECT v FROM VotActivityLog v WHERE v.ipaddress = :ipaddress"),
    @NamedQuery(name = "VotActivityLog.findByUsername", query = "SELECT v FROM VotActivityLog v WHERE v.username = :username"),
    @NamedQuery(name = "VotActivityLog.findByDescription", query = "SELECT v FROM VotActivityLog v WHERE v.description = :description"),
    @NamedQuery(name = "VotActivityLog.findByActionDate", query = "SELECT v FROM VotActivityLog v WHERE v.actionDate = :actionDate"),
    @NamedQuery(name = "VotActivityLog.findByAction", query = "SELECT v FROM VotActivityLog v WHERE v.action = :action"),
    @NamedQuery(name = "VotActivityLog.findByActionItem", query = "SELECT v FROM VotActivityLog v WHERE v.actionItem = :actionItem"),
    @NamedQuery(name = "VotActivityLog.findByActionResult", query = "SELECT v FROM VotActivityLog v WHERE v.actionResult = :actionResult"),
    @NamedQuery(name = "VotActivityLog.findByBranchId", query = "SELECT v FROM VotActivityLog v WHERE v.branchId = :branchId"),
    @NamedQuery(name = "VotActivityLog.findByCompanyId", query = "SELECT v FROM VotActivityLog v WHERE v.companyId = :companyId")})
*/
public class VotActivityLog implements Serializable {
    private static final long serialVersionUID = 1L;
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    //@Column(name = "log_id")
    private Integer logId;
    //@Basic(optional = false)
    //@Column(name = "event_id")
    private int eventId;
    //@Basic(optional = false)
    //@Column(name = "ipaddress")
    private String ipaddress;
    //@Basic(optional = false)
    //@Column(name = "username")
    private String username;
    //@Basic(optional = false)
    //@Column(name = "description")
    private String description;
    //@Basic(optional = false)
    //@Column(name = "action_date")
    //@Temporal(TemporalType.TIMESTAMP)
    private Date actionDate;
    //@Basic(optional = false)
    //@Column(name = "action")
    private String action;
    //@Basic(optional = false)
    //@Column(name = "action_item")
    private String actionItem;
   // @Basic(optional = false)
    //@Column(name = "action_result")
    private String actionResult;
    //@Basic(optional = false)
    //@Column(name = "branch_id")
    private int branchId;
    //@Basic(optional = false)
    //@Column(name = "company_id")
    private int companyId;

    public VotActivityLog() {
    }

    public VotActivityLog(Integer logId) {
        this.logId = logId;
    }

    public VotActivityLog(Integer logId, int eventId, String ipaddress, String username, String description, Date actionDate, String action, String actionItem, String actionResult, int branchId, int companyId) {
        this.logId = logId;
        this.eventId = eventId;
        this.ipaddress = ipaddress;
        this.username = username;
        this.description = description;
        this.actionDate = actionDate;
        this.action = action;
        this.actionItem = actionItem;
        this.actionResult = actionResult;
        this.branchId = branchId;
        this.companyId = companyId;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionItem() {
        return actionItem;
    }

    public void setActionItem(String actionItem) {
        this.actionItem = actionItem;
    }

    public String getActionResult() {
        return actionResult;
    }

    public void setActionResult(String actionResult) {
        this.actionResult = actionResult;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logId != null ? logId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VotActivityLog)) {
            return false;
        }
        VotActivityLog other = (VotActivityLog) object;
        if ((this.logId == null && other.logId != null) || (this.logId != null && !this.logId.equals(other.logId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.VotActivityLog[ logId=" + logId + " ]";
    }
    
}
