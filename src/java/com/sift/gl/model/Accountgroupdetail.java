/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.model;

/**
 *
 * @author yomi-pc
 */
public class Accountgroupdetail {
    private Integer acGrpId;
     private String classId;
     private Integer groupId;
     private String description;
     private String reportGroup;
     private Integer branch;
     private Integer companyid;

    public Accountgroupdetail() {
    }
    public Accountgroupdetail(String classId, String description, String reportGroup) {
       this.classId = classId;
       //this.groupId = groupId;
       this.description = description;
       this.reportGroup = reportGroup;
    }
    public Accountgroupdetail(String classId, Integer groupId, String description, String reportGroup) {
       this.classId = classId;
       this.groupId = groupId;
       this.description = description;
       this.reportGroup = reportGroup;
    }
     public Accountgroupdetail(String classId,Integer groupId, Integer acGrpId, String description, String reportGroup) {
       this.classId = classId;
       this.acGrpId = acGrpId;
       this.groupId = groupId;
       this.description = description;
       this.reportGroup = reportGroup;
    }
   
    public Integer getAcGrpId() {
        return this.acGrpId;
    }
    
    public void setAcGrpId(Integer acGrpId) {
        this.acGrpId = acGrpId;
    }
    public String getClassId() {
        return this.classId;
    }
    
    public void setClassId(String classId) {
        this.classId = classId;
    }
    public Integer getGroupId() {
        return this.groupId;
    }
    
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public String getReportGroup() {
        return this.reportGroup;
    }
    
    public void setReportGroup(String reportGroup) {
        this.reportGroup = reportGroup;
    }
    public Integer getBranch() {
        return this.branch;
    }
    public void setBranch(Integer branch) {
        this.branch = branch;
    }
    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }
    public Integer getCompanyid() {
        return this.companyid;
    }
}
