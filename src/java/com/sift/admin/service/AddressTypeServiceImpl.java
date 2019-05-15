package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.AddressType;
import com.sift.admin.dao.AddressTypeDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("addressTypeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AddressTypeServiceImpl implements AddressTypeService{
	 @Autowired
	 private AddressTypeDao addressTypeDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addAddressType(AddressType addressType){
		 addressTypeDao.addAddressType(addressType);
	 }

	 public List<AddressType> listAddressType(){
	  return addressTypeDao.listAddressType();
	 }

	 public AddressType getAddressType(int id){
	  return addressTypeDao.getAddressType(id);
	 }

	 public void deleteAddressType(AddressType addressType){
		 addressTypeDao.deleteAddressType(addressType);
	 }
}