package com.sift.easycoopfin.models;

import java.io.Serializable;

public class ProductAccount implements Serializable {

    public ProductAccount() {
    }
    private int id;
    private int productId;
    private String productAccountTypeCode;
    private String glAccountNumber;
    private int branchId;
    private int companyId;

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

    public void setCompanyId(int value) {
        this.companyId = value;
    }

    public int getCompanyId() {
        return companyId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int value) {
        this.branchId = value;
    }

    public String toString() {
        return "Product Account: Id: "+String.valueOf(getId())+" Account Number: "+getGlAccountNumber();
    }
}
