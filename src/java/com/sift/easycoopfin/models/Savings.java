
package com.sift.easycoopfin.models;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Savings implements Serializable {
	public Savings() {
	}
	private int id;
	
	private int companyId;
	
	private int branchId;
	
	private String accountNumber;
	
	private int memberId;
	
	//private float amount;
        private double amount;
	
	private String description;
	
	private String userId;
        
        private String checkerId;
        
        private String trxType;
	
	private String referenceNumber;
	
	private java.util.Date trxDate;
	
        private int productId;
        
        private String stringDate;
        
        private byte status = 0;
        
        private byte isProcessed = 0;
        
        private String verifierId;
        
        private byte isApproved = 0;
        private byte isBatch = 0;
        private String approvedBy;
        
        private Accountnameobj accountNameobj;
        
	private void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	
	public int getORMID() {
		return getId();
	}
	
        public void setProductId(int value){
            this.productId = value;
        }
        public int getProductId() {
		return this.productId ;
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
	
	public void setAccountNumber(String value) {
		this.accountNumber = value;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setMemberId(int value) {
		this.memberId = value;
	}
	public String getStringDate() {
		return stringDate;
	}
	
	public void setStringDate(String value) {
		this.stringDate = value;
	}
	public int getMemberId() {
		return memberId;
	}
	
	public void setAmount(double value) {
		this.amount = value;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setDescription(int value) {
		setDescription(new Integer(value));
	}
	
	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setUserId(String value) {
		this.userId = value;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setCheckerId(String value) {
		this.checkerId = value;
	}
	
	public String getCheckerId() {
		return checkerId;
	}
        public String getVerifierId(){
            return verifierId;
        }
        public void setVerifierId(String value){
            this.verifierId = value;
        }
        public void setTrxType(String value) {
		this.trxType = value;
	}
	
	public String getTrxType() {
		return trxType;
	}
	public void setReferenceNumber(String value) {
		this.referenceNumber = value;
	}
	
	public String getReferenceNumber() {
		return referenceNumber;
	}
	
	public void setTrxDate(java.util.Date value) {
		this.trxDate = value;
	}
	public byte getStatus(){
            return status;
        }
        public void setStatus(byte value){
            this.status = value;
        }
        public byte getIsProcessed(){
            return isProcessed;
        }
        public void setIsProcessed(byte value){
            this.isProcessed = value;
        }
	public java.util.Date getTrxDate() {
		return trxDate;
	}
	public void setIsApproved(byte value){
            this.isApproved = value;
        }
        public byte getIsApproved(){
            return isApproved;
        }
	public String toString() {
		return String.valueOf(getId());
	}

    /**
     * @return the isBatch
     */
    public byte getIsBatch() {
        return isBatch;
    }

    /**
     * @param isBatch the isBatch to set
     */
    public void setIsBatch(byte isBatch) {
        this.isBatch = isBatch;
    }

    /**
     * @return the approvedBy
     */
    public String getApprovedBy() {
        return approvedBy;
    }

    /**
     * @param approvedBy the approvedBy to set
     */
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
    public void setAccountNameobj(Accountnameobj value) {
		this.accountNameobj = value;
	}
	
	public Accountnameobj getAccountNameobj() {
		return accountNameobj;
	} 
   
}
