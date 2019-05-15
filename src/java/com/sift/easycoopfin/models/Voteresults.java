
package com.sift.easycoopfin.models;

import java.io.Serializable;
public class Voteresults implements Serializable {
	public Voteresults() {
	}
	
	private int id;
	
	private int voteId;
	
	private int memberId;
	
	private int voteOptionId;
        
       private String memberName;
	
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
	public String toString() {
		return String.valueOf(getId());
	}
	
}
