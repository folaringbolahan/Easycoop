package com.sift.admin.service;

import java.util.List;

import com.sift.admin.model.AddressItems;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface AddressItemsService { 
	public void addAddressItems(AddressItems addressItems);
	//public void updateAddressItems(AddressItems addressItems);
	public List<AddressItems> listAddressItems(); 
	public AddressItems getAddressItems(int id); 
	public void deleteAddressItems(AddressItems addressItems);
}