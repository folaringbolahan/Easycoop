/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.webservice.model;

/**
 *
 * @author Nelson Akpos
 */
public class UserGroupBeanWS {
    private Integer id;
  
    private String userId;
      
    private String companyId;
    
    private String branchId;
    
    private String usergroup; 

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
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
     * @return the usergroup
     */
    public String getUsergroup() {
        return usergroup;
    }

    /**
     * @param usergroup the usergroup to set
     */
    public void setUsergroup(String usergroup) {
        this.usergroup = usergroup;
    }
}
