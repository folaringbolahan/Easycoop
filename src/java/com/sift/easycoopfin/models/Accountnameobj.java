/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author logzy
 */
public class Accountnameobj implements Serializable {

    public Accountnameobj() {
    }
    private Integer id;
    private Integer companyId;
    private Integer branchId;
    private String accountNumber;
    private String accountName;
    
    public void setId(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    public void setCompanyId(Integer value) {
        this.companyId = value;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setBranchId(Integer value) {
        this.branchId = value;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setAccountName(String value) {
        this.accountName = value;
    }

    public String getAccountName() {
        return accountName;
    }
    public void setAccountNumber(String value) {
        this.accountNumber = value;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
  
    @Override
    public boolean equals(Object arg0) {
        if (arg0 == null) {
            return false;
        }
        if (!(arg0 instanceof Accountnameobj)) {
            return false;
        }
        Accountnameobj arg1 = (Accountnameobj) arg0;
        return (this.companyId == arg1.getCompanyId()
                && this.branchId == arg1.getBranchId()
                && //this.id == arg1.getId() &&
                this.accountNumber.equals(arg1.getAccountNumber()) // && this.accountName.equals(arg1.getAccountName())
                );
    }

    @Override
    public int hashCode() {
        int hsCode;
        //hsCode = id.hashCode() + companyId.hashCode()+ branchId.hashCode();
        hsCode = companyId.hashCode() + branchId.hashCode();
        hsCode = 19 * hsCode + accountNumber.hashCode(); //+accountName.hashCode();
        return hsCode;
    }
}
