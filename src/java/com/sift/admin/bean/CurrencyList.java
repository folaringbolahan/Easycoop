package com.sift.admin.bean;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import com.sift.admin.bean.AddressEntriesBean;

@XmlRootElement(name = "CurrencyList")
public class CurrencyList{
	    @XmlElement(name = "Currency")
	    public List<CurrencyBean> currency;
	    public List<CurrencyBean> getCurrencies() {
	        if (currency == null) {
	        	currency = new ArrayList<CurrencyBean>();
	        }
	        return this.currency;
	    }

}
