
package com.sift.easycoopfin.models;

import org.orm.PersistentException;
public class BranchProcessor {
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
				com.sift.easycoopfin.models.Branch _branch = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getBranchDAO().loadBranchByORMID(getId());
				if (_branch!= null) {
					copyFromBean(_branch);
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
				com.sift.easycoopfin.models.Branch _branch = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getBranchDAO().createBranch();
				copyToBean(_branch);
				if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getBranchDAO().save(_branch)) {
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
				com.sift.easycoopfin.models.Branch _branch= com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getBranchDAO().loadBranchByORMID(getId());
				if (_branch != null) {
					copyToBean(_branch);
					if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getBranchDAO().save(_branch)) {
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
				com.sift.easycoopfin.models.Branch _branch = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getBranchDAO().loadBranchByORMID(getId());
				if (_branch != null && com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getBranchDAO().delete(_branch)) {
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
	
	private void copyFromBean(com.sift.easycoopfin.models.Branch _branch) {
		setCompanyId(_branch.getCompanyId());
		setId(_branch.getORMID());
	}
	
	private void copyToBean(com.sift.easycoopfin.models.Branch _branch) {
		_branch.setCompanyId(getCompanyId());
	}
	
}

