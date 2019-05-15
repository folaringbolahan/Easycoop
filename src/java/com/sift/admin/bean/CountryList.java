package com.sift.admin.bean;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import com.sift.admin.bean.AddressEntriesBean;

@XmlRootElement(name = "CountryList")
public class CountryList{
	    @XmlElement(name = "Country")
	    public List<CountryBean> country;
	    public List<CountryBean> getCountries() {
	        if (country == null) {
	        	country = new ArrayList<CountryBean>();
	        }
	        return this.country;
	    }

}
