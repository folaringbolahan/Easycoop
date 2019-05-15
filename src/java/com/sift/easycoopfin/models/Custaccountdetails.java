/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models;

import java.io.Serializable;

/**
 *
 * @author logzy
 */
public class Custaccountdetails implements Serializable {

    private String id;
    private String Product;
    private String Title;
    private int branchId;
    private int companyId;
  

    public Custaccountdetails() {
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }
    
    public void setProduct(String value) {
        this.Product = value;
    }

    public String getProduct() {
        return Product;
    }

    public void setTitle(String value) {
        this.Title = value;
    }

    public String getTitle() {
        return Title;
    }

    public void setCompanyId(int value) {
        this.companyId = value;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setBranchId(int value) {
        this.branchId = value;
    }
 
    public int getBranchId() {
        return branchId;
    }

}
