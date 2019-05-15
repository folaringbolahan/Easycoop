
package com.sift.easycoopfin.models;

import org.orm.PersistentException;
public class VoteresultsProcessor {
	private int id;
	
	private int voteId;
	
	private int memberId;
	
	private int voteOptionId;
        
        private String memberName;
	
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
	
	public void setMemberId(int value) {
		this.memberId = value;
	}
	
	public int getMemberId() {
		return memberId;
	}
	
	public void setVoteOptionId(int value) {
		this.voteOptionId = value;
	}
	
	public int getVoteOptionId() {
		return voteOptionId;
	}
	public void setMemberName(String value){
            this.memberName = value;
        }
        public String getMemberName(){
            return memberName;
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
				com.sift.easycoopfin.models.Voteresults _voteresults = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteresultsDAO().loadVoteresultsByORMID(getId());
				if (_voteresults!= null) {
					copyFromBean(_voteresults);
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
				com.sift.easycoopfin.models.Voteresults _voteresults = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteresultsDAO().createVoteresults();
				copyToBean(_voteresults);
				if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteresultsDAO().save(_voteresults)) {
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
				com.sift.easycoopfin.models.Voteresults _voteresults= com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteresultsDAO().loadVoteresultsByORMID(getId());
				if (_voteresults != null) {
					copyToBean(_voteresults);
					if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteresultsDAO().save(_voteresults)) {
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
				com.sift.easycoopfin.models.Voteresults _voteresults = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteresultsDAO().loadVoteresultsByORMID(getId());
				if (_voteresults != null && com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteresultsDAO().delete(_voteresults)) {
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
	
	private void copyFromBean(com.sift.easycoopfin.models.Voteresults _voteresults) {
		setVoteId(_voteresults.getVoteId());
		setMemberId(_voteresults.getMemberId());
		setVoteOptionId(_voteresults.getVoteOptionId());
		setId(_voteresults.getORMID());
	}
	
	private void copyToBean(com.sift.easycoopfin.models.Voteresults _voteresults) {
		_voteresults.setVoteId(getVoteId());
		_voteresults.setMemberId(getMemberId());
		_voteresults.setVoteOptionId(getVoteOptionId());
	}
	
}

