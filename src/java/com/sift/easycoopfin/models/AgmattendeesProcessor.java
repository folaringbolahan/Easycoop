
package com.sift.easycoopfin.models;

import org.orm.PersistentException;
public class AgmattendeesProcessor {
	private int id;
	
	private int memberId;
	
	private int agmsId;
	
	private String action="";
	
	public void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	
	public void setMemberId(int value) {
		this.memberId = value;
	}
	
	public int getMemberId() {
		return memberId;
	}
	
	public void setAgmsId(int value) {
		this.agmsId = value;
	}
	
	public int getAgmsId() {
		return agmsId;
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
				com.sift.easycoopfin.models.Agmattendees _agmattendees = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmattendeesDAO().loadAgmattendeesByORMID(getId());
				if (_agmattendees!= null) {
					copyFromBean(_agmattendees);
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
				com.sift.easycoopfin.models.Agmattendees _agmattendees = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmattendeesDAO().createAgmattendees();
				copyToBean(_agmattendees);
				if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmattendeesDAO().save(_agmattendees)) {
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
				com.sift.easycoopfin.models.Agmattendees _agmattendees= com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmattendeesDAO().loadAgmattendeesByORMID(getId());
				if (_agmattendees != null) {
					copyToBean(_agmattendees);
					if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmattendeesDAO().save(_agmattendees)) {
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
				com.sift.easycoopfin.models.Agmattendees _agmattendees = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmattendeesDAO().loadAgmattendeesByORMID(getId());
				if (_agmattendees != null && com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmattendeesDAO().delete(_agmattendees)) {
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
	
	private void copyFromBean(com.sift.easycoopfin.models.Agmattendees _agmattendees) {
		setMemberId(_agmattendees.getMemberId());
		setAgmsId(_agmattendees.getAgmsId());
		setId(_agmattendees.getORMID());
	}
	
	private void copyToBean(com.sift.easycoopfin.models.Agmattendees _agmattendees) {
		_agmattendees.setMemberId(getMemberId());
		_agmattendees.setAgmsId(getAgmsId());
	}
	
}

