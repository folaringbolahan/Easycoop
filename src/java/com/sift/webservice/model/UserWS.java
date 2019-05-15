/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.webservice.model;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.format.annotation.DateTimeFormat;
/**
 *
 * @author Nelson Akpos
 */
@Entity
@Table(name="USERS")
@XmlRootElement(name="USERS")
@XmlAccessorType(XmlAccessType.NONE)
public class UserWS {
     @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
 
    @Column(name="UserId")
    private String userId;
    
     @Column(name="companyId")
    private String companyId;
    
    @Column(name="branch")
    private String branchId;
    
     @Column(name="AccessLevel")
    private String groupId;
     
   
    
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}

	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

    /**
     * @return the companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the branchId
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * @param branchId the branchId to set
     */
    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

	
}
