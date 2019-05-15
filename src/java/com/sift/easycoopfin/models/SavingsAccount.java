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
public class SavingsAccount implements Serializable {

    public SavingsAccount() {
    }
    private int id;
    private int companyId;
    private int branchId;
    private String accountNumber;
    private float balance;
    private String title;
    private String productTypeCode;
    private String name;
    private String productCode;
    
    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public int getORMID() {
        return getId();
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

    public void setProductTypeCode(String value) {
        this.productTypeCode = value;
    }

    public String getProductTypeCode() {
        return productTypeCode;
    }
public void setProductCode(String value) {
        this.productCode = value;
    }

    public String getProductCode() {
        return productCode;
    }
    public void setAccountNumber(String value) {
        this.accountNumber = value;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getTitle() {
        return title;
    }
    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return name;
    }
    public void setBalance(float value){
        this.balance = value;
    }
    public float getBalance() {
        return balance;
    }
}
