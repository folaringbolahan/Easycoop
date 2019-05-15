/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.utility;

import java.util.Date;

/**
 *
 * @author Olakunle Awotunbo
 */
public class CompStockTypeBean {

    private Integer compStockTypeId;
    private String compStockName;
    private String companyName;
    private Date createdDate;
    private String delFlg;
    private String createdBy;
    private boolean isUsedByMember;

    /**
     * @return the compStockTypeId
     */
    public Integer getCompStockTypeId() {
        return compStockTypeId;
    }

    /**
     * @param compStockTypeId the compStockTypeId to set
     */
    public void setCompStockTypeId(Integer compStockTypeId) {
        this.compStockTypeId = compStockTypeId;
    }

    /**
     * @return the compStockName
     */
    public String getCompStockName() {
        return compStockName;
    }

    /**
     * @param compStockName the compStockName to set
     */
    public void setCompStockName(String compStockName) {
        this.compStockName = compStockName;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the delFlg
     */
    public String getDelFlg() {
        return delFlg;
    }

    /**
     * @param delFlg the delFlg to set
     */
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the isUsedByMember
     */
    public boolean isIsUsedByMember() {
        return isUsedByMember;
    }

    /**
     * @param isUsedByMember the isUsedByMember to set
     */
    public void setIsUsedByMember(boolean isUsedByMember) {
        this.isUsedByMember = isUsedByMember;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}
