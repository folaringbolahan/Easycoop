
package com.sift.easycoopfin.models;

import org.orm.PersistentException;
public class ProductTypeProcessor {
	private int id;
	
	private String name;
	
	private byte attractsInterest = 0;
	
	private String action="";
	
	public void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name == null ? "" : name;
	}
	
	public void setAttractsInterest(byte value) {
		this.attractsInterest = value;
	}
	
	public byte getAttractsInterest() {
		return attractsInterest;
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
				com.sift.easycoopfin.models.ProductType _productType = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductTypeDAO().loadProductTypeByORMID(getId());
				if (_productType!= null) {
					copyFromBean(_productType);
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
				com.sift.easycoopfin.models.ProductType _productType = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductTypeDAO().createProductType();
				copyToBean(_productType);
				if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductTypeDAO().save(_productType)) {
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
				com.sift.easycoopfin.models.ProductType _productType= com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductTypeDAO().loadProductTypeByORMID(getId());
				if (_productType != null) {
					copyToBean(_productType);
					if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductTypeDAO().save(_productType)) {
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
				com.sift.easycoopfin.models.ProductType _productType = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductTypeDAO().loadProductTypeByORMID(getId());
				if (_productType != null && com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductTypeDAO().delete(_productType)) {
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
	
	private void copyFromBean(com.sift.easycoopfin.models.ProductType _productType) {
		setName(_productType.getName());
		setAttractsInterest(_productType.getAttractsInterest());
		setId(_productType.getORMID());
	}
	
	private void copyToBean(com.sift.easycoopfin.models.ProductType _productType) {
		_productType.setName(getName());
		_productType.setAttractsInterest(getAttractsInterest());
	}
	
}

