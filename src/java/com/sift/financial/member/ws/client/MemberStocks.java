package com.sift.financial.member.ws.client;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MemberStocks {
	
	private String companyCode;
	
	private List <MemberStock> memberStocks;

	public String getCompanyCode() {
		return companyCode;
	}
	
	@XmlAttribute
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public List<MemberStock> getMemberStocks() {
		return memberStocks;
	}
	@XmlElement
	public void setMemberStocks(List<MemberStock> memberStocks) {
		this.memberStocks = memberStocks;
	}

}
