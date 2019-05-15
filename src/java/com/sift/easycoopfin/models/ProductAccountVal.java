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
public class ProductAccountVal implements Serializable {
       public ProductAccountVal() {
    }
    private int id;
    private String productType;
    private String productAccountTypeCode;
  

    private void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public int getORMID() {
        return getId();
    }


    public void setProductAccountTypeCode(String value) {
        this.productAccountTypeCode = value;
    }

    public String getProductAccountTypeCode() {
        return productAccountTypeCode;
    }

    public void setProductType(String value) {
        this.productType = value;
    }

    public String getProductType() {
        return productType;
    }


    public String toString() {
        return String.valueOf(getId());
    }
}
