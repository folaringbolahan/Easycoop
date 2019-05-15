package com.sift.admin.bean;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import com.sift.admin.bean.AddressEntriesBean;

@XmlRootElement(name = "AddressEntriesList")
public class AddressEntriesList{
	    @XmlElement(name = "AddressEntry")
	    public List<AddressEntriesBean> addressEntry;
	    public List<AddressEntriesBean> getAddressEntries() {
	        if (addressEntry == null) {
	        	addressEntry = new ArrayList<AddressEntriesBean>();
	        }
	        return this.addressEntry;
	    }

}
