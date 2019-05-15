package com.sift.admin.dao;

import java.util.List;

import com.sift.admin.model.AddressItems;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface AddressItemsDao{
	 public void addAddressItems(AddressItems addDetails);
	 public void updateAddressItems(AddressItems addDetails);
	 public List<AddressItems> listAddressItems();
	 public AddressItems getAddressItems(int id);
	 public void deleteAddressItems(AddressItems addDetails);
}