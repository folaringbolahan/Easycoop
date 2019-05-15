
package com.sift.easycoopfin.models;

import org.orm.PersistentException;
public class ProductAccountProcessor {
	private int id;
	
	private int productId;
	
	private String productAccountTypeCode;
	
	private String glAccountNumber;
        
        private int branchId;
private int companyId;
	
	private String action="";
	
	public void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
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
		return glAccountNumber == null ? "" : glAccountNumber;
	}
	
public void setCompanyId(int value) {
        this.companyId = value;
    }

    public int getCompanyId() {
        return companyId;
    }
 public int getBranchId(){
        return branchId;
    }
    public void setBranchId(int value){
        this.branchId = value;
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
				com.sift.easycoopfin.models.ProductAccount _productAccount = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().loadProductAccountByORMID(getId());
				if (_productAccount!= null) {
					copyFromBean(_productAccount);
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
				com.sift.easycoopfin.models.ProductAccount _productAccount = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().createProductAccount();
				copyToBean(_productAccount);
				if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().save(_productAccount)) {
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
				com.sift.easycoopfin.models.ProductAccount _productAccount= com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().loadProductAccountByORMID(getId());
				if (_productAccount != null) {
					copyToBean(_productAccount);
					if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().save(_productAccount)) {
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
				com.sift.easycoopfin.models.ProductAccount _productAccount = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().loadProductAccountByORMID(getId());
				if (_productAccount != null && com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().delete(_productAccount)) {
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
	
	private void copyFromBean(com.sift.easycoopfin.models.ProductAccount _productAccount) {
		setProductId(_productAccount.getProductId());
		setProductAccountTypeCode(_productAccount.getProductAccountTypeCode());
		setGlAccountNumber(_productAccount.getGlAccountNumber());
		setId(_productAccount.getORMID());
	}
	
	private void copyToBean(com.sift.easycoopfin.models.ProductAccount _productAccount) {
		_productAccount.setProductId(getProductId());
		_productAccount.setProductAccountTypeCode(getProductAccountTypeCode());
		_productAccount.setGlAccountNumber(getGlAccountNumber());
	}
	
}

