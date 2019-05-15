package com.sift.financial.member.ws.client;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class MemberVote {
	
	private String memberCode;
	private String memberName;
	private String vote;
	private String companyCode;
	private String memberType;
	
	
	public String getMemberCode() {
		return memberCode;
	}
	@XmlElement
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getMemberName() {
		return memberName;
	}
	@XmlElement
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getVote() {
		return vote;
	}
	@XmlElement
	public void setVote(String vote) {
		this.vote = vote;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	
	@XmlAttribute
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getMemberType() {
		return memberType;
	}
	@XmlElement
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	
}
