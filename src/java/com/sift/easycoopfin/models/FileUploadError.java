/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Inove
 */
public class FileUploadError  implements Serializable  {
    public FileUploadError(){}
    private int id;
    private int companyId;
    private int branchId;
    private String fileName;
    private String location;
    private String description;
    private String referenceNumber;
    private int productId;
    private String userId;
    private Date processedDate;
   
    
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

    public void setBranchId(int value) {
        this.branchId = value;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setFileName(String value) {
        this.fileName = value;
    }

    public String getFileName() {
        return fileName;
    }

    public void setLocation(String value) {
        this.location = value;
    }

    public String getLocation() {
        return location;
    }

    public void setReferenceNumber(String value) {
        this.referenceNumber = value;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setUserId(String value) {
        this.userId = value;
    }

    public String getUserId() {
        return userId;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getDescription() {
        return description;
    }

    public void setProductId(int value) {
        this.productId = value;
    }

    public int getProductId() {
        return productId;
    }

      public void setProcessedDate(Date value) {
        this.processedDate = value;
    }

    public Date getProcessedDate() {
        return processedDate;
    }
    public String toString() {
        return String.valueOf(getId());
    }       
            
}
