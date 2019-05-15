
package com.sift.easycoopfin.models;

import org.orm.PersistentException;
public class ProductAccountTypeProcessor {
	private int id;
	
	private String code;
	
	private String description;
	
	private String action="";
	
	public void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	
	public void setCode(String value) {
		this.code = value;
	}
	
	public String getCode() {
		return code == null ? "" : code;
	}
	
	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return description == null ? "" : description;
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
				com.sift.easycoopfin.models.ProductAccountType _productAccountType = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountTypeDAO().loadProductAccountTypeByORMID(getId());
				if (_productAccountType!= null) {
					copyFromBean(_productAccountType);
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
				com.sift.easycoopfin.models.ProductAccountType _productAccountType = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountTypeDAO().createProductAccountType();
				copyToBean(_productAccountType);
				if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountTypeDAO().save(_productAccountType)) {
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
				com.sift.easycoopfin.models.ProductAccountType _productAccountType= com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountTypeDAO().loadProductAccountTypeByORMID(getId());
				if (_productAccountType != null) {
					copyToBean(_productAccountType);
					if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountTypeDAO().save(_productAccountType)) {
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
				com.sift.easycoopfin.models.ProductAccountType _productAccountType = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountTypeDAO().loadProductAccountTypeByORMID(getId());
				if (_productAccountType != null && com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountTypeDAO().delete(_productAccountType)) {
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
	
	private void copyFromBean(com.sift.easycoopfin.models.ProductAccountType _productAccountType) {
		setCode(_productAccountType.getCode());
		setDescription(_productAccountType.getDescription());
		setId(_productAccountType.getORMID());
	}
	
	private void copyToBean(com.sift.easycoopfin.models.ProductAccountType _productAccountType) {
		_productAccountType.setCode(getCode());
		_productAccountType.setDescription(getDescription());
	}
	
}

