package com.sift.admin.dao;

import java.util.List;

import com.sift.admin.model.AddressType;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface AddressTypeDao{
	 public void addAddressType(AddressType addDetails);
	 public List<AddressType> listAddressType();
	 public AddressType getAddressType(int id);
	 public void deleteAddressType(AddressType addDetails);
}