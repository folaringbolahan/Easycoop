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
public class ProductAccountRel implements Serializable {

    public ProductAccountRel() {
    }
    
    private int id;
    private int productId;
    private String productAccountTypeCode;
    private String glAccountNumber;
    private String description;

    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public int getORMID() {
        return getId();
    }

    public void setProductId(int value) {
        this.productId = value;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductAccountTypeCode(String value) {
        this.productAccountTypeCode = value;
    }

    public String getProductAccountTypeCode() {
        return productAccountTypeCode;
    }

    public void setGlAccountNumber(String value) {
        this.glAccountNumber = value;
    }

    public String getGlAccountNumber() {
        return glAccountNumber;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getDescription() {
        return description;
    }
}
