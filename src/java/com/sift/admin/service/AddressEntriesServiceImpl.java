package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.AddressEntries;
import com.sift.admin.dao.AddressEntriesDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("addressEntriesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AddressEntriesServiceImpl implements AddressEntriesService{
	 @Autowired
	 private AddressEntriesDao addressEntriesDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addAddressEntries(AddressEntries addressEntries){
		 addressEntriesDao.addAddressEntries(addressEntries);
	 }

	 public List<AddressEntries> listAddressEntries(){
	  return addressEntriesDao.listAddressEntries();
	 }
	 
	 public List<AddressEntries> listAddressEntries(String keyId,String typeId){
	return addressEntriesDao.listAddressEntries(keyId,typeId);
	 }

	 public AddressEntries getAddressEntries(int id){
	  return addressEntriesDao.getAddressEntries(id);
	 }

	 public void deleteAddressEntries(AddressEntries addressEntries){
		 addressEntriesDao.deleteAddressEntries(addressEntries);
	 }
}