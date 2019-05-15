
package com.sift.easycoopfin.models;

import org.orm.PersistentException;
public class SavingsProcessor {
	private int id;
	
	private int companyId;
	
	private int branchId;
	
	private String accountNumber;
	
	private int memberId;
	
	private float amount;
	
	private String description;
	
	private String userId;
	
	private String referenceNumber;
	
	private java.util.Date trxDate;
	
	private String action="";
	
	public void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
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
		return accountNumber == null ? "" : accountNumber;
	}
	
	public void setMemberId(int value) {
		this.memberId = value;
	}
	
	public int getMemberId() {
		return memberId;
	}
	
	public void setAmount(float value) {
		this.amount = value;
	}
	
	public float getAmount() {
		return amount;
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
	
	public void setReferenceNumber(String value) {
		this.referenceNumber = value;
	}
	
	public String getReferenceNumber() {
		return referenceNumber == null ? "" : referenceNumber;
	}
	
	public void setTrxDate(java.util.Date value) {
		this.trxDate = value;
	}
	
	public java.util.Date getTrxDate() {
		return trxDate;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String process() {
		String result = "Unexcepted result";
		if (action.equals("search")) {
			try {
				com.sift.easycoopfin.models.Savings _savings = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().loadSavingsByORMID(getId());
				if (_savings!= null) {
					copyFromBean(_savings);
					result = "Search success";
				}
				else {
					result = "Search failed";
				}
			}
			catch (PersistentException e) {
				result = "Search error: " + e.toString();
			}
		}
		else if(action.equals("insert"))  {
			try {
				com.sift.easycoopfin.models.Savings _savings = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().createSavings();
				copyToBean(_savings);
				if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().save(_savings)) {
					result = "Insert success";
				}
				else {
					result = "Insert failed";
				}
			}
			catch (Exception e) {
				result = "Insert error: " + e.toString();
			}
		}
		else if (action.equals("update")) {
			try {
				com.sift.easycoopfin.models.Savings _savings= com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().loadSavingsByORMID(getId());
				if (_savings != null) {
					copyToBean(_savings);
					if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().save(_savings)) {
						result = "Update success";
					}
					 else {
						result = "Update failed";
					}
				}
				 else  {
					result = "Update failed";
				}
				
			}
			catch (PersistentException e) {
				result = "Update error: " + e.toString();
			}
		}
		else if (action.equals("delete")) {
			try {
				com.sift.easycoopfin.models.Savings _savings = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().loadSavingsByORMID(getId());
				if (_savings != null && com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().delete(_savings)) {
					result = "Delete success";
				}
				else {
					result = "Delete failed";
				}
			}
			catch (PersistentException e)  {
				result = "Delete error: " + e.toString();
			}
		}
		else if (action.equals("")) {
			result = "";
		}
		action = "";
		return result;
	}
	
	private void copyFromBean(com.sift.easycoopfin.models.Savings _savings) {
		setCompanyId(_savings.getCompanyId());
		setBranchId(_savings.getBranchId());
		setAccountNumber(_savings.getAccountNumber());
		setMemberId(_savings.getMemberId());
		setAmount((float) _savings.getAmount());
		setDescription(_savings.getDescription());
		setUserId(_savings.getUserId());
		setReferenceNumber(_savings.getReferenceNumber());
		setTrxDate(_savings.getTrxDate());
		setId(_savings.getORMID());
	}
	
	private void copyToBean(com.sift.easycoopfin.models.Savings _savings) {
		_savings.setCompanyId(getCompanyId());
		_savings.setBranchId(getBranchId());
		_savings.setAccountNumber(getAccountNumber());
		_savings.setMemberId(getMemberId());
		_savings.setAmount(getAmount());
		_savings.setDescription(getDescription());
		_savings.setUserId(getUserId());
		_savings.setReferenceNumber(getReferenceNumber());
		_savings.setTrxDate(getTrxDate());
	}
	
}

