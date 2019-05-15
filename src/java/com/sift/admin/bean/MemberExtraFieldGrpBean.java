/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.bean;

import com.sift.admin.model.MemberExtraField;

/**
 *
 * @author Nelson Akpos
 */
public class MemberExtraFieldGrpBean {
    
    private Integer id;
    private String fieldOption;
    private int groupid; 
    private String description;
    private String companyName;

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
     * @return the groupid
     */
    public int getGroupid() {
        return groupid;
    }

    /**
     * @param groupid the groupid to set
     */
    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    /**
     * @return the fieldOption
     */
    public String getFieldOption() {
        return fieldOption;
    }

    /**
     * @param fieldOption the fieldOption to set
     */
    public void setFieldOption(String fieldOption) {
        this.fieldOption = fieldOption;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
}
