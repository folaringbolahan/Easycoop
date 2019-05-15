package com.sift.admin.dao;

import java.util.List;

import com.sift.admin.model.AddressEntries;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface AddressEntriesDao{
	 public void addAddressEntries(AddressEntries addDetails);
	 public List<AddressEntries> listAddressEntries();
	 public List<AddressEntries> listAddressEntries(String keyId,String typeId);
	 public AddressEntries getAddressEntries(int id);
	 public void updateAddressEntries(AddressEntries addDetails);
	 public void deleteAddressEntries(AddressEntries addDetails);
}