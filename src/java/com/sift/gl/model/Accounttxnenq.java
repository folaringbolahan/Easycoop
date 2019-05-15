/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.model;

import java.util.Date;

/**
 *
 * @author yomi-pc
 */
public class Accounttxnenq implements java.io.Serializable{
    
private Integer acId;
     private String accountno;
     private String name;
     private String startdate;
     private String enddate;
     private Integer branchid;
     private Integer companyid;
     private double netmvmt;
     private double bal;
     private double totdr;
     private double totcr;

    public Accounttxnenq() {
    }

	
    public Accounttxnenq(String accountno, String name, String startdate,String enddate, Integer branchid,Integer companyid) {
        this.accountno = accountno;
        this.name = name;
        this.startdate = startdate;
        this.enddate = enddate;
        this.branchid = branchid;
        this.companyid = companyid;
    }
    
   
    public Integer getAcId() {
        return this.acId;
    }
    
    public void setAcId(Integer acId) {
        this.acId = acId;
    }
    public String getAccountno() {
        return this.accountno;
    }
    
    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getStartdate() {
        return this.startdate;
    }
    
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
    public String getEnddate() {
        return this.enddate;
    }
    
    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
    public Integer getBranchid() {
        return this.branchid;
    }
    
    public void setBranchid(Integer branchid) {
        this.branchid = branchid;
    }
    public Integer getCompanyid() {
        return this.companyid;
    }
    
    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }
    public double getTotdr() {
        return this.totdr;
    }
    
    public void setTotdr(double totdr) {
        this.totdr = totdr;
    }
    public double getTotcr() {
        return this.totcr;
    }
    
    public void setTotcr(double totcr) {
        this.totcr = totcr;
    }
     public double getNetmvmt() {
        return this.netmvmt;
    }
    
    public void setNetmvmt(double netmvmt) {
        this.netmvmt = netmvmt;
    }
    public double getBal() {
        return this.bal;
    }
    
    public void setBal(double bal) {
        this.bal = bal;
    }
}
