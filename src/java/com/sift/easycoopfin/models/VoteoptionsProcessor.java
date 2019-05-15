
package com.sift.easycoopfin.models;

import org.orm.PersistentException;
public class VoteoptionsProcessor {
	private int id;
	
	private int voteId;
	
	private String voteOption;
	
	private String action="";
	
	public void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	
	public void setVoteId(int value) {
		this.voteId = value;
	}
	
	public int getVoteId() {
		return voteId;
	}
	
	public void setVoteOption(String value) {
		this.voteOption = value;
	}
	
	public String getVoteOption() {
		return voteOption == null ? "" : voteOption;
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
				com.sift.easycoopfin.models.Voteoptions _voteoptions = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteoptionsDAO().loadVoteoptionsByORMID(getId());
				if (_voteoptions!= null) {
					copyFromBean(_voteoptions);
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
				com.sift.easycoopfin.models.Voteoptions _voteoptions = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteoptionsDAO().createVoteoptions();
				copyToBean(_voteoptions);
				if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteoptionsDAO().save(_voteoptions)) {
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
				com.sift.easycoopfin.models.Voteoptions _voteoptions= com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteoptionsDAO().loadVoteoptionsByORMID(getId());
				if (_voteoptions != null) {
					copyToBean(_voteoptions);
					if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteoptionsDAO().save(_voteoptions)) {
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
				com.sift.easycoopfin.models.Voteoptions _voteoptions = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteoptionsDAO().loadVoteoptionsByORMID(getId());
				if (_voteoptions != null && com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteoptionsDAO().delete(_voteoptions)) {
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
	
	private void copyFromBean(com.sift.easycoopfin.models.Voteoptions _voteoptions) {
		setVoteId(_voteoptions.getVoteId());
		setVoteOption(_voteoptions.getVoteOption());
		setId(_voteoptions.getORMID());
	}
	
	private void copyToBean(com.sift.easycoopfin.models.Voteoptions _voteoptions) {
		_voteoptions.setVoteId(getVoteId());
		_voteoptions.setVoteOption(getVoteOption());
	}
	
}

