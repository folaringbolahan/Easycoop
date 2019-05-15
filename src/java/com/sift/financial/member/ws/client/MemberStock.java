package com.sift.financial.member.ws.client;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MemberStock {
	
	private String memberCode;
	private String memberName;
	private Stocks stocks;
	private String companyCode;
	
	
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
	public Stocks getStocks() {
		return stocks;
	}
	@XmlElement
	public void setStocks(Stocks stocks) {
		this.stocks = stocks;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	@XmlAttribute
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

}
