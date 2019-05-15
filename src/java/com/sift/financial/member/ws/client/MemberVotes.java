package com.sift.financial.member.ws.client;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class MemberVotes {
	
	private String companyCode;
	
	private List <MemberVote> memberVote;

	public String getCompanyCode() {
		return companyCode;
	}

	@XmlAttribute
	public void setCompanyCode(String companyCode) 
	{
		this.companyCode = companyCode;
	}

	public List<MemberVote> getMemberVote() {
		return memberVote;
	}
	
	@XmlElement(name= "Votes")
	public void setMemberVote(List<MemberVote> memberVote) {
		this.memberVote = memberVote;
	}

	
}
