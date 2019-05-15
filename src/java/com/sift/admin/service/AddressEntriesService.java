package com.sift.admin.service;

import java.util.List;
import com.sift.admin.model.AddressEntries;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface AddressEntriesService{ 
	public void addAddressEntries(AddressEntries addressEntries);
	public List<AddressEntries> listAddressEntries();
	public List<AddressEntries> listAddressEntries(String keyId,String typeId); 
	public AddressEntries getAddressEntries(int id); 
	public void deleteAddressEntries(AddressEntries addressEntries);
}