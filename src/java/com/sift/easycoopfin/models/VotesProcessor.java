
package com.sift.easycoopfin.models;

import org.orm.PersistentException;
public class VotesProcessor {
	private int id;
	
	private int companyId;
	
          private int agmId;
	private String description;
	
	private String title;
	
	private java.util.Date voteDate;
	
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
	
	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return description == null ? "" : description;
	}
	
	public void setTitle(String value) {
		this.title = value;
	}
	
	public String getTitle() {
		return title == null ? "" : title;
	}
	
	public void setVoteDate(java.util.Date value) {
		this.voteDate = value;
	}
	
	public java.util.Date getVoteDate() {
		return voteDate;
	}
        
        public int getAgmId() {
		return agmId;
	}
	public void setAgmId(int value) {
		this.agmId = value;
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
				com.sift.easycoopfin.models.Votes _votes = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVotesDAO().loadVotesByORMID(getId());
				if (_votes!= null) {
					copyFromBean(_votes);
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
				com.sift.easycoopfin.models.Votes _votes = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVotesDAO().createVotes();
				copyToBean(_votes);
				if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVotesDAO().save(_votes)) {
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
				com.sift.easycoopfin.models.Votes _votes= com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVotesDAO().loadVotesByORMID(getId());
				if (_votes != null) {
					copyToBean(_votes);
					if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVotesDAO().save(_votes)) {
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
				com.sift.easycoopfin.models.Votes _votes = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVotesDAO().loadVotesByORMID(getId());
				if (_votes != null && com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVotesDAO().delete(_votes)) {
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
	
	private void copyFromBean(com.sift.easycoopfin.models.Votes _votes) {
		setCompanyId(_votes.getCompanyId());
		setDescription(_votes.getDescription());
		setTitle(_votes.getTitle());
		setVoteDate(_votes.getVoteDate());
		setId(_votes.getORMID());
	}
	
	private void copyToBean(com.sift.easycoopfin.models.Votes _votes) {
		_votes.setCompanyId(getCompanyId());
		_votes.setDescription(getDescription());
		_votes.setTitle(getTitle());
		_votes.setVoteDate(getVoteDate());
	}
	
}

