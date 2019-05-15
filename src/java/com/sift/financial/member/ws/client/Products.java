package com.sift.financial.member.ws.client;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Products implements Serializable {

    public Products() {
    }
    private int id;
    private String code;
    
    private String name;
    private String segmentCode;
    private int companyId;
   
    private float interestRate;
    private int branchId;
    private String productTypeCode;
 
    private int hasInterest = 0;
    private byte isDefault = 0;
    private byte isTaxable = 0;
    private int currencyId;
    private int productId;
    private String controlAccount;
    private Double penalty=0.0;
    
    private int hasPenalty =0;
    private float initialAmountMax = 0f;
    private float initialAmountMin = 0f;
    private float interestRateMin = 0f;
    private float interestRateMax = 0f;
    
 

    private void setId(int value) {
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

    public void setInterestRate(float value) {
        this.interestRate = value;
    }

    public float getInterestRate() {
        return interestRate;
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

    public void setHasInterest(int value) {
        this.hasInterest = value;
    }

    public int getHasInterest() {
        return hasInterest;
    }

    public void setIsTaxable(byte value) {
        this.isTaxable = value;
    }

    public byte getIsTaxable() {
        return isTaxable;
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

    public int getCurrencyId() {
        return currencyId;
    }

    public void setBranchId(int value) {
        this.branchId = value;
    }

    public String getControlAccount() {
        return controlAccount;
    }

    public void setControlAccount(String controlAccount) {
        this.controlAccount = controlAccount;
    }
    
    public String toString() {
        return String.valueOf(getId());
    }

    public Double getPenalty() {
        return penalty;
    }

    public void setPenalty(Double penalty) {
        this.penalty = penalty;
    }

    public int getHasPenalty() {
        return hasPenalty;
    }

    public void setHasPenalty(int hasPenalty) {
        this.hasPenalty = hasPenalty;
    }

    public float getInitialAmountMax() {
        return initialAmountMax;
    }

    public void setInitialAmountMax(float initialAmountMax) {
        this.initialAmountMax = initialAmountMax;
    }

    public float getInitialAmountMin() {
        return initialAmountMin;
    }

    public void setInitialAmountMin(float initialAmountMin) {
        this.initialAmountMin = initialAmountMin;
    }

    public float getInterestRateMin() {
        return interestRateMin;
    }

    public void setInterestRateMin(float interestRateMin) {
        this.interestRateMin = interestRateMin;
    }

    public float getInterestRateMax() {
        return interestRateMax;
    }

    public void setInterestRateMax(float interestRateMax) {
        this.interestRateMax = interestRateMax;
    }
   
}
