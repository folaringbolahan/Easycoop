
package com.sift.easycoopfin.models;

import org.orm.PersistentException;
public class CompanyProcessor {
	private int id;
	
	private int companyId;
	
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
				com.sift.easycoopfin.models.Company _company = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCompanyDAO().loadCompanyByORMID(getId());
				if (_company!= null) {
					copyFromBean(_company);
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
				com.sift.easycoopfin.models.Company _company = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCompanyDAO().createCompany();
				copyToBean(_company);
				if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCompanyDAO().save(_company)) {
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
				com.sift.easycoopfin.models.Company _company= com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCompanyDAO().loadCompanyByORMID(getId());
				if (_company != null) {
					copyToBean(_company);
					if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCompanyDAO().save(_company)) {
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
				com.sift.easycoopfin.models.Company _company = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCompanyDAO().loadCompanyByORMID(getId());
				if (_company != null && com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCompanyDAO().delete(_company)) {
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
	
	private void copyFromBean(com.sift.easycoopfin.models.Company _company) {
		setCompanyId(_company.getCompanyId());
		setId(_company.getORMID());
	}
	
	private void copyToBean(com.sift.easycoopfin.models.Company _company) {
		_company.setCompanyId(getCompanyId());
	}
	
}

