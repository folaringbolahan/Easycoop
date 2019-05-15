/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.hp.model;

import java.io.Serializable;

/**
 *
 * @author yomi
 */
public class Hpvalidationrules {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String description;
    private String resultcond;
    private String validationtype;
    private double flatrate;
    private String formula;
    private String productcode;
    private int branchid;
    private int companyid;

    public Hpvalidationrules() {
    }

    public Hpvalidationrules(Integer id) {
        this.id = id;
    }

    public Hpvalidationrules(Integer id, int branchid, int companyid) {
        this.id = id;
        this.branchid = branchid;
        this.companyid = companyid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValidationtype() {
        return validationtype;
    }

    public void setValidationtype(String validationtype) {
        this.validationtype = validationtype;
    }

    public double getFlatrate() {
        return flatrate;
    }

    public void setFlatrate(double flatrate) {
        this.flatrate = flatrate;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }
    public String getResultcond() {
        return resultcond;
    }

    public void setResultcond(String resultcond) {
        this.resultcond = resultcond;
    }
    public int getBranchid() {
        return branchid;
    }

    public void setBranchid(int branchid) {
        this.branchid = branchid;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    
}
