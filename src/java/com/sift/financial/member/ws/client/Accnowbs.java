package com.sift.financial.member.ws.client;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Accnowbs {
	
	private String productcode;
	private String customerno;
	private String subno;
	private String branchcode;
	private String branchId;
	private String company;
	private String companycode;
        //@XmlElement(name = "timezone")
        private String timezone;
	
	public String getProductcode() {
		return productcode;
	}
	
	@XmlElement
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getCustomerno() {
		return customerno;
	}
	
	@XmlElement
	public void setCustomerno(String customerno) {
		this.customerno = customerno;
	}
	
	public String getSubno() {
		return subno;
	}
	
	@XmlElement
	public void setSubno(String subno) {
		this.subno = subno;
	}
	
	public String getBranchcode() {
		return branchcode;
	}
	
	@XmlElement
	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}
	
	
	public String getBranchId() {
		return branchId;
	}
	
	@XmlElement
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	public String getCompany() {
		return company;
	}
	@XmlElement
	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanycode() {
		return companycode;
	}
@XmlElement
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

    public String getTimezone() {
        return timezone;
    }
@XmlElement
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
        
        

}
