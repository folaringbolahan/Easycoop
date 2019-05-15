
package com.sift.easycoopfin.models;

import org.orm.PersistentException;
public class AgmsProcessor {
	private int id;
	
	private int companyId;
	
	private java.util.Date startDate;
	
	private java.util.Date endDate;
	
	private byte isDeleted;
	
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
	
	public void setStartDate(java.util.Date value) {
		this.startDate = value;
	}
	
	public java.util.Date getStartDate() {
		return startDate;
	}
	
	public void setEndDate(java.util.Date value) {
		this.endDate = value;
	}
	
	public java.util.Date getEndDate() {
		return endDate;
	}
	
	public void setIsDeleted(byte value) {
		this.isDeleted = value;
	}
	
	public byte getIsDeleted() {
		return isDeleted;
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
				com.sift.easycoopfin.models.Agms _agms = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmsDAO().loadAgmsByORMID(getId());
				if (_agms!= null) {
					copyFromBean(_agms);
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
				com.sift.easycoopfin.models.Agms _agms = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmsDAO().createAgms();
				copyToBean(_agms);
				if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmsDAO().save(_agms)) {
					result = "Insert success";
				}
				else {
					result = "Insert operation failed";
				}
			}
			catch (Exception e) {
				result = "Insert error: " + e.toString();
			}
		}
		else if (action.equals("update")) {
			try {
				com.sift.easycoopfin.models.Agms _agms= com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmsDAO().loadAgmsByORMID(getId());
				if (_agms != null) {
					copyToBean(_agms);
					if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmsDAO().save(_agms)) {
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
				com.sift.easycoopfin.models.Agms _agms = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmsDAO().loadAgmsByORMID(getId());
				if (_agms != null && com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmsDAO().delete(_agms)) {
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
	
	private void copyFromBean(com.sift.easycoopfin.models.Agms _agms) {
		setCompanyId(_agms.getCompanyId());
		setStartDate(_agms.getStartDate());
		setEndDate(_agms.getEndDate());
		setIsDeleted(_agms.getIsDeleted());
		setId(_agms.getORMID());
	}
	
	private void copyToBean(com.sift.easycoopfin.models.Agms _agms) {
		_agms.setCompanyId(getCompanyId());
		_agms.setStartDate(getStartDate());
		_agms.setEndDate(getEndDate());
		_agms.setIsDeleted(getIsDeleted());
	}
	
}

