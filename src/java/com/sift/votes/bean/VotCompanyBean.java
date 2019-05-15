/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.bean;

import com.sift.admin.model.Company;

/**
 *
 * @author Nelson Akpos
 */
public class VotCompanyBean {
      private Integer companyid;
   
    private Integer companyrefid;
   
    private String companyname;
  
   
 
    private String active;

    /**
     * @return the companyid
     */
    public Integer getCompanyid() {
        return companyid;
    }

    /**
     * @param companyid the companyid to set
     */
    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
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

    /**
     * @return the companyname
     */
    public String getCompanyname() {
        return companyname;
    }

    /**
     * @param companyname the companyname to set
     */
    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    /**
     * @return the description
     */
   
    /**
     * @return the active
     */
    public String getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

    
}
