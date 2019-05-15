package com.sift.admin.service;

import java.util.List;

import com.sift.admin.model.AddressType;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface AddressTypeService { 
	public void addAddressType(AddressType addressType);
	public List<AddressType> listAddressType(); 
	public AddressType getAddressType(int id); 
	public void deleteAddressType(AddressType addressType);
}