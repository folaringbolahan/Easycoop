package com.sift.gl.model;
// Generated Nov 11, 2010 5:32:25 PM by Hibernate Tools 3.2.1.GA



/**
 * Accountsegments generated by hbm2java
 */
public class Accountsegmentdetails  implements java.io.Serializable {


     private Integer segmentid;
     private String name;
     private String type;
     private Integer length;
     private boolean mandatory;
     private boolean predefined;
     private Integer branch;
     private Integer companyid;
     private boolean inuse;

    public Accountsegmentdetails() {
    }

	
    public Accountsegmentdetails(String name, String type, Integer length, boolean mandatory, boolean predefined) {
       //this.segmentId = segmentId;
       this.name = name;
       this.type = type;
       this.length = length;
       this.mandatory = mandatory;
       this.predefined = predefined;
    }
   
    public Integer getSegmentid() {
        return this.segmentid;
    }
    
    public void setSegmentid(Integer segmentid) {
        this.segmentid = segmentid;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    public Integer getLength() {
        return this.length;
    }
    
    public void setLength(Integer length) {
        this.length = length;
    }
    public boolean getMandatory() {
        return this.mandatory;
    }
    
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
    public boolean getPredefined() {
        return this.predefined;
    }
    public void setPreDefined(boolean predefined) {
        this.predefined = predefined;
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
    public void setInuse(boolean inuse) {
        this.inuse = inuse;
    }
    public boolean getInuse() {
        return this.inuse;
    }
}


