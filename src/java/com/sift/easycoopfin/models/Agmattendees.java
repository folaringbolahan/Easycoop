
package com.sift.easycoopfin.models;

import java.io.Serializable;
public class Agmattendees implements Serializable {
	public Agmattendees() {
	}
	
	private int id;
	
	private int memberId;
	
	private int agmsId;
	private String participantName;
	private void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	
	public int getORMID() {
		return getId();
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
	public void setParticipantName(String value) {
		this.participantName = value;
	}
	
	public String getParticipantName() {
		return participantName;
	}
	public String toString() {
		return String.valueOf(getId());
	}
	
}
