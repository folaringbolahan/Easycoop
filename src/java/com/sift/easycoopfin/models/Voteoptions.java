
package com.sift.easycoopfin.models;

import java.io.Serializable;
public class Voteoptions implements Serializable {
	public Voteoptions() {
	}
	
	private int id;
	
	private int voteId;
	
	private String voteOption;
        
        
	
	private void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	
	public int getORMID() {
		return getId();
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
		return voteOption;
	}
	
	public String toString() {
		return String.valueOf(getId());
	}
	
}
