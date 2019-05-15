package com.sift.financial.member.ws.client;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Stocks {
	
	
	private String companyCocde;
	
	private List<Stocks> stocks;

	public List<Stocks> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stocks> stocks) {
		this.stocks = stocks;
	}

	public String getCompanyCocde() {
		return companyCocde;
	}

	public void setCompanyCocde(String companyCocde) {
		this.companyCocde = companyCocde;
	}

}
