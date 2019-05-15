package com.sift.hp.model;

import java.io.Serializable;
public class Hpproduct implements Serializable {

    public Hpproduct() {
    }
    private int id;
    private String code;
    private byte isDeleted = 0;
    private String name;
    private String segmentCode;
    private int companyId;
    private float initialAmountMax = 0f;
    private float initialAmountMin = 0f;
    private float interestRateMin = 0f;
    private float interestRateMax = 0f;
    private float penalty = 0f;
    private double interestRate;
    private int branchId;
    private String productTypeCode;
    private String taxCode1;
    private String taxCode2;
    private String taxCode3;
    private byte hasInterest = 0;
    private byte isDefault = 0;
    private byte isTaxable = 0;
    private byte hasPenalty = 0;
    private int currencyId;
    private int productId;
    private int loanDuration = 0;
    private int defaultPenaltyDays = 0;
    private String controlAccount;
    private String loanTypeCode;
    private String penaltyFormula;
    private byte isActive = 0;

    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public int getORMID() {
        return getId();
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String getCode() {
        return code;
    }

    public void setSegmentCode(String value) {
        this.segmentCode = value;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setIsDeleted(byte value) {
        this.isDeleted = value;
    }

    public byte getIsDeleted() {
        return isDeleted;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return name;
    }

    public void setCompanyId(int value) {
        this.companyId = value;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setInitialAmountMax(float value) {
        this.initialAmountMax = value;
    }

    public float getInitialAmountMax() {
        return initialAmountMax;
    }

    public void setInitialAmountMin(float value) {
        this.initialAmountMin = value;
    }

    public float getInitialAmountMin() {
        return initialAmountMin;
    }

    public void setInterestRateMin(float value) {
        this.interestRateMin = value;
    }

    public float getInterestRateMin() {
        return interestRateMin;
    }

    public void setInterestRateMax(float value) {
        this.interestRateMax = value;
    }

    public float getInterestRateMax() {
        return interestRateMax;
    }

    public void setInterestRate(double value) {
        this.interestRate = value;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setPenalty(float value) {
        this.penalty = value;
    }

    public float getPenalty() {
        return penalty;
    }

    public void setProductTypeCode(String value) {
        this.productTypeCode = value;
    }

    public String getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductId(int value) {
        this.productId = value;
    }

    public int getProductId() {
        return productId;
    }

    public void setHasInterest(byte value) {
        this.hasInterest = value;
    }

    public byte getHasInterest() {
        return hasInterest;
    }

    public void setHasPenalty(byte value) {
        this.hasPenalty = value;
    }

    public byte getHasPenalty() {
        return hasPenalty;
    }

    public void setDefaultPenaltyDays(int value) {
        this.defaultPenaltyDays = value;
    }

    public int getDefaultPenaltyDays() {
        return defaultPenaltyDays;
    }
    public void setPenaltyFormula(String value){
        this.penaltyFormula = value;
    }
    public String getPenaltyFormula(){
        return penaltyFormula;
    }
    public void setIsTaxable(byte value) {
        this.isTaxable = value;
    }

    public byte getIsTaxable() {
        return isTaxable;
    }

    public void setTaxCode1(String value) {
        this.taxCode1 = value;
    }

    public String getTaxCode1() {
        return taxCode1;
    }

    public void setTaxCode2(String value) {
        this.taxCode2 = value;
    }

    public String getTaxCode2() {
        return taxCode2;
    }

    public void setTaxCode3(String value) {
        this.taxCode3 = value;
    }

    public String getTaxCode3() {
        return taxCode3;
    }

    public void setIsDefault(byte value) {
        this.isDefault = value;
    }

    public byte getIsDefault() {
        return isDefault;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setCurrencyId(int value) {
        this.currencyId = value;
    }

    public int getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(int value) {
        this.loanDuration = value;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setBranchId(int value) {
        this.branchId = value;
    }

    public String getControlAccount() {
        return controlAccount;
    }

    public void setControlAccount(String value) {
        this.controlAccount = value;
    }

    public String getLoanTypeCode() {
        return loanTypeCode;
    }

    public void setLoanTypeCode(String value) {
        this.loanTypeCode = value;
    }

    public byte getIsActive() {
        return isActive;
    }

    public void setIsActive(byte value) {
        this.isActive = value;
    }

    public String toString() {
        return String.valueOf(getId());
    }
}
