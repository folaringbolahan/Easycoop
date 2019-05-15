package com.sift.easycoopfin.models;

import java.io.Serializable;

public class Branch implements Serializable {

    public Branch() {
    }
    private int id;
    private int companyId;
    private String branchCode;
    private String branchName;

    private void setId(int value) {
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

    public void setBranchCode(String value) {
        this.branchCode = value;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchName(String value) {
        this.branchName = value;
    }

    public String getBranchName() {
        return branchName;
    }

    public String toString() {
        return String.valueOf(getId());
    }
}
