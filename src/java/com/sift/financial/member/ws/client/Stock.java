package com.sift.financial.member.ws.client;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Stock {
	
	private String type;
	
	private String qauntity;
	
	private String companyCocde;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getQauntity() {
		return qauntity;
	}

	public void setQauntity(String qauntity) {
		this.qauntity = qauntity;
	}

	public String getCompanyCocde() {
		return companyCocde;
	}

	public void setCompanyCocde(String companyCocde) {
		this.companyCocde = companyCocde;
	}

}
